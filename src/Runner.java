
public class Runner {

	public static void main(String[] args) {
		WebScraper webScraper = new WebScraper();
		HeroLookup.populate(webScraper.getHeroes());
		
		Match[] matches = webScraper.getAvailableMatches();
		
		Graph graph = new Graph();
		System.out.println("Found " + matches.length + " matches");
		for (int i = 0; i < matches.length; i++) {
			graph.updateWeights(matches[i]);
		}
		
		graph.save();
	}
}
