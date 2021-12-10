package yy;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "yy.servlet_swa", urlPatterns = { "/sea" })

public class servlet_sea extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		String s = request.getQueryString();
		if (s == null)
			s = "polit";
		s = bb.get_text("seagull", s, "page");

			PrintWriter wr = response.getWriter();
		wr.print(s);
		wr.close();
	}
}
