package composants;
public class GrapheMA extends Graphe {
	
	/**
	 * @brief Affiche le graphe sous forme de matrice d'adjacence
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i=1; i<=NB_POINTS; ++i) {
			for (int j=1; j<=NB_POINTS; ++j) {
				sb.append(super.aArc(i, j) ? 1 : 0);
				sb.append(" ");
			}
			
			sb.append("\n");
		}
		
		return sb.toString();
	}
}
