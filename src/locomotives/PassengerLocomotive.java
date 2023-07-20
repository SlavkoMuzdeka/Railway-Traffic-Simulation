package locomotives;

import enums.Drive;

public class PassengerLocomotive extends Locomotive {

	public PassengerLocomotive(Integer power, String label, Drive drive) {
		super(power, label, drive);
	}

	@Override
	public String toString() {
		return "Passenger locomotive." + super.toString();
	}

}
