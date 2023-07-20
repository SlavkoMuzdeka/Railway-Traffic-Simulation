package compositions;

import java.io.Serializable;
import java.util.ArrayList;

public class MovementHistory implements Serializable {

	private static final long serialVersionUID = 8472815627341079760L;

	private ArrayList<String> stations;
	private ArrayList<Coordinate> coordinates;
	private Long time;

	public MovementHistory() {
		super();
		stations = new ArrayList<>();
		coordinates = new ArrayList<>();
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public void setStations(ArrayList<String> stations) {
		this.stations = stations;
	}

	public ArrayList<String> getStations() {
		return stations;
	}

	public void setCoordinates(ArrayList<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}

	public ArrayList<Coordinate> getCoordinates() {
		return coordinates;
	}

	public void addCoordinate(Coordinate coordinate) {
		coordinates.add(coordinate);
	}

	public void addStation(String station) {
		stations.add(station);
	}

	@Override
	public String toString() {
		return "The names of the stations where the train composition made stops: " + stations + "\nThe coordinates of the train's movement are: "
				+ coordinates + "" + "\nThe time of movement of the train composition: " + time + "[ms]";
	}

}
