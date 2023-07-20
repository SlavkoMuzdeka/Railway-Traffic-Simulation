package models;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import compositions.MovementHistory;
import compositions.RailwayComposition;
import fileWatchers.FileWatcherComposition;
import fileWatchers.FileWatcherVehicle;
import main.Main;

public class Map implements ActionListener {

	private RailwayStation[] stations;
	private JFrame frame, frame1;
	private JButton btn1, btn2;
	private JPanel panel1, panel2, panel3;
	private JTextArea[] area;
	private JPanel panel;
	private JScrollPane pane;
	public static JPanel[][] map;
	public static Object[][] maps;

	public static RailRoadCrossing crossing1, crossing2, crossing3;
	public static Section section1, section2, section3, section4;

	public static final Integer NUM_OF_ROWS = 30;
	public static final Integer NUM_OF_COLUMNS = 30;
	public Handler handler;

	{
		try {
			handler = new FileHandler("Main.log");
			Logger.getLogger(Map.class.getName()).setUseParentHandlers(false);
			Logger.getLogger(Map.class.getName()).addHandler(handler);
		} catch (IOException ex) {
			Logger.getLogger(Map.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}

	public Map() {
		stations = new RailwayStation[5];
		map = new JPanel[NUM_OF_ROWS][NUM_OF_COLUMNS];
		maps = new Object[30][30];

		frame = new JFrame("Railway Traffic Simulation");
		frame.setSize(800, 800);
		frame.setLocation(400, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel2 = new JPanel();
		panel2.setLayout(new GridLayout(30, 30, 1, 1));
		panel3 = new JPanel();

		btn1 = new JButton("Start simulation");
		btn1.setFocusable(false);
		btn1.addActionListener(this);
		panel3.add(btn1);

		btn2 = new JButton("Show all movements and details");
		btn2.setFocusable(false);
		btn2.addActionListener(this);
		panel3.add(btn2);

		for (Integer i = 0; i < NUM_OF_ROWS; i++) {
			for (Integer j = 0; j < NUM_OF_COLUMNS; j++) {
				map[i][j] = new JPanel();
				panel2.add(map[i][j]);
			}
		}

		makeTable();

		panel1.add(panel2, BorderLayout.CENTER);
		panel1.add(panel3, BorderLayout.SOUTH);

		frame.add(panel1);
		frame.setVisible(true);
	}

	private void makeTable() {
		Road put = new Road();
		crossing1 = new RailRoadCrossing();
		crossing2 = new RailRoadCrossing();
		crossing3 = new RailRoadCrossing();

		for (Integer i = 0; i < stations.length; i++) {
			stations[i] = new RailwayStation();
		}
		stations[0].setStationName("A");
		stations[1].setStationName("B");
		stations[2].setStationName("C");
		stations[3].setStationName("D");
		stations[4].setStationName("E");

		section1 = new Section(1, crossing1);
		section2 = new Section(2, crossing2);
		section3 = new Section(3, crossing3);
		section4 = new Section(4, null);

		stations[0].setSection1(section1);
		stations[1].setSection1(section1);
		stations[1].setSection2(section2);
		stations[2].setSection2(section2);
		stations[2].setSection3(section3);
		stations[2].setSection4(section4);
		stations[3].setSection4(section4);
		stations[4].setSection3(section3);

		setPath(put, 20, 21, 0, 1);
		setPath(put, 20, 29, 7, 8);
		setPath(put, 20, 21, 3, 6);
		setPath(put, 20, 29, 21, 22);
		setPath(put, 20, 21, 23, 25);
		setPath(put, 20, 21, 27, 29);
		setPath(put, 7, 29, 13, 14);
		setPath(put, 0, 5, 13, 14);

		setCrossing(crossing1, 20, 21, 2, 2);
		setCrossing(crossing2, 6, 6, 13, 14);
		setCrossing(crossing3, 20, 21, 26, 26);

		setStation(stations[0], 27, 28, 1, 2);
		setStation(stations[1], 5, 6, 6, 7);
		setStation(stations[2], 12, 13, 19, 20);
		setStation(stations[3], 1, 2, 26, 27);
		setStation(stations[4], 25, 26, 25, 26);

		setRailroad(29, 29, 2, 2);
		setRailroad(22, 26, 2, 2);
		setRailroad(16, 19, 2, 2);
		setRailroad(16, 16, 3, 5);
		setRailroad(6, 15, 5, 5);
		setRailroad(6, 6, 8, 12);
		setRailroad(6, 6, 15, 19);
		setRailroad(7, 11, 19, 19);
		setRailroad(14, 18, 20, 20);
		setRailroad(18, 18, 21, 26);
		setRailroad(19, 19, 26, 26);
		setRailroad(22, 24, 26, 26);
		setRailroad(25, 25, 27, 29);
		setRailroad(12, 12, 21, 26);
		setRailroad(9, 11, 26, 26);
		setRailroad(9, 9, 27, 28);
		setRailroad(5, 8, 28, 28);
		setRailroad(5, 5, 23, 27);
		setRailroad(3, 4, 23, 23);
		setRailroad(1, 3, 22, 22);
		setRailroad(1, 1, 23, 25);

		for (Integer i = 0; i < NUM_OF_ROWS; i++) {
			for (Integer j = 0; j < NUM_OF_COLUMNS; j++) {
				if (map[i][j] == null) {
					map[i][j].setBackground(Color.WHITE);
				}
			}
		}
	}

	private void setPath(Road put, Integer i, Integer j, Integer k, Integer l) {
		for (Integer pom = i; pom <= j; pom++) {
			for (Integer pom1 = k; pom1 <= l; pom1++) {
				maps[pom][pom1] = put;
				map[pom][pom1].setBackground(Color.blue);
			}
		}
	}

	private void setCrossing(RailRoadCrossing crossing, Integer i, Integer j, Integer k, Integer l) {
		for (Integer pom = i; pom <= j; pom++) {
			for (Integer pom1 = k; pom1 <= l; pom1++) {
				maps[pom][pom1] = crossing;
				map[pom][pom1].setBackground(Color.black);
			}
		}
	}

	private void setStation(RailwayStation station, Integer i, Integer j, Integer k, Integer l) {
		for (Integer pom = i; pom <= j; pom++) {
			for (Integer pom1 = k; pom1 <= l; pom1++) {
				maps[pom][pom1] = station;
				map[pom][pom1].setBackground(Color.GRAY);
			}
		}
	}

	private void setRailroad(Integer i, Integer j, Integer k, Integer l) {
		for (Integer pom = i; pom <= j; pom++) {
			for (Integer pom1 = k; pom1 <= l; pom1++) {
				maps[pom][pom1] = new Railroad();
				map[pom][pom1].setBackground(Color.GRAY);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn1) {
			FileWatcherComposition fwk = new FileWatcherComposition(this);
			FileWatcherVehicle fwv = new FileWatcherVehicle();
			fwk.start();
			fwv.start();
			btn1.setEnabled(false);
			for (Integer i = 0; i < stations.length; i++) {
				new Thread(stations[i]).start();
			}
		} else if (e.getSource() == btn2) {
			Integer num = RailwayComposition.numberOfRailwayCompoistions;
			frame1 = new JFrame("The history of the movement of compositions");
			area = new JTextArea[num];
			panel = new JPanel();
			panel.setLayout(new GridLayout(1, num, 3, 3));
			frame1.setSize(600, 600);
			frame1.setLocation(500, 50);
			frame1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			try {
				Integer b;
				for (Integer i = 0; i < num; i++) {
					b = i + 1;
					FileInputStream fis = new FileInputStream(Main.path + b + "_Composition.ser");
					ObjectInputStream ois = new ObjectInputStream(fis);
					MovementHistory movementHistory = (MovementHistory) ois.readObject();
					ois.close();
					area[i] = new JTextArea();
					area[i].setText(movementHistory.toString());
					panel.add(area[i]);
				}
			} catch (Exception ex) {
				Logger.getLogger(Map.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			}
			pane = new JScrollPane(panel);
			frame1.add(pane);
			frame1.setVisible(true);
		}
	}

	public RailwayStation[] getStations() {
		return stations;
	}

	public void setStations(RailwayStation[] stations) {
		this.stations = stations;
	}

}
