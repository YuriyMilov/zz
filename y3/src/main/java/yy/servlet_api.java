package yy;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

@WebServlet(name = "yy.servlet_api", urlPatterns = { "/api" })
public class servlet_api extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		

String s=get("hello");
		
		PrintWriter w = response.getWriter();
		w.print(s);
		w.close();
	}
	
	public static String get(String s) {

		HttpResponse response=null;
		
		try {
			StringEntity params = new StringEntity(
					// "{\"prompt\": \"Once upon a time\",\n"

					"{\"prompt\": \"" 
					+ s 
					+ "\",\n" 
					+ "\"max_tokens\": 111,\n"
					+ "\"temperature\": 0.7,\n"
					+ "\"top_p\": 1,\n"
					+ "\"n\": 1,\n" 
					+ "\"stream\": false,\n"
					+ "\"logprobs\": null,\n" 
					+ "\"stop\": \"\\n\"} ");

			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost("https://api.openai.com/v1/engines/davinci/completions");
			request.addHeader("Authorization", "Bearer xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			request.addHeader("Content-Type", "application/json; charset=utf-8");
			//request.addHeader("Accept", "application/json");
			//request.addHeader("Accept", "charset=utf-8");
		
			
			request.setEntity(params);
			response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			//s = EntityUtils.toString(entity);
			
			//InputStream is = entity.getContent();
	
				
			byte[] bb = EntityUtils.toByteArray(entity);
			
			System.out.println("привет " + Arrays.toString(bb));
			 s= "привет " + new String(bb, StandardCharsets.UTF_8);
			
			//s= EntityUtils.toString(entity, StandardCharsets.UTF_8);
			 
			
		} catch (Exception ex) {
			System.out.println(ex.toString());
		
		}
		return s;
	}
}