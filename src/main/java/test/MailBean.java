package test;

public class MailBean {
	
	private String receive;
	
	private String pwd;
	
	private String subject;
	
	private String text;
	
	private String date;
	
	private String priority;
	
	private String[] filepath;

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String[] getFilepath() {
		return filepath;
	}

	public void setFilepath(String[] filepath) {
		this.filepath = filepath;
	}

}
