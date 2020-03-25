package com.inglc.codebase.service.impl;

import com.inglc.codebase.repository.entity.Account;
import com.inglc.codebase.repository.read.AccountReadMapper;
import com.inglc.codebase.repository.write.AccountWriteMapper;
import com.inglc.codebase.service.CodeBaseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author inglc
 * @date 2020/3/25
 */
@Service
public class CodeBaseServiceImpl implements CodeBaseService {


	@Autowired
	private AccountReadMapper accountReadMapper;
	@Autowired
	private AccountWriteMapper accountWriteMapper;


	@Override
	public int insertAccountMaster(Account account) {
		return accountWriteMapper.insertAccount(account);
	}

	public List<Account> getAllAccountMaster(){
		return accountWriteMapper.getAllAccount();
	}

	public List<Account> getAllAccountSlave(){
		return accountReadMapper.getAllAccount();
	}
}
