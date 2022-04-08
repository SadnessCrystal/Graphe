package graphes;

public class GrapheMA implements Igraph {
	private int[][] coefficients;
	
	
	/**
	 * @brief Constructeur de GrapheMA
	 * @param nbNoeuds Le nombre de noeuds dans le graphe
	 */
	public GrapheMA(int nbNoeuds) {
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
	 * @brief Retourne le graphe sous forme de matrice d'adjacence
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<getNbNoeuds(); ++i) {
			for (int j=0; j<getNbNoeuds(); ++j) {
				sb.append(this.aArc(i+1, j+1) ? 1 : 0);
				sb.append(" ");
			}
			
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
