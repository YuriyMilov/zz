package qq;

public class cron implements Runnable {
	public String key = "";

	public cron(String blog) {
		this.key = blog;
	}

	public void run() {
			rss.rfu_utf("https://qqqyyy.appspot.com/dds?" + key);
	}
}
