package gg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class gpt {

	public static void main(String... args) throws Exception {
		String prompt = "The following is a spooky story written for kids, just in time for Halloween. Everyone always talks about the old house at the end of the street, but I couldnâ€™t believe what happened when I went inside.";
		prompt = "The following is a conversation with an AI assistant. The assistant is helpful, creative, clever, and very friendly.\r\n"
				+ "\r\nHuman: Hello, who are you?\r\nAI: I am an AI created by OpenAI. How can I help you today?\r\n"
				+ "Human: I'd like to chat with you.\r\nAI:";
		String[] stop = new String[] { "\r\n", "Human: ", "AI: " };// "\n","Human:","AI:" };
		String engine = "curie";// davinci
//String stopstr = "\"n\",\"Human:\",\"AI:\"";
//String[] stop = stopstr.split(",");
		int max_tokens = 333;
		double temperature = 1;
		int top_p = 1;
		double frequency_penalty = 0;
		double presence_penalty = 0;

		String s = prompt
				+ gpt3(prompt, engine, stop, max_tokens, temperature, top_p, frequency_penalty, presence_penalty)
				+ "\r\nHuman: ";
		System.out.println(s);

		while (!s.contains("qqq")) {
			if (s.length() > 444)
				s = "..." + s.substring(111);
			if (s.contains("AI:"))
				s = s.substring(s.indexOf("AI:"));

			prompt = s + read("") + "\r\nAI:";
			
			
			
			String s2 = gpt3(prompt, engine, stop, max_tokens, temperature, top_p, frequency_penalty, presence_penalty);
			s = prompt + s2 + "\r\nHuman: ";
			System.out.println(s);
			
			
			VoiceManager vv = VoiceManager.getInstance();
			System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");  
			Voice voice = vv.getVoice("kevin16");
			voice.allocate();
			voice.speak(s2);
			voice.deallocate();
		}
	}

	public static String read(String s) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		s = br.readLine();

		return s;
	}

	public static String gpt3(String prompt, String engine, String[] stop, int max_tokens, double temperature,
			int top_p, double frequency_penalty, double presence_penalty) {
		try {
			URL url = new URL("https://api.openai.com/v1/engines/" + engine + "/completions");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; utf-8");


con.setRequestProperty("Authorization", "Bearer ...code...");

			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);

			JSONObject jsn = new JSONObject();
			jsn.put("max_tokens", max_tokens);
			jsn.put("prompt", prompt);
			jsn.put("temperature", temperature);
			jsn.put("top_p", top_p);
			jsn.put("n", 1);
			jsn.put("stream", false);
			jsn.put("frequency_penalty", frequency_penalty);
			jsn.put("presence_penalty", presence_penalty);
			jsn.put("stop", stop);
			String s = jsn.toString();

			OutputStream os = con.getOutputStream();
			byte[] input = s.getBytes("utf-8");
			os.write(input, 0, input.length);

			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder sb = new StringBuilder();
				String s2 = null;
				while ((s2 = br.readLine()) != null)
					sb.append(s2.trim());

				s = sb.toString();
				s = StringEscapeUtils.unescapeJava(s);

				s = s.substring(s.indexOf("[{\"text\": ") + 11, s.indexOf("\", \"index\":"));

				while (s.contains("\n\n"))
					s = s.replace("\n\n", "\n");
				
				s = s.replace("â€™", "'").replace("â€œ", "'").replace("â€", "'");	
				
				
				
				return s +" ...";
			}
		} catch (Exception ex) {
			return ex.toString();
		}
	}

	public static void wf(String f, String s) throws Exception {
		PrintWriter writer = new PrintWriter(f, "UTF-8");
		writer.print(s);
		writer.close();
	}

}
