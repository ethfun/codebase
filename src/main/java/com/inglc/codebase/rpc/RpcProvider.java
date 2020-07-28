package com.inglc.codebase.rpc;

/**
 * @author liuchen
 * @date 2020/7/28
 */
public class RpcProvider {

	public static void main(String[] args) throws Exception {
		HelloService service = new HelloServiceImpl();
		RpcFramework.export(service, 1234);
	}

}