package wagons;

public class FreightWagon extends Wagon {

	private Integer maxLoadCapacity;

	public FreightWagon(Integer length, String label, Integer maxLoadCapacity) {
		super(length, label);
		this.maxLoadCapacity = maxLoadCapacity;
	}

	public Integer getMaxLoadCapacity() {
		return maxLoadCapacity;
	}

	public void setMaxLoadCapacity(Integer maxLoadCapacity) {
		this.maxLoadCapacity = maxLoadCapacity;
	}

	@Override
	public String toString() {
		return "Freight wagon" + super.toString() + ".Maxium load capacity: " + maxLoadCapacity;
	}

}
