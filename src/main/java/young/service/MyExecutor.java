package young.service;

import young.common.Event;
import young.common.Result;

public class MyExecutor implements Executor {
	private Result r;

	public void execute(Event e) {
		System.out.println(e);
		r.setMessage(e.getContent());
		synchronized (r) {
			r.notifyAll();
		}
	}

	public MyExecutor(Result r) {
		this.r = r;
	}

}
