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

@WebServlet(name = "rss.qq2", urlPatterns = { "/qq2" })
public class qq2 extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		String s = "", url = "";
		try {
			url = request.getQueryString().trim();
			s = qq._info.get_rss(url);
			//qq._info.send_mail("George M.", "4guest@gmail.com", "", "4guest.gmsn123@blogger.com", "", s);
			qq._info.send_mail("Ymilog", "ymilov@gmail.com", "", "ymilov.gmsn123@blogger.com", "", s);
		} catch (Exception e) {
			s = e.toString();
			try {
				qq._info.send_mail("Ymilog", "ymilov@gmail.com", "", "ymilov@gmail.com", "", s);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		qq._info.last_page=s;
		PrintWriter wr = response.getWriter();
		wr.print(s);
		wr.close();

	}

}