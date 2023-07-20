package vehicles;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

import enums.Direction;
import fileWatchers.FileWatcherVehicle;
import models.Map;
import models.RailRoadCrossing;
import models.Road;

public abstract class Vehicle extends Thread {

	protected String brand;
	protected String model;
	protected String gods;
	protected Integer velocity;
	protected Integer startI;
	protected Integer startJ;
	protected Color color;
	protected Direction direction;

	public Vehicle(String brand, String model, String gods, Integer velocity) {
		super();
		this.brand = brand;
		this.model = model;
		this.gods = gods;
		this.velocity = velocity;
	}

	@Override
	public void run() {
		if (Map.map[startI][startJ].getBackground().equals(Color.red)) {
			while (Map.map[startI][startJ].getBackground().equals(Color.red))
				;
		} else if (Map.map[startI][startJ].getBackground().equals(Color.yellow)) {
			while (Map.map[startI][startJ].getBackground().equals(Color.yellow))
				;
		}
		if (isTruck()) {
			Map.map[startI][startJ].setBackground(color);
		} else if (isCar()) {
			Map.map[startI][startJ].setBackground(color);
		}
		while (true) {
			try {
				Thread.sleep(velocity);
			} catch (InterruptedException ex) {
				Logger.getLogger(FileWatcherVehicle.class.getName()).log(Level.WARNING,
						ex.fillInStackTrace().toString());
			}
			Integer prevI = startI, prevJ = startJ;
			Direction directionOfMovement = determineVehicleDirection(direction, startI, startJ);
			if (directionOfMovement.equals(Direction.GORE)) {
				startI = startI - 1;
			} else if (directionOfMovement.equals(Direction.DOLE)) {
				startI = startI + 1;
			} else if (directionOfMovement.equals(Direction.LIJEVO)) {
				startJ = startJ - 1;
			} else if (directionOfMovement.equals(Direction.DESNO)) {
				startJ = startJ + 1;
			}
			direction = directionOfMovement;
			if (startI >= Map.NUM_OF_ROWS) {
				Map.map[prevI][prevJ].setBackground(Color.blue);
				break;
			} else if (startI < 0) {
				Map.map[prevI][prevJ].setBackground(Color.blue);
				break;
			} else if (startJ >= Map.NUM_OF_COLUMNS) {
				Map.map[prevI][prevJ].setBackground(Color.blue);
				break;
			} else if (startJ < 0) {
				Map.map[prevI][prevJ].setBackground(Color.blue);
				break;
			}
			if (Map.maps[startI][startJ] instanceof RailRoadCrossing
					&& ((RailRoadCrossing) Map.maps[startI][startJ]).isStripedCrossingBusy()) {
				while (((RailRoadCrossing) Map.maps[startI][startJ]).isStripedCrossingBusy())
					;
				if (((RailRoadCrossing) Map.maps[startI][startJ]).isStripedCrossingUnderVoltage()) {
					while (((RailRoadCrossing) Map.maps[startI][startJ]).isStripedCrossingUnderVoltage())
						;
				}
			}
			if (Map.map[startI][startJ].getBackground().equals(Color.red)) {
				while (Map.map[startI][startJ].getBackground().equals(Color.red))
					;
			} else if (Map.map[startI][startJ].getBackground().equals(Color.yellow)) {
				while (Map.map[startI][startJ].getBackground().equals(Color.yellow))
					;
			}
			if (this instanceof Truck) {
				Map.map[startI][startJ].setBackground(color);
			} else if (this instanceof Car) {
				Map.map[startI][startJ].setBackground(color);
			}
			if (Map.maps[prevI][prevJ] instanceof Road) {
				Map.map[prevI][prevJ].setBackground(Color.blue);
			} else if (Map.maps[prevI][prevJ] instanceof RailRoadCrossing) {
				Map.map[prevI][prevJ].setBackground(Color.black);
			}
		}
	}

	public Direction determineVehicleDirection(Direction direction, Integer startI, Integer startJ) {
		if (startI == 0) {
			return direction;
		} else if (startI == 29) {
			return direction;
		}
		if (startJ == 0) {
			return direction;
		} else if (startJ == 29) {
			return direction;
		}
		if (direction.equals(Direction.GORE)) {
			direction = isUp();
			return direction;
		} else if (direction.equals(Direction.DESNO)) {
			direction = isRight();
			return direction;
		} else if (direction.equals(Direction.DOLE)) {
			direction = isDown();
			return direction;
		} else if (direction.equals(Direction.LIJEVO)) {
			direction = isLeft();
			return direction;
		}
		return direction;
	}

	private Direction isUp() {
		if ((Map.maps[startI][startJ + 1] instanceof Road || Map.maps[startI][startJ + 1] instanceof RailRoadCrossing)
				&& Map.maps[startI][startJ + 1] != null) {
			return Direction.DESNO;
		} else if ((Map.maps[startI - 1][startJ] instanceof Road || Map.maps[startI - 1][startJ] instanceof RailRoadCrossing)
				&& Map.maps[startI - 1][startJ] != null) {
			return Direction.GORE;
		} else if ((Map.maps[startI][startJ - 1] instanceof Road || Map.maps[startI][startJ - 1] instanceof RailRoadCrossing)
				&& Map.maps[startI][startJ - 1] != null) {
			return Direction.LIJEVO;
		}
		return direction;
	}

	private Direction isRight() {
		if ((Map.maps[startI + 1][startJ] instanceof Road || Map.maps[startI + 1][startJ] instanceof RailRoadCrossing)
				&& Map.maps[startI + 1][startJ] != null) {
			return Direction.DOLE;
		} else if ((Map.maps[startI][startJ + 1] instanceof Road || Map.maps[startI][startJ + 1] instanceof RailRoadCrossing)
				&& Map.maps[startI][startJ + 1] != null) {
			return Direction.DESNO;
		} else if ((Map.maps[startI - 1][startJ] instanceof Road || Map.maps[startI - 1][startJ] instanceof RailRoadCrossing)
				&& Map.maps[startI - 1][startJ] != null) {
			return Direction.GORE;
		}
		return direction;
	}

	private Direction isDown() {
		if ((Map.maps[startI + 1][startJ] instanceof Road || Map.maps[startI + 1][startJ] instanceof RailRoadCrossing)
				&& Map.maps[startI + 1][startJ] != null) {
			return Direction.DOLE;
		} else if ((Map.maps[startI][startJ - 1] instanceof Road || Map.maps[startI][startJ - 1] instanceof RailRoadCrossing)
				&& Map.maps[startI][startJ - 1] != null) {
			return Direction.LIJEVO;
		} else if ((Map.maps[startI][startJ + 1] instanceof Road || Map.maps[startI][startJ + 1] instanceof RailRoadCrossing)
				&& Map.maps[startI][startJ + 1] != null) {
			return Direction.DESNO;
		}
		return direction;
	}

	private Direction isLeft() {
		if ((Map.maps[startI][startJ - 1] instanceof Road || Map.maps[startI][startJ - 1] instanceof RailRoadCrossing)
				&& Map.maps[startI][startJ - 1] != null) {
			return Direction.LIJEVO;
		} else if ((Map.maps[startI + 1][startJ] instanceof Road || Map.maps[startI + 1][startJ] instanceof RailRoadCrossing)
				&& Map.maps[startI + 1][startJ] != null) {
			return Direction.DOLE;
		} else if ((Map.maps[startI - 1][startJ] instanceof Road || Map.maps[startI - 1][startJ] instanceof RailRoadCrossing)
				&& Map.maps[startI - 1][startJ] != null) {
			return Direction.GORE;
		}
		return direction;
	}

	@Override
	public String toString() {
		return "Vehicle:" + brand + ".Vehicle model: " + model + ".Vehicle gods: " + gods;
	}

	protected abstract Boolean isCar();

	protected abstract Boolean isTruck();

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getGods() {
		return gods;
	}

	public void setGods(String gods) {
		this.gods = gods;
	}

	public Integer getVelocity() {
		return velocity;
	}

	public void setVelocity(Integer velocity) {
		this.velocity = velocity;
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
