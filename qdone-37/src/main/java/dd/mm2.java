package dd;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Message;
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

import dd.stkl;

@WebServlet(
	    name = "zz.mm2",
	    urlPatterns = {"/mm2"}
	)
public class mm2 extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String sh = req.getScheme() + "://" + req.getServerName() + ":"
				+ req.getServerPort() + req.getContextPath();

	//	int k = Jsoup.parse(stkl.mail).text().length();
	//	if(k>2222)	
		//	{ 
		//stkl.mail="qq test";
		//String s = send_mail("", "ymilov@gmail.com", "", "ymilov@gmail.com", "на "+ _info.get_date_rus3(), _info.get_all_new_rss());
//		String s = send_mail("", "ymilov@gmail.com", "", "ymilov@gmail.com", "на "+ _info.get_date_rus3(), stkl.mail);
		String s = send_mail("", "ymilov@gmail.com", "", "ymilov.k9992.tverskoy@blogger.com", _info.get_date_rus3(), _info.get_all_new_rss());
	//		}
		resp.setCharacterEncoding("UTF8");
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		//out.write( "stkl.mail length = " +k+ " "+ s);
		out.write(s);
		out.flush();
		out.close();
		stkl.mail="";
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}
	
	public static String send_mail(String from_name, String from_address,
			String to_name, String to_address, String subj, String body) {
		String s = "send_mail ok ddtor.dd.mm2";
		try {
			Properties props = new Properties();
			Session session = Session.getDefaultInstance(props, null);
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from_address, MimeUtility
					.encodeText(from_name, "utf-8", "B")));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to_address, MimeUtility.encodeText(to_name, "utf-8", "B")));
			msg.setSubject(MimeUtility.encodeText(
					MimeUtility.encodeText(subj, "utf-8", "B"), "utf-8", "B"));

			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(body, "text/html;charset=utf-8");
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(textPart);
			msg.setContent(mp);
			Transport.send(msg);

		} catch (Exception e) {
			s = e.toString();
		}
		return s;
	}



	private static final long serialVersionUID = 1L;

}

//String subj = MimeUtility.encodeText("Сообщения на " + stkl.get_date() + " года", "utf-8", "B");

