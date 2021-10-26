package qq;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import javax.servlet.annotation.WebServlet; 
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

@WebServlet(name = "ds", urlPatterns = { "/ds" })
public class ds extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String s = "", table = "trends", id = "CA", field = "last";

			if (request.getQueryString() != null)
				put_text(table, id, field, new Text(request.getQueryString()));
			else
				s = get_text(table, id, field);

			/*
			 * table = request.getParameter("table"); id = request.getParameter("id"); field
			 * = request.getParameter("field"); value = request.getParameter("value"); if
			 * (value == null) { value = s_get(table, id, field);
			 * response.getWriter().print("get value OK: " + value); } else { s_put(table,
			 * id, field, value); response.getWriter().print("put value OK: " + value); }
			 */

		PrintWriter w = response.getWriter();
		w.print(s);
		w.close();
	}

	public static void put_text(String table, String id, String field, Text txt) {
	
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
		Entity zzz = new Entity(table, id);
		zzz.setProperty(field, txt);
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

	public static void s_put(String table, String id, String field, String value) {

		Entity zzz = new Entity(table, id);
		zzz.setProperty(field, value);
		DatastoreServiceFactory.getDatastoreService().put(zzz);
	}

	public static Date get_date(String table, String id, String field) {

		String s = "";
		Date d=null;
		Key kk = KeyFactory.createKey(table, id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			d = (Date)datastore.get(kk).getProperties().get(field);
		} catch (EntityNotFoundException e) {
			s = e.toString();
			rss.w2a("error", "s_get('...') " + s);
		}
		return d;
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
	
		//LocalDateTime now = new Date().toInstant().atZone(ZoneId.of("America/New_York")).toLocalDateTime();
		LocalDateTime dt_now = LocalDateTime.now(ZoneId.of("America/New_York"));
		//LocalDateTime dd2 = LocalDateTime.now(ZoneId.of("America/New_York")).plus(Duration.ofHours(n));
			
		String s = "";
		Date d=null;
		Key kk = KeyFactory.createKey("blogtime", id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			d = (Date)datastore.get(kk).getProperties().get("dt_last");
			LocalDateTime dt_last_plus = (d.toInstant().atZone(ZoneId.of("America/New_York"))).toLocalDateTime().plus(Duration.ofMinutes(n));
				
			if(dt_now.isAfter(dt_last_plus))
				{
				Entity zzz = new Entity("blogtime", id);
				zzz.setProperty("dt_last", new Date());
				datastore.put(zzz);				
				return true;}
			else
				return false;
		} catch (EntityNotFoundException e) {
			Entity zzz = new Entity("blogtime", id);
			zzz.setProperty("dt_last", new Date());
			datastore.put(zzz);		
			s = e.toString();
			rss.w2a("error", "time2do('...') " + s);
			return false;
		}
	}

}