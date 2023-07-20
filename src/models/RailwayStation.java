package models;

import java.util.ArrayList;

import compositions.RailwayComposition;
import enums.Direction;

public class RailwayStation extends Thread {

	private String stationName;
	private ArrayList<RailwayComposition> compositionsOnStation;
	private RailwayComposition k;
	private Section section1, section2, section3, section4;

	public RailwayStation() {
		super();
		compositionsOnStation = new ArrayList<>();
	}

	public RailwayStation(String stationName) {
		super();
		this.stationName = stationName;
		compositionsOnStation = new ArrayList<>();
	}

	public void addOnStation(RailwayComposition composition) {
		compositionsOnStation.add(composition);
		k = composition;
		determineCoordinates(k);
	}

	public void determineCoordinates(RailwayComposition composition) {
		ArrayList<String> stations = composition.getStationsList();
		RailwayComposition composition1 = composition;
		if (stations.size() == 2) {
			String station1 = stations.get(0);
			String station2 = stations.get(1);
			twoStationCoordinates(station1, station2, composition1);
		} else if (stations.size() == 3) {
			String station1 = stations.get(0);
			threeStationCoordinate(station1, stations, composition1);
		} else if (stations.size() == 4) {
			String station1 = stations.get(0);
			fourStationCoordinates(station1, stations, composition1);
		}
	}

	public void fourStationCoordinates(String station, ArrayList<String> stations,
			RailwayComposition composition) {
		RailwayComposition composition1 = composition;
		if ("A".equals(station)) {
			setCoordinates(27, 2, Direction.GORE, composition1);
		} else if ("E".equals(station)) {
			setCoordinates(25, 26, Direction.GORE, composition1);
		} else if ("D".equals(station)) {
			setCoordinates(1, 26, Direction.LIJEVO, composition1);
		}
	}

	public void threeStationCoordinate(String station, ArrayList<String> stations, RailwayComposition composition) {
		RailwayComposition composition1 = composition;
		if ("A".equals(station)) {
			setCoordinates(27, 2, Direction.GORE, composition1);
		} else if ("B".equals(station)) {
			setCoordinates(6, 7, Direction.DESNO, composition1);
		} else if ("C".equals(station)) {
			setCoordinates(12, 19, Direction.GORE, composition1);
		} else if ("D".equals(station)) {
			setCoordinates(1, 26, Direction.LIJEVO, composition1);
		} else if ("E".equals(station)) {
			setCoordinates(25, 26, Direction.GORE, composition1);
		}
	}

	public void twoStationCoordinates(String station1, String station2, RailwayComposition composition) {
		RailwayComposition composition1 = composition;
		if ("A".equals(station1) && "B".equals(station2)) {
			setCoordinates(27, 2, Direction.GORE, composition1);
		} else if ("B".equals(station1) && "A".equals(station2)) {
			setCoordinates(6, 6, Direction.LIJEVO, composition1);
		} else if ("B".equals(station1) && "C".equals(station2)) {
			setCoordinates(6, 7, Direction.DESNO, composition1);
		} else if ("C".equals(station1) && "B".equals(station2)) {
			setCoordinates(12, 19, Direction.GORE, composition1);
		} else if ("C".equals(station1) && "D".equals(station2)) {
			setCoordinates(12, 20, Direction.DESNO, composition1);
		} else if ("C".equals(station1) && "E".equals(station2)) {
			setCoordinates(13, 20, Direction.DOLE, composition1);
		} else if ("D".equals(station1) && "C".equals(station2)) {
			setCoordinates(1, 26, Direction.LIJEVO, composition1);
		} else if ("E".equals(station1) && "C".equals(station2)) {
			setCoordinates(25, 26, Direction.GORE, composition1);
		}
	}

	public void setCoordinates(Integer i, Integer j, Direction direction, RailwayComposition composition) {
		composition.setStartI(i);
		composition.setStartJ(j);
		composition.setDirection(direction);
		for (Integer k = 0; k < composition.getLocomotives().length; k++) {
			composition.getLocomotives()[k].setStartI(i);
			composition.getLocomotives()[k].setStartJ(j);
		}
		for (Integer k = 0; k < composition.getWagons().length; k++) {
			composition.getWagons()[k].setStartI(i);
			composition.getWagons()[k].setStartJ(j);
		}
	}

	@Override
	public void run() {
		while (true) {
			if (this.compositionsOnStation.size() != 0) {
				RailwayComposition composition = this.compositionsOnStation.get(0);
				String station = this.stationName;
				Direction direction = composition.getDirection();
				Section section = getSection(station, direction);
				if (section != null) {
					RailRoadCrossing prelaz = getRailRoadCrossing(section);
					if (section.getSectionDirection() == -1) {
						if (section.getCompositionSecondDirection() == 0) {
							composition.setSection(section);
							Integer temp = section.getCompositionFirstDirection();
							temp++;
							section.setCompositionFirstDirection(temp);
							if (prelaz != null) {
								prelaz.setStripedCrossingBusy(true);
							}
							synchronized (composition) {
								composition.notify();
								this.compositionsOnStation.remove(0);
							}
						}
					} else if (section.getSectionDirection() == 1) {
						if (section.getCompositionFirstDirection() == 0) {
							composition.setSection(section);
							Integer temp = section.getCompositionSecondDirection();
							temp++;
							section.setCompositionSecondDirection(temp);
							if (prelaz != null) {
								prelaz.setStripedCrossingBusy(true);
							}
							synchronized (composition) {
								composition.notify();
								this.compositionsOnStation.remove(0);
							}
						}
					}
				}
			}
		}
	}

	public RailRoadCrossing getRailRoadCrossing(Section section) {
		if (section.getNumbOfSection() == 1) {
			return Map.crossing1;
		} else if (section.getNumbOfSection() == 2) {
			return Map.crossing2;
		} else if (section.getNumbOfSection() == 3) {
			return Map.crossing3;
		} else {
			return null;
		}
	}

	public Section getSection(String station, Direction direction) {
		if ("A".equals(station) && direction == Direction.GORE && section1.getCompositionSecondDirection() == 0) {
			section1.setSectionDirection(-1);
			return section1;
		} else if ("B".equals(station)) {
			if (direction == Direction.LIJEVO && section1.getCompositionFirstDirection() == 0) {
				section1.setSectionDirection(1);
				return section1;
			} else if (direction == Direction.DESNO && section2.getCompositionSecondDirection() == 0) {
				section2.setSectionDirection(-1);
				return section2;
			}
		} else if ("C".equals(station)) {
			if (direction == Direction.GORE && section2.getCompositionFirstDirection() == 0) {
				section2.setSectionDirection(1);
				return section2;
			} else if (direction == Direction.DESNO && section4.getCompositionSecondDirection() == 0) {
				section4.setSectionDirection(-1);
				return section4;
			} else if (direction == Direction.DOLE && section3.getCompositionSecondDirection() == 0) {
				section3.setSectionDirection(-1);
				return section3;
			}
		} else if ("D".equals(station) && direction == Direction.LIJEVO && section4.getCompositionFirstDirection() == 0) {
			section4.setSectionDirection(1);
			return section4;
		} else if ("E".equals(station) && direction == Direction.GORE && section3.getCompositionFirstDirection() == 0) {
			section3.setSectionDirection(1);
			return section3;
		}
		return null;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public ArrayList<RailwayComposition> getCompositionsOnStation() {
		return compositionsOnStation;
	}

	public void setCompositionsOnStation(ArrayList<RailwayComposition> compositionsOnStation) {
		this.compositionsOnStation = compositionsOnStation;
	}

	public RailwayComposition getK() {
		return k;
	}

	public void setK(RailwayComposition k) {
		this.k = k;
	}

	public Section getSection1() {
		return section1;
	}

	public void setSection1(Section section1) {
		this.section1 = section1;
	}

	public Section getSection2() {
		return section2;
	}

	public void setSection2(Section section2) {
		this.section2 = section2;
	}

	public Section getSection3() {
		return section3;
	}

	public void setSection3(Section section3) {
		this.section3 = section3;
	}

	public Section getSection4() {
		return section4;
	}

	public void setSection4(Section section4) {
		this.section4 = section4;
	}

	@Override
	public String toString() {
		return "Station name: " + stationName;
	}

}
