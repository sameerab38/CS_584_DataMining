/**
 * @author SameeraBammidi
 * Created on: 12/02/2017
 */

import java.util.Date;

public class Transaction
{

	int TradeId;
	int InsiderId;
	Date dateOfTransaction;
	int StockId;
	double totalTransactionPrice;
	double dollarVolumePerTransaction;

	public Transaction(int tradeId, int insiderId, Date dateOfTransaction, int stockId, double totalTransactionPrice, double dollarVolumePerTransaction)
	{
		super();
		this.TradeId = tradeId;
		this.InsiderId = insiderId;
		this.dateOfTransaction = dateOfTransaction;
		this.StockId = stockId;
		this.totalTransactionPrice = totalTransactionPrice;
		this.dollarVolumePerTransaction = dollarVolumePerTransaction;
	}

	@Override
	public String toString()
	{
		return "Transaction [TradeId=" + TradeId + ", InsiderId=" + InsiderId + ", dateOfTransaction="
				+ dateOfTransaction + ", StockId=" + StockId + ", totalTransactionPrice=" + totalTransactionPrice
				+ ", dollarVolumePerTransaction=" + dollarVolumePerTransaction + "]";
	}
	
}
