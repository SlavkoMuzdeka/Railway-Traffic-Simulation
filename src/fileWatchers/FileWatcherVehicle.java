package fileWatchers;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import enums.Direction;
import exceptions.MyException;
import main.Main;
import models.Map;
import vehicles.Vehicle;

public class FileWatcherVehicle extends Thread {

	private Handler handler;
	public static Integer maxVelocity1, maxVelocity2, maxVelocity3;
	public static Integer numVehicles1, numVehicles2, numVehicles3;
	public static final String vehiclePath = ".\\src\\vehicles";

	{
		try {
			handler = new FileHandler("fileWatcherVehicles.log");
			Logger.getLogger(FileWatcherVehicle.class.getName()).setUseParentHandlers(false);
			Logger.getLogger(FileWatcherVehicle.class.getName()).addHandler(handler);
		} catch (Exception ex) {
			Logger.getLogger(Map.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public FileWatcherVehicle() {
		super();
	}

	public void createVehicles(String txt) throws MyException, IOException {
		Integer newNumOfVehicles1, newNumOfVehicles2, newNumOfVehicles3;
		Vehicle[] vehicles1, vehicles2, vehicles3;

		BufferedReader br = new BufferedReader(new FileReader(vehiclePath + "\\" + txt));
		String s = br.readLine();
		String[] arr = s.split(";");
		br.close();

		Main.checkValidation(arr);

		maxVelocity1 = Integer.parseInt(arr[0]);
		maxVelocity2 = Integer.parseInt(arr[1]);
		maxVelocity3 = Integer.parseInt(arr[2]);
		numVehicles1 = Integer.parseInt(arr[3]);
		numVehicles2 = Integer.parseInt(arr[4]);
		numVehicles3 = Integer.parseInt(arr[5]);
		Main.path = arr[6];

		newNumOfVehicles1 = determineVehicleNumbers(numVehicles1, Main.numVehicles1);
		newNumOfVehicles2 = determineVehicleNumbers(numVehicles2, Main.numVehicles2);
		newNumOfVehicles3 = determineVehicleNumbers(numVehicles3, Main.numVehicles3);

		vehicles1 = new Vehicle[newNumOfVehicles1];
		vehicles2 = new Vehicle[newNumOfVehicles2];
		vehicles3 = new Vehicle[newNumOfVehicles3];

		Main.startVehicle(vehicles1, maxVelocity1, 29, 8, 21, 0, Direction.GORE, Direction.DESNO);
		Main.startVehicle(vehicles2, maxVelocity2, 29, 14, 0, 13, Direction.GORE, Direction.DOLE);
		Main.startVehicle(vehicles3, maxVelocity3, 29, 22, 20, 29, Direction.GORE, Direction.LIJEVO);
	}

	private Integer determineVehicleNumbers(Integer i, Integer j) {
		if (i >= j) {
			return i;
		} else {
			return j;
		}
	}

	@Override
	public void run() {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				try {
					Main.createVehicle();
				} catch (Exception ex) {
					Logger.getLogger(Map.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
				}
			}
		};
		new Thread(runnable).start();
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path dir = Paths.get(vehiclePath);
			dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
			while (true) {
				WatchKey key;
				try {
					key = watcher.take();
				} catch (Exception ex) {
					return;
				}
				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();
					@SuppressWarnings("unchecked")
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					Path fileName = ev.context();
					if (fileName.toString().trim().endsWith(".txt") && kind.equals(ENTRY_MODIFY)) {
						Runnable runnable1 = new Runnable() {
							@Override
							public void run() {
								try {
									createVehicles(fileName.toString());
								} catch (Exception ex) {
									Logger.getLogger(Map.class.getName()).log(Level.WARNING,
											ex.fillInStackTrace().toString());
								}
							}
						};
						new Thread(runnable1).start();
					}
				}
				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(FileWatcherVehicle.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

}
