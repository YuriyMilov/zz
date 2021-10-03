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
			
			rss.w2a("servlet rss", "u=" +u + " hs=" + hs + " to=" + to + " from=" + from + " \r\n" + s.substring(0,200));

			PrintWriter wr = response.getWriter();
			wr.print(s);
			wr.close();
	}

	private String sendSimpleMail() {
	    // [START simple_example]
	    Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);
	
	    try {
	      Message msg = new MimeMessage(session);
	      msg.setFrom(new InternetAddress("ymilov@gmail.com", "Example.com Admin"));
	      msg.addRecipient(
	          Message.RecipientType.TO, new InternetAddress("ymilov@gmail.com", "Mr. User"));
	      msg.setSubject("test");
	      msg.setText("This is a test");
	      Transport.send(msg);
	    } catch (Exception e) {
	  return e.toString();
	    }
	    
	    return "Email sent";
	  }

	private String sendMultipartMail() {
	    Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);
	
	    String msgBody = "...";
	
	    try {
	      Message msg = new MimeMessage(session);
	      msg.setFrom(new InternetAddress("ymilov@gmail.com", "Example.com Admin"));
	      msg.addRecipient(
	          Message.RecipientType.TO, new InternetAddress("ymilov@gmail.com", "Mr. User"));
	      msg.setSubject("Multipart email");
	      msg.setText(msgBody);
	
	      // [START multipart_example]
	      String htmlBody = "qqqqqqqqqq"; // ...
	      byte[] attachmentData = htmlBody.getBytes(); // ...
	      Multipart mp = new MimeMultipart();
	
	      MimeBodyPart htmlPart = new MimeBodyPart();
	      htmlPart.setContent(htmlBody, "text/html");
	      mp.addBodyPart(htmlPart);
	
	      MimeBodyPart attachment = new MimeBodyPart();
	      InputStream attachmentDataStream = new ByteArrayInputStream(attachmentData);
	      attachment.setFileName("manual.txt");
	      //attachment.setContent(attachmentDataStream, "application/pdf");
	      attachment.setContent(attachmentDataStream, "application/txt");
	      mp.addBodyPart(attachment);
	
	      msg.setContent(mp);
	      // [END multipart_example]
	
	      Transport.send(msg);
	
	    } catch (Exception e) {
	    	return e.toString(); 
	    }
	    return "Multipart email sent";
	  }

}