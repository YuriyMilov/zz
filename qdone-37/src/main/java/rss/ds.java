package rss;
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
    name = "rss.ds",
    urlPatterns = {"/ds"}
)
public class ds extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
      throws IOException { 

	 	  
    response.setContentType("text/plain");
    response.setCharacterEncoding("UTF-8");
    String table="", id="", field="",value="";
    try {
    	table=request.getParameter("table");
    	id=request.getParameter("id");
    	field=request.getParameter("field");
    	value=request.getParameter("value");
	if (value==null)		
		{value=kust.s_get(table,id,field);
		response.getWriter().print("get value OK: " + value);}
	else
		{kust.s_put(table,id,field,value);
		response.getWriter().print("put value OK: "+value);}
	
	
   	
	} catch (Exception e) {
		
	response.getWriter().print("Error "+ e.toString() + "  \r\n\r\n" + kust.m2a("error", e.toString()));
	}
    
    
  }
	
}