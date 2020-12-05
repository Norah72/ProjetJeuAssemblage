package file;

import controleur.Play;
import java.io.File;

public class DeleteFile {
	
	private File partieFichier; 

	
	public DeleteFile(String file){
		this.partieFichier = new File(file);
	}
	
	
	public void supprimerFile(){
		this.partieFichier.delete();
	}
}