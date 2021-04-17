package es.upm.dit.isst.educovid.aux;

public class PushMessage {
	private final String title;
	private final String body;

	public PushMessage(String title, String body) {
		this.title = title;
		this.body = body;
	}

	public String getTitle() {
		return this.title;
	}

	public String getBody() {
		return this.body;
	}

	@Override
	public String toString() {
		return "PushMessage [title=" + this.title + ", body=" + this.body + "]";
	}

}
