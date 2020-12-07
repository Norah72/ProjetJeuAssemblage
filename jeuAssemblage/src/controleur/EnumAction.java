package controleur;

/**
 *
 * @author Alexandre BELLEBON - Auréline DEROIN - Clémentine LEROY - Léo VINCENT
 */
public enum EnumAction {

	/**
	 * Enum Quitter la partie
	 */
    QUITTER("Quitter sans sauvegarder\n"),

	/**
	 * Enum Placer une pièce
	 */
    PLACER("Placer une pièce\n"),

	/**
	 * Enum Déplaer une pièce
	 */
    DEPLACER("Déplacer une pièce\n"),

	/**
	 * Enum Supprimmer une pièce
	 */
    SUPPRIMER("Supprimer une pièce\n"),

	/**
	 * Enum Rotation d'une pièce dans la liste des pièce a jouer
	 */
    ROTATION_PIECEAJOUER("Rotation d'une pièce disponible\n"),

	/**
	 * Enum Rotation d'une pièce sur le plateau
	 */
    ROTATION_PIECEPLACER("Rotation d'une pièce sur la plateau\n"),

	/**
	 * Enum Sauvegarde de partie
	 */
    SAUVEGARDER("Sauvegarder la partie\n"),

	/**
	 * Enum fin de partie 
	 */
    FIN_DE_PARTIE("Score/Fin\n");

    private final String action ;  //texte dans le menu console
	
    /**
     * Constructeur
     * @param action 
     */
    private EnumAction(String action) {  
        this.action = action;
    }
	
    

    /**
     * Probabilité de l'ia de choisir l'action
     * @param action Action du joueur
     * @return Probabilité des choix de l'ia
     */
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

