
public class Runner {

	public static void main(String[] args) {
		WebScraper webScraper = new WebScraper();
		HeroLookup heroLookup = new HeroLookup();
		Graph graph = new Graph();
		
		heroLookup.populate(webScraper.getHeroes());
		
		Match[] matches = webScraper.getAvailableMatches();
		
		for (int i = 0; i < matches.length; i++) {
			graph.updateWeights(matches[i]);
		}
		
		graph.save();
	}

}
