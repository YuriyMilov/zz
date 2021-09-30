package ku;

public class get_all_from_url {

	public static void main(String[] args) throws Exception {
		String s = "";
		// s = "https://polit.ddtor.com/p/blog-page_21.html";
		// s="https://gamesnews.quicklydone.com/p/rss-2_27.html";
		//s = "https://ont.ddtor.com/p/rss.html";
		//s = "https://gamesnews.quicklydone.com/p/rss.html";
		s = "https://zmt.ddtor.com/p/rss.html";
		

		s = rss.rss_all(s, 8);
		
		
		rss.w2f("C:\\Users\\win10\\Desktop\\___qqqqqqqqq___.html", s);
		System.out.println("------------------ OK ----------------------");
	}

}
