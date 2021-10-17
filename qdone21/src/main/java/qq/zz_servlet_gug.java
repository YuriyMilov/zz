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

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		String g = request.getParameter("g");
		String b = request.getParameter("b");
		String s = request.getQueryString();

		if (s == null)
			s = ds.get_text("blog", "main", "content");
		else {
			if (g == null && b == null) {
				s = "https://trends.google.com/trends/trendingsearches/daily/rss";
				s = rss.rss_gug(s);
				ds.put_text("blog", "main", "content", new Text(s));
				rss.w2m("DS", "kuka@quicklydone.com", "", "kuka2.trends@blogger.com", rss.rus_date(), s);
			} else {
				if (b != null && g != null) {
				
					s = "https://trends.google.com/trends/trendingsearches/daily/rss?geo=" + g;
					s = rss.rss_gug(s);
					ds.put_text("blog", b, "content", new Text(s));
					rss.w2m("DS", "kuka@quicklydone.com", "", "kuka2.trends-ru@blogger.com", rss.rus_date(), s);
				} else
					s = ds.get_text("blog", b, "content");
			}
		}

		// rss.w2m("DS", "kuka@quicklydone.com", "", "kuka2.trends@blogger.com",
		// rss.rus_date(), s);
		// rss.w2ma(rss.rus_date(), s);

		PrintWriter wr = response.getWriter();
		wr.print(s);
		wr.close();
	}
}