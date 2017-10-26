import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class WebScraper {

	public WebScraper() {
		
	}
	
	public String[][] getHeroes() {	
		Document allHerosDoc;
		try {
			allHerosDoc = Jsoup.connect("http://www.dotabuff.com/heroes/").get();
			Element heroesElement = allHerosDoc.body().child(0).child(7).child(2).child(2).child(1).child(0);
			int numberOfHeroes = heroesElement.childNodeSize() - 1;
			String[][] names = new String[2][numberOfHeroes];
			
			for (int i = 0; i < numberOfHeroes; i++) {
				Element heroElement = heroesElement.child(i);
				names[0][i] = heroElement.child(0).child(0).html();
				String baseHTML = heroElement.html();
				names[1][i] = baseHTML.substring(baseHTML.indexOf("heroes/") + 7, baseHTML.indexOf("-"));
			}
			return names;		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Match[] getAvailableMatches() {
		Document allMatchesDoc;
		try {
			allMatchesDoc = Jsoup.connect("https://www.dotabuff.com/matches?game_mode=all_pick&lobby_type=ranked_matchmaking").get();
			Element matchesElement = allMatchesDoc.body().child(0).child(7).child(2).child(0).child(2).child(1);
			int numberOfMatches = matchesElement.childNodeSize();
			for (int i = 0; i < numberOfMatches; i++) {
				Element matchElement = matchesElement.child(i);
				System.out.println(matchElement);
				System.out.println();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return null;
	}
}
