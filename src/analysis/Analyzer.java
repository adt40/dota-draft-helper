package analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import training.HeroLookup;
import training.Logger;

public class Analyzer {

	private double[][][] weights;
	
	public static final int WEIGHT_AGAINST = 0;
	public static final int WINS_AGAINST = 1;
	public static final int LOSSES_AGAINST = 2;
	public static final int WEIGHT_WITH = 3;
	public static final int WINS_WITH = 4;
	public static final int LOSSES_WITH = 5;
	
	public Analyzer() {
		weights = new double[HeroLookup.NumberOfHeroes][HeroLookup.NumberOfHeroes][6];
		File weightsFile = new File("weights.dat");
		if (weightsFile.exists()) {
			load();
		} else {
			//Why are you running this without any weights calculated???
			//TODO: add a case for this later when I care
		}
	}
	
	public double getSingleStat(int hero1, int hero2, int stat) {
		return weights[hero1][hero2][stat];
	}
	
	public HeroValuePair[] getNBestHeroes(int hero, int N, int stat) {
		HeroValuePair[] bestHero = new HeroValuePair[N];
		for (int i = 0; i < bestHero.length; i++) {
			bestHero[i] = new HeroValuePair();
		}
		for (int i = 0; i < HeroLookup.NumberOfHeroes; i++) {
			double weight = weights[hero][i][stat];
			int lowestValueIndex = getIndexOfLowestValue(HeroValuePair.getValueList(bestHero));
			if (weight > bestHero[lowestValueIndex].value) {
				bestHero[lowestValueIndex].hero = i;
				bestHero[lowestValueIndex].value = weight;
			}
		}
		return HeroValuePair.sortByValue(bestHero);
	}
	
	private int getIndexOfLowestValue(double[] input) {
		int indexOfLowestValue = 0;
		for (int i = 0; i < input.length; i++) {
			if (input[i] < input[indexOfLowestValue]) {
				indexOfLowestValue = i;
			}
		}
		return indexOfLowestValue;
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
			Logger.Log(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Logger.Log(e.getMessage());
		}
	}
}
