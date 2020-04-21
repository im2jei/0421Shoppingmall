package DB;

public class ManagementDTO {
	int code;
	String cname;
	int cnt;
	int price;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String name) {
		this.cname = name;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String[] getArray() {
		String[] returnData = new String[4];
		returnData[0] = String.valueOf(this.code);
		returnData[1] = this.cname;
		returnData[2] = String.valueOf(this.cnt);
		returnData[3] = String.valueOf(this.price);

		return returnData;
	}

}
