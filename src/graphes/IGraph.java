package graphes;

import java.util.Iterator;

public interface IGraph extends Iterable<String>{

	/**
	 * 
	 * @return Nombre de noeuds dans le graphe
	 */
	int getNbNoeuds();

	/**
	 * 
	 * @param label Label à tester
	 * @return true si le label existe, false sinon
	 */
	boolean estNoeudOK(String label);

	/**
	 * 
	 * @param n1 Label du premier noeud
	 * @param n2 Label du second noeud
	 * @return true si les 2 noeuds existent, false sinon
	 */
	boolean estArcOK(String n1, String n2);

	/**
	 * 
	 * @param label1 Noeud prédécesseur
	 * @param label2 Noeud successeur
	 * @param valeur Valeur de l'arc
	 * @throws RuntimeException S'il existe déjà un arc entre les 2 noeuds
	 */
	void ajouterArc(String label1, String label2, int valeur) throws RuntimeException;

	/**
	 * 
	 * @param label1 Label du premier noeud
	 * @param label2 Label du second noeud
	 * @return true s'il y a un arc entre le premier noeud et le second, false sinon
	 * @throws IllegalArgumentException Si l'un des 2 labels ne sont pas dans le graphe
	 */
	boolean aArc(String label1, String label2) throws IllegalArgumentException;

	@Override
	String toString();

	/**
	 * 
	 * @param label Label du noeud
	 * @return Nombre de successeurs du noeud
	 * @throws IllegalArgumentException Si le label n'est pas dans le graphe
	 */
	int dOut(String label) throws IllegalArgumentException;

	/**
	 * 
	 * @param label Label du noeud
	 * @return Nombre de prédécesseurs du noeud
	 * @throws IllegalArgumentException Si le label n'est pas dans le graphe
	 */
	int dIn(String label) throws IllegalArgumentException;

	/**
	 * 
	 * @param n1 Label du premier noeud
	 * @param n2 Label du second noeud
	 * @return Valeur du noeud entre le premier et le second noeud
	 * @throws IllegalArgumentException Si aucun arc n'est trouvé entre le premier et le second noeud
	 */
	int getValeur(String n1, String n2) throws IllegalArgumentException;
	
	@Override
	Iterator<String> iterator();
}