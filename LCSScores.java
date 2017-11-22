import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * File Name: LCSScores
 */

/**
 * @author Sameera Bammidi
 * Created on: 19/11/2017
 *
 */
public class LCSScores
{
	public static void main(String[] args) throws SQLException, FileNotFoundException
	{
		// get jdbc connection
		Connection connection = JDBCMySQLConnection.getConnection();

		// get stockids (distinct companies)
		HashSet<Integer> stockids = new HashSet<Integer>();
		Statement statement = connection.createStatement();
		String query = "select distinct StockId from insidertrades2_demo"; // get stock ids
		ResultSet rs = statement.executeQuery(query);

		while(rs.next())
		{
			System.out.println(rs.getInt("StockId"));
			stockids.add(rs.getInt("StockId"));
		}
		System.out.println(stockids.size());

		HashMap<Integer, HashSet<Pair>> purchases_company_insiderPair_map = new HashMap<Integer, HashSet<Pair>>(); 
		HashMap<Integer, HashMap<Integer, TreeSet<Date>>> purchases_company_insiderTradeDates_map = new HashMap<Integer,HashMap<Integer, TreeSet<Date>>>();

		// for each stock id get all the PURCHASE trades and populate key=InsiderID val=Set of all his/her trade dates
		for(Integer stockid : stockids)
		{
			HashMap<Integer, TreeSet<Date>>  inid_date_map = new HashMap <Integer, TreeSet<Date>>();
			String query2 = "select InsiderId,Date from insidertrades2_demo where Action = 'Buy'  AND  StockId = " + stockid;
			rs = statement.executeQuery(query2);
			while(rs.next())
			{
				int id = rs.getInt("InsiderId");
				Date d = rs.getDate("Date");
				if(inid_date_map.containsKey(id))
				{
					inid_date_map.get(id).add(d);
				}
				else
				{
					TreeSet<Date> hsd = new TreeSet<Date>();
					hsd.add(d);
					inid_date_map.put(id, hsd);
				}
			}
			purchases_company_insiderTradeDates_map.put(stockid, inid_date_map);
			// now populate the pairwise Longest Common Subsequence score
			// output ONE single file of all similarity scores ( 1/2 of nxn matrix since it will be symmetric (which is also a version of adj matrix))
			// Build graph: nodes = insiderID, edges = LCS score, threshold = 10 => remove every edge that does not have a LCS > 10
			HashSet<Pair> allPurchasePairsForCurrentCompany = new HashSet<Pair>();
			ArrayList<Integer> allInsiders = new ArrayList<Integer> ();
			allInsiders.addAll(inid_date_map.keySet());
			for(int i=0; i<(allInsiders.size()-1); i++)
			{
				for(int j=i+1; j< allInsiders.size(); j++)
				{
					Pair p = new Pair(allInsiders.get(i), allInsiders.get(j));
					TreeSet<Date> d1 = inid_date_map.get(allInsiders.get(i));
					TreeSet<Date> d2 = inid_date_map.get(allInsiders.get(j));

					ArrayList<Date> d1Array = new ArrayList<Date>();
					d1Array.addAll(d1);
					//System.out.println(d1Array.toString()+d1Array.size());
					ArrayList<Date> d2Array = new ArrayList<Date>();
					d2Array.addAll(d2);
					//System.out.println(d2Array.toString()+d2Array.size());
					p.setScore(getLongestCommonSubsequence(d1Array,d2Array));
					//System.out.println("returned from lcs comp.. "+p.getScore());
					//System.exit(1);
					allPurchasePairsForCurrentCompany.add(p);
				}
			}
			purchases_company_insiderPair_map.put(stockid, allPurchasePairsForCurrentCompany);
		}
		connection.close();

		// saving purchase longestCommonSubsequence scores to a file for plotting histograms
		PrintWriter pwrp = new PrintWriter(new File("purchases_all_lcsScores.csv"));
		pwrp.println("company_stockID, insider1_id, insider2_id, lcs_score");
		for(Integer stockid: stockids)
		{
			HashSet<Pair> hpr = purchases_company_insiderPair_map.get(stockid);
			for(Pair p : hpr)
			{
				pwrp.println(stockid+","+p.id1+","+p.id2+","+p.getScore());
			}
		}
		pwrp.close();

		for(Integer companyPurchase : purchases_company_insiderTradeDates_map.keySet())
		{
			System.out.println("Company: " + companyPurchase + " Insider trade dates: " + purchases_company_insiderTradeDates_map.get(companyPurchase));
			System.out.println("==============================================================================");
		}
	}

	private static int getLongestCommonSubsequence(ArrayList<Date> a, ArrayList<Date> b)
	{
		int m = a.size();
		int n = b.size();
		int[][] dp = new int[m+1][n+1];

		for(int i=0; i<=m; i++){
			for(int j=0; j<=n; j++){
				if(i==0 || j==0){
					dp[i][j]=0;
				}else if(a.get(i-1).equals(b.get(j-1))){
					dp[i][j] = 1 + dp[i-1][j-1];
				}else{
					dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
				}
			}
		}
		return dp[m][n];
	}
}
