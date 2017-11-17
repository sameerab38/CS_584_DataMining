
public class Stock_demo
{
	private int StockId;
	private String CompanyName;
	private String Ticker;
	private String Exchange;
	private String Sector;
	private int Follow;
	public int getStockId() {
		return StockId;
	}
	public void setStockId(int stockId) {
		StockId = stockId;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getTicker() {
		return Ticker;
	}
	public void setTicker(String ticker) {
		Ticker = ticker;
	}
	public String getExchange() {
		return Exchange;
	}
	public void setExchange(String exchange) {
		Exchange = exchange;
	}
	public String getSector() {
		return Sector;
	}
	public void setSector(String sector) {
		Sector = sector;
	}
	public int getFollow() {
		return Follow;
	}
	public void setFollow(int follow) {
		Follow = follow;
	}
	@Override
	public String toString() {
		return "Stock_demo [StockId=" + StockId + ", CompanyName="
				+ CompanyName + ", Ticker=" + Ticker + ", Exchange=" + Exchange
				+ ", Sector=" + Sector + ", Follow=" + Follow + "]";
	}
}
