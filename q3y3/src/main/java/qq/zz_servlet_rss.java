package qq;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "qq.zz_servlet_rss", urlPatterns = { "/rss" })
public class zz_servlet_rss extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		String xml = "https://www.anekdot.ru/rss/export_j.xml";
		String adr_from = "ymilov@gmail.com";
		int h = 24;

		String u = request.getParameter("u");
		String hs = request.getParameter("h");
		String to = request.getParameter("to");
		String from = request.getParameter("from");

		if (hs != null)
			h = Integer.parseInt(hs);
		if (u != null)
			xml = u;

		String s = rss.rss_s(xml, h);

		if (to == null)
			rss.w2ma("RSS-w2ma", s);
		else {
			if (from != null)
				adr_from = from;
			if (s.length() > 333)
				rss.w2m("Admin RSS", adr_from, "", to, rss.rus_date(), s);
		}

		PrintWriter wr = response.getWriter();
		wr.print(s);
		wr.close();
	}

}