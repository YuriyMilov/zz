package yy;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;

@WebServlet(name = "yy.servlet_bb", urlPatterns = { "/bb" })

public class servlet_bb extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		String s = request.getQueryString();
		if (s == null)
			s = "polit";
		s = bb.get_text("pages", s, "html");

		String ads = "<script async src=\"https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-2500695075596096\" crossorigin=\"anonymous\"></script><!-- bb.ddtor.com --> <ins class=\"adsbygoogle\"  style=\"display:block\" data-ad-client=\"ca-pub-2500695075596096\"  data-ad-slot=\"5150340667\" data-ad-format=\"auto\" data-full-width-responsive=\"true\"></ins><script>  (adsbygoogle = window.adsbygoogle || []).push({}); </script>";

		PrintWriter wr = response.getWriter();
		wr.print(ads + s);
		wr.close();
	}
}
