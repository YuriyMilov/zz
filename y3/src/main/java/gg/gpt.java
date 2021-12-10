package gg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

public class gpt {

	public static void main(String... args) throws Exception {

		String prompt = "The following is a spooky story written for kids, just in time for Halloween. Everyone always talks about the old house at the end of the street, but I couldn’t believe what happened when I went inside.";
		//prompt ="раз два";

		String engine = "curie";// davinci
		String[] stop = new String[] { "\r\n","qq" };
		int max_tokens = 333;
		double temperature = 0.7; 
		int top_p = 1;
		double frequency_penalty = 0.0;
		double presence_penalty = 0.0;

		System.out.println("-- " + "prompt" + " --> "
				+ gpt3(prompt, engine, stop, max_tokens, temperature, top_p, frequency_penalty, presence_penalty));
	}

	public static String gpt3(String prompt, String engine, String[] stop, int max_tokens, double temperature,
			int top_p, double frequency_penalty, double presence_penalty) {

		try {

			URL url = new URL("https://api.openai.com/v1/engines/" + engine + "/completions");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Authorization", "Bearer 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");
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
			// jsn.put("stop", "length");

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

				// System.out.println(s);

				// s = s.substring(s.indexOf("[{\"text\": ") + 11, s.indexOf("\", \"index\":"));
				return s;
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
