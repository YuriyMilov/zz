package qq;

import com.google.appengine.api.datastore.Text;

public class trd implements Runnable {
	public String s = "";
	public String[] ss = null;
	int h = 12;

	public trd(String[] ss, int h) {
		this.ss = ss;
		this.h = h;
		rss.w2a("trd-1", "11111111111");
	}

	public void run() {

		// rss.w2a("trd-2", "22222222222222");
		try {
		//	for (String s2 : ss) {
		//		s = s + rss.rss_h(s2, h);
		//	}
			
			ds_old.put_text("pages", "ont", "html", new Text(s));
			System.out.println("-------------------FIN---------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
