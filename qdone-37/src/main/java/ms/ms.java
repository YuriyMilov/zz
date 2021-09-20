package ms;
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
public class ms extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException { 

    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");
    
	String s = "", url = "";

	
    response.getWriter().print(s);
  }
	
}