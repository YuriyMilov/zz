package qq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;

//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;
//import com.google.api.services.blogger.Blogger;
//import com.google.api.services.blogger.BloggerScopes;

@WebServlet(name = "qq.post", urlPatterns = { "/post" })
public class post extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
/*
		if (_info._info.equals("_info._info")) {
			new _info();
			_info._info = _info._info + " " + "\r\n " + LocalDateTime.now().toLocalTime().toString();
		} else
			_info._info = _info._info + "\r\n " + LocalDateTime.now().toLocalTime().toString();

		String s = Jsoup.parse(_info.last_page).text();
		if (s.length() > 333)
			s = s.substring(0, 333);
*/
		String url = "https://www.googleapis.com/blogger/v3/blogs/7037838639333350381/pages?key=AIzaSyD_oTX6PU2dQK3HRQ6fbqTzJpduw32ySAg";
		HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();

		httpClient.setRequestMethod("POST");
		httpClient.setRequestProperty("Accept", " application/json");
		httpClient.setRequestProperty("x-client-data",
				"CIe2yQEIo7bJAQjEtskBCKmdygEImbXKAQj/vMoBCJm9ygEIir/KAQjnyMoBCPDJygEItMvKARjov8oB");

		// int responseCode = httpClient.getResponseCode();

		String jsonInputString = "{\"title\": \"Upendra\", \"content\": \"Programmer\"}";

		httpClient.setDoOutput(true);

		try (OutputStream os = httpClient.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		}

		try (BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()))) {

			StringBuilder sb = new StringBuilder();
			String line;

			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
			// print result
		String s = sb.toString();
			PrintWriter wr = response.getWriter();
			//wr.print(_info._info + "\r\n" + s);
			wr.print(_info._info + "\r\n" + s);
				wr.close();
		}
		
/*
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			System.out.println(response.toString());
		}
*/
	}
}