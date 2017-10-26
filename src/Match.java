
public class Match {
	
	private long ID;
	private int winner;
	private int[] radiantTeam;
	private int[] direTeam;
	
	public Match(long ID, int winner, int[] radiantTeam, int[] direTeam) {
		this.ID = ID;
		this.winner = winner;
		this.radiantTeam = radiantTeam;
		this.direTeam = direTeam;
	}

	public long getID() {
		return ID;
	}

	public int getWinner() {
		return winner;
	}

	public int[] getRadiantTeam() {
		return radiantTeam;
	}

	public int[] getDireTeam() {
		return direTeam;
	}
	
	
}
