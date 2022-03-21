package composants;
public class GrapheLA extends Graphe {
	
	/**
	 * @brief Retourne le graphe sous forme de liste d'adjacence
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i=1; i<=NB_POINTS; ++i) {
			
			sb.append(i);
			sb.append(" ->");
			
			for (int j=1; j<=NB_POINTS; ++j) {
				if (super.aArc(i, j)) {
					sb.append(" ");
					sb.append(j);
				}
			}
			
			sb.append(" \n");
		}
		
		return sb.toString();
	}
}
