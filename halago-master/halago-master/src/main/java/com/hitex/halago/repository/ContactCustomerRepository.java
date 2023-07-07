package com.hitex.halago.repository;

import com.hitex.halago.model.ContactCustomer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactCustomerRepository extends CrudRepository<ContactCustomer,String> {
    @Query("Select contact From ContactCustomer contact where contact.id=:id")
    ContactCustomer findContact(@Param("id") int id);

    @Query("Select count(contact) From ContactCustomer contact order by contact.created desc ")
    int totalContact();
}
