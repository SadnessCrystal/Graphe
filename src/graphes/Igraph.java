package graphes;

public interface Igraph {
	/**
	 * @brief Retourne le nombre de noeuds
	 * @return Nombre de noeuds
	 */
	int getNbNoeuds();
	

	/**
	 * @brief Ajoute un arc au graphe
	 * @param i Numéro de la ligne
	 * @param j Numéro de la colonne
	 */
	void ajouterArc(int i, int j, int valeur);
	
	
	int valeurArc(int i, int j);
	
	
	/**
	 * @brief Retourne l'état de la matrice aux coordonnées (i,j)
	 * @param i Numéro de la ligne
	 * @param j Numéro de la colonne
	 * @return true s'il y a un arc, false sinon
	 */
	default boolean aArc(int i, int j) {
		return valeurArc(i, j) != 0;
	}
	
	
	/**
	 * @brief Retourne le nombre de successeurs
	 * @param n Numéro du noeud
	 * @return Nombre de successeurs
	 */
	default int dOut(int n) {
		int nbDegre = 0;
		
		for (int i=0; i<getNbNoeuds(); ++i) {
			nbDegre += aArc(n, i+1) ? 1 : 0;
		}
		
		return nbDegre;
	}
	
	
	/**
	 * @brief Retourne le nombre de prédécesseurs
	 * @param n Numéro du noeud
	 * @return Nombre de prédécesseurs
	 */
	default int dIn(int n) {
		int nbDegre = 0;
		
		for (int i=0; i<getNbNoeuds(); ++i) {
			nbDegre += aArc(i+1, n) ? 1 : 0;
		}
		
		return nbDegre;
	}
}
