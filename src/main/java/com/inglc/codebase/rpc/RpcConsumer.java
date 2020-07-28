package com.inglc.codebase.rpc;

/**
 * @author liuchen
 * @date 2020/7/28
 */
public class RpcConsumer {

	/**
	 * https://www.iteye.com/blog/javatar-1123915
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		HelloService service = RpcFramework.refer(HelloService.class, "127.0.0.1", 1234);
		for (int i = 0; i < Integer.MAX_VALUE; i ++) {
			String hello = service.hello("World" + i);
			System.out.println(hello);
			Thread.sleep(1000);
		}
	}

}