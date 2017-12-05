/**
 * Sameera Bammidi
 * Created On: 12/01/2017
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LCSSNDA
{

	public static void main(String[] args) throws IOException, ParseException
	{




		// Get all insiders from the purchase_LCS_Egonets_NodeAndEdge_Count.csv.		
		File iinput = new File ("purchase_LCS_Egonets_NodeAndEdge_Count.csv");

		if(!iinput.exists())
		{
			System.out.println("file not found");
		}

		BufferedReader ibr = new BufferedReader(new FileReader(iinput));

		String iline;
		iline = ibr.readLine();// ignore first
		HashSet<Integer> insiderIdLCSPurchases = new HashSet<Integer>();

		while ((iline = ibr.readLine()) != null)
		{
			String[] tokens = iline.split(",");

			insiderIdLCSPurchases.add(Integer.parseInt(tokens[0]));
		}
		ibr.close();

		/* Filter above to retain only those insiders left after LCS thresholds.. 
		 */
		File input = new File ("purchases_trade_Price_SNDA_all_Data.csv");

		if(!input.exists())
		{
			System.out.println("file not found");
		}

		PrintWriter pwrp = new PrintWriter(new File("purchases_trade_Price_LCS_Data.csv"));
		pwrp.println("ID, Date, insider_id, stock_id, NumberOfShares, PurchasePrice, ClosingPrice, StockVolume");
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		line = br.readLine();// ignore first
		while ((line = br.readLine()) != null)
		{
			//System.out.println(line);
			String[] tokens = line.split(",");
			if(insiderIdLCSPurchases.contains(Integer.parseInt(tokens[2])))
			{
				pwrp.println(line);
			}

		}
		pwrp.close();
		br.close();

		// So far only filtered data and created a file.. now for actual Signed normalized dollar amount computations..



		// required data structs
		/*
		 * a set of ratios for each insider , these ratios signify normalized profit or loss. 
		 * Keeping track as a set also lets us track no of transactions at different price points
		 *  
		 * */
		
		// map< key = insider id, value = map2>, where map2 = map <key = <Date,Price>  , value =sumOfSharesCount> 
		HashMap <Integer, HashMap<TradesAtAValue,Integer>> AllTradesAggregated = new HashMap <Integer, HashMap<TradesAtAValue,Integer>> () ; 
		
		// key = insider id, value = set of all ratios
		HashMap <Integer, Set<Double>> AllSignedNormalizedDAs = new HashMap<Integer, Set<Double>> ();


		/*
		 * For each purchase transaction compute the total purchase price = no.of shares purchased x purchase price.
		 * For each purchase transaction compute dollar volume = closingPrice x stock volume.
		 * For each date, for each insider Compute sdna = total purchase price/dollar volume.
		 * If closingPrice > purchase price the store +sdna else store -sdna. 
		 * Save all of the above to a file purchases_SDNA_Data.csv.
		 * */




	}
	
	
}




