package ku;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class get_all_from_file {

	public static String sgood="";
	
	public static void main(String[] args) throws Exception {
		String s = "";
		// "https://blog.feedspot.com/healthy_living_rss_feeds/";

		s = "C:\\Users\\win10\\Desktop\\qqq.txt";
		s = String.join("\r\n", Files.readAllLines(Paths.get(s)));
		get_all_from_file(s);

		// s= "C:\\Users\\win10\\Desktop\\qqq.txt";
		// s = String.join("\n\r", Files.readAllLines(Paths.get(s)));
		//System.out.println(s);
		
		
		
		System.out.println("------------------ OK ----------------------");
		FileWriter fw = new FileWriter("C:\\Users\\win10\\Desktop\\qq-44.txt");
		fw.write(sgood);
		fw.close();
		System.out.println(get_all_from_file.sgood);
		System.out.println("=====****  OK  ****=======");
		
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
}
//