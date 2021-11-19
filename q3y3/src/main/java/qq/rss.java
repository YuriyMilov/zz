package qq;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class rss {

	static public final String rss_h(String s, int i) {

		System.out.println("-- RSS -> " + s);

		String ss = "", link = "", title = "", content = "", tt = "", dtm = "", dt = "";
		int ii = -1;
		try {
			// w2ma("RSS_h", s);
			SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(s)));
			List<SyndEntry> synd_entry = feed.getEntries();
			tt = feed.getTitle();
			if (tt.contains("Google Alert - "))
				i = i + 2;
			tt = tt.replace("RSS", "").replace("Google Alert - ", "G-Alt ");
			for (Object o : synd_entry) {
				Date d = ((SyndEntryImpl) o).getPublishedDate();
				LocalDateTime localDateTime = d.toInstant().atZone(ZoneId.of("America/New_York")).toLocalDateTime();
				boolean bb = localDateTime.isAfter(LocalDateTime.now().minus(Duration.ofHours(i)));

				if (bb) {
					link = ((SyndEntryImpl) o).getLink();
					title = ((SyndEntryImpl) o).getTitle();
					SyndContent synd_content = ((SyndEntryImpl) o).getDescription();
					List<SyndContent> sclist = null;
					if (synd_content != null)
						content = ((SyndEntryImpl) o).getDescription().getValue();
					else {
						sclist = ((SyndEntryImpl) o).getContents();
						ii = sclist.size();
						if (ii > 0)
							content = sclist.get(0).getValue();
						else
							content = "";
						content = content.replace("<b>", "").replace("</b>", "").replace("Read more", "");
					}
					if (content.length() > 1)
						content = fit(content);
					
					dtm = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT)
							.format(localDateTime);
					dt = tt + "    " + dtm;

					System.out.println("--- " + bb + "-----> " + dtm);
					
					ss = ss + "<div style=\"color:#777777;font-family: Arial;font-size:13px;text-decoration:none;\">"
							+ dt + "</div><div><a href='" + link
							+ "' style=\"color:#0044bb;font-family: Arial;font-size:15px;text-decoration:none;\" target=\"_blank\"><b>"
							+ title + "</b></a>&nbsp;<br/><br /></div>"
							+ "<div style=\"color:#222222;font-family: Arial;font-size:13px;\">&nbsp;&nbsp;&nbsp;&nbsp;"
							+ content + "</div><div></div><hr/>";
							//+ content + "<br /><br /></div></td></tr></table><hr/>";
					
								
					
				} else
					System.out.println("--- " + bb + "-----> " + dtm);
			}
		} catch (Exception e) {
			 System.out.println(e.toString());
			// w2ma("RSS_h Error", e.toString());
		}
		return ss;
	}

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

	public static String w2a(String subject, String body) {
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
			mp.addBodyPart(textPart);
			Transport.send(msg);
		} catch (Exception e) {
			return e.toString();
		}
		return "";
	}

	public static String w2m(String from_name, String from_address, String to_name, String to_address, String subj,
			String body) {
		String s = "";
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		try {
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
		} catch (Exception e) {
			s = e.toString();
		}
		return s;
	}

	public static String rss_all(String s, int h) {

		String ss = "";

		s = rfu_utf(s);

		int i = s.indexOf("---begin---<br />");

		if (i > -1) {
			s = s.substring(i);
			i = s.indexOf("---end---");
			s = s.substring(0, i);
			s = s.replace("---begin---", "").replace("---end---", "").trim();
		}

		// System.out.println(s);
		String[] sss = s.split("<br />");

		for (String s2 : sss)
			if (s2.indexOf("http") > -1)
				ss = ss + rss_h(s2, h);

		ss = "<html><body><table><tr><td valign='top'>" + ss + "</td></tr></table></body></html>";

		return ss;
	}

	public static String rus_date() {
		/*
		 * Date dd = new Date();
		 * 
		 * TimeZone tz = TimeZone.getTimeZone("Europe/Moscow"); Calendar cc =
		 * Calendar.getInstance(tz); cc.setTime(dd); cc.add(Calendar.HOUR, -1);
		 * 
		 * String s_hh = String.valueOf(cc.get(Calendar.HOUR_OF_DAY)); String s_mm =
		 * String.valueOf(String.format("%02d", cc.get(Calendar.MINUTE))); String s_dofm
		 * = String.valueOf(cc.get(Calendar.DAY_OF_MONTH)); String s_dow =
		 * String.valueOf(cc.get(Calendar.DAY_OF_WEEK)).replace("1", "воскресенье")
		 * .replace("2", "понедельник").replace("3", "вторник").replace("4",
		 * "среда").replace("5", "четверг") .replace("6", "пятница").replace("7",
		 * "суббота");
		 * 
		 * String s = String.valueOf(cc.get(Calendar.MONTH)); s = s.replace("10",
		 * " ноября ").replace("11", " декабря ").replace("0", " января ").replace("1",
		 * " февраля ") .replace("2", " марта ").replace("3", " апреля ").replace("4",
		 * " мая ").replace("5", " июня ") .replace("6", " июля ").replace("7",
		 * " августа ").replace("8", " сентября ").replace("9", " октября ");
		 * 
		 * s = s_hh + ":" + s_mm + " " + s_dow + " " + s_dofm + s;
		 */

		return LocalDateTime.now(ZoneId.of("America/Toronto")).format(DateTimeFormatter.ofPattern("MMM dd, HH:mm"));
	}

	public static String time() {
		/*
		 * Date dd = new Date();
		 * 
		 * TimeZone tz = TimeZone.getTimeZone("Europe/Moscow"); Calendar cc =
		 * Calendar.getInstance(tz); cc.setTime(dd); cc.add(Calendar.HOUR, -1);
		 * 
		 * String s_hh = String.valueOf(cc.get(Calendar.HOUR_OF_DAY)); String s_mm =
		 * String.valueOf(String.format("%02d", cc.get(Calendar.MINUTE))); String s_dofm
		 * = String.valueOf(cc.get(Calendar.DAY_OF_MONTH)); String s_dow =
		 * String.valueOf(cc.get(Calendar.DAY_OF_WEEK)).replace("1", "воскресенье")
		 * .replace("2", "понедельник").replace("3", "вторник").replace("4",
		 * "среда").replace("5", "четверг") .replace("6", "пятница").replace("7",
		 * "суббота");
		 * 
		 * String s = String.valueOf(cc.get(Calendar.MONTH)); s = s.replace("10",
		 * " ноября ").replace("11", " декабря ").replace("0", " января ").replace("1",
		 * " февраля ") .replace("2", " марта ").replace("3", " апреля ").replace("4",
		 * " мая ").replace("5", " июня ") .replace("6", " июля ").replace("7",
		 * " августа ").replace("8", " сентября ").replace("9", " октября ");
		 * 
		 * s = s_hh + ":" + s_mm + " " + s_dow + " " + s_dofm + s;
		 */

		// return
		// LocalDateTime.now(ZoneId.of("America/Toronto")).format(DateTimeFormatter.ofPattern("MMM
		// dd, HH:mm"));
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
	}

	static public final String rss_gug(String s) {
		s = rfu_utf(s);
		String sss[] = s.split("</");
		String s11 = "<a href='" + sss[2].substring(1 + sss[2].lastIndexOf(">")) + "'><b>"
				+ sss[0].substring(1 + sss[0].lastIndexOf(">")) + "</a></b><br/><i>"
				+ sss[1].substring(1 + sss[1].lastIndexOf(">")) + "</i><br/><br/>";
		String[] ss = s.split("<item>");
		int i = 0;
		int n = ss.length;
		for (String s2 : ss) {
			try {
				if (i > 0 && i < n) {
					int k = s2.indexOf("</item>");
					String s3 = s2.substring(0, k);
					ss = s3.split("</");

					// int m = 0;
					// for (String s4 : ss) {
					// System.out.println("--- " + m++ + " --->" + Jsoup.parse("</" + s4).text());
					// }

					String link1 = Jsoup.parse("</" + ss[3]).text();
					String link2 = Jsoup.parse("</" + ss[9]).text();
					String link3 = Jsoup.parse("</" + ss[14]).text();
					String date1 = Jsoup.parse("</" + ss[4]).text();
					String traffic = Jsoup.parse("</" + ss[1]).text();
					String title1 = Jsoup.parse("</" + ss[0]).text();
					String title2 = Jsoup.parse("</" + ss[7]).text();
					String title3 = Jsoup.parse("</" + ss[12]).text();
					String whatisthis = Jsoup.parse("</" + ss[5]).text();
					String content1 = Jsoup.parse("</" + ss[2]).text();
					String content2 = Jsoup.parse("</" + ss[8]).text();
					String content3 = Jsoup.parse("</" + ss[13]).text();
					String source1 = Jsoup.parse("</" + ss[6]).text();
					String source2 = Jsoup.parse("</" + ss[10]).text();
					String source3 = Jsoup.parse("</" + ss[15]).text();
					s11 = s11 + "<table><tr><td>&nbsp;<img src='" + whatisthis
							+ "'></td><td valign='top'><div style=\"color:#999999;font-family: Arial;font-size:12px;text-decoration:none;\">"
							+ "<a href='" + link1
							+ "' style=\"color:#0044bb;font-family: Arial;font-size:15px;text-decoration:none;\" target=\"_blank\"><b>"
							+ title1 + "</b></a><br /><i>" + date1 + "<br />" + traffic + "<br />" + source1 + "<br />"
							+ content1 + "</i></div></td></tr></table>"

							+ "<table><tr><td></td><td valign='top'><div><a href='" + link2
							+ "' style=\"color:#0044bb;font-family: Arial;font-size:15px;text-decoration:none;\" target=\"_blank\"><b>"
							+ title2
							+ "</b></a>&nbsp;</div><div style=\"color:#999999;font-family: Arial;font-size:12px;\"><i>"
							+ source2
							+ "</i><div style=\"color:#222222;font-family: Arial;font-size:13px;\">&nbsp;&nbsp;&nbsp;&nbsp;"
							+ content2 + "</div></td></tr><tr><td></td><td valign='top'><div><a href='" + link3
							+ "' style=\"color:#0044bb;font-family: Arial;font-size:15px;text-decoration:none;\" target=\"_blank\"><b>"
							+ title3
							+ "</b></a>&nbsp;</div><div style=\"color:#999999;font-family: Arial;font-size:12px;\"><i>"
							+ source3 + "</i></div>"
							+ "<div style=\"color:#222222;font-family: Arial;font-size:13px;\">&nbsp;&nbsp;&nbsp;&nbsp;"
							+ content3 + "</div></td></tr></table><hr/>";
				}
			} catch (Exception e) {
				System.out.println("-------------------------------> " + e.toString());
			}
			i++;
		}
		return s11;
	}

	public static String w2ma(String subj, String s) {
		s = s.replace("<hr/>", "qqq").replace("<br />", "qqq").replace("<table>", "qqq");
		s = Jsoup.parse(s).text();
		s = s.replace("qqq", "\r\n");
		if (s.length() > 5555)
			s = s.substring(0, 5555);
		s = rss.w2a(subj, s);
		return s;
	}

	public static String[] frmt(String s, String begin, String end) throws Exception {

		s = s.replace("<div>", "_qqq_").replace("</div>", "_qqq_").replace("<br />", "_qqq_").replace("&nbsp;", " ")
				.replace("&amp;", "&");
		s = Jsoup.parse(s).text();

		int i = s.indexOf(begin);
		s = s.substring(i);
		i = s.indexOf(end);
		s = s.substring(0, i);

		String sss = "";
		String[] ss = s.split("_qqq_");
		for (String sa : ss)
			if (sa.contains("http"))
				sss = sss + sa.trim() + "_qqq_";
		ss = sss.split("_qqq_");
		return ss;
	}

	public static String[] frmt_ddtr(String s, String blog) throws Exception {
		//String sf = "C:\\Users\\ym\\Desktop\\_" + LocalDateTime.now().hashCode() + "_.txt";
		//_info.w2f(sf, s);
		//System.out.println("file -----------> " + sf);
		
		

		int i = s.indexOf(blog + "-rss-begin");
		s = s.substring(i);
		i = s.indexOf(blog + "-rss-end");
		s = s.substring(0, i);
		
		String ps = "_qqq_";
		s = s.replace("<div", ps + "<div");
		s = s.replace("<p", ps + "<p");
		s = s.replace("<br", ps + "<br");
		s = s.replace("<span", ps + "<span");
		
		
		s = Jsoup.parse(s).text();
		
		String[] ss = s.split(ps);
		s = "";
		for (String sa : ss) {
			sa = sa.trim();
			if (sa.indexOf("http") == 0)
				s = s + sa.trim() + ps;
		}
		ss = s.split(ps);
		return ss;
	}

	static public final String rss_s(String s, int i) {
		String ss = "", link = "", title = "", content = "", tt = "";
		int ii = -1;
		try {
			// s=s.replace("</item>","<pubDate>Sun, 10 Oct 2021 06:52:04
			// +0000</pubDate>\r\n</item>");

			s = rss.rfu_utf(s);

			SyndFeed feed = new SyndFeedInput().build(new StringReader(s));

			// SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(s)));
			List<SyndEntry> synd_entry = feed.getEntries();
			tt = feed.getTitle();

			if (tt.contains("Google Alert - "))
				i = i + 2;

			tt = tt.replace("RSS", "").replace("Google Alert - ", "G-alt ");

			for (Object o : synd_entry) {

				Date d = ((SyndEntryImpl) o).getPublishedDate();

				// LocalDateTime localDateTime =
				// d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

				LocalDateTime localDateTime = d.toInstant().atZone(ZoneId.of("America/New_York")).toLocalDateTime();

				boolean bb = localDateTime.isAfter(LocalDateTime.now().minus(Duration.ofHours(i)));

				// System.out.println("-- Date -> " + d + " -->> " + bb);

				if (bb) {

					link = ((SyndEntryImpl) o).getLink();
					title = ((SyndEntryImpl) o).getTitle();
					SyndContent synd_content = ((SyndEntryImpl) o).getDescription();

					List<SyndContent> sclist = null;

					if (synd_content != null)
						content = ((SyndEntryImpl) o).getDescription().getValue();
					else {
						sclist = ((SyndEntryImpl) o).getContents();
						ii = sclist.size();
						if (ii > 0)
							content = sclist.get(0).getValue();
						else
							content = "";
						content = content.replace("<b>", "").replace("</b>", "").replace("Read more", "");
					}
					if (content.length() > 1)
						content = fit(content);

					String dtm = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT)
							.format(localDateTime);

					// System.out.println(dtm);
					ss = ss + "<table><tr><td>&nbsp;</td><td valign='top'><div style=\"color:#777777;font-family: Arial;font-size:13px;text-decoration:none;\">"
							+ dtm + "<br /></div></td></tr><tr><td></td><td valign='top'><div><a href='" + link
							+ "' style=\"color:#0044bb;font-family: Arial;font-size:15px;text-decoration:none;\" target=\"_blank\"><b>"
							+ title + "</b></a>&nbsp;<br /></div>"
							+ "<div style=\"color:#222222;font-family: Arial;font-size:13px;\">&nbsp;&nbsp;&nbsp;&nbsp;"
							+ content + "<br /></div></td></tr></table><hr/>";

					/*
					 * <td valign='top'><a href='" + link +
					 * "' target='_blank'><img src='http://3.bp.blogspot.com/-UEeXrZLtJCM/Xe5qS93LHBI/AAAAAAABSr4/ei1k8POBBBom8OIZmbbTRLQZVZEUKEviACK4BGAYYCw/s770/rss2.png' /></a></td>"
					 * + "
					 */

				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			w2a("RSS Error", s + "\r\n" + e.toString());
		}

		return "<table><tr><td valign='top'><b>" + tt + "</b><hr/>" + ss + "</td></tr></table>";
	}

	static public final String fit(String s) {

		Document doc = Jsoup.parse(s);
		for (Element img : doc.select("img")) {
			if (img.attr("width").length() > 0)
				try {
					if (Integer.parseInt(img.attr("width")) > 240)
						img.attr("width", "240");
				} catch (Exception e) {
					w2a("Error rss.fit(IMG width)", img.attr("width") + " " + e.toString());
				}
			else {
				img.attr("width", "240");

			}
			img.removeAttr("height");
		}
		for (Element iframe : doc.select("iframe")) {
			//iframe.attr("width", "560");
			//iframe.attr("height", "315");
			iframe.attr("width", "420");
			iframe.attr("height", "240");
			
			iframe.removeAttr("style");
		}
		for (Element div : doc.select("div")) {
			div.removeAttr("style");
		}

		return doc.toString();
	}

	static public final String rss_gam(String s, int i) {

		System.out.println("-- RSS -> " + s);

		String ss = "", link = "", title = "", content = "", tt = "";
		int ii = -1;
		try {

			SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(s)));
			List<SyndEntry> synd_entry = feed.getEntries();
			tt = feed.getTitle();

			for (Object o : synd_entry) {
				Date d = ((SyndEntryImpl) o).getPublishedDate();

				// LocalDateTime localDateTime =
				// d.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

				LocalDateTime localDateTime = d.toInstant().atZone(ZoneId.of("America/New_York")).toLocalDateTime();

				boolean bb = localDateTime.isAfter(LocalDateTime.now().minus(Duration.ofHours(i)));

				// System.out.println("-- Date -> " + d + " -->> " + bb +" " +
				// DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL,
				// FormatStyle.SHORT).format(localDateTime));
				if (bb) {
					link = ((SyndEntryImpl) o).getLink();
					title = ((SyndEntryImpl) o).getTitle();
					SyndContent synd_content = ((SyndEntryImpl) o).getDescription();
					List<SyndContent> sclist = null;
					if (synd_content != null)
						content = ((SyndEntryImpl) o).getDescription().getValue();
					else {
						sclist = ((SyndEntryImpl) o).getContents();
						ii = sclist.size();
						if (ii > 0)
							content = sclist.get(0).getValue();
						else
							content = "";
						content = content.replace("<b>", "").replace("</b>", "").replace("Read more", "");
					}
					if (content.length() > 1)
						content = fig(content);

					String dtm = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT)
							.format(localDateTime.atZone(ZoneId.of("America/New_York")));
					String dt = tt + "    " + dtm;
					System.out.println(dtm);
					ss = ss + "<table><tr><td>&nbsp;</td><td valign='top'><div style=\"color:#777777;font-family: Arial;font-size:13px;text-decoration:none;\">"
							+ dt + "</div></td></tr><tr><td></td><td valign='top'><div><a href='" + link
							+ "' style=\"color:#0044bb;font-family: Arial;font-size:15px;text-decoration:none;\" target=\"_blank\"><b>"
							+ title + "</b></a>&nbsp;<br/><br /></div>"
							+ "<div style=\"color:#222222;font-family: Arial;font-size:13px;\">&nbsp;&nbsp;&nbsp;&nbsp;"
							+ content + "<br /></div><br /><hr/></td></tr></table>";
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			rss.w2a("RSS_h Error", "rss.zz_servlet_gam\r\n" + s + "\r\n" + e.toString());
		}

		return "<table><tr><td valign='top'>" + ss + "</td></tr></table>";
	}

	static public final String fig(String s) {

		Document doc = Jsoup.parse(s);
		for (Element img : doc.select("img")) {
			if (img.attr("width").length() > 0)
				try {
					if (Integer.parseInt(img.attr("width")) > 640)
						img.attr("width", "640");
				} catch (Exception e) {
					rss.w2a("Error kust.fit(IMG width)", img.attr("width") + " " + e.toString());
				}
			else {
				img.attr("width", "640");

			}
			img.removeAttr("height");
		}
		for (Element iframe : doc.select("iframe")) {
			iframe.attr("width", "560");
			iframe.attr("height", "315");
			iframe.removeAttr("style");
		}
		for (Element div : doc.select("div")) {
			div.removeAttr("style");
		}

		return doc.toString();
	}
	
	public static List<String> get_keys_of(String kind) {
		  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		  Query q = new Query(kind);
		  PreparedQuery pq = datastore.prepare(q);
		  List<Entity> eee =pq.asList(FetchOptions.Builder.withDefaults());		  //.withLimit(5)
		
		  //String s="";
		 // for (Entity e: eee)
		//	  s=s+ e.getKey().getName() +"<br />";
		 // return s; 
		  
		  List<String> result = new ArrayList<String>();
		  for (Entity entity : eee) {
		    //if (!entity.getKey().getName().startsWith("_"))
		      result.add(entity.getKey().getName());
		  }
		  return result;
		}

	public static Date get_date(String table, String id, String field) {
	
		String s = "";
		Date d = null;
		Key kk = KeyFactory.createKey(table, id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			d = (Date) datastore.get(kk).getProperties().get(field);
		} catch (EntityNotFoundException e) {
			s = e.toString();
			rss.w2a("error", "s_get('...') " + s);
		}
		return d;
	}

	public static Long get_int(String table, String id, String field) {
	
		Long i = (long) 0;
	
		Key kk = KeyFactory.createKey(table, id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			i = (Long) datastore.get(kk).getProperties().get(field);
		} catch (EntityNotFoundException e) {
			rss.w2a("error", e.toString());
		}
		return i;
	}

	public static String get_text(String table, String id, String field) {
	
		String s = "";
		Key kk = KeyFactory.createKey(table, id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			Text txt = (Text) datastore.get(kk).getProperties().get(field);
			s = txt.getValue();
		} catch (EntityNotFoundException e) {
			s = e.toString();
			rss.w2a("error", "s_get('...') " + s);
		}
		return s;
	}

	public static void page_update(String id, Text txt, Date d) {
	
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Entity zzz = new Entity("pages", id);
		zzz.setProperty("html", txt);
		zzz.setProperty("last_update", d);
		ds.put(zzz);
	}

	public static void put_text_date(String table, String id, String field1, Text txt, String field2, Date d) {
	
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
	
		Entity zzz = new Entity(table, id);
		zzz.setProperty(field1, txt);
		zzz.setProperty(field2, d);
		ds.put(zzz);
	}

	public static void put_txt_hmqqq(String table, String id, String text_field, Text text_value, String h, int h_value,
			String m, int m_value) {
	
		Entity zzz = new Entity(table, id);
		zzz.setProperty(text_field, text_value);
		zzz.setProperty(h, h_value);
		zzz.setProperty(m, m_value);
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
			rss.w2a("error", "s_get('...') " + s);
		}
		return s;
	}

	public static void s_put(String table, String id, String field, String value) {
	
		Entity zzz = new Entity(table, id);
		zzz.setProperty(field, value);
		DatastoreServiceFactory.getDatastoreService().put(zzz);
	}

	public static void s_put_date(String table, String id, String field1, String value1, String field2, Date dt) {
	
		Entity zzz = new Entity(table, id);
		zzz.setProperty(field1, value1);
		zzz.setProperty(field2, dt);
		DatastoreServiceFactory.getDatastoreService().put(zzz);
	}

	public static void s_put2(String table, String id, String field1, String value1, String field2, String value2) {
	
		Entity zzz = new Entity(table, id);
		zzz.setProperty(field1, value1);
		zzz.setProperty(field2, value2);
		DatastoreServiceFactory.getDatastoreService().put(zzz);
	}

	public static boolean time2do(String id, int n) {
	
		// LocalDateTime now = new
		// Date().toInstant().atZone(ZoneId.of("America/New_York")).toLocalDateTime();
		LocalDateTime dt_now = LocalDateTime.now(ZoneId.of("America/New_York"));
		// LocalDateTime dd2 =
		// LocalDateTime.now(ZoneId.of("America/New_York")).plus(Duration.ofHours(n));
	
		String s = "";
		Date d = null;
		Key kk = KeyFactory.createKey("blogtime", id);
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			d = (Date) datastore.get(kk).getProperties().get("dt_last");
			LocalDateTime dt_last_plus = (d.toInstant().atZone(ZoneId.of("America/New_York"))).toLocalDateTime()
					.plus(Duration.ofMinutes(n));
	
			if (dt_now.isAfter(dt_last_plus)) {
				Entity zzz = new Entity("blogtime", id);
				zzz.setProperty("dt_last", new Date());
				datastore.put(zzz);
				return true;
			} else
				return false;
		} catch (Exception e) {
			Entity zzz = new Entity("blogtime", id);
			zzz.setProperty("dt_last", new Date());
			datastore.put(zzz);
			s = e.toString();
			rss.w2a(s, "");
			return false;
		}
	}

	static public final String fit_gamesnews(String s) {
	
		Document doc = Jsoup.parse(s);
		for (Element img : doc.select("img")) {
			if (img.attr("width").length() > 0)
				try {
					if (Integer.parseInt(img.attr("width")) > 680)
						img.attr("width", "680");
				} catch (Exception e) {
					w2a("Error rss.fit(IMG width)", img.attr("width") + " " + e.toString());
				}
			else {
				img.attr("width", "680");
	
			}
			img.removeAttr("height");
		}
		for (Element iframe : doc.select("iframe")) {
			iframe.attr("width", "560");
			iframe.attr("height", "315");
		
			
			iframe.removeAttr("style");
		}
		for (Element div : doc.select("div")) {
			div.removeAttr("style");
		}
	
		return doc.toString();
	}

	static public final String rss_h2(String s, int i, String key) {
	
		System.out.println("-- RSS -> " + s);
	
		String ss = "", link = "", title = "", content = "", tt = "", dtm = "", dt = "";
		int ii = -1;
		try {
			// w2ma("RSS_h", s);
			SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(s)));
			List<SyndEntry> synd_entry = feed.getEntries();
			tt = feed.getTitle();
			if (tt.contains("Google Alert - "))
				i = i + 2;
			tt = tt.replace("RSS", "").replace("Google Alert - ", "G-Alt ");
			for (Object o : synd_entry) {
				Date d = ((SyndEntryImpl) o).getPublishedDate();
				LocalDateTime localDateTime = d.toInstant().atZone(ZoneId.of("America/New_York")).toLocalDateTime();
				boolean bb = localDateTime.isAfter(LocalDateTime.now().minus(Duration.ofHours(i)));
	
				if (bb) {
					link = ((SyndEntryImpl) o).getLink();
					title = ((SyndEntryImpl) o).getTitle();
					SyndContent synd_content = ((SyndEntryImpl) o).getDescription();
					List<SyndContent> sclist = null;
					if (synd_content != null)
						content = ((SyndEntryImpl) o).getDescription().getValue();
					else {
						sclist = ((SyndEntryImpl) o).getContents();
						ii = sclist.size();
						if (ii > 0)
							content = sclist.get(0).getValue();
						else
							content = "";
						content = content.replace("<b>", "").replace("</b>", "").replace("Read more", "");
					}
					if (content.length() > 1)
						if(key.contains("gamesnews"))
							content = fit_gamesnews(content);
						else
							content = fit(content);
					
					dtm = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT)
							.format(localDateTime);
					dt = tt + "    " + dtm;
	
					System.out.println("--- " + bb + "-----> " + dtm);
					
					ss = ss + "<tr><td><hr/><div style=\"color:#777777;font-family: Arial;font-size:13px;text-decoration:none;\">"
							+ dt + "</div><div><a href='" + link
							+ "' style=\"color:#0044bb;font-family: Arial;font-size:15px;text-decoration:none;\" target=\"_blank\"><b>"
							+ title + "</b></a>&nbsp;<br/><br /></div>"
							+ "<div style=\"color:#222222;font-family: Arial;font-size:13px;\">&nbsp;&nbsp;&nbsp;&nbsp;"
							+ content + "</div><div></div></td></tr>";
							//+ content + "<br /><br /></div></td></tr></table><hr/>";
					
								
					
				} else
					System.out.println("--- " + bb + "-----> " + dtm);
			}
		} catch (Exception e) {
			 System.out.println(e.toString());
			// w2ma("RSS_h Error", e.toString());
		}
		return "<table>"+ss+"</table>";
	}

}