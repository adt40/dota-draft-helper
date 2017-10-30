import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BackendRunner {

	public static void main(String[] args) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date startTime = new Date();
		Logger.Log("Starting at " + dateFormat.format(startTime));
		
		WebScraper webScraper = new WebScraper();
		HeroLookup.populate(webScraper.getHeroes());
		
		Match[] matches = webScraper.getAvailableMatches();
		
		Graph graph = new Graph();
		Logger.Log("Found " + matches.length + " new matches");
		Logger.Log("Updating " + (matches.length * 65) + " weights");
		for (int i = 0; i < matches.length; i++) {
			graph.updateWeights(matches[i]);
		}
		
		graph.save();
		
		Date endTime = new Date();
		Logger.Log("Finished at " + dateFormat.format(endTime) + "\n");
	}
}
