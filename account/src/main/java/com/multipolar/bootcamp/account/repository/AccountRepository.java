package com.multipolar.bootcamp.account.repository;

import com.multipolar.bootcamp.account.domain.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
}
