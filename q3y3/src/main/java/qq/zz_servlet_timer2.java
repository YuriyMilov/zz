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

import com.google.appengine.api.datastore.Text;

@WebServlet(name = "qq.zz_servlet_timer2", urlPatterns = { "/zz" })
public class zz_servlet_timer2 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		String from = "", to = "", u = "", s = "", ss = "", qq = request.getQueryString();
		int h = 8, m=2;
		try {
		//	if (qq == null)
		//		ss = ds.get_text("blog", "gamesnews", "content");
		//	else 
		//	{
		//		if (!qq.contains("="))
		//			ss = ds.get_text("blog", qq, "content");
		//		else 
				{
					from = request.getParameter("from");
					if (from == null)
						from = "kuka@quicklydone.com";

					to = request.getParameter("to");
					if (to == null)
						to = "kuka2.ddtor@blogger.com";

					u = request.getParameter("u");
					if (u == null)
						u = "https://ont.ddtor.com/p/rss.html";

					qq = u.substring(u.indexOf("//") + 2, u.indexOf("."));

					s = request.getParameter("h");
					if (s != null)
						h = Integer.parseInt(s);

					s = request.getParameter("m");
					if (s != null)
						m = Integer.parseInt(s);

					
					boolean bb = ds_old.time2do(qq, m);
					//ss=String.valueOf(bb)+ "  sssssssssssssssssssss";
					System.out.println("----------------------->         "+ bb);
					if (bb) {
					
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
									ss = ss + rss.rss_gam(s2, h);
								else
									ss = ss + rss.rss_h(s2, h);

						//if (ss.length() > 333) {
							ss = "<html><body><table><tr><td valign='top'>" + ss + "</td></tr></table></body></html>";
							ds_old.put_text("blog", qq, "content", new Text(ss));
						//	rss.w2m("DS", from, "", to, rss.rus_date(), ss);
						//}

					}
					else
					ss= "time2do("+qq+", "+m+") = false  \r\n<br />"+ request.getQueryString();

				}

		//	}
		} catch (Exception e) {
			ss = e.toString();
			rss.w2ma("gam Exception", u + "\r\n\r\n" + ss);
		}

		PrintWriter wr = response.getWriter();
		wr.print(ss);
		wr.close();
	}

}