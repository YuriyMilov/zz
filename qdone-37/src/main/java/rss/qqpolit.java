package rss;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "rss.qqpolit", urlPatterns = { "/polit" })
public class qqpolit extends HttpServlet {
 
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		//String shttp = request.getScheme() + "" + "://" + request.getServerName() + ":" + request.getServerPort()+ request.getContextPath();

		String hs=request.getQueryString();
		if(hs==null)
			hs="2";
			
		String s = kust.rfu_utf("https://qdone-37.appspot.com/rss?u=https://polit.ddtor.com/p/blog-page_21.html&a=ymilov.k9992.tverskoy@blogger.com&h="+hs);

		PrintWriter wr = response.getWriter();
		wr.print(s);
		wr.close();
	}
}