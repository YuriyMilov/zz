package yy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Text;

@WebServlet(name = "yy.servlet_dds", urlPatterns = { "/dds" })
public class servlet_dds extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String key = request.getQueryString(); 
		if (key == null)
			key = "polit"; 

		String[] ss = bb.get_text("ddtor", key, "rss").split("https://");
		int h = Integer.parseInt(String.valueOf(bb.get_int("ddtor", key, "h"))), m = Integer.parseInt(String.valueOf(bb.get_int("ddtor", key, "m")));
		String from = bb.s_get("ddtor", key, "from"), to = bb.s_get("ddtor", key, "to"), s = "";

		for (String s2 : ss)
			if (s2.length() > 4) {
				if(s2.indexOf("trends.google.com")==0)
					s = s + bb.rss_gug("https://" + s2);
				else						
					s = s + bb.rss_h2("https://" + s2, h, key);
			}

		bb.page_update(key, new Text(s), new Date());
		
		if (bb.blogtime_plust_h(key, m)) 
			bb.w2m("DS", from, "", to, bb.rus_date(), "<html><body>"+s+"</body></html>");

		//List<String> kkk = rss.get_keys_of("ddtor"); String skk=""; for(String sk: kkk)skk = skk+sk+"<br />";
		PrintWriter w = response.getWriter();
		w.print(s);
		w.close();
	}
/*
	public static void page_update(String id, Text txt, Date d) {

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Entity zzz = new Entity("pages", id);
		zzz.setProperty("html", txt);
		zzz.setProperty("last_update", d);
		ds.put(zzz);
	}

	public static String s_get(String table, String id, String field) {

		String s = "";
		Key kk = KeyFactory.createKey(table, id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			s = datastore.get(kk).getProperties().get(field).toString();
		} catch (EntityNotFoundException e) {
			s = e.toString();
			bb.w2a("error", "s_get('...') " + s);
		}
		return s;
	}

	public static void s_put2(String table, String id, String field1, String value1, String field2, String value2) {

		Entity zzz = new Entity(table, id);
		zzz.setProperty(field1, value1);
		zzz.setProperty(field2, value2);
		DatastoreServiceFactory.getDatastoreService().put(zzz);
	}

	public static void put_txt_hmqqq(String table, String id, String text_field, Text text_value, String h, int h_value,
			String m, int m_value) {

		Entity zzz = new Entity(table, id);
		zzz.setProperty(text_field, text_value);
		zzz.setProperty(h, h_value);
		zzz.setProperty(m, m_value);
		DatastoreServiceFactory.getDatastoreService().put(zzz);
	}

	public static void s_put(String table, String id, String field, String value) {

		Entity zzz = new Entity(table, id);
		zzz.setProperty(field, value);
		DatastoreServiceFactory.getDatastoreService().put(zzz);
	}

	public static Date get_date(String table, String id, String field) {

		String s = "";
		Date d = null;
		Key kk = KeyFactory.createKey(table, id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			d = (Date) datastore.get(kk).getProperties().get(field);
		} catch (EntityNotFoundException e) {
			s = e.toString();
			bb.w2a("error", "s_get('...') " + s);
		}
		return d;
	}

	public static Long get_int(String table, String id, String field) {

		Long i = (long) 0;

		Key kk = KeyFactory.createKey(table, id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			i = (Long) datastore.get(kk).getProperties().get(field);
		} catch (EntityNotFoundException e) {
			bb.w2a("error", e.toString());
		}
		return i;
	}

	public static void put_text_date(String table, String id, String field1, Text txt, String field2, Date d) {

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Entity zzz = new Entity(table, id);
		zzz.setProperty(field1, txt);
		zzz.setProperty(field2, d);
		ds.put(zzz);
	}

	public static String get_text(String table, String id, String field) {

		String s = "";
		Key kk = KeyFactory.createKey(table, id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Text txt = (Text) datastore.get(kk).getProperties().get(field);
			s = txt.getValue();
		} catch (EntityNotFoundException e) {
			s = e.toString();
			bb.w2a("error", "s_get('...') " + s);
		}
		return s;
	}
	
	public static void s_put_date(String table, String id, String field1, String value1, String field2, Date dt) {

		Entity zzz = new Entity(table, id);
		zzz.setProperty(field1, value1);
		zzz.setProperty(field2, dt);
		DatastoreServiceFactory.getDatastoreService().put(zzz);
	}

	public static boolean time2do_old(String id, int n) {

		LocalDateTime dt_now = LocalDateTime.now(ZoneId.of("America/New_York"));
		// LocalDateTime dd2 = LocalDateTime.now(ZoneId.of("America/New_York")).plus(Duration.ofHours(n));

		String s = "";
		Date d = null;
		Key kk = KeyFactory.createKey("blogtime", id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			d = (Date) datastore.get(kk).getProperties().get("dt_last");
			LocalDateTime dt_last_plus = (d.toInstant().atZone(ZoneId.of("America/New_York"))).toLocalDateTime()
					.plus(Duration.ofMinutes(n));

			if (dt_now.isAfter(dt_last_plus)) {
				Entity zzz = new Entity("blogtime", id);
				zzz.setProperty("dt_last", new Date());
				datastore.put(zzz);
				return true;
			} else
				return false;
		} catch (Exception e) {
			Entity zzz = new Entity("blogtime", id);
			zzz.setProperty("dt_last", new Date());
			datastore.put(zzz);
			s = e.toString();
			bb.w2a(s, "");
			return false;
		}
	}

	public static boolean blogtime_plust_h(String id, int n) {

		LocalDateTime dt_now = LocalDateTime.now(ZoneId.of("America/New_York"));
		// LocalDateTime dd2 = LocalDateTime.now(ZoneId.of("America/New_York")).plus(Duration.ofHours(n));

		String s = "";
		Date d = null;
		Key kk = KeyFactory.createKey("blogtime", id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			d = (Date) datastore.get(kk).getProperties().get("dt_last");
			LocalDateTime dt_last_plus = (d.toInstant().atZone(ZoneId.of("America/New_York"))).toLocalDateTime()
					.plus(Duration.ofHours(n));

			if (dt_now.isAfter(dt_last_plus)) {
				Entity zzz = new Entity("blogtime", id);
				zzz.setProperty("dt_last", new Date());
				datastore.put(zzz);
				return true;
			} else
				return false;
		} catch (Exception e) {
			Entity zzz = new Entity("blogtime", id);
			zzz.setProperty("dt_last", new Date());
			datastore.put(zzz);
			s = e.toString();
			bb.w2a(s, "");
			return false;
		}
	}
	*/
}