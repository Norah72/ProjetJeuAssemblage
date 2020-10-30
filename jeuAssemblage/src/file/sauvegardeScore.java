package file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class sauvegardeScore {

	public sauvegardeScore(){

	}

	public void write() {
		try {
			String pseudo = "Pseudo";
			int score = 90;

			File file = new File("Score.txt");

			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(pseudo);
			bw.write(score);
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
