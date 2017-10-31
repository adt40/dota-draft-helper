package training;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Logger {

	public static void Log(String message) {
		File log = new File("log.txt");
		if (!log.exists()) {
			try {
				log.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileWriter fr = new FileWriter("log.txt", true);
			BufferedWriter br = new BufferedWriter(fr);
			br.write(message + "\n");
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
