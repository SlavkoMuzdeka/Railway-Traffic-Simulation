package models;

public class Railroad {

	private boolean underVoltage;

	public Railroad() {
		super();
		this.underVoltage = false;
	}

	public boolean isUnderVoltage() {
		return underVoltage;
	}

	public void setUnderVoltage(boolean underVoltage) {
		this.underVoltage = underVoltage;
	}
}
