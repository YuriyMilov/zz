package ku;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class main {

	public static void main(String[] args) throws Exception {

		// s=ku.rfu_utf("https://blog.feedspot.com/poker_rss_feeds/");
		// Document dd = Jsoup.parse();

		String s = Files.readString(Path.of("C:\\Users\\ym\\Desktop\\qqq.txt"));
		String[] sss = s.split(". <a class=\"tlink\" href=\"");
		int i = sss.length;
		//System.out.println(i);
		i = 0;
		String ss = "";
String s3="",ss3="";
		for (String s2 : sss) {
			if (i++ > 0 && i < sss.length) {

				int n = s2.indexOf("site:");
				s2 = s2.substring(n);
				n = s2.indexOf("\" target=");
				s2 = s2.substring(5, n);
				s2 = s2.replace("%3A", ":").replace("%2F", "/").replace("%3F", "?").replace("%3D", "=");

				if (s2.length() > 4)
					{
					
					
					if(!s2.contains("www.poker52.fr"))
						s3 = ku.rss_h(s2, 12);
					else
						
					 System.out.println("\r\n-------------- HA_HA_HA!!!! :) --------->>> "+s2+" <<<-----------------------\r\n");
					
					
					
					
					
					ss3=ss3+s3;
					ss = ss + s2 + "\r\n";}
				// System.out.println("\r\n-----------------------\r\n-----------------------\r\n");
			}
		}
		
//		FileWriter fw = new FileWriter("C:\\Users\\ym\\Desktop\\zzz.txt");
//		fw.write(ss);
//		fw.close();
//		System.out.println(ss);

		FileWriter fw = new FileWriter("C:\\Users\\ym\\Desktop\\zzz.txt.html");
		fw.write(ss3);
		fw.close();
		System.out.println(ss);

		

	}
}
