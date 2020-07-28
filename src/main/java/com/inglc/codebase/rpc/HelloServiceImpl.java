package com.inglc.codebase.rpc;

/**
 * @author liuchen
 * @date 2020/7/28
 */
public class HelloServiceImpl implements HelloService {

	public String hello(String name) {
		return "Hello " + name;
	}

}