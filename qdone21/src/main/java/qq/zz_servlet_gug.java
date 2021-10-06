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

@WebServlet(name = "qq.zz_servlet_gug", urlPatterns = { "/gug" })
public class zz_servlet_gug extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		String s= "https://trends.google.com/trends/trendingsearches/daily/rss";
			s=rss.rss_gug(s);
			rss.w2m("Ymilog", "kuka@quicklydone.com", "", "kuka2.trends@blogger.com", rss.rus_date(), s);
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