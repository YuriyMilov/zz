package qq;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;

@WebServlet(name = "qq.zz_servlet_bb", urlPatterns = { "/bb" })
public class zz_servlet_bb extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
String ss="";
	String s = rss.rfu_utf("https://ont.ddtor.com/p/tt.html");
						int i = s.indexOf("---begin---<br />");

						if (i > -1) {
							s = s.substring(i);
							i = s.indexOf("---end---");
							s = s.substring(0, i);
							s = s.replace("---begin---", "").replace("---end---", "").trim();
						}

						for (String s2 : s.split("<br />"))
						{ 
							s2=s2.replace("&amp;", "&");
							System.out.println("--------->  "+s2+"  <----------");
							
							if (s2.indexOf("http") == 0)
								ss=ss+=rss.rfu_utf(s2);
						}
							
						
						System.out.println("--------->  END  <----------");
						
		PrintWriter wr = response.getWriter();
		wr.print(ss);
		wr.close();
	}

}