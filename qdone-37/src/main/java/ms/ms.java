package ms;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
 
@WebServlet(
    name = "ms.ms",
    urlPatterns = {"/ms"}
)
public class ms extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException { 

    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    
	 String s="";
	try {
		s = get_rss("https://www.mississauga.com/rss/article?category=news");
	
		s=send_mail("Ymilog", "ymilov@gmail.com", "", "ymilov.qdone@blogger.com", "", s);
		
		m2a("Mississauga blog OK", s);
		
	} catch (Exception e) {
		
		s= e.toString();
		m2a("error", s);
	}
		
	
    response.getWriter().print(s);
  }

public static String m2a(String subject, String body) {
	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);
	Message msg = new MimeMessage(session);

	try {
		msg.setFrom(new InternetAddress("ymilov@gmail.com", "Admin"));
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress("admins"));
		msg.setSubject(subject);
		msg.setText(body);

		Multipart mp = new MimeMultipart();
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setContent(body, "text/html;charset=utf-8");
		// textPart.setContent(body, "text/html");
		mp.addBodyPart(textPart);
		Transport.send(msg);
	} catch (Exception e) {
		return e.toString();
	}

	return "email sent";
}

public static String send_mail(String from_name, String from_address, String to_name, String to_address,
		String subj, String body) throws Exception {
	String s = "emailing started," + to_address;
	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);
	Message msg = new MimeMessage(session);
	msg.setFrom(new InternetAddress(from_address, MimeUtility.encodeText(from_name, "utf-8", "B")));
	msg.addRecipient(Message.RecipientType.TO,
			new InternetAddress(to_address, MimeUtility.encodeText(to_name, "utf-8", "B")));
	msg.setSubject(MimeUtility.encodeText(MimeUtility.encodeText(subj, "utf-8", "B"), "utf-8", "B"));

	MimeBodyPart textPart = new MimeBodyPart();
	textPart.setContent(body, "text/html;charset=utf-8");
	Multipart mp = new MimeMultipart();
	mp.addBodyPart(textPart);
	msg.setContent(mp);
	Transport.send(msg);
	s=s+" "+"... finished and sent";
	return s;

}

static public final String fit(String s) {

	Document doc = Jsoup.parse(s);
	for (Element img : doc.select("img")) {
		img.attr("width", "560");
		img.removeAttr("height");
	}
	for (Element iframe : doc.select("iframe")) {
		iframe.attr("width", "560");
		iframe.attr("height", "315");
		s = iframe.toString();
	}
	for (Element div : doc.select("div")) {
		div.removeAttr("style");
	}
	return doc.toString();
}

static public final String get_rss(String s) throws Exception {
String s7=s;
		URL feedSource = new URL(s);
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(feedSource));
		
	 
		
		String ss = "", date = "", link = "", title = "", content = "", description = "";
		List<SyndEntry> eee = feed.getEntries();

		int i = eee.size();
		if (i >= 0) { // --- Entries > 0

			List<SyndContent> ee = eee.get(0).getContents();
			Date dd = eee.get(0).getPublishedDate();
			if (dd != null)
				date = eee.get(0).getPublishedDate().toString();
			else
				date = "no published date";
			i = ee.size();
			if (i > 0) { // ---> YES content = getContents().get(0).getValue();
				for (SyndEntry e : eee) {
					link = e.getLink();
					title = e.getTitle();
					title = "<p style=\"color:blue;font-size:18px;\"><a href=\"" + link + "\" target='_blank'>" + title
							+ "</a><!--qqq-"+s7+"-qqq--></p>";
					content = e.getContents().get(0).getValue();
					s = title + content + "<hr>";
					ss = ss + s;
				}
			} else {// --> NO contents --> DESCRIPTION = e.getDescription().getValue();
				for (SyndEntry e : eee) {
					link = e.getLink();
					title = e.getTitle();
					title = "<p style=\"color:blue;font-size:18px;\"><a href=\"" + link + "\" target='_blank'>" + title
							+ "</a><!--qqq-"+s7+"-qqq--></p>";

					description = e.getDescription().getValue();
					s = title + description + "<hr><hr>";
					ss = ss + s;
				}
			}
		}

		//ss = fit(ss);
		ss = "Hello!<br><br><i>" + date + "</i><br>______" + ss;
		ss = "<div style=\"width:600px;\">" + ss + "</div>";
		return ss;
	}




	
}