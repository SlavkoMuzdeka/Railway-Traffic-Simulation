package vehicles;

import java.util.Random;

public class Car extends Vehicle {

	private Integer numOfDoors;
	private Random rand;

	public Car(String brand, String model, String gods, Integer velocity) {
		super(model, brand, gods, velocity);
		rand = new Random();
		Integer low = 1;
		Integer max = 10;
		this.numOfDoors = rand.nextInt(max - low) + low;
	}

	public Integer getNumOfDoors() {
		return numOfDoors;
	}

	public void setNumOfDoors(Integer numOfDoors) {
		this.numOfDoors = numOfDoors;
	}

	@Override
	public String toString() {
		return super.toString() + ".Number of doors:" + numOfDoors;
	}

	@Override
	protected Boolean isCar() {
		return true;
	}

	@Override
	protected Boolean isTruck() {
		return false;
	}

}
