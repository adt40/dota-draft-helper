
public class Runner {

	public static void main(String[] args) {
		WebScraper webScraper = new WebScraper();
		Graph graph = new Graph();
		
		HeroLookup.populate(webScraper.getHeroes());
		
		Match[] matches = webScraper.getAvailableMatches();
		for (int i = 0; i < matches.length; i++) {
			System.out.println(matches[i].toString());
		}
		
		for (int i = 0; i < matches.length; i++) {
			graph.updateWeights(matches[i]);
		}
		
		graph.save();
		
	}

}
