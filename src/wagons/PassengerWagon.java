package wagons;

public abstract class PassengerWagon extends Wagon {

	public PassengerWagon(Integer length, String label) {
		super(length, label);
	}

	@Override
	public String toString() {
		return "Passenger wagon" + super.toString();
	}

}
