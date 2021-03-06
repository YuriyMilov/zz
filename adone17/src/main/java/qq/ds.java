package qq;


import java.io.IOException;
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

@WebServlet(name = "ds", urlPatterns = { "/ds" })
public class ds extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		String table = "", id = "", field = "", value = "";
		try {
			table = request.getParameter("table");
			id = request.getParameter("id");
			field = request.getParameter("field");
			value = request.getParameter("value");
			if (value == null) {
				value = s_get(table, id, field);
				response.getWriter().print("get value OK: " + value);
			} else {
				s_put(table, id, field, value);
				response.getWriter().print("put value OK: " + value);
			}

		} catch (Exception e) {

			response.getWriter().print("Error " + e.toString() + "  \r\n\r\n" + kust.w2a("error", e.toString()));
		}

	}

	public static String s_get(String table, String id, String field) {

		String s = "";
		Key kk = KeyFactory.createKey(table, id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			s = datastore.get(kk).getProperties().get(field).toString();
		} catch (EntityNotFoundException e) {
			s = e.toString();
			kust.w2a("error", "s_get('...') " + s);
		}
		return s;
	}

	public static void s_put(String table, String id, String field, String value) {

		Entity zzz = new Entity(table, id);
		zzz.setProperty(field, value);
		DatastoreServiceFactory.getDatastoreService().put(zzz);
	}

	public static void s_put2(String table, String id, String field1, String value1, String field2, String value2) {

		Entity zzz = new Entity(table, id);
		zzz.setProperty(field1, value1);
		zzz.setProperty(field2, value2);
		DatastoreServiceFactory.getDatastoreService().put(zzz);
	}

}