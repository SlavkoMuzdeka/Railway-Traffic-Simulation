# Railway Traffic Simulation

## Project Motivation

The primary motivation behind the "Railway Traffic Simulation" project is to explore and demonstrate concurrent programming concepts using threads in Java to simulate the dynamic movement of trains and vehicles on a railway and road network. By implementing a multi-threaded simulation, the project aims to showcase the power and challenges of concurrency in managing complex transportation systems.

Using threads in Java, the project will enable the simultaneous movement of multiple trains and vehicles on a 2D map representing railway stations, tracks, roads, and level crossings. The simulation will model the interactions between trains and vehicles, handle collisions, and ensure smooth and safe transportation operations.

The project focuses on the following key aspects:

- `Concurrent Train and Vehicle Movement`: The project will implement separate threads for each train and vehicle, allowing them to move independently on the railway and road network. Concurrent programming will be used to manage their movement simultaneously, reflecting real-time transportation scenarios.

- `Collision Detection`: The simulation will employ concurrent techniques to detect and handle collisions between trains and vehicles, ensuring safety and preventing accidents at level crossings and congested areas.

- `Thread Synchronization`: To avoid race conditions and data conflicts, thread synchronization will be implemented to coordinate the movement of trains and vehicles on shared tracks and roads.

- `Realistic Train and Vehicle Behaviors`: Trains will consist of locomotives and wagons, each with specific attributes such as type, power, and length. Vehicles will have characteristics like brand, model, and speed. The project aims to provide a realistic simulation of train and vehicle behaviors based on their attributes and type of locomotion (steam, diesel, electric).

- `Dynamic Configuration`: The simulation will read configuration files to define various parameters, such as maximum speeds on different tracks, the number of vehicles on each road, and paths to store log files.

## Getting started

### Key Dependencies & Platforms

- [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html): Make sure you have Java 11 installed on your machine. You can download and install it from the official Oracle website or use a Java Development Kit (JDK) distribution suitable for your operating system.

- [Eclipse IDE](https://www.eclipse.org/ide/): I recommend using Eclipse IDE for Java development. Make sure you have Eclipse IDE installed on your machine. You can download it from the Eclipse website and follow the installation instructions.

## Key features

- `Concurrent Train and Vehicle Movement`: The simulation will demonstrate concurrent movement of trains and vehicles on the map, showing real-time interactions between different elements of the transportation system.

- `Collision Detection and Avoidance`: Thread synchronization will handle collision detection at level crossings and congested areas, ensuring safe and efficient transportation operations.

- `Realistic Train and Vehicle Attributes`: Trains and vehicles will have realistic attributes and locomotion types, influencing their speed and behavior on the network.

- `Dynamic Configuration`: Configuration files will enable dynamic adjustments to the simulation, allowing changes in vehicle numbers, maximum speeds, and log file storage paths during runtime.

- `Visualization of Train and Vehicle Movement`: The graphical user interface (GUI) will visually represent train and vehicle movement on the map, displaying their positions, speeds, and path histories.

The "Railway Traffic Simulation" project aims to showcase the significance of concurrent programming in managing complex transportation systems. By implementing real-time interactions and collision handling, the simulation will provide a comprehensive understanding of thread-based parallelism in transportation management.