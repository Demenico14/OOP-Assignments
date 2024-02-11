package main;

import java.util.ArrayList;
import java.util.Scanner;

import ports.Port;
import ships.Ship;
import containers.*;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		ArrayList<Container> containers = new ArrayList<>();
		ArrayList<Ship> ships = new ArrayList<>();
		ArrayList<Port> ports = new ArrayList<>();

		while (true) {
			System.out.println("Menu:");
			System.out.println("1. Create a port");
			System.out.println("2. Create a ship");
			System.out.println("3. Create a container");
			System.out.println("4. Load a container to a ship");
			System.out.println("5. Unload a container from a ship");
			System.out.println("6. Sail a ship to another port");
			System.out.println("7. Refuel a ship");
			System.out.println("0. Exit");

			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();

			switch (choice) {
				case 1: {
					System.out.print("Enter the X coordinate of the port: ");
					double x = scanner.nextDouble();
					System.out.print("Enter the Y coordinate of the port: ");
					double y = scanner.nextDouble();

					int portID = ports.size() ; // Determine the port ID based on the current number of ports

					ports.add(new Port(portID, x, y));
					System.out.println("Port created successfully. Port ID: " + portID);

					break;
				}
//				case 1: {
//					System.out.print("Enter the port ID where the container should be placed initially: ");
//					int portID = scanner.nextInt();
//					System.out.print("Enter the weight of the container: ");
//					int weight = scanner.nextInt();
//					System.out.print("Enter the type of container (B for Basic, L for Liquid, R for Refrigerated): ");
//					String containerType = scanner.next();
//
//					Container container;
//					if (weight > 5000) {
//						container = new HeavyContainer(containers.size(), weight);
//					} else {
//						container = new BasicContainer(containers.size(), weight);
//					}
//
//					if (containerType.equalsIgnoreCase("L")) {
//						container = new LiquidContainer(containers.size(), weight);
//					} else if (containerType.equalsIgnoreCase("R")) {
//						container = new RefrigeratedContainer(containers.size(), weight);
//					} else if (!containerType.equalsIgnoreCase("B")) {
//						System.out.println("Invalid container type.");
//						continue;
//					}
//
//					if (!ports.isEmpty() && portID > 0 && portID <= ports.size()) {
//						ports.get(portID).getContainers().add(container);
//						containers.add(container);
//						System.out.println("Container added successfully.");
//					} else {
//						System.out.println("Invalid port ID.");
//						continue; // Continue to the next iteration of the loop
//					}
//
//					break;
//				}
				case 2: {
					System.out.print("Enter the port ID where the ship is located: ");
					int portID = scanner.nextInt();
					System.out.print("Enter the total weight capacity of the ship: ");
					int totalWeightCapacity = scanner.nextInt();
					System.out.print("Enter the maximum number of all containers the ship can carry: ");
					int maxNumberOfAllContainers = scanner.nextInt();
					System.out.print("Enter the maximum number of heavy containers the ship can carry: ");
					int maxNumberOfHeavyContainers = scanner.nextInt();
					System.out.print("Enter the maximum number of refrigerated containers the ship can carry: ");
					int maxNumberOfRefrigeratedContainers = scanner.nextInt();
					System.out.print("Enter the maximum number of liquid containers the ship can carry: ");
					int maxNumberOfLiquidContainers = scanner.nextInt();
					System.out.print("Enter the fuel consumption per kilometer of the ship: ");
					double fuelConsumptionPerKM = scanner.nextDouble();

					ships.add(new Ship(ships.size(), ports.get(portID), totalWeightCapacity,
							maxNumberOfAllContainers, maxNumberOfHeavyContainers, maxNumberOfRefrigeratedContainers,
							maxNumberOfLiquidContainers, fuelConsumptionPerKM));
					break;
				}
				case 3: {
					System.out.print("Enter the port ID where the container should be placed initially: ");
					int portID = scanner.nextInt();

					System.out.print("Enter the weight of the container: ");
					int weight = scanner.nextInt();
					System.out.print("Enter the type of container (B for Basic, L for Liquid, R for Refrigerated): ");
					String containerType = scanner.next();

					Container container;
					if (weight > 5000) {
						container = new HeavyContainer(containers.size(), weight);
					} else {
						container = new BasicContainer(containers.size(), weight);
					}

					if (containerType.equalsIgnoreCase("L")) {
						container = new LiquidContainer(containers.size() , weight);
					} else if (containerType.equalsIgnoreCase("R")) {
						container = new RefrigeratedContainer(containers.size() , weight);
					} else if (!containerType.equalsIgnoreCase("B")) {
						System.out.println("Invalid container type.");
						continue;
					}
					if (!ports.isEmpty() && portID >= 0 && portID <= ports.size()) { // Updated bounds check
						ports.get(portID).getContainers().add(container);
						containers.add(container);
						System.out.println("Container added successfully.");
					} else {
						System.out.println("Invalid port ID.");
						continue; // Continue to the next iteration of the loop
					}

					break;
				}

				case 4: {
					System.out.print("Enter the ship ID: ");
					int shipID = scanner.nextInt();
					System.out.print("Enter the container ID: ");
					int containerID = scanner.nextInt();

					if(ships.get(shipID ).load(containers.get(containerID))){
						System.out.println("loaded container ");
					}
					else {
						System.out.println("FAILED");
					}
					break;
				}
				case 5: {
					System.out.print("Enter the ship ID: ");
					int shipID = scanner.nextInt();
					System.out.print("Enter the container ID: ");
					int containerID = scanner.nextInt();

					if(ships.get(shipID).unLoad(containers.get(containerID))){
						System.out.println("Unloaded container ");
					}
					else {
						System.out.println("FAILED");
					}

					break;
				}
				case 6: {
					System.out.print("Enter the ship ID: ");
					int shipID = scanner.nextInt();
					System.out.print("Enter the destination port ID: ");
					int portID = scanner.nextInt();

					// Assuming the Ship and Port objects are properly initialized and accessible

					// Get the coordinates of the destination port
					System.out.print("Enter the X coordinate of the destination port: ");
					double destinationX = scanner.nextDouble();
					System.out.print("Enter the Y coordinate of the destination port: ");
					double destinationY = scanner.nextDouble();

					// Create a new Port object for the destination port using the provided coordinates
					Port destinationPort = new Port(portID, destinationX, destinationY);

					Ship ship = ships.get(shipID);
					double fuelConsumption = ship.sailTo(destinationPort);
					double distance = ports.get(portID).getDistance(destinationPort);

					if (fuelConsumption == -1) {
						System.out.println("Ship cannot sail to the destination port due to insufficient fuel.");
					} else {
						System.out.println("Fuel Consumption: " + (int)fuelConsumption);
						System.out.println("Distance: " + (int)distance);
					}

					break;
				}
				case 7: {
					System.out.print("Enter the ship ID: ");
					int shipID = scanner.nextInt();
					System.out.print("Enter the amount of fuel to refuel: ");
					double fuel = scanner.nextDouble();

					Ship ship = ships.get(shipID);
					double totalFuel = ship.reFuel(fuel);

					System.out.println("Ship " + shipID + " has been refueled. Total fuel amount: " + totalFuel);

					break;
				}
				case 0: {
					System.out.println("Existing the Program");
					return;
				}
			}
		}
	}
}