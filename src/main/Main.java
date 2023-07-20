package main;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import enums.Direction;
import exceptions.MyException;
import fileWatchers.FileWatcherVehicle;
import models.Map;
import vehicles.Car;
import vehicles.Truck;
import vehicles.Vehicle;

public class Main {

	public static final String vehiclePath = ".\\src\\vehicles\\Vehicle.txt";

	public static Integer maxVelocity1, maxVelocity2, maxVelocity3;
	public static Integer numVehicles1, numVehicles2, numVehicles3;

	public static Integer timeOfCreation = 2000;
	public static final Integer minVelocity = 200;

	public static Random rand = new Random();

	public static final String[] vehicleType = { "A", "K" };
	public static final String[] direction = { "P", "K" };
	public static final String[] carModel = { "BMW m4", "BMW m5", "BMW m6", "Audi a6", "Audi a4", "Audi a8" };
	public static final String[] carBrand = { "BMW" };
	public static final String[] carGods = { "1992", "2001", "2005", "2021", "2017", "2010" };
	public static final String[] truckModel = { "Mercedes truck", "Mercedes s", "Mercedes a", "Mercedes r",
			"Mercedes p", "Mercedes o" };
	public static final String[] truckBrand = { "Mercedes" };
	public static final String[] truckGods = { "1993", "2012", "2015", "2011", "2017", "2010" };

	public static String path;

	public static void main(String[] args) {
		new Map();
	}

	public static void createVehicle() throws MyException, IOException {
		Vehicle[] vehicles1, vehicles2, vehicles3;
		BufferedReader br = new BufferedReader(new FileReader(vehiclePath));
		String s = br.readLine();
		String[] arr = s.split(";");
		br.close();

		checkValidation(arr);

		maxVelocity1 = Integer.parseInt(arr[0]);
		maxVelocity2 = Integer.parseInt(arr[1]);
		maxVelocity3 = Integer.parseInt(arr[2]);

		numVehicles1 = Integer.parseInt(arr[3]);
		numVehicles2 = Integer.parseInt(arr[4]);
		numVehicles3 = Integer.parseInt(arr[5]);

		path = arr[6];

		vehicles1 = new Vehicle[numVehicles1];
		vehicles2 = new Vehicle[numVehicles2];
		vehicles3 = new Vehicle[numVehicles3];

		startVehicle(vehicles1, maxVelocity1, 29, 8, 21, 0, Direction.GORE, Direction.DESNO);
		startVehicle(vehicles2, maxVelocity2, 29, 14, 0, 13, Direction.GORE, Direction.DOLE);
		startVehicle(vehicles3, maxVelocity3, 29, 22, 20, 29, Direction.GORE, Direction.LIJEVO);
	}

	public static void startVehicle(Vehicle[] vehicles, Integer velocity, Integer j, Integer k, Integer l, Integer m,
			Direction direction1, Direction direction2) {
		Integer brzinaKretanjaVozila;
		for (Integer i = 0; i < vehicles.length; i++) {
			String tip = getRandom(vehicleType);
			if ("A".equals(tip)) {
				brzinaKretanjaVozila = rand.nextInt(velocity - minVelocity) + minVelocity;// Brzina se krece od
																							// 200 do maksimalno
																							// dozvoljene na tom
																							// putu
				vehicles[i] = new Car(getRandom(carModel), getRandom(carBrand), getRandom(carGods),
						brzinaKretanjaVozila);
				vehicles[i].setColor(Color.red);
				if ("P".equals(getRandom(direction))) {
					vehicles[i].setStartI(j);
					vehicles[i].setStartJ(k);
					vehicles[i].setDirection(direction1);
				} else {
					vehicles[i].setStartI(l);
					vehicles[i].setStartJ(m);
					vehicles[i].setDirection(direction2);
				}
				try {
					Thread.sleep(timeOfCreation);
				} catch (Exception ex) {
					Logger.getLogger(FileWatcherVehicle.class.getName()).log(Level.WARNING,
							ex.fillInStackTrace().toString());
				}
				vehicles[i].start();
			} else if ("K".equals(tip)) {
				brzinaKretanjaVozila = rand.nextInt(velocity - minVelocity) + minVelocity;
				vehicles[i] = new Truck(getRandom(truckModel), getRandom(truckBrand), getRandom(truckGods),
						brzinaKretanjaVozila);
				vehicles[i].setColor(Color.yellow);
				if ("P".equals(getRandom(direction))) {
					vehicles[i].setStartI(j);
					vehicles[i].setStartJ(k);
					vehicles[i].setDirection(direction1);
				} else {
					vehicles[i].setStartI(l);
					vehicles[i].setStartJ(m);
					vehicles[i].setDirection(direction2);
				}
				try {
					Thread.sleep(timeOfCreation);
				} catch (Exception ex) {
					Logger.getLogger(FileWatcherVehicle.class.getName()).log(Level.WARNING,
							ex.fillInStackTrace().toString());
				}
				vehicles[i].start();
			}
		}
	}

	public static void checkValidation(String[] arr) throws MyException {
		for (Integer i = 0; i < arr.length; i++) {
			if (arr[i].isBlank() || arr[i].isEmpty()) {
				throw new MyException("Field mustn't be empty.");
			}
		}
	}

	public static String getRandom(String[] arr) {
		return arr[rand.nextInt(arr.length)];
	}

}
