package yy;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "yy.servlet_rss", urlPatterns = { "/rss" })
public class servlet_rss extends HttpServlet {
	private static final long serialVersionUID = 1L;

 
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String s = request.getQueryString(); 
		if (s == null)
			s = "https://rusvesna.su/rss.xml"; 

				if(s.indexOf("trends.google.com")==0)
					s = bb.rss_gug(s);
				else						
					s = bb.rss_h2(s,24,"");

		PrintWriter w = response.getWriter();
		w.print(s);
		w.close();
	}

}