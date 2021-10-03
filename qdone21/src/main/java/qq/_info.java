package qq;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class _info {

	public static void main(String[] args) throws Exception {
		String s="";
		
		s= "https://www.google.com/alerts/feeds/04888279385366086103/14719186293862211996";
				
			s= rss.rss_h(s, 2);
			rss.w2f("C:\\Users\\ym\\Desktop\\___qqqqqqqqq___.html", s);
			
		System.out.println("------------------ OK ----------------------");

		
	}
	
	public static void get_url(String s, int i) throws Exception
	{
		s="https://www.google.com/alerts/feeds/04888279385366086103/14719186293862211996";
	s = rss.rss_all(s, i);
	}
	public static void get_file(String s, int i) throws Exception
	{
	
		// "https://blog.feedspot.com/healthy_living_rss_feeds/";
		s = "C:\\Users\\win10\\Desktop\\qqq.txt";
		s = String.join("\r\n", Files.readAllLines(Paths.get(s)));
		get_all_from_file(s);

		// s= "C:\\Users\\win10\\Desktop\\qqq.txt";
		// s = String.join("\n\r", Files.readAllLines(Paths.get(s)));
		//System.out.println(s);

		System.out.println("------------------ OK ----------------------");
		
		//FileWriter fw = new FileWriter("C:\\Users\\win10\\Desktop\\qq-44.txt");
		//fw.write(sgood);
		//fw.close();

		System.out.println("=====****  OK  ****=======");
	}
	public static void get_feeds(String s, int i) throws Exception
	{
	s = rss.rss_all(s, i);
	}
	
	public static void get_all_from_file(String s) throws Exception {
		String[] sss = s.split("\r\n");
		//String ss = "";
		String s3 = "", ss3 = "";
		for (String s2 : sss) {
					s3 = rss.rss_h(s2, 6);
					ss3 = ss3 + s3;
				//	ss = ss + s2 + "\r\n";
				System.out.println("\r\n-----------------------\r\n");
			}

		FileWriter fw = new FileWriter("C:\\Users\\win10\\Desktop\\zzz.txt.html");
		fw.write(ss3);
		fw.close();
		//System.out.println(ss3);





	};
	
	public static void get_feeds(String s) throws Exception {

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
	};
	
}
