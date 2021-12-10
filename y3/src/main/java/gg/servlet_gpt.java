package gg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "gg.servlet_gpt", urlPatterns = { "/gpt" })

public class servlet_gpt extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");

		String sprompt = request.getParameter("prompt");

		if (sprompt == null)
			sprompt = "The following is a spooky story written for kids, just in time for Halloween. Everyone always talks about the old house at the end of the street, but I couldn’t believe what happened when I went inside.";

		String engine = request.getParameter("engine");
		if (engine == null)
			engine = "curie";// davinci

		String smax_tokens = request.getParameter("max_tokens");
		int max_tokens = 333;
		if (smax_tokens != null)
			max_tokens = Integer.parseInt(smax_tokens);

		String stp = request.getParameter("stp");
		int top_p = 1;
		if (stp != null)
			top_p = Integer.parseInt(stp);

		String[] stop = new String[]{"\\n"};
		String stopstr = request.getParameter("stop");
		if (stopstr != null)
			stop = stopstr.split(",");	
			
		double temperature = 0.7;
		String stemperature = request.getParameter("temperature");
		if (stemperature != null)
			temperature = Double.parseDouble(stemperature);

		double frequency_penalty = 0.5;
		String sfrequency_penalty = request.getParameter("frequency_penalty");
		if (sfrequency_penalty != null)
			frequency_penalty = Double.parseDouble(sfrequency_penalty);

		double presence_penalty = 0.2;
		String spresence_penalty = request.getParameter("presence_penalty");
		if (spresence_penalty != null)
			presence_penalty = Double.parseDouble(spresence_penalty);

		String s = "Prompt: <br /><b><i>" + sprompt + "</i></b><br /><br />";
		s = s + gpt.gpt3(sprompt, engine, stop, max_tokens, temperature, top_p, frequency_penalty, presence_penalty);

		s=s.replace("\n", "<br />\n");
		s = s + " ... (c) GPT-3<br /><br /> <a href='https://www.ddtor.com/gpt-3'><b> <== BACK ==[ </b></a>";

		PrintWriter wr = response.getWriter();
		wr.print(s);
		wr.close();

	}
}