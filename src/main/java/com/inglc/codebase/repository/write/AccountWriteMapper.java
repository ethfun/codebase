package com.inglc.codebase.repository.write;


import com.inglc.codebase.repository.entity.Account;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountWriteMapper {


	int insertAccount(Account account);

	List<Account> getAllAccount();

}
