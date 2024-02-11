
package ports;

import interfaces.IPort;
import ships.Ship;
import containers.Container;
import java.util.ArrayList;
import java.util.Collections;


public class Port implements IPort {
	

	private int ID;

	private double X;

	private double Y;

	private ArrayList<Container> containers;

	private ArrayList<Ship> history;

	private ArrayList<Ship> current;
	
	
	

	public Port(int ID, double X, double Y) {
		this.ID = ID;
		this.X = X;
		this.Y = Y;
		containers = new ArrayList<Container>();
		history = new ArrayList<Ship>();
		current = new ArrayList<Ship>();
	}

	public double getX() {
		return X;
	}

	public double getY() {
		return Y;
	}

	public double getDistance(Port otherPort) {
		double distanceX = Math.abs(this.X - otherPort.getX());
		double distanceY = Math.abs(this.Y - otherPort.getY());
		return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
	}
	

	@Override
	public void incomingShip(Ship s) {
		if(!current.contains(s))
			current.add(s);
	}

	@Override
	public void outgoingShip(Ship s) {
		if(!history.contains(s))
			history.add(s);
	}
	
	
	

	public ArrayList<Container> getContainers() {
		return containers;
	}
	

	public ArrayList<Ship> getCurrent() {
		return current;
	}

	@Override
	public String toString() {
		String str = String.format("Port %d: (%.2f, %.2f)\n", ID, X, Y);
		ArrayList<ArrayList<Container>> sorted = Container.getContainersSortedByType(containers);
		for(ArrayList<Container> conts : sorted)
			if(!conts.isEmpty()) {
				str += "  " + conts.get(0).getClass().getSimpleName() + ":";
				for(Container cont : conts)
					str += " " + cont.getID();
				str += "\n";
			}
		
		Collections.sort(current);
		for(Ship ship : current)
			str += ship.toString();
			
		return str;
	}
	
}

