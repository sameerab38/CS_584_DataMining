
public class Insiderrelations_demo
{
	private int RelationId;
	private int StockId;
	private int InsiderId;	
	private String Position;
	public int getRelationId() {
		return RelationId;
	}
	public void setRelationId(int relationId) {
		this.RelationId = relationId;
	}
	public int getStockId() {
		return StockId;
	}
	public void setStockId(int stockId) {
		this.StockId = stockId;
	}
	public int getInsiderId() {
		return InsiderId;
	}
	public void setInsiderId(int insiderId) {
		this.InsiderId = insiderId;
	}
	public String getPosition() {
		return Position;
	}
	public void setPosition(String position) {
		this.Position = position;
	}
	@Override
	public String toString() {
		return "Insiderrelations_demo [RelationId=" + RelationId + ", StockId="
				+ StockId + ", InsiderId=" + InsiderId + ", Position="
				+ Position + "]";
	}
}
