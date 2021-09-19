package qq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
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

import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader; 

public class _info extends Thread {
	public _info() {
		this.start();  
	}

	int k=0;
	public void run() {
		while (true) {
			i++;
			if(i==60)
			{
				m2a("Hours: " + k, LocalDateTime.now().toLocalTime().toString());
				i=0;
			}
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
	
			ss = fit(ss);
			ss = "Hello!<br><br><i>" + date + "</i><br>______" + ss;
			ss = "<div style=\"width:600px;\">" + ss + "</div>";
			return ss;
		}

	static public final  String rfu_utf(String s) {
		try {
			URL url = new URL(s);
	
			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf8"));
			s = "";
			String thisLine = "";
			while ((thisLine = br.readLine()) != null) { 
				s = s + thisLine + "\r\n";
			}
			br.close();
			return s.toString();
	
		} catch (Exception e) {
			return e.toString();
		}
	}

	static public final String get_last_rss() throws Exception {
		 String s= rfu_utf("http://gamesnews.quicklydone.com/");
			
		 int i=s.indexOf("<!--qqq-");
		 s=s.substring(i);
		 i= s.indexOf("-qqq-->");
		 s=s.substring(8,i);
		 return s;
	}

	static public final String get_next_rss(String last_rss) throws Exception {
		
		String s = "http://gamesnews.quicklydone.com/p/rss.html";
		
		 s = rfu_utf(s);
		 
		int i = s.indexOf("<!--qqq-rss-begin-qqq-->");
		s = s.substring(i);
		i = s.indexOf("<!--qqq-rss-end-qqq-->");
		s = s.substring(26, i).trim();	
		
		String[] sss = s.split("<br />");
		i = 0;
		for (String s2 : sss) {
			if (s2.trim().contains(last_rss)) {
				
			//	System.out.println("index=" + i);
				if (i == (sss.length - 1))
					i = -1;
				
					s=sss[i+1].trim();
			}
			i++;
		}
		//System.out.println("next RSS " + s);
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
	
		return "email to admin sent";
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

	public static int i = 0;
	public static String _info = "_info._info";
	public static String last_page = "last_page";
	
}
