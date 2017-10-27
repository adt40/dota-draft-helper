import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
				String baseHTML = heroElement.html().split("\n")[0];
				names[1][i] = baseHTML.substring(baseHTML.indexOf("heroes/") + 7, baseHTML.lastIndexOf("-"));
			}
			return names;		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Match[] getAvailableMatches() {
		ArrayList<Match> matches = new ArrayList<Match>();
		Document allMatchesDoc;
		try {
			allMatchesDoc = Jsoup.connect("https://www.dotabuff.com/matches?game_mode=all_pick&lobby_type=ranked_matchmaking").get();
			Element matchesElement = allMatchesDoc.body().child(0).child(7).child(2).child(0).child(2).child(1);
			int numberOfMatches = matchesElement.childNodeSize();
			for (int i = 0; i < numberOfMatches; i++) {
				Element matchElement = matchesElement.child(i);
				String IDChunk = matchElement.child(0).html();
				long ID = Long.parseLong(getValueFromChunk(IDChunk));

				if (!isRecentMatch(ID)) {
					String winnerChunk = matchElement.child(2).html();
					String winnerString = getValueFromChunk(winnerChunk);
					Team winner = Team.RADIANT;
					if (winnerString.equals("Radiant Victory")) {
						winner = Team.RADIANT;
					} else {
						winner = Team.DIRE;
					}
					
					Element radiantTeamElement = matchElement.child(4);
					int[] radiantTeam = new int[5];
					for (int k = 0; k < 5; k++) {
						String radiantHeroChunk = radiantTeamElement.child(k).html();
						radiantTeam[k] = HeroLookup.getIDBySystemName(radiantHeroChunk.substring(radiantHeroChunk.indexOf("/heroes/") + 8, radiantHeroChunk.indexOf("\">")));
					}
					
					Element direTeamElement = matchElement.child(5);
					int[] direTeam = new int[5];
					for (int k = 0; k < 5; k++) {
						String direHeroChunk = direTeamElement.child(k).html();
						direTeam[k] = HeroLookup.getIDBySystemName(direHeroChunk.substring(direHeroChunk.indexOf("/heroes/") + 8, direHeroChunk.indexOf("\">")));
					}
					
					matches.add(new Match(ID, winner, radiantTeam, direTeam));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Object[] objArr = matches.toArray();
		Match[] matchArr = new Match[objArr.length];
		for (int i = 0; i < matchArr.length; i++) {
			matchArr[i] = (Match)objArr[i];
		}
		saveRecentMatches(matchArr);
		return matchArr;
	}
	
	private String getValueFromChunk(String chunk) {
		return chunk.substring(chunk.indexOf("\">") + 2, chunk.indexOf("</a>"));
	}
	
	
	private long[] loadRecentMatches() {
		ArrayList<Long> ids = new ArrayList<Long>();
		try {
			File recentMatchesFile = new File("recent_matches.dat");
			if (!recentMatchesFile.exists()) {
				recentMatchesFile.createNewFile();
			}
			
			FileReader fr = new FileReader("recent_matches.dat");
			BufferedReader br = new BufferedReader(fr);
			
			String line = "";
			while((line = br.readLine()) != null) {
				ids.add(Long.parseLong(line));
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Object[] objArr = ids.toArray();
		long[] idArr = new long[objArr.length];
		for (int i = 0; i < idArr.length; i++) {
			idArr[i] = (long)objArr[i];
		}
		return idArr;
	}
	
	private void saveRecentMatches(Match[] matches) {
		FileWriter fw;
		try {
			File recentMatchesFile = new File("recent_matches.dat");
			recentMatchesFile.createNewFile();
			fw = new FileWriter("recent_matches.dat", true);
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < matches.length; i++) {
				bw.append(matches[i].getID() + "\n");
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private boolean isRecentMatch(long ID) {
		boolean found = false;
		long[] recentMatches = loadRecentMatches();
		for (int k = 0; k < recentMatches.length; k++) {
			if (recentMatches[k] == ID) {
				found = true;
				break;
			}
		}
		return found;
	}
}

