package com.hitex.halago.service;

import com.hitex.halago.model.ContactCustomer;

import java.util.List;

public interface ContactCustomerService {
    List<ContactCustomer> getListContactCustomer(Integer pageSize, Integer pageNumber);
    ContactCustomer findCustomerContact(int id);

}
