package young.common;

public class Event implements java.io.Serializable {
	private static final long serialVersionUID = 4874348833458734464L;

	private String globalId;

	private String id;
	private String content;
	private String topic;

	public String getGlobalId() {
		return globalId;
	}

	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "id:" + this.getId() + ",topic:" + this.getTopic() + ",content:"
				+ this.getContent();
	}

}
