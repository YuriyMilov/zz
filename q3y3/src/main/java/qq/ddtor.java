package qq;

import java.time.LocalDateTime;

public class ddtor {

	public static void main(String[] args) throws Exception {
		String s = "https://www.ddtor.com/rss", blog = "", h = "", m = "";
		s = rss.rfu_utf(s);
		String s5=s;
	//	System.out.println(s);
		String[] ss = rss.frmt_ddtr(s, "blogs");
		if (ss != null) {
			
			for (String s2 : ss) {
				s = s2.substring(s2.indexOf("?blog=") + 6);
				blog = s.substring(0, s.indexOf("&"));
				s = s.substring(s.indexOf("&h=") + 3);
				h = s.substring(0, s.indexOf("&"));
				m = s.substring(s.indexOf("&m=") + 3);
				
				


				System.out.println("\r\n---->  "+blog + " " + h + " " + m);
				//System.out.println(s2);
				
				String s7="";
				String[] sss=rss.frmt_ddtr(s5, blog);				
				for (String s3 : sss)
					{
					System.out.println(s3);
					s7 = s7 + rss.rss_h(s3, Integer.parseInt(h));
					}
				//System.out.println(s7);
				String sf = "C:\\Users\\ym\\Desktop\\_" + LocalDateTime.now().hashCode() + "_.html";
				 _info.w2f(sf, s7);
			}
			
		} else
			System.out.println("ss==null");
		new ddtor("222");
		System.out.println("--- ok ---");
	}

	public ddtor(String s) {
		// System.out.println(s);
	}

}
