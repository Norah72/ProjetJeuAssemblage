package controleur;

public enum EnumAction {
	QUITTER("Quitter sans sauvegarder\n"), PLACER("Placer une pièce\n"), DEPLACER("Déplacer une pièce\n"), SUPPRIMER("Supprimer une pièce\n"), ROTATION_PIECEAJOUER("Rotation d'une pièce disponible\n"), ROTATION_PIECEPLACER("Rotation d'une pièce sur la plateau\n"), SAUVEGARDER("Sauvegarder la partie\n"), FIN_DE_PARTIE("Score/Fin\n");

	private final String action ;  

	private EnumAction(String action) {  
		this.action = action;
	}
	
	//Probabilité d'apparaitre dans les choix de l'ia
	public int getProbaAction(EnumAction action){
		int proba = 0;
        switch(action)
        {
            case QUITTER :
                proba = 0;
                break;
            case PLACER :
                proba = 3;
                break;
            case DEPLACER :
                proba = 3;
                break;
            case SUPPRIMER :
                proba = 1;
                break;
			case ROTATION_PIECEAJOUER :
                proba = 2;
                break;
			case ROTATION_PIECEPLACER :
                proba = 2;
                break;
			case SAUVEGARDER:
                proba = 0;
                break;
			case FIN_DE_PARTIE :
                proba = 1;
                break;
        }
		
		return proba;
	}
	
	@Override
	public String toString(){
		return this.action;
	}
	
}

