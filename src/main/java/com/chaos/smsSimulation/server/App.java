package com.chaos.smsSimulation.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chaos.smsSimulation.server.controller.PortListener;

public class App 
{
	public static void main(String[] args)
	{
		try {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

			PortListener listener = ctx.getBean("PortListener", PortListener.class);
			listener.startListen();
			
			ctx.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
