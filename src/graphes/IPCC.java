package graphes;

import java.util.List;

public interface IPCC {
	boolean estOK(IGraphe g);
	int pc(IGraphe g, Integer noeudDepart, Integer noeudArrivee, List<Integer> chemin);
}
