import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class WebScraper {

	public WebScraper() {
		
	}
	
	public String[] getHeroes() {
		
		Document allHerosDoc;
		try {
			allHerosDoc = Jsoup.connect("http://www.dotabuff.com/heroes/").get();
			Element heroElement = allHerosDoc.body().child(0).child(7).child(2).child(2).child(1).child(0);
			int numberOfHeroes = heroElement.childNodeSize() - 1;
			for (int i = 0; i < numberOfHeroes; i++) {
				
			}
			
			
		
		
		
		
		
		
		
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Match[] getAvailableMatches() {
		return null;
	}
}
