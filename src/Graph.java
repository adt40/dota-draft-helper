
public class Graph {

	double[][][] weights;
	
	
	public Graph() {
		boolean b = false;
		if (b) { //file exists
			load();
		} else {
			for (int i = 0; i < HeroLookup.NumberOfHeroes; i++) {
				for (int j = 0; j < HeroLookup.NumberOfHeroes; j++) {
					weights[i][j][0] = 0.5; //weight
					weights[i][j][1] = 0;	//wins
					weights[i][j][2] = 0;	//losses
				}
			}
		}
	}
	
	public void load() {
		
	}
	
	public void save() {
		
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
		for (int i = 0; i < 5; i++) {
			for (int k = 0; k < 5; k++) {
				int wh = winningTeam[i];
				int lh = losingTeam[k];
				weights[wh][lh][1]++;
				weights[lh][wh][2]++;
				
				int wins = (int)weights[wh][lh][1];
				int losses = (int)weights[lh][wh][2];
				double newWeight = sigmoid(wins, losses);
				
				weights[wh][lh][0] = newWeight;
				weights[lh][wh][0] = newWeight;
			}
		}
	}
	
	private double sigmoid(int wins, int losses) {
		return 1 / (1 + Math.pow(Math.E, -(wins - losses)));
	}
}
