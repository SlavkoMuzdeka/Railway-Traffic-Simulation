package wagons;

public class SpecialPurposeWagon extends Wagon {

	public SpecialPurposeWagon(Integer length, String label) {
		super(length, label);
	}

	@Override
	public String toString() {
		return "Special purpose wagon. " + super.toString();
	}

}
