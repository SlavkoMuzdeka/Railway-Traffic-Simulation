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

import compositions.RailwayComposition;
import enums.Drive;
import exceptions.MyException;
import locomotives.Locomotive;
import locomotives.ManoeuvringLocomotive;
import locomotives.PassengerLocomotive;
import locomotives.FreightLocomotive;
import locomotives.UniversalLocomotive;
import main.Main;
import models.Map;
import wagons.PassengerWagon;
import wagons.PassengerRestaurantWagon;
import wagons.PassengerHammockWagon;
import wagons.PassengerSeatWagon;
import wagons.PassengerSleepWagon;
import wagons.FreightWagon;
import wagons.Wagon;
import wagons.SpecialPurposeWagon;

public class FileWatcherComposition extends Thread {

	public static final String putanja = ".\\src\\compositions";
	public Handler handler;
	private Map map;

	{
		try {
			handler = new FileHandler("FileWatcherComposition.log");
			Logger.getLogger(FileWatcherComposition.class.getName()).setUseParentHandlers(false);
			Logger.getLogger(FileWatcherComposition.class.getName()).addHandler(handler);
		} catch (Exception ex) {
			Logger.getLogger(FileWatcherComposition.class.getName()).log(Level.WARNING,
					ex.fillInStackTrace().toString());
		}
	}

	public FileWatcherComposition(Map map) {
		super();
		this.map = map;

	}

	public void createComposition(String txt) throws IOException, MyException {
		Integer numOfLocomotives, numOfWagons, velocity, pom;
		Locomotive[] locomotives;
		Wagon[] wagons;
		RailwayComposition composition;
		String[] stations;

		BufferedReader bf = new BufferedReader(new FileReader(putanja + "\\" + txt));
		String s = bf.readLine();
		String[] arr = s.split(";");
		bf.close();

		numOfLocomotives = Integer.parseInt(arr[0]);
		numOfWagons = Integer.parseInt(arr[1]);
		locomotives = new Locomotive[numOfLocomotives];
		wagons = new Wagon[numOfWagons];
		stations = arr[arr.length - 1].split("-");

		Main.checkValidation(arr);
		if (numOfLocomotives == 0) {
			throw new MyException("Number of locomotives must be a minimum 1!");
		}

		pom = createLocomotives(2, numOfLocomotives, arr, locomotives);
		velocity = createWagons(pom, numOfWagons, arr, wagons);

		try {
			if (areLocomotivesTheSameType(locomotives) == false) {
				if (areWagonsTheSameType(wagons, locomotives) == false) {
					if (areStationsValid(stations) == false) {
						composition = new RailwayComposition(locomotives, wagons, velocity, stations, map);
						composition.start();
					} else {
						throw new MyException("Stations aren't valid!");
					}
				} else {
					throw new MyException("Wagons aren't the same type!");
				}
			} else {
				throw new MyException("Locomotives aren't the same type");
			}
		} catch (ArrayIndexOutOfBoundsException ex) {
			Logger.getLogger(FileWatcherComposition.class.getName()).log(Level.WARNING,
					ex.fillInStackTrace().toString());
		}
	}

	private Integer createLocomotives(Integer j, Integer numOfLocomotives, String[] arr, Locomotive[] locomotives)
			throws MyException {
		for (Integer i = 0; i < numOfLocomotives; i++) {
			if ("PUTNICKA".equals(arr[j].toUpperCase())) {
				j++;
				locomotives[i] = new PassengerLocomotive(Integer.parseInt(arr[j++]), arr[j++],
						Drive.valueOf(arr[j++].toUpperCase()));
			} else if ("TERETNA".equals(arr[j].toUpperCase())) {
				j++;
				locomotives[i] = new FreightLocomotive(Integer.parseInt(arr[j++]), arr[j++],
						Drive.valueOf(arr[j++].toUpperCase()));
			} else if ("UNIVERZALNA".equals(arr[j].toUpperCase())) {
				j++;
				locomotives[i] = new UniversalLocomotive(Integer.parseInt(arr[j++]), arr[j++],
						Drive.valueOf(arr[j++].toUpperCase()));
			} else if ("MANEVARSKA".equals(arr[j].toUpperCase())) {
				j++;
				locomotives[i] = new ManoeuvringLocomotive(Integer.parseInt(arr[j++]), arr[j++],
						Drive.valueOf(arr[j++].toUpperCase()));
			} else {
				throw new MyException("Wrong name for locomotive");
			}
		}
		return j;
	}

	private Integer createWagons(Integer j, Integer numOfWagons, String[] arr, Wagon[] wagons) throws MyException {
		Integer velocity;
		if (numOfWagons == 0) {
			velocity = Integer.parseInt(arr[j]);
			if (velocity >= 2000) {
				throw new MyException("Velocity of composition must be less than 2000!");
			}
			return velocity;
		} else {
			for (Integer i = 0; i < numOfWagons; i++) {
				if ("TERETNI".equals(arr[j].toUpperCase())) {
					j++;
					wagons[i] = new FreightWagon(Integer.parseInt(arr[j++]), arr[j++], Integer.parseInt(arr[j++]));
				} else if ("PUTNICKIRESTORAN".equals(arr[j].toUpperCase())) {
					j++;
					wagons[i] = new PassengerRestaurantWagon(Integer.parseInt(arr[j++]), arr[j++], arr[j++]);
				} else if ("PUTNICKISALEZAJEM".equals(arr[j].toUpperCase())) {
					j++;
					wagons[i] = new PassengerHammockWagon(Integer.parseInt(arr[j++]), arr[j++],
							Integer.parseInt(arr[j++]));
				} else if ("PUTNICKISASJEDISTEM".equals(arr[j].toUpperCase())) {
					j++;
					wagons[i] = new PassengerSeatWagon(Integer.parseInt(arr[j++]), arr[j++],
							Integer.parseInt(arr[j++]));
				} else if ("PUTNICKIZASPAVANJE".equals(arr[j].toUpperCase())) {
					j++;
					wagons[i] = new PassengerSleepWagon(Integer.parseInt(arr[j++]), arr[j++]);
				} else if ("VAGONZAPOSEBNENAMJENE".equals(arr[j].toUpperCase())) {
					j++;
					wagons[i] = new SpecialPurposeWagon(Integer.parseInt(arr[j++]), arr[j++]);
				} else {
					throw new MyException("Wrong name for wagon!");
				}
			}
			velocity = Integer.parseInt(arr[j]);
			if (velocity >= 2000) {
				throw new MyException("Velocity of composition must be less than 2000!");
			}
			return velocity;
		}
	}

	private boolean areLocomotivesTheSameType(Locomotive[] locomotives) {
		for (Integer i = 0; i < locomotives.length; i++) {
			for (Integer j = i + 1; j < locomotives.length; j++) {
				if (locomotives[i] instanceof PassengerLocomotive) {
					if (locomotives[j] instanceof FreightLocomotive) {
						return true;
					} else if (!(locomotives[i].getDrive().equals(locomotives[j].getDrive()))) {
						return true;
					}
				} else if (locomotives[i] instanceof FreightLocomotive) {
					if (locomotives[j] instanceof PassengerLocomotive) {
						return true;
					} else if (!(locomotives[i].getDrive().equals(locomotives[j].getDrive()))) {
						return true;
					}
				} else if (locomotives[i] instanceof ManoeuvringLocomotive) {
					if (locomotives[j] instanceof PassengerLocomotive || locomotives[j] instanceof FreightLocomotive) {
						return true;
					} else if (!(locomotives[i].getDrive().equals(locomotives[j].getDrive()))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean areWagonsTheSameType(Wagon[] wagons, Locomotive[] locomotives) {
		for (Integer i = 0; i < wagons.length; i++) {
			for (Integer j = 0; j < locomotives.length; j++) {
				if (wagons[i] instanceof FreightWagon) {
					if (locomotives[j] instanceof PassengerLocomotive
							|| locomotives[j] instanceof ManoeuvringLocomotive) {
						return true;
					}
				} else if (wagons[i] instanceof PassengerWagon || wagons[i] instanceof PassengerRestaurantWagon
						|| wagons[i] instanceof PassengerHammockWagon || wagons[i] instanceof PassengerSeatWagon
						|| wagons[i] instanceof PassengerSleepWagon) {
					if (locomotives[j] instanceof FreightLocomotive
							|| locomotives[j] instanceof ManoeuvringLocomotive) {
						return true;
					}
				} else if (wagons[i] instanceof SpecialPurposeWagon) {
					if (locomotives[j] instanceof FreightLocomotive || locomotives[j] instanceof PassengerLocomotive) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean areStationsValid(String[] stations) {
		if (stations.length == 1 || stations.length > 4) {
			return true;
		} else if (stations.length == 2) {
			String stations1 = stations[0];
			String stations2 = stations[1];
			if (areTwoStationValid(stations1, stations2) == false) {
				return true;
			}
		} else if (stations.length == 3) {
			String stations1 = stations[0];
			String stations2 = stations[1];
			String stations3 = stations[2];
			if (areThreeStationValid(stations1, stations2, stations3) == false) {
				return true;
			}
		} else if (stations.length == 4) {
			String stations1 = stations[0];
			String stations2 = stations[1];
			String stations3 = stations[2];
			String stations4 = stations[3];
			if (areFourStationValid(stations1, stations2, stations3, stations4) == false) {
				return true;
			}
		}
		return false;
	}

	private boolean areTwoStationValid(String stations1, String stations2) {
		if ("A".equals(stations1) && "B".equals(stations2)) {
			return true;
		} else if ("B".equals(stations1) && ("A".equals(stations2) || "C".equals(stations2))) {
			return true;
		} else if ("C".equals(stations1) && ("B".equals(stations2) || "E".equals(stations2) || "D".equals(stations2))) {
			return true;
		} else if ("D".equals(stations1) && "C".equals(stations2)) {
			return true;
		} else if ("E".equals(stations1) && "C".equals(stations2)) {
			return true;
		}
		return false;
	}

	private boolean areThreeStationValid(String stations1, String stations2, String stations3) {
		if ("A".equals(stations1) && "B".equals(stations2) && "C".equals(stations3)) {
			return true;
		} else if ("C".equals(stations1) && "B".equals(stations2) && "A".equals(stations3)) {
			return true;
		} else if ("B".equals(stations1) && "C".equals(stations2) && ("D".equals(stations3) || "E".equals(stations3))) {
			return true;
		} else if ("D".equals(stations1) && "C".equals(stations2) && ("E".equals(stations3) || "B".equals(stations3))) {
			return true;
		} else if ("E".equals(stations1) && "C".equals(stations2) && ("D".equals(stations3) || "B".equals(stations3))) {
			return true;
		}
		return false;
	}

	private boolean areFourStationValid(String stations1, String stations2, String stations3, String stations4) {
		if ("A".equals(stations1) && "B".equals(stations2) && "C".equals(stations3)
				&& ("D".equals(stations4) || "E".equals(stations4))) {
			return true;
		} else if (("E".equals(stations1) || "D".equals(stations1)) && "C".equals(stations2) && "B".equals(stations3)
				&& "A".equals(stations4)) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path dir = Paths.get(putanja);
			dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
			while (true) {
				WatchKey key;
				try {
					key = watcher.take();
				} catch (Exception ex) {
					Logger.getLogger(FileWatcherComposition.class.getName()).log(Level.WARNING,
							ex.fillInStackTrace().toString());
					return;
				}
				for (WatchEvent<?> event : key.pollEvents()) {
					WatchEvent.Kind<?> kind = event.kind();
					@SuppressWarnings("unchecked")
					WatchEvent<Path> ev = (WatchEvent<Path>) event;
					Path fileName = ev.context();
					if ((kind.equals(ENTRY_MODIFY) || kind.equals(ENTRY_CREATE))
							&& fileName.toString().trim().endsWith(".txt")) {
						try {
							createComposition(fileName.toString());
						} catch (IOException | MyException ex) {
							Logger.getLogger(FileWatcherComposition.class.getName()).log(Level.WARNING,
									ex.fillInStackTrace().toString());
						}
					}
				}
				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			}
		} catch (IOException ex) {
			Logger.getLogger(FileWatcherComposition.class.getName()).log(Level.WARNING,
					ex.fillInStackTrace().toString());
		}
	}
}
