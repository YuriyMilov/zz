package qq;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "qq.servlet_run", urlPatterns = { "/run" })
public class servlet_run extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		
		List<String> kkk = rss.get_keys_of("ddtor");
		
		PrintWriter wr = response.getWriter();
		
		for(String s: kkk)		{	
			new Thread(new cron(s)).start();
			wr.print(s+ " ");		
		}
		 
		wr.print("\r\n------------------\r\n OK");
		wr.close();
	}
}