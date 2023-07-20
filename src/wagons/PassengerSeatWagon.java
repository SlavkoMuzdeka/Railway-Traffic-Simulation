package wagons;

public class PassengerSeatWagon extends PassengerWagon {

	private int numberOfSeats;

	public PassengerSeatWagon(Integer length, String label, Integer numberOfSeats) {
		super(length, label);
		this.numberOfSeats = numberOfSeats;
	}

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	@Override
	public String toString() {
		return "Passenger wagon with seats " + super.toString() + ".Number of seats: " + numberOfSeats;
	}

}
