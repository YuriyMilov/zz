package aa;
import java.io.IOException;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
 
@WebServlet(
    name = "aa.aa",
    urlPatterns = {"/aa"}
)
public class aa extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException { 

    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");
    String s="", n="";
    try {
    	n=request.getParameter("n");
    	s=request.getParameter("s");
	if (s==null)
		s=kust.s_get(n);
	else
		kust.s_put(n, s);
	
   	//m2a("aa", s);
   	
	} catch (Exception e) {
		s= e.toString();
	}
    
    response.getWriter().print(s);
  }
	
}