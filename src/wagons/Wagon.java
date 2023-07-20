package wagons;

public abstract class Wagon {

	protected Integer length;
	protected String label;
	protected Integer startI;
	protected Integer startJ;

	public Wagon() {
		super();
	}

	public Wagon(Integer length, String label) {
		super();
		this.length = length;
		this.label = label;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
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
		return ".Length: " + length + ".Label: " + label;
	}

}
