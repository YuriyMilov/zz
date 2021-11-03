package qq;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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

@WebServlet(name = "qq.tt", urlPatterns = { "/tt" })
public class tt extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String from = "", to = "", u = "", s = "", ss = "", blog = request.getQueryString();
		int h = 8, m = 0;
		try {
			if (!blog.contains("="))
				ss = ds_old.get_text("blog", blog, "content");
			else {
				from = request.getParameter("from");
				if (from == null)
					from = "kuka@quicklydone.com";

				to = request.getParameter("to");
				if (to == null)
					to = "kuka2.ddtor@blogger.com";

				u = request.getParameter("u");
				if (u == null)
					u = "https://ont.ddtor.com/p/rss.html";

				blog = u.substring(u.indexOf("//") + 2, u.indexOf("."));

				s = request.getParameter("h");
				if (s != null)
					h = Integer.parseInt(s);

				s = request.getParameter("m");
				if (s != null)
					m = Integer.parseInt(s);
				else
					m = h * 60;

				boolean bb = ds_old.time2do(blog, m);
				System.out.println("---------- bb ------------>>   "+bb);
				if (bb) {
					System.out.println("-------- Start  reading -->  "+u);

					s = rss.rfu_utf(u);

					System.out.println("-------- Finish  reading -->  "+u);

					int i = s.indexOf("---begin---<br />");

					if (i > -1) {
						s = s.substring(i);
						i = s.indexOf("---end---");
						s = s.substring(0, i);
						s = s.replace("---begin---", "").replace("---end---", "").trim();
					}
					for (String s2 : s.split("<br />")) {
						if (s2.indexOf("http") == 0)
							ss = ss + rss.rss_h(s2, h);
					}
					
				//	if (ss.length() > 333) {
						ss = "<html><body><table><tr><td valign='top'>" + ss + "</td></tr></table></body></html>";
						ds_old.put_text("blog", blog, "content", new Text(ss));
						rss.w2ma(blog + " tt OK", ss);
						rss.w2m("DS", from, "", to, rss.rus_date(), ss);
				//	} else
				//		rss.w2ma(blog + " tt NOT OK", "  ss.length() < 333 " + ss);

					if (request.getServerName().indexOf("local") > -1) {
						// ss= new String(new String(ss).getBytes("UTF-8"));
						// System.out.println(ss);
						
						String sf="C:\\Users\\ym\\Desktop\\_"+LocalDateTime.now().hashCode()+"_.html";
						_info.w2f(sf, ss);

						System.out.println("------ file written ----------->      " + sf);
					}

				}
			}

		} catch (Exception e) {
			rss.w2ma("tt Exception", u + "\r\n\r\n" + e.toString());
		}

		PrintWriter wr = response.getWriter();
		wr.print(ss);
		wr.close();
	}

}