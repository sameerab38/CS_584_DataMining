import java.util.Date;

public class Insidertrades2_demo
{
	private int id;
	private Date Date;
	private int StockId;
	private String Action;
	private long NumberOfShares;
	private int InsiderId;
	private long RemainingHoldings;
	private double Price;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	public int getStockId() {
		return StockId;
	}
	public void setStockId(int stockId) {
		this.StockId = stockId;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		this.Action = action;
	}
	public long getNumberOfShares() {
		return NumberOfShares;
	}
	public void setNumberOfShares(long numberOfShares) {
		this.NumberOfShares = numberOfShares;
	}
	public int getInsiderId() {
		return InsiderId;
	}
	public void setInsiderId(int insiderId) {
		this.InsiderId = insiderId;
	}
	public long getRemainingHoldings() {
		return RemainingHoldings;
	}
	public void setRemainingHoldings(long remainingHoldings) {
		this.RemainingHoldings = remainingHoldings;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		this.Price = price;
	}
	@Override
	public String toString() {
		return "Insidertrades2_demo [id=" + id + ", Date=" + Date
				+ ", StockId=" + StockId + ", Action=" + Action
				+ ", NumberOfShares=" + NumberOfShares + ", InsiderId="
				+ InsiderId + ", RemainingHoldings=" + RemainingHoldings
				+ ", Price=" + Price + "]";
	}
}
