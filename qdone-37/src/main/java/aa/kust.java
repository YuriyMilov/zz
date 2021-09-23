package aa;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class kust {

	static public final String qqqq(String s) throws Exception {

		String ss = "", dd = "", date = "", link = "", title = "", content = "";
		try {
			SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(s)));
			List<SyndEntry> synd_entry = feed.getEntries();
			dd = feed.getDescription();
			for (Object o : synd_entry) {

				Date d = ((SyndEntryImpl) o).getPublishedDate();
				LocalDateTime localDateTime = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				boolean bb = localDateTime.isAfter(LocalDateTime.now().minus(Duration.ofHours(1)));

				System.out.println("-- DATE -->" + d + " " + bb);
				if (bb) {
					link = ((SyndEntryImpl) o).getLink();
					title = ((SyndEntryImpl) o).getTitle();

					SyndContent synd_content = ((SyndEntryImpl) o).getDescription();
					if (synd_content != null)
						content = ((SyndEntryImpl) o).getDescription().getValue();
					else
						content = "";

					System.out.println("- TITLE -> " + title);
					// System.out.println("- Content -> " + content);

					// s = "<b>" + title + "</b><br />-----------------<br />" + content;

					ss = ss + "<div><table><tr><td valign='top'>"

							+ "<br/><a href='" + link
							+ "' target='_blank'><img src='http://3.bp.blogspot.com/-UEeXrZLtJCM/Xe5qS93LHBI/AAAAAAABSr4/ei1k8POBBBom8OIZmbbTRLQZVZEUKEviACK4BGAYYCw/s770/rss2.png' /></a>"
							+ "</td>"

							+ "<td>&nbsp;</td>" + "<td valign='top'>"

							+ "<div style=\"color:#aaaaaa;font-family: Arial;font-size:13px;text-decoration:none;\">"
							+ "<i>" + dd + "</i>"

							+ "<br/>"

							+ "<a href='" + link
							+ "' style=\"color:#0044bb;font-family: Arial;font-size:14px;text-decoration:none;\" target=\"_blank\"><b>"
							+ title + "</b></a>"

							+ "</div>"

							+ "<div style=\"color:#222222;font-family: Arial;font-size:13px;\">&nbsp;&nbsp;&nbsp;&nbsp;"
							+ content + "</div>"

							+ "</td></tr></table></div><hr/>";

				}
			}

		} catch (Exception e) {
			m2a("RSS error", s + " " + e.toString());
		}

		return ss;
	}

	public static void main(String[] args) throws Exception {

		String ss = "";
		String s = "https://www.mississauga.com/rss/article?category=news";
		s = "https://galamil.blogspot.com/p/rss.html";
		s = "https://polit.ddtor.com/p/blog-page_21.html";
		s = rfu_utf(s);
		// <!--qqq-->"
		// <!--qqqq-->
		// <!--qqq_rss1_qqq-->
		// <!--qqq_rss2_qqq-->

		int i = s.indexOf("<!--qqq_rss1_qqq-->");
		s = s.substring(i);
		i = s.indexOf("<!--qqq_rss2_qqq-->");
		s = s.substring(0, i);
		s = s.replace("<!--qqq_rss1_qqq-->", "").replace("<!--qqq_rss2_qqq-->", "").trim();

		String[] sssss = s.split("<br />");

		// for (String s2 : sssss)
		// System.out.println(s2);

		// System.out.println("--------------------------------");

		for (int n = 0; n < sssss.length; n++) {
			s = sssss[n];
			System.out.println(s);
			s = qqqq(s);
			// s=fit(s);
			ss = ss + s;
		}

		wf("C:\\Users\\win10\\Desktop\\___qqqqqqqqq___.html", ss);
		System.out.println("----------- END ---------------------");

	}

	public static void wf(String f, String s) throws Exception {
		FileWriter myWriter = new FileWriter(f);
		myWriter.write(s);
		myWriter.close();
	};

	public static String rfu_utf(String s) {
		try {
			URL url = new URL(s);

			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf8"));
			s = "";
			String thisLine = "";
			while ((thisLine = br.readLine()) != null) { // while loop begins
															// here
				s = s + thisLine + "\r\n";
			}
			br.close();
			return s.toString();

		} catch (Exception e) {
			return e.toString();
		}
	}

	public static void s_put(String table, String id, String field, String value) {

		Entity zzz = new Entity(table, id);
		zzz.setProperty(field, value);
		DatastoreServiceFactory.getDatastoreService().put(zzz);
	}

	public static String s_get(String table, String id, String field) {

		String s = "";
		Key kk = KeyFactory.createKey(table, id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			s = datastore.get(kk).getProperties().get(field).toString();
		} catch (EntityNotFoundException e) {
			s = e.toString();
			m2a("error", "s_get('...') " + s);
		}
		return s;
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
		s = s + " " + "... finished and sent";
		return s;

	}

	static public final String get_rss(String s) throws Exception {
		String s7 = s;
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

			for (Object o : ee) {
				System.out.println(((SyndEntryImpl) o).getDescription().getValue());
			}

			if (i > 0) { // ---> YES content = getContents().get(0).getValue();
				for (SyndEntry e : eee) {
					link = e.getLink();
					title = e.getTitle();
					title = "<p style=\"color:blue;font-size:18px;\"><a href=\"" + link + "\" target='_blank'>" + title
							+ "</a><!--qqq-" + s7 + "-qqq--></p>";
					content = e.getContents().get(0).getValue();
					s = "<hr><br/>" + title + content;
					s = s.replace("<b>", "").replace("</b>", "");
					ss = ss + "<tr><td>" + s + "</td></tr>";
				}
			} else {// --> NO contents --> DESCRIPTION = e.getDescription().getValue();
				for (SyndEntry e : eee) {
					link = e.getLink();
					title = e.getTitle();
					title = "<p style=\"color:blue;font-size:18px;\"><a href=\"" + link + "\" target='_blank'>" + title
							+ "</a><!--qqq-" + s7 + "-qqq--></p>";
					description = e.getDescription().getValue();
					s = "<hr><br/>" + title + description;
					s = s.replace("<b>", "").replace("</b>", "");
					ss = ss + "<tr><td>" + s + "</td></tr>";
				}
			}
		}

		// ss = fit(ss);
		ss = "<table>" + ss + "</table>";
		ss = "<i>" + date + "</i><br/>" + ss;

		ss = "<div style=\"width:600px;\">" + ss + "</div>";
		System.out.println(ss);
		return ss;
	}

	static public final String fit(String s) {

		Document doc = Jsoup.parse(s);
		for (Element img : doc.select("img")) {
			img.attr("width", "560");
			// img.attr("width", "320");
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

		s = doc.toString();

		return s;
	}

}