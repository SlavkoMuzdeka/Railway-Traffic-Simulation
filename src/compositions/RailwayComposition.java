package compositions;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import enums.Direction;
import enums.Drive;
import locomotives.Locomotive;
import main.Main;
import models.Section;
import models.Map;
import models.Railroad;
import models.RailRoadCrossing;
import models.RailwayStation;
import wagons.Wagon;

public class RailwayComposition extends Thread {

	private Locomotive[] locomotives;
	private Wagon[] wagons;
	private String[] stations;

	private Integer velocity;
	private Integer startI, startJ;
	private Integer numberOfComposition;

	private Direction direction;
	private Section section;
	
	private Map map;
	private boolean isOnStation;
	private MovementHistory movementHistory;
	private ArrayList<String> stationsList, stationsList1;
	public static Integer num = 1, numberOfRailwayCompoistions;

	public RailwayComposition() {
		super();
	}

	public RailwayComposition(Locomotive[] locomotives, Wagon[] wagons, Integer velocity, String[] stations,
			Map map) {
		this.locomotives = locomotives;
		this.wagons = wagons;
		this.velocity = velocity;
		this.stations = stations;
		this.numberOfComposition = num++;
		this.map = map;
		
		movementHistory = new MovementHistory();
		isOnStation = true;
		stationsList = new ArrayList<>();
		stationsList1 = new ArrayList<>();
		for (Integer i = 0; i < stations.length; i++) {
			stationsList.add(stations[i]);
			stationsList1.add(stations[i]);
		}
	}

	@Override
	public synchronized void run() {
		Integer numOfStations = stations.length;
		Integer br = 0;
		Drive drive = null;
		for (Integer i = 0; i < locomotives.length; i++) {
			if (locomotives[i].getDrive() == Drive.ELEKTRICNA) {
				drive = Drive.ELEKTRICNA;
			}
		}
		long startTime = System.currentTimeMillis();
		while (numOfStations > 1) {
			if (isOnStation == true) {
				for (Integer i = 0; i < map.getStations().length; i++) {
					if (stationsList.get(0).equals(map.getStations()[i].getStationName())) {
						map.getStations()[i].addOnStation(this);
						break;
					}
				}
				try {
					wait();
				} catch (InterruptedException ex) {
					Logger.getLogger(RailwayComposition.class.getName()).log(Level.WARNING,
							ex.fillInStackTrace().toString());
				}
				isOnStation = false;
			} else {
				try {
					sleep(velocity);
				} catch (InterruptedException ex) {
					Logger.getLogger(Map.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
				}
				Integer prevI = startI, prevJ = startJ;
				Direction directionMovement = directionMovement();
				if (directionMovement == Direction.GORE) {
					startI = startI - 1;
				} else if (directionMovement == Direction.DOLE) {
					startI = startI + 1;
				} else if (directionMovement == Direction.LIJEVO) {
					startJ = startJ - 1;
				} else if (directionMovement == Direction.DESNO) {
					startJ = startJ + 1;
				}
				if (Map.maps[prevI][prevJ] instanceof RailwayStation) {
					Integer pom = section.getNumOfCompositionsOnSection();
					pom++;
					section.setNumOfCompositionsOnSection(pom);
				}
				if (drive != null) {
					if (Map.maps[startI][startJ] instanceof Railroad) {
						((Railroad) Map.maps[startI][startJ]).setUnderVoltage(true);
					} else if (Map.maps[startI][startJ] instanceof RailRoadCrossing) {
						RailRoadCrossing crossing = section.getCrossing();
						crossing.setStripedCrossingUnderVoltage(true);
					}
				}
				if (drive != null && br == 3) {
					RailRoadCrossing crossing = section.getCrossing();
					crossing.setStripedCrossingUnderVoltage(false);
					br = 0;
				}
				if (Map.map[startI][startJ].getBackground().equals(Color.green)
						|| Map.map[startI][startJ].getBackground().equals(Color.orange)) {
					while (Map.map[startI][startJ].getBackground().equals(Color.green)
							|| Map.map[startI][startJ].getBackground().equals(Color.orange))
						;
				}
				Integer currI = startI, currJ = startJ;
				Coordinate koordinata = new Coordinate(currI, currJ);
				movementHistory.addCoordinate(koordinata);
				for (Integer i = 0; i < locomotives.length; i++) {
					prevI = locomotives[i].getStartI();
					prevJ = locomotives[i].getStartJ();
					locomotives[i].setStartI(currI);
					locomotives[i].setStartJ(currJ);
					Map.map[currI][currJ].setBackground(Color.green);
					currI = prevI;
					currJ = prevJ;
				}
				for (Integer i = 0; i < wagons.length; i++) {
					prevI = wagons[i].getStartI();
					prevJ = wagons[i].getStartJ();
					wagons[i].setStartI(currI);
					wagons[i].setStartJ(currJ);
					Map.map[currI][currJ].setBackground(Color.orange);
					currI = prevI;
					currJ = prevJ;
				}
				if (Map.maps[prevI][prevJ] instanceof Railroad && drive != null) {
					((Railroad) Map.maps[prevI][prevJ]).setUnderVoltage(false);
				}
				if (Map.maps[prevI][prevJ] instanceof RailRoadCrossing) {
					Map.map[prevI][prevJ].setBackground(Color.black);
					br++;
				} else {
					Map.map[prevI][prevJ].setBackground(Color.GRAY);
				}
				if (drive != null) {
					if (br == 2) {
						Integer pom = section.getNumOfCompositionsOnSection();
						pom--;
						section.setNumOfCompositionsOnSection(pom);
						if (section.getNumOfCompositionsOnSection() == 0) {
							RailRoadCrossing crossing = section.getCrossing();
							crossing.setStripedCrossingBusy(false);
						}
						br++;
					}
				}
				if (br == 2 && drive == null) {
					Integer pom = section.getNumOfCompositionsOnSection();
					pom--;
					section.setNumOfCompositionsOnSection(pom);
					if (section.getNumOfCompositionsOnSection() == 0) {
						RailRoadCrossing crossing = section.getCrossing();
						crossing.setStripedCrossingBusy(false);
					}
					br = 0;
				}
				direction = directionMovement;
				if (Map.maps[startI][startJ] instanceof RailwayStation) {
					if (drive != null && br == 3) {
						RailRoadCrossing crossing = section.getCrossing();
						crossing.setStripedCrossingBusy(false);
						br = 0;
					}
					Integer broj = locomotives.length + wagons.length;
					for (Integer i = broj; i > 0; i--) {
						try {
							sleep(velocity);
						} catch (InterruptedException ex) {
							Logger.getLogger(Map.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
						}
						Integer pomPrevI = startI, pomPrevJ = startJ, pomTrenutnoI = startI, pomTrenutnoJ = startJ;
						for (Integer j = 0; j < locomotives.length; j++) {
							pomPrevI = locomotives[j].getStartI();
							pomPrevJ = locomotives[j].getStartJ();
							locomotives[j].setStartI(pomTrenutnoI);
							locomotives[j].setStartJ(pomTrenutnoJ);
							Map.map[pomTrenutnoI][pomTrenutnoJ].setBackground(Color.green);
							pomTrenutnoI = pomPrevI;
							pomTrenutnoJ = pomPrevJ;
						}
						for (Integer j = 0; j < wagons.length; j++) {
							pomPrevI = wagons[j].getStartI();
							pomPrevJ = wagons[j].getStartJ();
							wagons[j].setStartI(pomTrenutnoI);
							wagons[j].setStartJ(pomTrenutnoJ);
							Map.map[pomTrenutnoI][pomTrenutnoJ].setBackground(Color.orange);
							pomTrenutnoI = pomPrevI;
							pomTrenutnoJ = pomPrevJ;
						}
						if (Map.maps[pomPrevI][pomPrevJ] instanceof Railroad) {
							Map.map[pomPrevI][pomPrevJ].setBackground(Color.GRAY);
						}
					}
					if (section.getSectionDirection() == -1) {
						Integer temp = section.getCompositionFirstDirection();
						temp--;
						section.setCompositionFirstDirection(temp);
						if (section.getCompositionFirstDirection() == 0) {
							section.setSectionDirection(0);
						}
					} else if (section.getSectionDirection() == 1) {
						Integer temp = section.getCompositionSecondDirection();
						temp--;
						section.setCompositionSecondDirection(temp);
						if (section.getCompositionSecondDirection() == 0) {
							section.setSectionDirection(0);
						}
					}
					this.stationsList.remove(0);
					Map.map[startI][startJ].setBackground(Color.GRAY);
					numberOfRailwayCompoistions--;
					isOnStation = true;
				}
			}
		}
		long endTime = System.currentTimeMillis();
		long totalTime = (endTime - startTime);
		movementHistory.setTime(totalTime);
		movementHistory.setStations(stationsList1);
		try {
			FileOutputStream fos = new FileOutputStream(Main.path + "" + numberOfComposition + "_Composition" + ".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(movementHistory);
			oos.close();
			numberOfRailwayCompoistions++;
		} catch (Exception ex) {
			Logger.getLogger(Map.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public Direction directionMovement() {
		if (direction == Direction.GORE) {
			direction = isUp();
			return direction;
		} else if (direction == Direction.DESNO) {
			direction = isRight();
			return direction;
		} else if (direction == Direction.DOLE) {
			direction = isDown();
			return direction;
		} else if (direction == Direction.LIJEVO) {
			direction = isLeft();
			return direction;
		}
		return direction;
	}

	private Direction isUp() {
		if ((Map.maps[startI - 1][startJ] instanceof Railroad || Map.maps[startI - 1][startJ] instanceof RailwayStation)
				&& Map.maps[startI - 1][startJ] != null) {
			return Direction.GORE;
		} else if ((Map.maps[startI][startJ - 1] instanceof Railroad
				|| Map.maps[startI][startJ - 1] instanceof RailwayStation) && Map.maps[startI][startJ - 1] != null) {
			return Direction.LIJEVO;
		} else if ((Map.maps[startI][startJ + 1] instanceof Railroad
				|| Map.maps[startI][startJ + 1] instanceof RailwayStation) && Map.maps[startI][startJ + 1] != null) {
			return Direction.DESNO;
		}
		return direction;
	}

	private Direction isRight() {
		if ((Map.maps[startI][startJ + 1] instanceof Railroad || Map.maps[startI][startJ + 1] instanceof RailwayStation)
				&& Map.maps[startI][startJ + 1] != null) {
			return Direction.DESNO;
		} else if ((Map.maps[startI - 1][startJ] instanceof Railroad
				|| Map.maps[startI - 1][startJ] instanceof RailwayStation) && Map.maps[startI - 1][startJ] != null) {
			return Direction.GORE;
		} else if ((Map.maps[startI + 1][startJ] instanceof Railroad
				|| Map.maps[startI + 1][startJ] instanceof RailwayStation) && Map.maps[startI + 1][startJ] != null) {
			return Direction.DOLE;
		}
		return direction;
	}

	private Direction isLeft() {
		if ((Map.maps[startI][startJ - 1] instanceof Railroad || Map.maps[startI][startJ - 1] instanceof RailwayStation)
				&& Map.maps[startI][startJ - 1] != null) {
			return Direction.LIJEVO;
		} else if ((Map.maps[startI + 1][startJ] instanceof Railroad
				|| Map.maps[startI + 1][startJ] instanceof RailwayStation) && Map.maps[startI + 1][startJ] != null) {
			return Direction.DOLE;
		} else if ((Map.maps[startI - 1][startJ] instanceof Railroad
				|| Map.maps[startI - 1][startJ] instanceof RailwayStation) && Map.maps[startI - 1][startJ] != null) {
			return Direction.GORE;
		}
		return direction;
	}

	private Direction isDown() {
		if ((Map.maps[startI + 1][startJ] instanceof Railroad || Map.maps[startI + 1][startJ] instanceof RailwayStation)
				&& Map.maps[startI + 1][startJ] != null) {
			return Direction.DOLE;
		} else if ((Map.maps[startI][startJ - 1] instanceof Railroad
				|| Map.maps[startI][startJ - 1] instanceof RailwayStation) && Map.maps[startI][startJ - 1] != null) {
			return Direction.LIJEVO;
		} else if ((Map.maps[startI][startJ + 1] instanceof Railroad
				|| Map.maps[startI][startJ + 1] instanceof RailwayStation) && Map.maps[startI][startJ + 1] != null) {
			return Direction.DESNO;
		}
		return direction;
	}

	@Override
	public String toString() {
		return "RailwayStation [velocity=" + velocity + ", direction=" + direction + ", startI=" + startI
				+ ", startJ=" + startJ + ", stationsList=" + stationsList + ", stationsList1=" + stationsList1 + "]";
	}

	public ArrayList<String> getStationsList() {
		return stationsList;
	}

	public void setStationsList(ArrayList<String> stationsList) {
		this.stationsList = stationsList;
	}

	public ArrayList<String> getStationsList1() {
		return stationsList1;
	}

	public void setStationsList1(ArrayList<String> stationsList1) {
		this.stationsList1 = stationsList1;
	}

	public Integer getStartI() {
		return startI;
	}

	public void setStartI(Integer startI) {
		this.startI = startI;
	}

	public Integer getStartJ() {
		return startJ;
	}

	public void setStartJ(Integer startJ) {
		this.startJ = startJ;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Locomotive[] getLocomotives() {
		return locomotives;
	}

	public void setLocomotives(Locomotive[] locomotives) {
		this.locomotives = locomotives;
	}

	public Wagon[] getWagons() {
		return wagons;
	}

	public void setWagons(Wagon[] wagons) {
		this.wagons = wagons;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}
	
}
