package qq;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.Text;

@WebServlet(name = "qq.zz_servlet_gug", urlPatterns = { "/gug" })
public class zz_servlet_gug extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String s = request.getQueryString();
		if (s == null)
			s = "https://trends.google.com/trends/trendingsearches/daily/rss?geo=CA";

		s = rss.rss_gug(s);
		String table = "trends", id = "CA", field = "last";	
		ds.put_text(table, id, field, new Text(s));
		
		rss.w2ma(rss.rus_date(), s);
		PrintWriter wr = response.getWriter();
		wr.print(s.length() + "  "+s);
		wr.close();
	}


}