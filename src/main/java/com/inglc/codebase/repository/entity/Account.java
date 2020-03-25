package com.inglc.codebase.repository.entity;

import java.io.Serializable;
import java.math.BigInteger;
import lombok.Data;

/**
 * @author inglc
 * @date 2020/3/25
 */
@Data
public class Account implements Serializable {

	private BigInteger userId;
	private String name;
	private String password;
	private String email;
	private String address;
	private String country;
	private String phone;

}
