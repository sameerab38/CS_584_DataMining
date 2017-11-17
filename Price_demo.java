import java.util.Date;
public class Price_demo
{
	private int IdStock;
    private float High;
    private float Low;
    private float Open;
    private float Close;
    private float Volume;
    private Date Date;
    private int IdData;
    private int analysed;
	public int getIdStock() {
		return IdStock;
	}
	public void setIdStock(int idStock) {
		this.IdStock = idStock;
	}
	public float getHigh() {
		return High;
	}
	public void setHigh(float high) {
		this.High = high;
	}
	public float getLow() {
		return Low;
	}
	public void setLow(float low) {
		this.Low = low;
	}
	public float getOpen() {
		return Open;
	}
	public void setOpen(float open) {
		this.Open = open;
	}
	public float getClose() {
		return Close;
	}
	public void setClose(float close) {
		this.Close = close;
	}
	public float getVolume() {
		return Volume;
	}
	public void setVolume(float volume) {
		this.Volume = volume;
	}
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		this.Date = date;
	}
	public int getIdData() {
		return IdData;
	}
	public void setIdData(int idData) {
		this.IdData = idData;
	}
	public int getAnalysed() {
		return analysed;
	}
	public void setAnalysed(int analysed) {
		this.analysed = analysed;
	}
	@Override
	public String toString() {
		return "Price_demo [IdStock=" + IdStock + ", High=" + High + ", Low="
				+ Low + ", Open=" + Open + ", Close=" + Close + ", Volume="
				+ Volume + ", Date=" + Date + ", IdData=" + IdData
				+ ", analysed=" + analysed + "]";
	}
}
