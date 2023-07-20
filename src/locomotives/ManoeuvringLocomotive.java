package locomotives;

import enums.Drive;

public class ManoeuvringLocomotive extends Locomotive {

	public ManoeuvringLocomotive(Integer power, String label, Drive drive) {
		super(power, label, drive);
	}

	@Override
	public String toString() {
		return "Manoeuvring locomotive." + super.toString();
	}

}
