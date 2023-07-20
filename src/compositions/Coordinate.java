package compositions;

import java.io.Serializable;

public class Coordinate implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer startI;
	private Integer startJ;

	public Coordinate() {
		super();
	}

	public Coordinate(Integer startI, Integer startJ) {
		this.startI = startI;
		this.startJ = startJ;
	}

	public void setStartI(Integer startI) {
		this.startI = startI;
	}

	public Integer getStartI() {
		return startI;
	}

	public void setStartJ(Integer startJ) {
		this.startJ = startJ;
	}

	public Integer getStartJ() {
		return startJ;
	}

	@Override
	public String toString() {
		return "startI = " + startI + ",startJ = " + startJ + "\n";
	}

}
