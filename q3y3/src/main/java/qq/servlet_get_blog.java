package qq;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;

@WebServlet(name = "qq.servlet_get_blog", urlPatterns = { "/bb" })

public class servlet_get_blog extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		
		String s = request.getQueryString();

			if (s == null)
				s="polit";			
		
			s = ds_old.get_text("pages", s, "html");
				
		PrintWriter wr = response.getWriter();
		wr.print(s);
		wr.close();
		}
	}

