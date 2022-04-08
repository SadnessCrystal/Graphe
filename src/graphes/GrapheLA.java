package graphes;

public class GrapheLA implements Igraph {
	private int[][] coefficients;
	
	
	/**
	 * @brief Constructeur de GrapheLA
	 * @param nbNoeuds Le nombre de noeuds dans le graphe
	 */
	public GrapheLA(int nbNoeuds) {
		this.coefficients = new int[nbNoeuds][nbNoeuds];
	}
	
	
	@Override
	public int getNbNoeuds() {
		return coefficients.length;
	}
	
	
	@Override
	public void ajouterArc(int i, int j, int valeur) {
		this.coefficients[i-1][j-1] = valeur;
	}
	
	
	@Override
	public int valeurArc(int i, int j) {
		return this.coefficients[i-1][j-1];
	}
	
	
	/**
	 * @brief Retourne le graphe sous forme de liste d'adjacence
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<getNbNoeuds(); ++i) {
			sb.append(i+1);
			sb.append(" ->");
			
			for (int j=0; j<getNbNoeuds(); ++j) {
				if (this.aArc(i+1, j+1)) {
					sb.append(" ");
					sb.append(j+1);
				}
			}
			
			sb.append(" \n");
		}
		
		return sb.toString();
	}
}

