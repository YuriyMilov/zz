package qq;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;

@WebServlet(name = "qq.zz_servlet_rss", urlPatterns = { "/rss" })
public class zz_servlet_rss extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String s = "", adr_from = "ymilov@gmail.com", adr_to = "ymilov@gmail.com", url = "https://polit.ddtor.com/p/blog-page_21.html";
		String u="-", hs="-", from="-", to="-";
				int h = 2;
			 u = request.getParameter("u");
			 hs = request.getParameter("h");
			 to = request.getParameter("to");
			 from = request.getParameter("from");
			if (hs != null)
				h = Integer.parseInt(hs);
			if (u != null)
				url = u;
			if (to != null)
				adr_to = to;
			if (from != null)
				adr_from = from;
			s = rss.rss_all(url, h);			
			if (s.length() > 222)
				rss.w2m("Kuka", adr_from, "", adr_to, rss.rus_date(), s);

			PrintWriter wr = response.getWriter();
			wr.print(s);
			wr.close();
	}



}