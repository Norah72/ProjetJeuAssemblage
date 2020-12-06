package file;

import java.io.File;

public class DeleteFile {
	
	private final File partieFichier; 

	public DeleteFile(String file){
		this.partieFichier = new File(file);
	}
	
	
	public void supprimerFile(){
		this.partieFichier.delete();
	}
}