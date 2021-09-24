package dd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.jsoup.Jsoup;

import dd.stkl;

// GAE ddtor3 admin at ddtor quick-0
// github.com/YuriyMilov
// bb.ddtor.com
// bb.feofan.com

public class _info {

	// public static String semailto = "k9992.tverskoy@blogger.com";
	// public static String semailfrom = "k999.quicklydone@gmail.com";


	public static void main(String[] args) throws Exception {
		
		//String s = get_new_rss("http://novorossia-novosti.com/feed/");
	//	String s = get_new_rss("https://www.mississauga.com/rss/article?category=news");
		
		// String s=""; 
		// s = get_all_new_rss();
		 
		 FileWriter myWriter = new FileWriter("C:\\Users\\ym\\Desktop\\7777777777.html");
	      myWriter.write("s");
	      myWriter.close();
	      
		 System.out.println(1111);
	}
	

	
	public static void main2(String[] args) throws Exception {
		//get_new_rss("http://www.kp.ru/rss/msk-politics.xml");
		 String s = "http://novorossia-novosti.com/feed/";
		// s="http://www.kp.ru/rss/msk-politics.xml";
	
	
		int i = 0, k = 0;
		 s = get_new_rss(s);

		//String s = "";
		//stkl.sts = s;
		stkl.mail = s + stkl.mail;
		while (k++ < 15) {
			i = 0;
			while (i++ < 12) {
				s = _info.get_all_new_rss();
				//stkl.sts = s;
				stkl.mail = s + stkl.mail;

				BufferedWriter br = Files.newBufferedWriter(
						Paths.get("C:\\Users\\Yuriy\\Desktop\\qq\\" + i + ".txt.html"), StandardCharsets.UTF_8);
				br.write(s + "\r\n");
				br.close();

				////////////////////////////////////////////
				System.out.println("---> " + i + " write s to i.txt.html and sleep 5 min.");
				////////////////////////////////////////////

				Thread.sleep(600000);
			}

			BufferedWriter br = Files.newBufferedWriter(
					Paths.get("C:\\Users\\Yuriy\\Desktop\\qq\\stkl.mail." + k + ".txt.html"), StandardCharsets.UTF_8);
			br.write(stkl.mail);
			br.close();
			System.out.println("*********** " + k + " *************");
			System.out.println("*********** write stkl.mail to  stkl.mail.txt.html and sleep 30 min. *************");
			System.out.println("*********** " + k + " *************");
			Thread.sleep(1800000);
		}

	}

	public static String get_all_new_rss() {

		String s = "https://polit.ddtor.com/p/blog-page_21.html";
		//String s = "https://www.ddtor.com/p/blog-page_21.html";
		//s="http://k999blog.blogspot.com/p/blog-page_21.html";
				s=rfu_utf(s);
				
					
		s = stkl.get_first(s, "<!--qqq_rss1_qqq-->","<!--qqq_rss2_qqq-->").replace("<br />", "");

		//stkl.get_first(stkl.rfu_utf("http://www.ddtor.com/p/blog-page_21.html"), "<!-- qqq_rss1_qqq -->",
		//"<!-- qqq_rss2_qqq -->").replace("<br />", "");

		int i = s.indexOf("http");

	//	if (i < 0)	
	//		return s +" *************  " +String.valueOf(i);

			
			
			
			  
			 
		String[] sss = s.substring(i + 4).split("http");
		s = "";
		String s2 = "";
		for (String ss : sss) {
			
			
			s2 = get_new_rss("http"+ss);

			/////////////////////////////////////////////////////////
			// System.out.println("http" + ss);
			/////////////////////////////////////////////////////////

			s = s + s2 + "\r\n";
		}
		return s;
	
	}

	public static String get_new_rss(String s) {
		String s55=s, s3 = "", s4 = "", tit = "", dat = "", title = "", link = "", description = "";
		try {

			s = rfu_utf(s);

			int i = s.indexOf("<title>");
			if (i < 0)
				return "";
			tit = s.substring(i + 7);
			i = tit.indexOf("</title>");
			tit = tit.substring(0, i);

			///////////////////////////////////////////////
			// System.out.println(tit);
			///////////////////////////////////////////////

			i = s.indexOf("<item>");
			if (i < 0)
				return "";

			String[] sss = s.substring(i + 6).split("<item>");

			for (String s2 : sss) {

				int k = s2.indexOf("<pubDate>");
				if (k > -1)
					dat = s2.substring(k + 9);
				k = dat.indexOf("</pubDate>");
				if (k > -1)
					dat = dat.substring(0, k);

				boolean bb = date_old(dat);

				if (!bb) {

					if (s2.contains("<title>")) {
						i = s2.indexOf("<title>");
						title = s2.substring(i + 7);
					}

					if (title.contains("</title>")) {
						i = title.indexOf("</title>");
						title = title.substring(0, i);
					}

					if (s2.contains("<link>")) {
						i = s2.indexOf("<link>");
						link = s2.substring(i + 6);
						i = link.indexOf("</link>");
						link = link.substring(0, i);
					}

					if (s2.contains("<description>")) {
						i = s2.indexOf("<description>");
						description = s2.substring(i + 13);
						i = description.indexOf("</description>");
						description = description.substring(0, i);

						description = description.replace("<![CDATA[", "");
						description = description.replace("]]>", "");
						description = description.replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\"");

						description = Jsoup.parse(description).text();

						if (description.length() > 444)
							description = description.substring(0, 444) + "...";

					}

					s3 = "<div><table><tr><td valign='top'>" 
					
							+ "<br/><a href='" + link
							+ "' target='_blank'><img src='http://3.bp.blogspot.com/-UEeXrZLtJCM/Xe5qS93LHBI/AAAAAAABSr4/ei1k8POBBBom8OIZmbbTRLQZVZEUKEviACK4BGAYYCw/s770/rss2.png' /></a>"
							+ "</td>"
							
							+ "<td>&nbsp;</td>"
							+ "<td valign='top'>"
							
							+ "<div style=\"color:#aaaaaa;font-family: Arial;font-size:13px;text-decoration:none;\">"
							+ "<i>" + tit + "</i>"
							
									+ "<br/>"
									
									+ "<a href='" + link
							+ "' style=\"color:#0044bb;font-family: Arial;font-size:14px;text-decoration:none;\" target=\"_blank\"><b>"
							+ title + "</b></a>"
							
									+ "</div>"
							
							+ "<div style=\"color:#222222;font-family: Arial;font-size:13px;\">&nbsp;&nbsp;&nbsp;&nbsp;"
							+ Jsoup.parse(description).text() 
							+ "</div>"
							
							+ "</td></tr></table></div><hr/>";

	
				//	if (stkl.mail.contains(title))
				//		System.out.print("title EXISTS  --------->   " + title + " \r\n");
				//	else {
				//		System.out.print("NEW title  --------->   " + title + " \r\n");
						s4 = s4 + s3;
				//	}
				}
			}
		} catch (Exception e) {
			s4 = s55;// e.toString();
		}

		return s4;
	}

	@SuppressWarnings("unused")
	public static boolean date_old(String dt) {
		// dt = "Thu, 19 Mar 2015 19:41:42 GMT";
		// dt = "20 Mar 2015 19:53:19 +0300";
		boolean bb = false;
		Calendar cc = Calendar.getInstance();
		cc.add(Calendar.HOUR, -1);
		Date dc = cc.getTime();
		Date d2 = new Date();
		try {
			d2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").parse(dt);
		} catch (ParseException e) {
			try {
				d2 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss zzz").parse(dt);
			} catch (ParseException e1) {
				System.err.println(e1.toString());
			}
		}
		int n = d2.compareTo(dc);
		if (n == -1)
			bb = true;
		return bb;
	}

	public static String get_date_rus3() {

		Date dd = new Date();

		TimeZone tz = TimeZone.getTimeZone("Europe/Moscow");
		Calendar cc = Calendar.getInstance(tz);
		cc.setTime(dd);
		cc.add(Calendar.HOUR, -1);

		String s_hh = String.valueOf(cc.get(Calendar.HOUR_OF_DAY));
		String s_mm = String.valueOf(String.format("%02d", cc.get(Calendar.MINUTE)));
		String s_dofm = String.valueOf(cc.get(Calendar.DAY_OF_MONTH));
		String s_dow = String.valueOf(cc.get(Calendar.DAY_OF_WEEK)).replace("1", "воскресенье")
				.replace("2", "понедельник").replace("3", "вторник").replace("4", "среда").replace("5", "четверг")
				.replace("6", "пятница").replace("7", "суббота");

		String s = String.valueOf(cc.get(Calendar.MONTH));
		s = s.replace("10", " ноябрь ").replace("11", " декабрь ").replace("0", " январь ").replace("1", " февраль ")
				.replace("2", " март ").replace("3", " апрель ").replace("4", " май ").replace("5", " июнь ")
				.replace("6", " июль ").replace("7", " август ").replace("8", " сентябрь ").replace("9", " октябрь ");

		s = s_hh + ":" + s_mm + " " + s_dow + " " + s_dofm + s;

		return s;
	}



	public static String rfu_utf(String s) {
		try {
			URL url = new URL(s);
	
			URLConnection conn = url.openConnection();
			conn.setReadTimeout(10000); //10 Sec
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf8"));
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

}
