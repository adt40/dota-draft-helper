import java.util.Arrays;

public class Match {
	
	private long ID;
	private Team winner;
	private int[] radiantTeam;
	private int[] direTeam;
	
	public Match(long ID, Team winner, int[] radiantTeam, int[] direTeam) {
		this.ID = ID;
		this.winner = winner;
		this.radiantTeam = radiantTeam;
		this.direTeam = direTeam;
	}

	public long getID() {
		return ID;
	}

	public Team getWinner() {
		return winner;
	}

	public int[] getRadiantTeam() {
		return radiantTeam;
	}

	public int[] getDireTeam() {
		return direTeam;
	}

	@Override
	public String toString() {
		return "Match [ID=" + ID + ", winner=" + winner + ", radiantTeam=" + Arrays.toString(radiantTeam)
				+ ", direTeam=" + Arrays.toString(direTeam) + "]";
	}
	
	
}
