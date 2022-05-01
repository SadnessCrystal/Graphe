package graphes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface IGraphe extends Iterable<Integer>{
	int getNbSommets();
	void ajouterArc(int source, int valuation, int destination);
	int getValuation(int i, int j);
	boolean aArc(int i, int j);
	


	@Override
	default Iterator<Integer> iterator() {
		List<Integer> liste = new ArrayList<>();
		for (int i=0; i<this.getNbSommets(); ++i)
			liste.add(i+1);
		return liste.iterator();
	}
}
