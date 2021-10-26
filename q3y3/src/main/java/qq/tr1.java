package qq;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class tr1 implements Runnable {
	public static int i = 0;
	public static HttpServletResponse resp;
	
	public void run(){
		try {
		while (i++ < 100) {
			System.out.println(i);
			Thread.sleep(555);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
