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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

public class kust {
 
	public static void main(String[] args) throws Exception{
		String s = "https://www.realwire.com/rss/?id=184&row=&view=Synopsis";
		s="http://gamesnews.quicklydone.com/p/rss.html";
		
		s=qq._info.get_last_rss();
		s=qq._info.get_next_rss(s);
		
		//String out = new Scanner(new URL(s).openStream(), "UTF-8").useDelimiter("\\A").next();
		//s=out;
		//s= rfu_utf(s);
		
		s=qq._info.get_rss(s);
		
		
				
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
	public static String s_put(String name, String s) {
			  try {
			 	  Entity zzz = new Entity("aa","bb");
			 	  zzz.setProperty(name, s);     	   
		          DatastoreServiceFactory.getDatastoreService().put(zzz);
		    } catch (Exception e) {
				return e.toString();
			}
				return "ok";
	}	
	

	public static String s_get(String name) {
		
			  String s="";
			    try {
			    	Key kk = KeyFactory.createKey("aa", "bb");    	
			    	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			    	s=  datastore.get(kk).getProperties().get(name).toString();
			    } catch (Exception e) {
					s= e.toString();
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

}