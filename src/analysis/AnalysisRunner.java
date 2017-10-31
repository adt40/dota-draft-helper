package analysis;

import java.util.Scanner;

import training.HeroLookup;
import training.WebScraper;

public class AnalysisRunner {

	public static void main(String[] args) {
		WebScraper webScraper = new WebScraper();
		HeroLookup.populate(webScraper.getHeroes());
		
		Analyzer analyzer = new Analyzer();
		
		int N = 5;
		Scanner reader = new Scanner(System.in);
		String systemName = reader.nextLine();
		String team = reader.nextLine();
		int stat = 0;
		if (team.equals("with")) {
			stat = Analyzer.WEIGHT_WITH;
		} else if (team.equals("against")) {
			stat = Analyzer.WEIGHT_AGAINST;
		}
		
		int heroID = HeroLookup.getIDBySystemName(systemName);
		HeroValuePair[] best = analyzer.getNBestHeroes(heroID, N, stat);
		
		for (int i = 0; i < N; i++) {
			if (team.equals("with")) {
				System.out.println(HeroLookup.getCommonNameByID(best[i].hero) + " " + best[i].value);
			} else if (team.equals("against")) {
				System.out.println(HeroLookup.getCommonNameByID(best[i].hero) + " " + best[i].value);
			}
			
		}
		
		reader.nextLine();
		reader.close();
	}
}
