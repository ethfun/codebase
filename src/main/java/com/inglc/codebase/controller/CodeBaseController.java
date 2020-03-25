package com.inglc.codebase.controller;

import com.inglc.codebase.repository.entity.Account;
import com.inglc.codebase.service.CodeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author inglc
 * @date 2020/3/25
 */

@RequestMapping("/account")
@RestController
public class CodeBaseController {

	@Autowired
	private CodeBaseService codeBaseService;

	@GetMapping("/read/list")
	public ResponseEntity getAll(){
		return ResponseEntity.ok(codeBaseService.getAllAccountSlave());
	}

	@PostMapping("/write/save")
	public ResponseEntity save(){
		Account account = new Account();
		account.setName("wooha");
		account.setPassword("wohapassword");
		account.setAddress("woha Road");
		account.setEmail("wooha@gmail.com");
		account.setCountry("USA");
		account.setPhone("123-234-3456");
		return ResponseEntity.ok(codeBaseService.insertAccountMaster(account));
	}


	@GetMapping("/write/list")
	public ResponseEntity getAllWrite(){
		return ResponseEntity.ok(codeBaseService.getAllAccountMaster());
	}



}
