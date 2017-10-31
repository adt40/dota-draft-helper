package analysis;

public class HeroValuePair {

	public int hero;
	public double value;
	
	public HeroValuePair() {
		hero = 0;
		value = 0;
	}
	
	public HeroValuePair(int hero, double value) {
		this.hero = hero;
		this.value = value;
	}
	
	public HeroValuePair copy() {
		return new HeroValuePair(hero, value);
	}
	
	public static int[] getHeroList(HeroValuePair[] input) {
		int[] heroes = new int[input.length];
		for (int i = 0; i < input.length; i++) {
			heroes[i] = input[i].hero;
		}
		return heroes;
	}
	
	public static double[] getValueList(HeroValuePair[] input) {
		double[] values = new double[input.length];
		for (int i = 0; i < input.length; i++) {
			values[i] = input[i].value;
		}
		return values;
	}
	
	public static HeroValuePair[] sortByValue(HeroValuePair[] input) {
		int i = 1;
		while (i < input.length) {
			int j = i;
			while (j > 0 && input[j - 1].value < input[j].value) {
				HeroValuePair temp = input[j - 1].copy();
				input[j - 1] = input[j].copy();
				input[j] = temp;
				j--;
			}
			i++;
		}
		return input;
	}
}
