package qq;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class _info { 

	public static void main(String[] args) throws Exception {
		String s = "", ss = "", qq = "";
		int h = 8;
		String u = "https://gamesnews.quicklydone.com/p/rss.html";
	//	u="https://ont.ddtor.com/p/rss.html";
	//  u="https://poker.ddtor.com/p/rss.html";
				qq = u.substring(u.indexOf("//") + 2, u.indexOf("."));
				s = rss.rfu_utf(u);
				int i = s.indexOf("---begin---<br />");

				if (i > -1) {
					s = s.substring(i);
					i = s.indexOf("---end---");
					s = s.substring(0, i);
					s = s.replace("---begin---", "").replace("---end---", "").trim();
				}
				
					for (String s2 : s.split("<br />")) 
						if(s2.length()>5)
					//	if (qq.contains("gamesnews"))
					//		ss = ss + rss.rss_h(s2, h);
					//	else					
							ss = ss + rss.rss_gam(s2, h);
					
				//ss = "<html><body><table><tr><td valign='top'>" + ss + "</td></tr></table></body></html>";
	w2f("C:\\Users\\ym\\Desktop\\____OUT_qdone21_qq._info.main__.html", ss);
		
		
	
		System.out.println("------------------ OK  ----------------------");
	}

	public static String get_url(String s, int i) throws Exception {
		s = rss.rss_h(s, i);
		return s;
	}

	public static void f2f(String f1, String f2, int i) throws Exception {
		String ss="";
		for(String s2: Files.readAllLines(Paths.get(f1)))
		{
			s2 = rss.rss_h(s2, i);
			ss=ss+s2+"<br />";
		}
		w2f(f2,ss);		
	}

	public static void get_feedspot(String s) throws Exception {

		// s=ku.rfu_utf(s);

		String[] sss = s.split(". <a class=\"tlink\" href=\"");
		int i = sss.length;
		// System.out.println(i);
		i = 0;
		int j = sss.length;
		String ss = "";
		String s3 = "", ss3 = "";
		for (String s2 : sss) {
			if (i++ > 0 && i < j) {

				int n = s2.indexOf("site:");
				s2 = s2.substring(n);
				n = s2.indexOf("\" target=");
				s2 = s2.substring(5, n);
				s2 = s2.replace("%3A", ":").replace("%2F", "/").replace("%3F", "?").replace("%3D", "=");

				if (s2.length() > 4) {
					if (!s2.contains("https://www.credihealth.com/blog/feed/"))
						s3 = rss.rss_h(s2, 48);
					ss3 = ss3 + s3;
					ss = ss + s2 + "\r\n";
				}
				System.out.println("\r\n-----------------------\r\n-----------------------\r\n");
			}
		}

//		FileWriter fw = new FileWriter("C:\\Users\\ym\\Desktop\\zzz.txt");
//		fw.write(ss);
//		fw.close();
//		System.out.println(ss);

		FileWriter fw = new FileWriter("C:\\Users\\win10\\Desktop\\zzz.txt.html");
		fw.write(ss3);
		fw.close();
		System.out.println(ss);
	}

	public static String rff(String s) throws Exception {
		return String.join("\r\n", Files.readAllLines(Paths.get(s)));
	}

	public static void w2f(String f, String s) throws Exception {
		FileWriter myWriter = new FileWriter(f);
		myWriter.write(s);
		myWriter.close();
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
	

}
