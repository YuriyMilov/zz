package qq;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "qq.run", urlPatterns = { "/run" })
public class run extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		//http://localhost:8080/tt?u=

		String srvr = request.getServerName();
		if (srvr.indexOf("local")>-1)
			srvr="http://localhost:8080/tt?u=";
		else
			srvr="https://"+srvr+"/tt?u=";

		String s = "https://ont.ddtor.com/p/run.html";

		try {
			String[] ss=rss.frmt(rss.rfu_utf(s), "---begin---", "---end---");
		
			for (String s2 : ss)
					new Thread(new qq.trd(ss,8)).start();
			
		} catch (Exception e) {
			rss.w2ma("run Exception", s + " \r\n " + e.toString());
		}
		PrintWriter wr = response.getWriter();
		wr.print(request.getProtocol() +"  :   "+ request.getServerName()+":"+request.getServerPort() + "/tt?u=  <br /><br />" + new java.util.Date().toString() + " <br /><a href=" + s+">"+s+"</a>");
		wr.close();
	}
}