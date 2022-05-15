package graphes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface IGraphe extends Iterable<Integer>{
	static final int INFINI = Integer.MAX_VALUE;
	
	/**
	 * @return Nombre de sommets dans le graphe
	 */
	int getNbSommets();
	
	/**
	 * @brief Ajoute un arc au graphe
	 * @param a Noeud d'où part l'arc
	 * @param v Valuation de l'arc
	 * @param b Noeud où se dirige l'arc
	 * @throws IllegalArgumentException L'arc ne peut pas exister
	 */
	void ajouterArc(int a, int v, int b) throws IllegalArgumentException;
	
	/**
	 * @param i Noeud de départ de l'arc
	 * @param j Noeud de destanation de l'arc
	 * @return Valuation de l'arc entre le noeud i et j
	 * @throws IllegalArgumentException L'arc n'existe pas
	 */
	int getValuation(int i, int j) throws IllegalArgumentException;
	
	/**
	 * @param i Noeud de départ
	 * @param j Noeud de destination
	 * @return true s'il existe un arc entre le noeud i et j, false sinon
	 */
	boolean aArc(int i, int j);
	
	/**
	 * @param chemin Liste des noeuds composant le chemin
	 * @return La distance du chemin
	 */
	default int distance(List<Integer> chemin) {
		if (chemin.isEmpty()) return INFINI;
		
		int sommeDistance = 0;
		int predecesseur = chemin.remove(0);
		
		while (!chemin.isEmpty()) {
			try {
				sommeDistance += getValuation(predecesseur, chemin.get(0));
				predecesseur = chemin.remove(0);	
			}
			
			catch(IllegalArgumentException e) {
				return INFINI;
			}
		}
		return sommeDistance;
	}

	@Override
	default Iterator<Integer> iterator() {
		List<Integer> liste = new ArrayList<>();
		for (int i=0; i<this.getNbSommets(); ++i)
			liste.add(i+1);
		return liste.iterator();
	}
}
