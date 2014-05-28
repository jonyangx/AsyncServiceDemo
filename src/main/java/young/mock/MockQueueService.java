package young.mock;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

import young.common.Event;
import young.listener.Listener;

public class MockQueueService {

	private static LinkedBlockingQueue<Event> inputQueue = new LinkedBlockingQueue<Event>();
	private static LinkedBlockingQueue<Event> outputQueue = new LinkedBlockingQueue<Event>();

	public MockQueueService() {
		new MyThread().start();
	}

	public void listen(String topic, Listener l) throws Exception {
		Event e = outputQueue.take() ;
		l.processEvent(e);
	}

	public void send(Event e) {
		inputQueue.offer(e);
	}

	public static class MyThread extends Thread {
		public void run() {
			try {
				Thread.sleep(1000);
				Event e = inputQueue.take();
				Event newEvent = new Event();
				newEvent.setId("" + UUID.randomUUID());
				String topicPrefix = e.getTopic().split("_")[0];
				newEvent.setTopic(topicPrefix+"_output");
				newEvent.setContent("output message");
				outputQueue.offer(newEvent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
