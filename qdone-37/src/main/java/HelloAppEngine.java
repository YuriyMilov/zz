import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aa.kust;

@WebServlet(
    name = "HelloAppEngine",
    urlPatterns = {"/hello"}
)
public class HelloAppEngine extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException {

    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");
	String s = "ok";
	try {
		s=kust.s_get("last_rss");
		response.getWriter().print(s);
	} catch (Exception e) {
		kust.m2a("error", "kust.s_get('last_rss') " + e.toString());				
	}   

  }
  
  
}  