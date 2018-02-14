package adhoc;

public class SLCSP {
	private String zipcode;
	private String rate;

	public SLCSP(String zipcode, String rate) {
		this.zipcode = zipcode;
		this.rate = rate;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

}
