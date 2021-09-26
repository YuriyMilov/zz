package rss;

import java.io.IOException;
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

@WebServlet(name = "rss.qqrss", urlPatterns = { "/rss" })
public class qqrss extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String s = "", ur = "https://polit.ddtor.com/p/blog-page_21.html",  adr ="ymilov@blogger.com", u="", hs="", a="";
		int h = 24;
		try {
			u = request.getParameter("u");
			hs = request.getParameter("h");
			a = request.getParameter("a");
			
			if (hs != null)
				h = Integer.parseInt(hs);	
			if (a != null)
				adr=a;			
			if (u != null)
				ur=u;	
			
			s = kust.rss_all(u, h);
			
			if (s.length() > 222)
				kust.w2m("Ymilog", "ymilov@gmail.com", "", adr, kust.rus_date(), s);
			
		} catch (Exception e) {
			s=e.toString();
			kust.w2a("qq2",  "url = " + u + "  \r\nhours = " + hs + "\r\nadr = " + a + "\r\n" + s);
		}
		PrintWriter wr = response.getWriter();
		wr.print(s);
		wr.close();

	}

}