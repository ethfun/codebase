package com.inglc.codebase.repository.read;

import com.inglc.codebase.repository.entity.Account;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountReadMapper {

	List<Account> getAllAccount();
}
