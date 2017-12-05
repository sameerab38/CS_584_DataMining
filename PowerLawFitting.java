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
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/*
 * Power law fitting Similarity
 * 1. First read output file created by SimilarityScores.java (sale and purchase separately)
 * 2. Pick a threshold of similarity and build a graph
 * 3. For each company, make the graph G then find connected components, output to file < companyID , %nodes covered by CC , number of CC> 
 * 4. For each G, for node get Ego net, From all Ego nets, compute features, then use in pairs. e.g: <V_u,E_u> Output to file. 
 * 5. Use R to plot power law (that line..)
 * 6. Later, use that law to compute outlier score.
 * */
public class PowerLawFitting
{

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IOException, SQLException
	{
		//HashSet<Pair> hpairs = new HashSet<Pair>();
		System.out.println("===============================================Purchase Network===============================================");
		HashMap<Integer, HashSet<Pair>> company_allpairs = new HashMap<Integer, HashSet<Pair>>();

		File input = new File ("purchases_all_simscores.csv");

		if(!input.exists())
			System.out.println("not found");

		BufferedReader br = new BufferedReader(new FileReader(input));

		String line;
		line = br.readLine();// ignore first
		while((line = br.readLine()) != null)
		{
			String[] tokens = line.split(",");

			HashSet<Pair> hpairs;
			if(!company_allpairs.containsKey(Integer.parseInt(tokens[0])))
			{
				hpairs = new HashSet<Pair>();
				company_allpairs.put(Integer.parseInt(tokens[0]),hpairs);
			}
			else hpairs = company_allpairs.get(Integer.parseInt(tokens[0]));

			hpairs.add(new Pair(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Double.parseDouble(tokens[3])));

		}
		System.out.println(company_allpairs.keySet());
		br.close();

		double threshold = 0.5;
		UndirectedGraph<Integer, DefaultEdge> ug;

		HashSet<Set<Integer>> uniqueConnectedComponents = new HashSet<Set<Integer>>();
		//int debug_nonuniq_ctr = 0;

		HashMap<Integer,UndirectedGraph<Integer, DefaultEdge>> egoNets = new HashMap<Integer, UndirectedGraph<Integer,DefaultEdge>>();

		System.out.println();
		for( Integer k :company_allpairs.keySet())
		{
			/*System.out.println("====================");
			System.out.println("Company "+k);*/
			HashSet<Pair> hp = company_allpairs.get(k);
			ug = CreateGraph(hp, threshold);
			ConnectivityInspector<Integer, DefaultEdge> ci = new ConnectivityInspector<Integer, DefaultEdge>(ug);

			HashSet<Integer> hi = new HashSet<Integer>();
			for(Pair p: hp)
			{
				hi.add(p.id1); hi.add(p.id2);
			}

			for(Integer ii : hi)
			{
				if(ci.connectedSetOf(ii).size() > 1) // Ignore isolated nodes
				{
					//System.out.println(ii+" , "+ci.connectedSetOf(ii)+"	"+ci.connectedSetOf(ii).size()); // this displays same CC's multiple times because we are iterating on Vertices 
					uniqueConnectedComponents.add(ci.connectedSetOf(ii));
					//debug_nonuniq_ctr++;

					// compute it's egonet and add to global set
					UndirectedGraph<Integer, DefaultEdge> egonet_ii = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
					egonet_ii.addVertex(ii);
					// iterate over all nodes in connected set, retain only those that have an edge with the ego node
					for(Integer cn : ci.connectedSetOf(ii))
					{
						if(ug.containsEdge(ii, cn))
						{
							egonet_ii.addVertex(cn);
							//egonet_ii.addEdge(ii,cn);
						}
					}				
					// then also check for edges within all other nodes retained above and add them
					for(Integer cn : egonet_ii.vertexSet())
					{
						for(Integer cn2 : egonet_ii.vertexSet())
						{
							if(cn==cn2) continue;
							if(ug.containsEdge(cn, cn2))
							{
								egonet_ii.addEdge(cn,cn2);
							}
						}
					}

					// now add final egonet to global set
					egoNets.put(ii,egonet_ii);

				}
			}
		}
		//System.out.println(" non unique ctr: "+ debug_nonuniq_ctr);
		System.out.println(" uniq ctr: "+uniqueConnectedComponents.size());
		Iterator<Set<Integer>> itr = uniqueConnectedComponents.iterator() ;

		//ToDo: Display each company and its connected components

		while(itr.hasNext())
		{			
			Iterator<Integer> itr_in = itr.next().iterator();

			while(itr_in.hasNext())
			{
				System.out.print(itr_in.next());
				if(itr_in.hasNext())
				{
					System.out.print(" , ");
				}
			}

			System.out.println();
		}

		System.out.println("All ego nets: ");
		for(Integer eg:egoNets.keySet())
		{
			System.out.println("Insider Node: "+eg);
			System.out.println(egoNets.get(eg));
			System.out.println("==================================================================");
		}

		//Store all the connected components in a hash set
		PrintWriter pwr = new PrintWriter(new File("purchase_all_cc_size_distribution.csv"));
		for(Set s:uniqueConnectedComponents)
		{
			pwr.println(s.size());
		}
		pwr.close();

		//Calculate number of vertices and edges in each egonet of purchase network
		PrintWriter pwrEp = new PrintWriter(new File("purchase_Egonets_NodeAndEdge_Count.csv"));
		pwrEp.println("InsiderID, NodeCount, EdgeCount");
		for(Integer egoNetKey : egoNets.keySet())
		{
			int nodeCount = 0;
			int edgeCount = 0;

			UndirectedGraph<Integer, DefaultEdge> currentEgonet = egoNets.get(egoNetKey);
			nodeCount = currentEgonet.vertexSet().size();
			//System.out.println("egoNetKey: "+ egoNetKey + " nodeCount " + nodeCount);
			edgeCount = currentEgonet.edgeSet().size();
			//System.out.println("egoNetKey: "+ egoNetKey + " edgeCount " + edgeCount);
			//System.out.println("======================================================");
			pwrEp.println(egoNetKey + "," + nodeCount + "," + edgeCount);
		}
		pwrEp.close();

		//System.exit(1);
		System.out.println("===============================================Sale Network===============================================");
		HashMap<Integer, HashSet<Pair>> sale_company_allpairs = new HashMap<Integer, HashSet<Pair>>();

		File sale_input = new File("sale_all_simscores.csv");

		if(!sale_input.exists())
		{
			System.out.println("not found");
		}
		BufferedReader sbr = new BufferedReader(new FileReader(sale_input));
		line = sbr.readLine();

		while((line = sbr.readLine()) != null)
		{
			String[] tokens = line.split(",");


			HashSet<Pair> sale_hpairs;
			if(!sale_company_allpairs.containsKey(Integer.parseInt(tokens[0])))
			{
				sale_hpairs = new HashSet<Pair>();
				sale_company_allpairs.put(Integer.parseInt(tokens[0]),sale_hpairs);
			}
			else 
			{
				sale_hpairs = sale_company_allpairs.get(Integer.parseInt(tokens[0]));
			}

			sale_hpairs.add(new Pair(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Double.parseDouble(tokens[3])));

		}
		System.out.println(sale_company_allpairs.keySet());
		sbr.close();

		UndirectedGraph<Integer, DefaultEdge> ugs;
		HashSet<Set<Integer>> uniqueConnectedComponentsSale = new HashSet<Set<Integer>>();
		int sale_debug_nonuniq_ctr = 0;

		HashMap<Integer,UndirectedGraph<Integer, DefaultEdge>> egoNetsSale = new HashMap<Integer, UndirectedGraph<Integer,DefaultEdge>>();

		System.out.println();
		for( Integer k :sale_company_allpairs.keySet()  )
		{
			System.out.println("====================");
			System.out.println("Company "+k);
			HashSet<Pair> hp = sale_company_allpairs.get(k) ;
			ugs = CreateGraph(hp, threshold);
			ConnectivityInspector<Integer, DefaultEdge> ci = new ConnectivityInspector<Integer, DefaultEdge>(ugs);

			HashSet<Integer> hi = new HashSet<Integer>();
			for(Pair p: hp)
			{
				hi.add(p.id1); hi.add(p.id2);
			}

			for(Integer ii : hi)
			{
				if(ci.connectedSetOf(ii).size() > 1) // Remove isolated nodes
				{
					// ci.connectedSetOf(ii);
					System.out.println(ii+" , "+ci.connectedSetOf(ii) + " " + ci.connectedSetOf(ii).size());

					uniqueConnectedComponentsSale.add(ci.connectedSetOf(ii));
					sale_debug_nonuniq_ctr ++;

					// compute it's egonet and add to global set
					UndirectedGraph<Integer, DefaultEdge> egonet_ii = new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);
					egonet_ii.addVertex(ii);
					// iterate over all nodes in connected set, retain only those that have an edge with the ego node
					for(Integer cn : ci.connectedSetOf(ii))
					{
						if(ugs.containsEdge(ii, cn)){
							egonet_ii.addVertex(cn);
							//egonet_ii.addEdge(ii,cn);
						}
					}				
					// then also check for edges within all other nodes retained above and add them
					for(Integer cn : egonet_ii.vertexSet())
					{
						for(Integer cn2 : egonet_ii.vertexSet())
						{
							if(cn==cn2) continue;
							if(ugs.containsEdge(cn, cn2))
							{
								egonet_ii.addEdge(cn,cn2);
							}
						}
					}

					// now add final egonet to global set
					egoNetsSale.put(ii,egonet_ii);
				}
			}
		}
		System.out.println(" non unique ctr: " + sale_debug_nonuniq_ctr);
		System.out.println(" uniq ctr: " + uniqueConnectedComponentsSale.size());

		Iterator<Set<Integer>> itrSale = uniqueConnectedComponentsSale.iterator() ;
		while(itrSale.hasNext())
		{
			Iterator<Integer> itr_in = itrSale.next().iterator();

			while(itr_in.hasNext())
			{
				System.out.print(itr_in.next());
				if(itr_in.hasNext())
				{
					System.out.print(",");
				}
			}

			System.out.println();
		}
		System.out.println("All ego nets: ");
		for(Integer eg:egoNetsSale.keySet())
		{
			System.out.println("Insider Node: "+eg);
			System.out.println(egoNetsSale.get(eg));
			System.out.println("==================================================================");
		}

		//Store all the connected components in a hash set
		PrintWriter pwrs = new PrintWriter(new File("sale_all_cc_size_distribution.csv"));
		for(Set s:uniqueConnectedComponentsSale)
		{
			pwrs.println(s.size());
		}
		pwrs.close();


		//Calculate number of vertices and edges in each egonet of sale network
		PrintWriter pwrEs = new PrintWriter(new File("sale_Egonets_NodeAndEdge_Count.csv"));
		pwrEs.println("InsiderID, NodeCount, EdgeCount");
		for(Integer egoNetKeySale : egoNetsSale.keySet())
		{
			int nodeCount = 0;
			int edgeCount = 0;

			UndirectedGraph<Integer, DefaultEdge> currentEgonet = egoNetsSale.get(egoNetKeySale);
			nodeCount = currentEgonet.vertexSet().size();
			//System.out.println("egoNetKey: "+ egoNetKey + " nodeCount " + nodeCount);
			edgeCount = currentEgonet.edgeSet().size();
			//System.out.println("egoNetKey: "+ egoNetKey + " edgeCount " + edgeCount);
			//System.out.println("======================================================");
			pwrEs.println(egoNetKeySale + "," + nodeCount + "," + edgeCount);
		}
		pwrEs.close();
	}

	static UndirectedGraph<Integer, DefaultEdge> CreateGraph(HashSet<Pair> hpairs, double threshold)
	{
		HashSet<Integer> h_int = new HashSet<Integer>() ;
		for(Pair p: hpairs)
		{
			h_int.add(p.id1);
			h_int.add(p.id2);
		}

		UndirectedGraph<Integer, DefaultEdge> g =
				new SimpleGraph<Integer, DefaultEdge>(DefaultEdge.class);

		for(Integer i : h_int)
			g.addVertex(i);

		for(Pair p : hpairs)
		{
			if(p.score > threshold)
			{
				g.addEdge(p.id1, p.id2);
			}	
		}

		return g;
	}
}
