package com.agacostays.billing.storage;import com.agacostays.billing.entity.Bill;public interface InvoiceStorageService{String store(Bill b,String n);byte[] read(String n);}
