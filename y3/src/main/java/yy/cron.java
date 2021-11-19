package yy;

public class cron implements Runnable {
	public String key = "";
	public cron(String blog) {
		this.key = blog;
	}
	public void run() {
			bb.rfu_utf(bb.server_url+"dds?" + key);
	}
}
