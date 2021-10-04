package qq;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [END simple_includes]
// [START multipart_includes]
// [END multipart_includes]

@WebServlet(name = "qq.zz_servlet_email", urlPatterns = { "/hello" })

@SuppressWarnings("serial")
public class zz_servlet_email extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String s = " qqqqqqqq ";
		String type = req.getParameter("type");
		if (type != null && type.equals("multipart")) {
			resp.getWriter().print("Sending HTML email with attachment.");
			s = sendMultipartMail();
		}
		if (type != null && type.equals("admin"))
			s = send_Admin("ymilov@gmail.com", "test", "111<b>222</b>333333");
		else
			s = sendSimpleMail();

		resp.getWriter().print(s);
	}

	private String sendSimpleMail() {
		// [START simple_example]
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("ymilov@gmail.com", "Example.com Admin"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("ymilov@gmail.com", "Mr. User"));
			msg.setSubject("Your Example.com account has been activated");
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
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("ymilov@gmail.com", "Mr. User"));
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
			// attachment.setContent(attachmentDataStream, "application/pdf");
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

	public static String send_Admin(String to, String subject, String body) {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);

		try {
			msg.setFrom(new InternetAddress(to, "Admin"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress("admins"));
			msg.setSubject(subject);
			// msg.setText(body);
			msg.setContent(body, "text/html;charset=utf-8");

			Multipart mp = new MimeMultipart();
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(body, "text/html;charset=utf-8");
			mp.addBodyPart(textPart);
			Transport.send(msg);
		} catch (Exception e) {
			return e.toString();
		}
		return "sent as admin";
	}
}