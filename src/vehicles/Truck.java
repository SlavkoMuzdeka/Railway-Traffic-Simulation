package vehicles;

import java.util.Random;

public class Truck extends Vehicle {

	private Integer loadCapacity;
	private Random rand;

	public Truck(String model, String brand, String gods, Integer velocity) {
		super(model, brand, gods, velocity);
		rand = new Random();
		Integer low = 100;
		Integer max = 500;
		this.loadCapacity = rand.nextInt(max - low) + low;
	}

	public Integer getLoadCapacity() {
		return loadCapacity;
	}

	public void setLoadCapacity(Integer loadCapacity) {
		this.loadCapacity = loadCapacity;
	}

	@Override
	public String toString() {
		return super.toString() + ".Load capacity of truck is: " + loadCapacity;
	}

	@Override
	protected Boolean isCar() {
		return false;
	}

	@Override
	protected Boolean isTruck() {
		return true;
	}

}
