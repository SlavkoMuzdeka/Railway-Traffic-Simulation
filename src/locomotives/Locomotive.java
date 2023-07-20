package locomotives;

import enums.Drive;

public abstract class Locomotive {

	protected Integer power;
	protected String label;
	protected Drive drive;
	protected Integer startI;
	protected Integer startJ;

	public Locomotive() {
		super();
	}

	public Locomotive(Integer power, String label, Drive drive) {
		super();
		this.power = power;
		this.label = label;
		this.drive = drive;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Drive getDrive() {
		return drive;
	}

	public void setDrive(Drive drive) {
		this.drive = drive;
	}

	public Integer getStartI() {
		return startI;
	}

	public void setStartI(Integer startI) {
		this.startI = startI;
	}

	public Integer getStartJ() {
		return startJ;
	}

	public void setStartJ(Integer startJ) {
		this.startJ = startJ;
	}

	@Override
	public String toString() {
		return "Power: " + power + ".Label: " + label + ".Drive: " + drive;
	}

}
