package gg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

@WebServlet(name = "gg.servlet_gpt2", urlPatterns = { "/gpt2" })

public class servlet_gpt2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		String[] stop = new String[] { "\r\n", "Human: ", "AI: " };

		String s3 ="The following is a spooky story written for kids, just in time for Halloween. Everyone always talks about the old house at the end of the street, but I couldn't believe what happened when I went inside."; 
		String sprompt = request.getParameter("prompt1");
		if (sprompt== null)
			sprompt=s3;

		
		String engine = request.getParameter("engine");
		if (engine == null)
			engine = "curie";// davinci

		String smax_tokens = request.getParameter("max_tokens");
		int max_tokens = 222;
		if (smax_tokens != null)
			max_tokens = Integer.parseInt(smax_tokens);
		int top_p = 1;

		double temperature = 0.8;
		String stemperature = request.getParameter("temperature");
		if (stemperature != null)
			temperature = Double.parseDouble(stemperature);

		double frequency_penalty = 0.5;
		String sfrequency_penalty = request.getParameter("frequency_penalty");
		if (sfrequency_penalty != null)
			frequency_penalty = Double.parseDouble(sfrequency_penalty);

		double presence_penalty = 0.1;
		String spresence_penalty = request.getParameter("presence_penalty");
		if (spresence_penalty != null)
			presence_penalty = Double.parseDouble(spresence_penalty);

		String s = gpt.gpt3(sprompt, engine, stop, max_tokens, temperature, top_p, frequency_penalty, presence_penalty);
		s = s.replace("\n", "\n<br />&nbsp;&nbsp;&nbsp;");

		s = "<form action='/gpt2' method='post'>"
				+ "<textarea name='prompt1' rows='3' cols='88' autofocus>" 
				+ s3
				+ "</textarea><br /><input type='submit' value='Submit' /><br />&nbsp;&nbsp;&nbsp;"+s+"</form>";
		

		PrintWriter wr = response.getWriter();
		wr.print(s);
		wr.close();

	}
}