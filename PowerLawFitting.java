/**
 * File Name: PowerLawFitting
 * 
 * Author: Sameera Bammidi
 *Created on: 11/14/2017
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashMap;
import java.util.HashSet;

import org.jgrapht.*;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.*;

/*
 * Power law fitting 
 * 1. First read output file created by SimilarityScores.java (sale and purchase separately)
 * 2. Pick a threshold of similarity and build a graph
 * 3. For each company, make the graph G then find connected components, output to file < companyID , %nodes covered by CC , number of CC> 
 * 4. Unrelated to #3 above, For each G, for node get Ego net, From all Ego nets, compute features, then use in pairs. e.g: <V_u,E_u> Output to file. 
 * 5. Use R to plot power law (that line..). ?
 * 6. Later, use that law to compute out-lier score.  
 * */
public class PowerLawFitting {

	public static void main(String[] args) throws IOException {

		//HashSet<Pair> hpairs = new HashSet<Pair>();
		HashMap<Integer, HashSet<Pair>> company_allpairs = new HashMap<Integer, HashSet<Pair>>();


		File input = new File ("purchases_all_simscores.csv");
		if(!input.exists())
			System.out.println("not found");

		BufferedReader br = new BufferedReader(new FileReader(input));

		String line;
		line = br.readLine();// ignore first
		while((line=br.readLine())!=null){
			System.out.println(line);
			String[] tokens = line.split(",");


			HashSet<Pair> hpairs;
			if(!company_allpairs.containsKey(Integer.parseInt(tokens[0])))
			{
				hpairs = new HashSet<Pair>();
				company_allpairs.put(Integer.parseInt(tokens[0]),hpairs);
			}
			else hpairs = 
					company_allpairs.get(Integer.parseInt(tokens[0]));

			hpairs.add(new Pair(Integer.parseInt(tokens[1]) , Integer.parseInt(tokens[2]), Double.parseDouble(tokens[3])));

		}
		System.out.println(company_allpairs.keySet());
		br.close();

		double threshold = 0.5 ;
		UndirectedGraph<Integer, DefaultEdge> ug ;

		System.out.println();
		for( Integer k :company_allpairs.keySet()  ){
			System.out.println("====================");
			System.out.println("Company "+k);
			HashSet<Pair> hp = company_allpairs.get(k) ;
			ug = createGraph(hp, threshold);
			ConnectivityInspector<Integer, DefaultEdge> ci = new ConnectivityInspector<Integer, DefaultEdge>(ug);

			HashSet<Integer> hi = new HashSet<Integer>();
			for(Pair p: hp){
				hi.add(p.id1); hi.add(p.id2);
			}

			for(Integer ii : hi){
				// ci.connectedSetOf(ii);
				System.out.println(ii+" , "+ci.connectedSetOf(ii));
			}
		}

	}


	static UndirectedGraph<Integer, DefaultEdge> createGraph(HashSet<Pair> hpairs, double threshold)
	{
		HashSet<Integer> h_int = new HashSet<Integer>() ;
		for(Pair p: hpairs){
			h_int.add(p.id1);
			h_int.add(p.id2);
		}

		UndirectedGraph<Integer, DefaultEdge> g =
				new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);

		for(Integer i : h_int)
			g.addVertex(i);

		for(Pair p : hpairs){
			if(p.score > threshold){
				g.addEdge(p.id1, p.id2);
			}	
		}

		return g;
	}
}
