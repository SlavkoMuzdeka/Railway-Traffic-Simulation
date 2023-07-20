package locomotives;

import enums.Drive;

public class FreightLocomotive extends Locomotive {

	public FreightLocomotive(Integer power, String label, Drive drive) {
		super(power, label, drive);
	}

	@Override
	public String toString() {
		return "Freight locomotive." + super.toString();
	}

}
