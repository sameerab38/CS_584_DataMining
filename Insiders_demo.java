
public class Insiders_demo
{
	private int InsiderId;
	private String FirstName;
	private String LastName;	
	private String Link;
	public int getInsiderId() {
		return InsiderId;
	}
	public void setInsiderId(int insiderId) {
		this.InsiderId = insiderId;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		this.FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		this.LastName = lastName;
	}
	public String getLink() {
		return Link;
	}
	public void setLink(String link) {
		this.Link = link;
	}
	@Override
	public String toString() {
		return "Insiders_demo [InsiderId=" + InsiderId + ", FirstName="
				+ FirstName + ", LastName=" + LastName + ", Link=" + Link + "]";
	}
}
