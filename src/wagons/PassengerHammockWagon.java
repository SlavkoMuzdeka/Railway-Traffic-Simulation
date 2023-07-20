package wagons;

public class PassengerHammockWagon extends PassengerWagon {

	private Integer numberOfSeats;

	public PassengerHammockWagon(Integer length, String label, Integer numberOfSeats) {
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
		return "Passenger with a hammock wagon " + super.toString() + ".Number of hammocks: " + numberOfSeats;
	}

}
