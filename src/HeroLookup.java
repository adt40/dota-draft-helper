import java.util.HashMap;

public class HeroLookup {

	private static String[] commonNames;
	private static String[] systemNames;
	private static HashMap<String, Integer> commonToID = new HashMap<String, Integer>();
	private static HashMap<String, Integer> systemToID = new HashMap<String, Integer>();
	
	public static int NumberOfHeroes;
	
	public static void populate(String[][] names) {
		commonNames = names[0];
		systemNames = names[1];
		for (int i = 0; i < commonNames.length; i++) {
			commonToID.put(commonNames[i], i);
			systemToID.put(systemNames[i], i);
		}
		NumberOfHeroes = commonNames.length;
	}
	
	public static String getCommonNameByID(int ID) {
		return commonNames[ID];
	}
	
	public static String getSystemNameByID(int ID) {
		return systemNames[ID];
	}
	
	public static String getCommonNameBySystemName(String systemName) {
		return commonNames[systemToID.get(systemName)];
	}
	
	public static String getSystemNameByCommonName(String commonName) {
		return systemNames[commonToID.get(commonName)];
	}
	
	public static int getIDByCommonName(String commonName) {
		return commonToID.get(commonName);
	}
	
	public static int getIDBySystemName(String systemName) {

		return systemToID.get(systemName);
	}
	
}
