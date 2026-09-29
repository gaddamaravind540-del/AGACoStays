package com.agacostays.payroll.storage.impl;
import com.agacostays.payroll.entity.Payroll; import com.agacostays.payroll.exception.ResourceNotFoundException; import com.agacostays.payroll.storage.SalarySlipStorageService; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import java.nio.charset.StandardCharsets; import java.nio.file.*;
@Service public class SalarySlipStorageServiceImpl implements SalarySlipStorageService{private final Path dir;public SalarySlipStorageServiceImpl(@Value("${payroll.storage.path}")String p){dir=Paths.get(p);}public String store(Payroll p,String key){try{Files.createDirectories(dir);String t="""
AGA CoStays Salary Slip
Payroll ID: %s
Staff ID: %s
Period: %02d/%d
Basic Salary: %s
HRA: %s
Allowances: %s
Bonus: %s
Gross Salary: %s
Attendance Deduction: %s
Other Deductions: %s
Net Salary: %s
Payment Status: %s
Transaction ID: %s
""".formatted(p.getPayrollId(),p.getStaffId(),p.getMonth(),p.getYear(),p.getBasicSalary(),p.getHra(),p.getAllowances(),p.getBonus(),p.getGrossSalary(),p.getAttendanceDeduction(),p.getOtherDeductions(),p.getNetSalary(),p.getPaymentStatus(),p.getTransactionId());Files.writeString(dir.resolve(key+".txt"),t,StandardCharsets.UTF_8,StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING);return "/api/payroll/"+p.getPayrollId()+"/salary-slip";}catch(Exception e){throw new IllegalStateException("Unable to store salary slip",e);}}public byte[] read(String key){try{Path p=dir.resolve(key+".txt");if(!Files.exists(p))throw new ResourceNotFoundException("Salary slip file not found");return Files.readAllBytes(p);}catch(ResourceNotFoundException e){throw e;}catch(Exception e){throw new IllegalStateException("Unable to read salary slip",e);}}}
