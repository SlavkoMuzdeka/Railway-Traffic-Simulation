package models;

public class RailRoadCrossing {

	private boolean stripedCrossingUnderVoltage;
	private boolean stripedCrossingBusy;

	public RailRoadCrossing() {
		super();
		this.stripedCrossingUnderVoltage = false;
		this.stripedCrossingBusy = false;
	}

	public boolean isStripedCrossingUnderVoltage() {
		return stripedCrossingUnderVoltage;
	}

	public void setStripedCrossingUnderVoltage(boolean stripedCrossingUnderVoltage) {
		this.stripedCrossingUnderVoltage = stripedCrossingUnderVoltage;
	}

	public boolean isStripedCrossingBusy() {
		return stripedCrossingBusy;
	}

	public void setStripedCrossingBusy(boolean stripedCrossingBusy) {
		this.stripedCrossingBusy = stripedCrossingBusy;
	}
}
