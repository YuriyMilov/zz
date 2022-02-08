package gg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

@WebServlet(name = "gg.servlet_voice", urlPatterns = { "/v" })

public class servlet_voice extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		VoiceManager vv = VoiceManager.getInstance();
		System.setProperty(
		        "freetts.voices",
		        "com.sun.speech.freetts.en.us"
		            + ".cmu_us_kal.KevinVoiceDirectory");  
		Voice[] voices = vv.getVoices();
		System.out.println(voices.length);
		 for (int i = 0; i < voices.length; i++) {
		     System.out.println("    " + voices[i].getName() + " (" + voices[i].getDomain() + " domain)");
		 }
		 
		 
		Voice voice = vv.getVoice("kevin16");
		 voice.allocate();
		voice.speak("hello");
		voice.deallocate();
	

	PrintWriter wr = response.getWriter();
	wr.print("ok");
	wr.close();
	}
}