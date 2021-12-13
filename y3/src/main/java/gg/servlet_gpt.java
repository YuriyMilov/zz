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

//		String[] stop = new String[] {"\r\n","Human:","AI:"};// "\n","Human:","GPT-3:" };	
		String[] stop = new String[] {"\n","Human:","AI:"};
//		String stopstr = request.getParameter("stop");
//		if (stopstr != null)
//			stop = stopstr.split(",");	

		String	sprompt = request.getParameter("prompt");
		if (sprompt == null)
			//sprompt = "The following is a spooky story written for kids, just in time for Halloween. Everyone always talks about the old house at the end of the street, but I couldn’t believe what happened when I went inside.";
		
		sprompt = "The following is a conversation with an AI assistant. The assistant is helpful, creative, clever, and very friendly.\r\n"
				+ "\r\nHuman: Hello, who are you?\r\nAI: I am an AI created by OpenAI. How can I help you today?\r\n"
				+ "Human: I'd like to chat with you.\r\nAI:";
		
	/*	
		String style = request.getParameter("style");
		if(style==null)	
		{		sprompt = "The following is a conversation with an AI assistant. The assistant is helpful, creative, clever, and very friendly.\r\n"
				+ "\r\n" + "Human: Hello, who are you?\r\n"
				+ "GPT-3: I am an AI created by OpenAI. How can I help you today?\r\n"
				+ "Human: I'd like to cancel my subscription.\r\n" + "GPT-3:";

		}		
*/
		
		String engine = request.getParameter("engine");
		if (engine == null)
			engine = "curie";// davinci

		String smax_tokens = request.getParameter("max_tokens");
		int max_tokens = 222;
		if (smax_tokens != null)
			max_tokens = Integer.parseInt(smax_tokens);
		int top_p = 1;

		//  String stp = request.getParameter("stp");
		//	if (stp != null)
		//	top_p = Integer.parseInt(stp);

		double temperature = 0.7;
		String stemperature = request.getParameter("temperature");
		if (stemperature != null)
			temperature = Double.parseDouble(stemperature);

		double frequency_penalty = 0.0;
		String sfrequency_penalty = request.getParameter("frequency_penalty");
		if (sfrequency_penalty != null)
			frequency_penalty = Double.parseDouble(sfrequency_penalty);

		double presence_penalty = 0.0;
		String spresence_penalty = request.getParameter("presence_penalty");
		if (spresence_penalty != null)
			presence_penalty = Double.parseDouble(spresence_penalty);

//		String s = "Prompt: <br /><b><i>" + sprompt + "</i></b><br /><br />";
		//s = s + gpt.gpt3(sprompt, engine, stop, max_tokens, temperature, top_p, frequency_penalty, presence_penalty);

		//s=s.replace("\n", "<br />\n");
		//s = s + " ... (c) GPT-3<br /><br /> <a href='https://www.ddtor.com/gpt-3'><b> <== BACK ==[ </b></a>";
	//	String sout= sprompt + "\r\nGPT-3: " + gpt.gpt3(sprompt, engine, stop, max_tokens, temperature, top_p, frequency_penalty, presence_penalty);
		//String sout= gpt.gpt3(sprompt, engine, stop, max_tokens, temperature, top_p, frequency_penalty, presence_penalty);
		String s=yy.bb.rfu_utf("http://"+request.getServerName() + ":" + request.getServerPort() +"/tmp.html");
		
		
		String sout= sprompt + "AI: "+ gpt.gpt3(sprompt, engine, stop, max_tokens, temperature, top_p, frequency_penalty, presence_penalty)+"Human: ";
		
		
		s=s.replace("---qqq---", "" +sout);
		PrintWriter wr = response.getWriter();
		wr.print(s);
		wr.close();

	}
}