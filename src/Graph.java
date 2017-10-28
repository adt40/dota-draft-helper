import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Graph {

	private double[][][] weights;
	
	public Graph() {
		weights = new double[HeroLookup.NumberOfHeroes][HeroLookup.NumberOfHeroes][6];
		File weightsFile = new File("weights.dat");
		if (weightsFile.exists()) {
			load();
		} else {
			for (int i = 0; i < HeroLookup.NumberOfHeroes; i++) {
				for (int j = 0; j < HeroLookup.NumberOfHeroes; j++) {
					if (i != j) {
						weights[i][j][0] = 0.5; //weight against
						weights[i][j][1] = 0;	//wins against
						weights[i][j][2] = 0;	//losses against
						weights[i][j][3] = 0.5; //weight with
						weights[i][j][4] = 0; 	//wins with
						weights[i][j][5] = 0;	//losses with
					} else {
						//This can't happen since each hero is only picked once. Set weights to zero so it is never an option
						weights[i][j][0] = 0;
						weights[i][j][1] = 0;
						weights[i][j][2] = 0;
						weights[i][j][3] = 0;
						weights[i][j][4] = 0;
						weights[i][j][5] = 0;
					}
					
				}
			}
		}
	}
	
	public void load() {
		try {
			FileReader fr = new FileReader("weights.dat");
			BufferedReader br = new BufferedReader(fr);
			
			String line = "";
			int i = 0;
			int j = 0;
			while((line = br.readLine()) != null) {
				String[] splitLine = line.split(" ");
				for (int k = 0; k < 6; k++) {
					weights[i][j][k] = Double.parseDouble(splitLine[k]);
				}
				j++;
				if (j % HeroLookup.NumberOfHeroes == 0) {
					j = 0;
					i++;
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void save() {
		FileWriter fw;
		try {
			File weightsFile = new File("weights.dat");
			if (!weightsFile.exists()) {
				weightsFile.createNewFile();
			}
			
			fw = new FileWriter("weights.dat");
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i = 0; i < weights.length; i++) {
				for (int j = 0; j < weights[i].length; j++) {
					String line = "";
					for (int k = 0; k < 6; k++) {
						line += weights[i][j][k] + " ";
					}
					bw.write(line + "\n");
				}
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateWeights(Match match) {
		int[] winningTeam;
		int[] losingTeam;
		if (match.getWinner() == Team.RADIANT) {
			winningTeam = match.getRadiantTeam();
			losingTeam = match.getDireTeam();
		} else {
			winningTeam = match.getDireTeam();
			losingTeam = match.getRadiantTeam();
		}
		//weights for "against" heroes (opposing teams)
		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 5; k++) {
				int wh = winningTeam[i];
				int lh = losingTeam[k];
				weights[wh][lh][1]++;
				weights[lh][wh][2]++;
				
				int winnerWins = (int)weights[wh][lh][1];
				int winnerLosses = (int)weights[wh][lh][2];
				double winnerNewWeight = sigmoid(winnerWins, winnerLosses);
				
				int loserWins = (int)weights[lh][wh][1];
				int loserLosses = (int)weights[lh][wh][2];
				double loserNewWeight = sigmoid(loserWins, loserLosses);
				
				weights[wh][lh][0] = winnerNewWeight;
				weights[lh][wh][0] = loserNewWeight;
			}
		}
		
		//weights for "with" heroes (same team)
		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 5; k++) {
				if (i == k) {
					continue;
				}
				//winners
				int whi = winningTeam[i];
				int whk = winningTeam[k];
				weights[whi][whk][4]++;
				
				int winnerWins = (int)weights[whi][whk][4];
				int winnerLosses = (int)weights[whi][whk][5];
				double winnerNewWeight = sigmoid(winnerWins, winnerLosses);
				
				weights[whi][whk][3] = winnerNewWeight;
				
				//losers
				int lhi = losingTeam[i];
				int lhk = losingTeam[k];
				weights[lhi][lhk][5]++;
				
				int loserWins = (int)weights[lhi][lhk][4];
				int loserLosses = (int)weights[lhi][lhk][5];
				double loserNewWeight = sigmoid(loserWins, loserLosses);
				
				weights[lhi][lhk][3] = loserNewWeight;
			}
		}
	}
	
	private double sigmoid(int wins, int losses) {
		return 1 / (1 + Math.pow(Math.E, -(wins - losses)));
	}
}
