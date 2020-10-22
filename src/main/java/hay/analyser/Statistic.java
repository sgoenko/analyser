package hay.analyser;

public class Statistic {
	private int number;
	private double average;

	@Override
	public String toString() {
		return "Number of transactions = " + number + ", Average Transaction Value = " + average + "]";
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

}
