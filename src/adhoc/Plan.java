package adhoc;

public class Plan {
	private String state;
	private String rateArea;
	private String rate;

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

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Plan(String state, String rateArea, String rate) {
		this.state = state;
		this.rateArea = rateArea;
		this.rate = rate;
	}

}
