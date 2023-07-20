package locomotives;

import enums.Drive;

public class UniversalLocomotive extends Locomotive {

	public UniversalLocomotive(Integer power, String label, Drive drive) {
		super(power, label, drive);
	}

	@Override
	public String toString() {
		return "Universal locomotive." + super.toString();
	}

}
