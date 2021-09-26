package rss;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "rss.qqgm", urlPatterns = { "/gm" })
public class qqgm extends HttpServlet {
 
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		//String shttp = request.getScheme() + "" + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	
		String hs=request.getQueryString();
		if(hs==null)
			hs="8";
	
		String s = kust.rfu_utf("https://qdone-37.appspot.com/rss?u=https://galamil.blogspot.com/p/rss.html&a=galamil.qdone@blogger.com&h="+hs);
		
		PrintWriter wr = response.getWriter();
		wr.print(s);
		wr.close();
	}
}