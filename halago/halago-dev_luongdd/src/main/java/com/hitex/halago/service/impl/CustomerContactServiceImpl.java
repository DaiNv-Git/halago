package com.hitex.halago.service.impl;

import com.hitex.halago.model.ContactCustomer;
import com.hitex.halago.repository.ContactCustomerRepository;
import com.hitex.halago.service.ContactCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Service
public class CustomerContactServiceImpl implements ContactCustomerService {
    Logger logger = LoggerFactory.getLogger(BookKolsImpl.class);
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ContactCustomerRepository contactCustomerRepository;
    @Override
    public List<ContactCustomer> getListContactCustomer(Integer pageSize, Integer pageNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append("Select portal  ");
        builder.append("from ContactCustomer portal order by created desc ");
        Query query = entityManager.createQuery(builder.toString(),ContactCustomer.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        List<ContactCustomer> contactCustomers = query.getResultList();
        return contactCustomers;
    }

    @Override
    public ContactCustomer findCustomerContact(int id) {
        ContactCustomer contactCustomer = contactCustomerRepository.findContact(id);
        return contactCustomer;
    }
}
