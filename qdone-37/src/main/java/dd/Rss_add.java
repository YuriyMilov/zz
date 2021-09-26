package dd;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dd.stkl;


//import com.google.gwt.user.server.Base64Utils;
@WebServlet(
	    name = "zz.Rss_add",
	    urlPatterns = {"/rss"}
	)

public class Rss_add extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String s=_info.get_all_new_rss();
		//stkl.sts=s;
		stkl.mail = s + stkl.mail;
		 
		resp.setContentType("text/html; charset=utf-8");
		PrintWriter writer = resp.getWriter();
		resp.setCharacterEncoding("UTF-8");
		writer.write(s);
		writer.close();
		resp.flushBuffer();
		
		 // resp.setContentType("text/html");
		 //   resp.setCharacterEncoding("UTF-8");
		    //resp.getWriter().print(stkl.mail);
		  //  resp.getWriter().print(s);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}
	
	private static final long serialVersionUID = 1L;

}