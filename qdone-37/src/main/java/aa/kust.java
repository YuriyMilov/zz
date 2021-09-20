package aa;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class kust {
 
	public static void main(String[] args) throws Exception{
		
		
		//String s = "https://www.realwire.com/rss/?id=184&row=&view=Synopsis";
		String s="http://gamesnews.quicklydone.com/p/rss.html";
		
		//s=qq._info.get_last_rss();
		//s=qq._info.get_next_rss(s);
		s=qq._info.get_rss(s);
		
		//String out = new Scanner(new URL(s).openStream(), "UTF-8").useDelimiter("\\A").next();
		//s=out;
		//s= rfu_utf(s);
		
		//s=qq._info.get_rss(s);
		
		
				
		  FileWriter myWriter = new FileWriter("C:\\Users\\ym\\Desktop\\7777777777.html");
	     myWriter.write(s);
	      myWriter.close();
		

		System.out.println(s);
		
		
	}
	public static String rfu_utf(String s) {
		try {
			URL url = new URL(s);

			URLConnection conn = url.openConnection();
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
	public static void s_put(String name, String s) {
	
			 	  Entity zzz = new Entity("aa","bb");
			 	  zzz.setProperty(name, s);     	   
		          DatastoreServiceFactory.getDatastoreService().put(zzz);
	}	
	

	public static String s_get(String name) {
		
			  String s="";
			    	Key kk = KeyFactory.createKey("aa", "bb");    	
			    	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			    	try {
						s=  datastore.get(kk).getProperties().get(name).toString();
					} catch (EntityNotFoundException e) {
						s=e.toString();
						m2a("error","s_get('...') "+s);
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
	
	static public final String get_last_rss_ds() throws Exception {
		
		String s = s_get("last_rss");
		
		 
		 return s;
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

}