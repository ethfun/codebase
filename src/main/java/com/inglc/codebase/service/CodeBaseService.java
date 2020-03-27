package com.inglc.codebase.service;

import com.inglc.codebase.repository.entity.Account;
import java.util.List;

/**
 * @author inglc
 * @date 2020/3/25
 */
public interface CodeBaseService {

	int insertAccountMaster(Account account);

	List<Account> getAllAccountMaster();

	List<Account> getAllAccountSlave();


}
