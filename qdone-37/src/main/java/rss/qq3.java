package rss;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dd._info;

@WebServlet(name = "rss.qq3", urlPatterns = { "/qq3" })
public class qq3 extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String shttp = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		String s = "";
		try {
			s = "https://polit.ddtor.com/p/blog-page_21.html";
			s = kust.rfu_utf(shttp + "/qq2?" + s);
			if (s.length() > 222)
				kust.send_mail("", "ymilov@gmail.com", "", "ymilov.k9992.tverskoy@blogger.com", kust.get_date_rus3(),
						s);
		} catch (Exception e) {
			s = e.toString();
			kust.m2a("Error qq3", s);
		}

		try {
			s = "https://gamesnews.quicklydone.com/p/rss.html";
			s = kust.rfu_utf(shttp + "/qq2?" + s);
			if (s.length() > 222)
				kust.send_mail("Ymilog", "ymilov@gmail.com", "", "ymilov.gmsn123@blogger.com", "", s);
		} catch (Exception e) {
			s = e.toString();
			kust.m2a("Error qq3", s);
		}

		try {
			s = "https://galamil.blogspot.com/p/rss.html";
			s = kust.rfu_utf(shttp + "/qq2?" + s);
			if (s.length() > 222)
				kust.send_mail("Ymilog", "ymilov@gmail.com", "", "galamil.qdone@blogger.com", "", s);
		} catch (Exception e) {
			s = e.toString();
			kust.m2a("Error qq3", s);
		}

		PrintWriter wr = response.getWriter();
		wr.print(s);
		wr.close();
	}
}