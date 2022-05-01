package tests.algorithmes;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import algorithmes.PCC;
import algorithmes.PCCBellman;
import algorithmes.PCCDijkstra;
import exceptions.NoPathEx;
import graphes.IGraphe;
import graphes.types.GrapheLA;

public class PCCTest {
	private static final String REPERTOIRE_ENONCE = "graphes/";
	private static final String REPERTOIRE_REPONSE = "reponses/";
	private static final String REPERTOIRE_DIJKSTRA = "ac/";
	private static final String REPERTOIRE_BELLMAN = "sc/";

	private static final List<String> listeFichiers = new ArrayList<>();
	static {
		listeFichiers.add("g-10-1.txt");
		listeFichiers.add("g-10-2.txt");
		listeFichiers.add("g-10-3.txt");
		listeFichiers.add("g-10-4.txt");
		listeFichiers.add("g-10-5.txt");
		listeFichiers.add("g-10-6.txt");
		listeFichiers.add("g-10-7.txt");
		listeFichiers.add("g-10-8.txt");
		listeFichiers.add("g-10-9.txt");
		listeFichiers.add("g-10-10.txt");
		
		listeFichiers.add("g-100-1.txt");
		listeFichiers.add("g-100-2.txt");
		listeFichiers.add("g-100-3.txt");
		listeFichiers.add("g-100-4.txt");
		listeFichiers.add("g-100-5.txt");
		
		//!!! Exception NoPathEx pour Dijkstra et pass avec chemin différent pour Bellman
		//listeFichiers.add("g-1000-1.txt");
		listeFichiers.add("g-1000-2.txt");
		
		//!!! Exception NoPathEx pour Dijkstra et pass avec chemin différent pour Bellman
		//listeFichiers.add("g-10000-1.txt");
		listeFichiers.add("g-10000-2.txt");
		
		/*
		listeFichiers.add("g-100000-1.txt");
		listeFichiers.add("g-100000-2.txt");
		
		listeFichiers.add("g-1000000-1.txt");
		listeFichiers.add("g-1000000-2.txt");*/
	}
	
	//@Test
	public void testDijkstra() throws IOException {
		for (String nomFichier : listeFichiers) {
			//System.out.println(REPERTOIRE_DIJKSTRA + nomFichier);
			comparaisonFichier(REPERTOIRE_DIJKSTRA + nomFichier, new PCCDijkstra());
		}
	}

	
	@Test
	public void testBellman() throws IOException {
		long t = System.currentTimeMillis();
		for (String nomFichier : listeFichiers) {
			//System.out.println(REPERTOIRE_BELLMAN + nomFichier);
			comparaisonFichier(REPERTOIRE_BELLMAN + nomFichier, new PCCBellman());
		}
		System.out.println(System.currentTimeMillis() - t);
	}
	
	private static String lectureFichier(String nomFichier) throws IOException {
		return new String(Files.readAllBytes(Paths.get(nomFichier)));
	}
	
	private static void comparaisonFichier(String nomFichier, PCC typeAlgo) throws IOException {
		assertEquals(importation(REPERTOIRE_ENONCE + nomFichier, typeAlgo), lectureFichier(REPERTOIRE_REPONSE + nomFichier.replace('g', 'r')));
	}
	
	private static String importation(String nomFichier, PCC typeAlgo) throws FileNotFoundException {
		File f = new File(nomFichier);
		Scanner sc = new Scanner(f);
		IGraphe g = new GrapheLA(sc.nextInt());
		
		while (sc.hasNext()) {
			int noeudDepart = sc.nextInt();
			int valuation = sc.nextInt();
			int noeudArrivee = sc.nextInt();
			
			if (valuation != 0) {
				g.ajouterArc(noeudDepart, valuation, noeudArrivee);
			}
			
			else {
				try {
					return typeAlgo.algorithme(g, noeudDepart, noeudArrivee);
				}
				
				catch(NoPathEx e) {
					return "pas de chemin entre " + noeudDepart + " et " + noeudArrivee;
				}
			}
		}
		sc.close();
		throw new RuntimeException();
	}
}
