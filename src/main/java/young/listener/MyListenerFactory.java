package young.listener;

import young.mock.MockQueueService;

public class MyListenerFactory {

	private static MockQueueService service = new MockQueueService();

	public static MockQueueService getQueueService() {
		return service;
	}

	// 注册listener到消息总线
	// 保存需要处理的id,executor到listener
	public static void listen(String topic, Listener listener) throws Exception {
		// Listener listener = getListener(topic);
		service.listen(topic, listener);
	}

}
