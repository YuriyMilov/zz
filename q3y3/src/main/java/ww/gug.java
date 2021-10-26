package ww;

import org.jsoup.Jsoup;

import qq._info;
import qq.rss;

public class gug {

	public static void main(String[] args) throws Exception {
String s22="";
		//String s = qq._info.rff("C:\\Users\\ym\\Desktop\\zzz.txt");
		String s= "https://trends.google.com/trends/trendingsearches/daily/rss";
		s = "https://trends.google.com/trends/trendingsearches/daily/rss?geo=CA";

		s=rss.rfu_utf(s);

		String sss[] = 	s.split("</");
		System.out.println(">>>>>>>>>>> TITLE --------------->"+sss[0].substring(1+sss[0].lastIndexOf(">")));
		System.out.println(">>>>>>>>>>> DESCRIPTION --------->"+sss[1].substring(1+sss[1].lastIndexOf(">")));
		System.out.println(">>>>>>>>>>> LINK ------- -------->"+sss[2].substring(1+sss[2].lastIndexOf(">")));
		System.out.println("****************************************************************");
		String s11="<a href='"+sss[2].substring(1+sss[2].lastIndexOf(">"))+"'><b>"+sss[0].substring(1+sss[0].lastIndexOf(">"))+"</a></b><br/><i>"+sss[1].substring(1+sss[1].lastIndexOf(">"))+"</i><br/><br/>";
		String[] ss = s.split("<item>");
		int i = 0, m=0;
		int n = ss.length;
		
		for (String s2 : ss) {
			
			
			

			if (i > 0 && i < n) {
				int k = s2.indexOf("</item>");
				String s3 = s2.substring(0, k);
				//	System.out.println("------------------------------->  "+s3.split("</").length );
					ss=s3.split("</");
				for (String s4 : ss) {
					System.out.println("*** " + m++ + " ***" +s4);
			}
				
				//System.out.println("\r\n--------------------------------------------\r\n");
				System.out.println(s3);

				String link1 = Jsoup.parse("</"+ss[3]).text();
				String link2 = Jsoup.parse("</"+ss[9]).text();
				String link3 = Jsoup.parse("</"+ss[14]).text();
				
				String date1 =Jsoup.parse("</"+ss[4]).text();
				String traffic =Jsoup.parse("</"+ss[1]).text();
				
				
				String title1 = Jsoup.parse("</"+ss[0]).text();
				String title2 = Jsoup.parse("</"+ss[7]).text();
				String title3 = Jsoup.parse("</"+ss[12]).text();
				String whatisthis=Jsoup.parse("</"+ss[5]).text();
				Jsoup.parse("</"+ss[2]).text();
				String content2 = Jsoup.parse("</"+ss[8]).text();
				String content3 = Jsoup.parse("</"+ss[13]).text();
				
				Jsoup.parse("</"+ss[6]).text();
				Jsoup.parse("</"+ss[10]).text();
				String source3 = Jsoup.parse("</"+ss[15]).text();
				
				
				s22 =   "<table><tr><td>&nbsp;<img src='" + whatisthis + "'></td><td valign='top'><div style=\"color:#999999;font-family: Arial;font-size:12px;text-decoration:none;\">"
						+ "<a href='" + link1 + "' style=\"color:#0044bb;font-family: Arial;font-size:15px;text-decoration:none;\" target=\"_blank\"><b>"
						+ title1 + "</b></a><br /><i>" + date1  + "<br />" + traffic + "</i></div></td></tr></table>"
								
						+ "<table><tr><td></td><td valign='top'><div><a href='" + link2
						+ "' style=\"color:#0044bb;font-family: Arial;font-size:15px;text-decoration:none;\" target=\"_blank\"><b>"
						+ title2 + "</b></a>&nbsp;</div><div style=\"color:#999999;font-family: Arial;font-size:12px;\"><i>"
						+ source3 +"</i><div style=\"color:#222222;font-family: Arial;font-size:13px;\">&nbsp;&nbsp;&nbsp;&nbsp;"
						+ content2 + "</div></td></tr><tr><td></td><td valign='top'><div><a href='" + link3
						+ "' style=\"color:#0044bb;font-family: Arial;font-size:15px;text-decoration:none;\" target=\"_blank\"><b>"
						+ title3 + "</b></a>&nbsp;</div><div style=\"color:#999999;font-family: Arial;font-size:12px;\"><i>"+ source3 +"</i></div>"
						+ "<div style=\"color:#222222;font-family: Arial;font-size:13px;\">&nbsp;&nbsp;&nbsp;&nbsp;"
						+ content3 + "</div></td></tr></table><hr/>";
				 System.out.println(i + "  -- >   " + s22.length() + "   <---");
				
				s11=s11 +s22;			}
			i++;
		}

		_info.w2f("C:\\Users\\ym\\Desktop\\____OUT___.html",s22);
		 System.out.println("--------------  ALL -- > " + s11.length() + "   <---------------");

	}

}
