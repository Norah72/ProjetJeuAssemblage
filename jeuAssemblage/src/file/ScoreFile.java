package file;

import controleur.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public class ScoreFile {
	private HashMap<String, String> listeScore = new HashMap<String, String>();
	private File scoreFile = new File("src/file/partie/score.txt"); 
        
	public void write(Play jeu) throws IOException {
		try{
			if(!this.scoreFile.exists()){
				this.scoreFile.createNewFile();
			}
			
			FileWriter score = new FileWriter (this.scoreFile, true);
			score.write(jeu.getPseudo()+" "+jeu.getPlateau().getScore()+"\n");
			score.close();
		}
		catch(Exception e){
			System.out.println("Impossible d'enregistrer le score : "+e);
		}
	}
	
	public void affiche(){
		boolean affichePseudo = true;
                String p = null;
                String s = null;
		try{
			if(!this.scoreFile.exists()){
				System.out.println("Il n'y a pas encore de score !");
			}else{
				for (String ligne : Files.readAllLines(this.scoreFile.toPath())) {
					for(String caractere : ligne.split(" ")){
						if(affichePseudo){
							System.out.print("- Pseudo : ");
                                                        p=caractere;
                                                }
                                                else{
							System.out.print(" | Score : ");
                                                        s=caractere;
                                                }
						System.out.print(caractere);
						affichePseudo = !affichePseudo;
					}
                                        listeScore.put(p,s);
                                }
                                        if(listeScore.size()>12){
                                            System.out.println("test");
                                            int min = 9999;
                                            String key = "";
                                            for(String i : listeScore.keySet()){
                                                int tmp = Integer.parseInt(listeScore.get(i));
                                                if(tmp<min){
                                                    min=tmp;
                                                    key = i;
                                                }
                                            }
                                            System.out.println("test1 " + min + " " + key);
                                            System.out.println(listeScore);
                                            listeScore.remove(key);
                                            System.out.println(listeScore);
                                            File tmp = new File("temp.txt");
                                            BufferedWriter newScore = new BufferedWriter (new FileWriter(tmp));
                                            for(String i : listeScore.keySet()){
                                                newScore.write(i+" "+listeScore.get(i)+"\n");
                                                newScore.flush();
                                            }
                                            newScore.close();
                                            scoreFile.delete();
                                            tmp.renameTo(new File("src/file/partie/score.txt"));
                                        }
					System.out.println(" point(s)");
                        }
		}
		catch(Exception e){
			System.out.println("Impossible d'afficher le score : "+e);
		}
		
	}
        public HashMap<String, String> getListeScore(){
            return listeScore;
        }
}
