package graphes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface IGraphe extends Iterable<Integer>{
	static final int INFINI = Integer.MAX_VALUE;
	
	int getNbSommets();
	void ajouterArc(int source, int valuation, int destination);
	int getValuation(int i, int j);
	boolean aArc(int i, int j);
	
	default int distance(List<Integer> chemin) {
		int sommeDistance = 0;
		int predecesseur = chemin.remove(0);
		while (!chemin.isEmpty()) {
			sommeDistance += getValuation(predecesseur, chemin.get(0));
			predecesseur = chemin.remove(0);
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
