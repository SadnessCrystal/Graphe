package graphes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class GrapheLA implements IGraph{
	private Map<String, Integer> noeuds;
	private ArrayList<ArrayList<Arc>> la;
	
	private static class Arc  {
		String cible;
		int valeur;
		
		/**
		 * @brief Constructeur de Arc
		 * @param cible Successeur du noeud
		 * @param valeur Valeur de l'arc
		 */
		Arc(String cible, int valeur) {
			this.cible = cible;
			this.valeur = valeur;
		}
		@Override
		public boolean equals(Object ob) {
			if (this == ob)
				return true;
			if (ob == null)
				return false;
			if (ob.getClass() != Arc.class)
				return false;
			Arc a = (Arc) ob;
			return a.cible.equals(this.cible); // on ne tient PAS compte de la valeur
			// afin de faciliter l'ecriture de la methode aArc
			// qui cherche si un arc avec la meme cible existe peu importe la valeur
			// en utilisant contains sur une liste d'arcs or contains appelle
			// cette methode equals
		}
	}

	/**
	 * @brief Constructeur de GrapheMA
	 * @param labels Tableau des labels du graphe
	 */
	public GrapheLA(String[] labels) {
		this.noeuds = new HashMap<>();
		int nb = labels.length;
		for (int i =0; i< nb; ++i)
			this.noeuds.put(labels[i], i);
		la = new ArrayList<>();
		for (int i = 0; i < nb; ++i)
			la.add(new ArrayList<>());
	}
	
	@Override
	public int getNbNoeuds() {
		return la.size();
	}
	
	@Override
	public boolean estNoeudOK(String label) {
		return noeuds.containsKey(label);
	}
	
	@Override
	public boolean estArcOK(String n1, String n2) {
		return estNoeudOK(n1) && estNoeudOK(n2);
	}
	
	@Override
	public void ajouterArc(String label1, String label2, int valeur) throws RuntimeException {
		if (aArc(label1,label2))
			throw new RuntimeException("Il existe déjà un arc entre " + label1 + " et " + label2);
		int n1 = noeuds.get(label1);
		la.get(n1).add(new Arc(label2, valeur));
	}
	
	@Override
	public boolean aArc(String label1, String label2) throws IllegalArgumentException {
		if (!estArcOK(label1, label2))
			throw new IllegalArgumentException("L'un des labels entre " + label1 + " et " + label2 + "n'existent pas dans ce graphe");
		int n1 = noeuds.get(label1);
		return la.get(n1).contains(new Arc(label2, 0));
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(String label1 : noeuds.keySet()) {
			s.append(label1 + " -> ");
			for (Arc a : la.get(noeuds.get(label1)))
				s.append(a.cible + "("+ a.valeur + ") ");
			s.append("\n");
		}
		return s.toString();
	}
	
	@Override
	public int dOut(String label) throws IllegalArgumentException {
		if (!estNoeudOK(label))
			throw new IllegalArgumentException(label + " n'existe pas dans ce graphe");
		return la.get(noeuds.get(label)).size();
	}
	
	@Override
	public int dIn(String label) throws IllegalArgumentException {
		if (!estNoeudOK(label))
			throw new IllegalArgumentException(label + " n'existe pas dans ce graphe");
		
		int d = 0;
		Arc a = new Arc(label, 0);
		for(int i = 0; i< la.size(); ++i)
			if (la.get(i).contains(a))
				++d;
		return d;
	}

	/**
	 * 
	 * @param n1 Label du premier noeud
	 * @param n2 Label du second noeud
	 * @return Valeur du noeud entre le premier et le second noeud
	 * @throws IllegalArgumentException Si aucun arc n'est trouvé entre le premier et le second noeud
	 * @throws RuntimeException Si aucune valeur pour l'arc n'est trouvé
	 */
	@Override
	public int getValeur(String n1, String n2) throws IllegalArgumentException, RuntimeException {
		if (!aArc(n1, n2))
			throw new IllegalArgumentException("Aucun arc entre " + n1 + " et " + n2);
			
		for (Arc a : la.get(noeuds.get(n1)))
			if (a.cible.equals(n2))
				return a.valeur;
		throw new RuntimeException("Pas de valeur trouvée pour l'arc " + n1 +" -> " +n2);
	}
	
	@Override
	public Iterator<String> iterator() {
		return noeuds.keySet().iterator();
	}
}
