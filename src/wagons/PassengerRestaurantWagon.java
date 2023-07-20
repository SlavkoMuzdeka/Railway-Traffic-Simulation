package wagons;

public class PassengerRestaurantWagon extends PassengerWagon {

	private String description;

	public PassengerRestaurantWagon(Integer length, String label, String description) {
		super(length, label);
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Passenger restaurant wagon " + super.toString() + ".Description: " + description;
	}

}
