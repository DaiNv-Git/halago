package com.hitex.halago.repository;

import com.hitex.halago.model.logTransaction.LogTransactionBase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogTransactionRepository extends CrudRepository<LogTransactionBase,Integer> {
}
