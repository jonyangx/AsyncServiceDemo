package young.coordinator;

import java.util.UUID;

import young.common.Event;
import young.common.Result;
import young.listener.Listener;
import young.listener.MyListenerFactory;
import young.service.Executor;
import young.service.MyExecutor;

public class MyCoordinator {
	// public static void doSomething() throws Exception {
	// //放在这里有问题，同步等待消息，不可以继续执行代码了
	// //MyListenerFactory.register("deposit_output");
	// System.out.println("do business logic");
	// Event e = new Event();
	// e.setGlobalId(""+UUID.randomUUID());
	// e.setId(""+UUID.randomUUID());
	// e.setContent("send message");
	// e.setTopic("deposit_input");
	// MyListenerFactory.getQueueService().send(e);
	//
	// //这里listen有问题,可能在register的时候，消息早就被其他listener消费掉了
	// MyListenerFactory.listen("deposit_output");
	// }

	public static void doSomething() throws Exception {
		//用线程间的共享来完成协作
		final Result result = new Result();
		final Listener l = new Listener() {
			public void processEvent(Event e) {
				Executor executor = new MyExecutor(result);
				executor.execute(e);
			}

		};

		final Thread t = new Thread() {
			public void run() {
				try {
					synchronized (result) {
						result.notifyAll();
					}
					MyListenerFactory.listen("deposit_output", l);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
		
		synchronized (result) {
			result.wait();//尽量保证listen先完成
		}

		System.out.println("do business logic");
		
		Event e = new Event();
		e.setGlobalId("" + UUID.randomUUID());
		e.setId("" + UUID.randomUUID());
		e.setContent("send message");
		e.setTopic("deposit_input");
		
		MyListenerFactory.getQueueService().send(e);
		
		//等待结果
		synchronized (result) {
			result.wait(6000);
		}
		System.out.println(result.getMessage());

	}

	public static void main(String[] args) throws Exception {
		MyCoordinator.doSomething();
	}

}
