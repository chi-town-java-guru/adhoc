package adhoc;

public class Zip {
	private String zipcode;
	private String state;
	private String rateArea;

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRateArea() {
		return rateArea;
	}

	public void setRateArea(String rateArea) {
		this.rateArea = rateArea;
	}

	public Zip(String zipcode, String state, String rateArea) {
		this.zipcode = zipcode;
		this.state = state;
		this.rateArea = rateArea;
	}

}
