package qq;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Text;

@WebServlet(name = "qq.servlet_bb", urlPatterns = { "/bb" })

public class servlet_bb extends HttpServlet {
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
			s = "polit";
		s = ds_old.get_text("pages", s, "html");

		String ads = "<script async=\"\" src=\"//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js\"></script>"
				+ "<!--728 x 90-->"
				+ "<ins class=\"adsbygoogle\" data-ad-client=\"ca-pub-2500695075596096\" data-ad-slot=\"1013034565\" style=\"display: inline-block; height: 90px; width: 640px;\"></ins>"
				+ "<script>  (adsbygoogle = window.adsbygoogle || []).push({});  </script>";

		ads = "<script async src=\"https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js?client=ca-pub-2500695075596096\"\r\n"
				+ "     crossorigin=\"anonymous\"></script>\r\n" + "<!-- bb.ddtor.com -->\r\n"
				+ "<ins class=\"adsbygoogle\"\r\n" + "     style=\"display:block\"\r\n"
				+ "     data-ad-client=\"ca-pub-2500695075596096\"\r\n" + "     data-ad-slot=\"5150340667\"\r\n"
				+ "     data-ad-format=\"auto\"\r\n" + "     data-full-width-responsive=\"true\"></ins>\r\n"
				+ "<script>\r\n" + "     (adsbygoogle = window.adsbygoogle || []).push({});\r\n" + "</script>";
		s = ads + s;

		PrintWriter wr = response.getWriter();
		wr.print(s);
		wr.close();
	}
}
