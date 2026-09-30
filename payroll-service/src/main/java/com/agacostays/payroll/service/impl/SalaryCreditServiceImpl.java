package com.agacostays.payroll.service.impl;

import com.agacostays.payroll.audit.*;
import com.agacostays.payroll.dto.response.SalaryCreditResponse;
import com.agacostays.payroll.entity.*;
import com.agacostays.payroll.enums.*;
import com.agacostays.payroll.event.*;
import com.agacostays.payroll.event.producer.*;
import com.agacostays.payroll.exception.*;
import com.agacostays.payroll.repository.*;
import com.agacostays.payroll.service.*;
import com.agacostays.payroll.validation.*;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.*;

@Service
public class SalaryCreditServiceImpl implements SalaryCreditService {
	final PayrollRepository repo;
	final StaffBankAccountRepository bank;
	final PayrollValidationService val;
	final PayrollEventProducer prod;
	final PayrollAuditHelper audit;

	public SalaryCreditServiceImpl(PayrollRepository r, StaffBankAccountRepository b, PayrollValidationService v,
			PayrollEventProducer p, PayrollAuditHelper a) {
		repo = r;
		bank = b;
		val = v;
		prod = p;
		audit = a;
	}

	@Transactional
	public SalaryCreditResponse credit(Long id, String mode, Long actor) {
		Payroll p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payroll not found: " + id));
		val.beforeCredit(p);
		PaymentMode pm = parse(mode);
		if (pm == PaymentMode.BANK_TRANSFER && bank.findByStaffId(p.getStaffId()).isEmpty())
			throw new BusinessRuleException("Active bank account required for bank transfer");
		String tx = "SAL-" + id + "-" + System.currentTimeMillis();
		p.setPaymentMode(pm);
		p.setPaymentStatus(PayrollStatus.PAID);
		p.setTransactionId(tx);
		p.setPaidAt(OffsetDateTime.now());
		repo.save(p);
		prod.credited(new SalaryCreditedEvent(id, p.getStaffId(), p.getNetSalary(), tx));
		audit.record(new AuditLogRequest("SALARY_CREDITED", actor, p.getBranchId(), id, "transactionId=" + tx));
		return new SalaryCreditResponse(id, p.getStaffId(), p.getNetSalary(), p.getPaymentStatus().name(), tx,
				"Salary credited");
	}

	public SalaryCreditResponse retry(Long id, Long actor) {
		Payroll p = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Payroll not found: " + id));
		if (p.getPaymentStatus() != PayrollStatus.FAILED)
			throw new BusinessRuleException("Retry allowed only for failed payment");
		return credit(id, p.getPaymentMode() == null ? PaymentMode.BANK_TRANSFER.name() : p.getPaymentMode().name(),
				actor);
	}

	@Transactional
	public java.util.List<SalaryCreditResponse> creditAll(int m, int y, String mode, Long actor) {
		return repo.findByMonthAndYear(m, y).stream().filter(p -> p.getPaymentStatus() != PayrollStatus.PAID)
				.map(p -> credit(p.getPayrollId(), mode, actor)).toList();
	}

	private PaymentMode parse(String s) {
		try {
			return PaymentMode.valueOf((s == null ? "BANK_TRANSFER" : s).toUpperCase());
		} catch (Exception e) {
			throw new BusinessRuleException("Invalid payment mode");
		}
	}
}
