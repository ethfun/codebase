package com.inglc.codebase.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liuchen
 * @date 2020/7/29
 */
@Data
@AllArgsConstructor
public class InvocationHandlerImpl implements InvocationHandler {

	private String host;
	private int port;



	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Socket socket = new Socket(host, port);
		try {
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			try {
				output.writeUTF(method.getName());
				output.writeObject(method.getParameterTypes());
				output.writeObject(args);
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				try {
					Object result = input.readObject();
					if (result instanceof Throwable) {
						throw (Throwable) result;
					}
					return result;
				} finally {
					input.close();
				}
			} finally {
				output.close();
			}
		} finally {
			socket.close();
		}
	}
}
