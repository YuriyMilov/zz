package qq;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;

@WebServlet(name = "qq.zz_servlet_gam", urlPatterns = { "/gam" })

public class zz_servlet_gam extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String from = "", to = "", u = "", s = "", ss = "", qq = request.getQueryString();
		int h = 2;
		try {
			if (qq == null)
				ss = ds.get_text("blog", "gamesnews", "content");
			else {
				if (!qq.contains("="))
					ss = ds.get_text("blog", qq, "content");
				else {
					from = request.getParameter("from");
					if (from == null)
						from = "kuka@quicklydone.com";

					to = request.getParameter("to");
					if (to == null)
						to = "kuka2.games@blogger.com";

					u = request.getParameter("u");
					if (u == null)
						u = "https://gamesnews.quicklydone.com/p/rss.html";

					qq = u.substring(u.indexOf("//") + 2, u.indexOf("."));

					s = request.getParameter("h");
					if (s != null)
						h = Integer.parseInt(s);

					s = rss.rfu_utf(u);
					int i = s.indexOf("---begin---<br />");

					if (i > -1) {
						s = s.substring(i);
						i = s.indexOf("---end---");
						s = s.substring(0, i);
						s = s.replace("---begin---", "").replace("---end---", "").trim();
					}

					for (String s2 : s.split("<br />"))
						if (s2.length() > 5)
							if (qq.contains("gamesnews"))
								ss = ss + rss.rss_h(s2, h);
							else
								ss = ss + rss.rss_h(s2, h);

					ss = "<html><body><table><tr><td valign='top'>" + ss + "</td></tr></table></body></html>";
					ds.put_text("blog", qq, "content", new Text(ss));
					rss.w2m("DS", from, "", to, rss.rus_date(), ss);
				}
			}
		} catch (Exception e) {
			ss = e.toString();
			rss.w2ma("gam Exception", u + "\r\n\r\n" + ss);
		}

		PrintWriter wr = response.getWriter();
		wr.print(ss);
		wr.close();
	}

}