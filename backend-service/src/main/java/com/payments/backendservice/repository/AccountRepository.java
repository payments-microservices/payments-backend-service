package com.payments.backendservice.repository;

import com.payments.backendservice.collection.Account;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
    Account findByUserName(String userName);
}
