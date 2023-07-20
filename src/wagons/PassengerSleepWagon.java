package wagons;

public class PassengerSleepWagon extends PassengerWagon {

	public PassengerSleepWagon(Integer length, String label) {
		super(length, label);
	}

	@Override
	public String toString() {
		return "Passenger for sleeping wagon " + super.toString();
	}

}
