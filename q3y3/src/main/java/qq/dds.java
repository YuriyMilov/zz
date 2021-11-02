package qq;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Text;

@WebServlet(name = "dds", urlPatterns = { "/dds" })
public class dds extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String html = "<a href='/'>_root_</a><br><br><a href='_ah/admin'>_ah/admin</a><br><br><form action=/ds method='post'>"
			+ "blog: <input type=text name='key' value='all'> "
			+ "h = <input type=text name='h' value='hhh'> m = <input type=text name='m' value='mmm'><br><br>"
			+ "<textarea name=s rows=11 cols=80>qqq</textarea><br><br><input type=submit value='update' /></form> ";

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		String key = request.getQueryString();
		if (key == null)
			key = "all"; 

		String[] ss = get_text("ddtor", key, "rss").split("https://");
		int h = Integer.parseInt(String.valueOf(get_int("ddtor", key, "h")));
		int m = Integer.parseInt(String.valueOf(get_int("ddtor", key, "m")));
		String from = s_get("ddtor", key, "from");
		String to = s_get("ddtor", key, "to");

		boolean bb = time2do(key, m);
		String s = "";

		if (bb) {
			for (String s2 : ss)
				if (s2.length() > 1) {
					s = s + rss.rss_h("https://" + s2, 24);
				}

			dds.page_update(key, new Text(s), new Date());
			rss.w2ma(key, " ss.length=" + ss.length + " bb=" + bb + " h=" + h + " m=" + m + " from=" + from + " to="
					+ to + " \r\n" + s);
			rss.w2m("DS", from, "", to, rss.rus_date(), s);
		}
		// new Thread(new qq.trd(ss,8)).start();
		PrintWriter w = response.getWriter();
		w.print(new Date().toInstant().toString() + " key = " + key + " time2do = " + bb + "<br />" + s);
		w.close();
	}

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
			rss.w2a("error", "s_get('...') " + s);
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
			rss.w2a("error", "s_get('...') " + s);
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
			rss.w2a("error", e.toString());
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
			rss.w2a("error", "s_get('...') " + s);
		}
		return s;
	}

	public static void s_put_date(String table, String id, String field1, String value1, String field2, Date dt) {

		Entity zzz = new Entity(table, id);
		zzz.setProperty(field1, value1);
		zzz.setProperty(field2, dt);
		DatastoreServiceFactory.getDatastoreService().put(zzz);
	}

	public static boolean time2do(String id, int n) {

		// LocalDateTime now = new
		// Date().toInstant().atZone(ZoneId.of("America/New_York")).toLocalDateTime();
		LocalDateTime dt_now = LocalDateTime.now(ZoneId.of("America/New_York"));
		// LocalDateTime dd2 =
		// LocalDateTime.now(ZoneId.of("America/New_York")).plus(Duration.ofHours(n));

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
			rss.w2a(s, "");
			return false;
		}
	}

}