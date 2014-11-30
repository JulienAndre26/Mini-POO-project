package zuul.project;

import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game. Users can walk
 * around some scenery. That's all. It should really be extended to make it more
 * interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 * 
 * @author Michael KÃ¶lling and David J. Barnes
 * @version 2011.08.08
 */

public class Game {
	private Room tabRooms[] = new Room[6];
    private Parser parser;
    private Player player;
    private Room currentRoom;
    private Course oopCourses[] = new Course[4];

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
    	createPlayer("BinSab");
        createRooms();
        createCourses();
        parser = new Parser();
    }
    
    private void printLearnedCourses()
    {
    	Set listKeys = player.getLearnedCourses().keySet();  // Obtenir la liste des clés
		Iterator iterateur=listKeys.iterator();
		
		System.out.println("Here are all the courses you've learned :\n");
		// Parcourir les clés et afficher les entrées de chaque clé;
		while(iterateur.hasNext())
		{
			Course key= (Course) iterateur.next();
			System.out.println ("Course of " + key.toString());
			if (player.getLearnedCourses().get(key))
				System.out.print(" and you've learned the lab associated");
			else
				System.out.print(" and you didn't learned the lab associated");
		}
    }
    
    private void createCourses()
    {
    	this.oopCourses[0] = new Course("Basics", "OOP");
    	this.oopCourses[1] = new Course("Inheritance", "OOP");
    	this.oopCourses[2] = new Course("Designing classes", "OOP");
    	this.oopCourses[3] = new Course("Inheritance", "OOP");
    }

    private void createPlayer(String name)
    {
    	this.player = new Player(name);
    }
    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room classroom, lecture, lab, exam, library, lunchroom;

        // create the rooms
        classroom = new Room("in a classroom of ");
        lecture = new Room("in a lecture of ");
        lab = new Room("in a lab of ");
        exam = new Room("in an exam room of ");
        library = new Room("in the library");
        lunchroom = new Room("in the lunchroom");
        
        // initialise room exits
        classroom.setExit("east", lecture);
        classroom.setExit("south", lab);
        classroom.setExit("west", exam);

        lecture.setExit("west", classroom);
        lecture.setExit("south", library);

        lab.setExit("north", classroom);
        lab.setExit("east", library);

        exam.setExit("east", classroom);
        exam.setExit("south", lunchroom);

        library.setExit("west", lab);
        library.setExit("north", lecture);
        
        lunchroom.setExit("east", lab);
        lunchroom.setExit("north", exam);
        
        tabRooms[0] = classroom;
        tabRooms[1] = lecture;
        tabRooms[2] = lab;
        tabRooms[3] = exam;
        tabRooms[4] = library;
        tabRooms[5] = lunchroom;

        currentRoom = lunchroom; // start game in lunchroom
    }
    
    private void refreshRooms()
    {
    	String matieres[] = {"english", "OOP", "maths", "physic"};
    	Random rand = new Random();
    	int numMatiere;
    	
    	for (int i = 0; i < 4; i++)
    	{
    		numMatiere = rand.nextInt(4);
    		tabRooms[i].addSubject(matieres[numMatiere]);
    	}
    }
    
    

    /**
     * Main play routine. Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            refreshRooms();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out
                .println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command
     *            The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("inventory")) {
            inventory();
        }
        else if (commandWord.equals("courses")) {
            printLearnedCourses();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information. Here we print some stupid, cryptic
     * message and a list of the command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void inventory()
    {
    	player.addItem("tablet");
    	System.out.println("Here is the content of your inventory :\n");
    	for (int i = 0; i < player.getInventory().size(); i++)
    	{
    		System.out.println(player.getInventory().get(i));
    	}
    }
    /**
     * Try to in to one direction. If there is an exit, enter the new room,
     * otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            if (currentRoom.getShortDescription() == "in a classroom of ")
            	checkCourse(currentRoom);
            System.out.println(currentRoom.getLongDescription());
        }
    }
    
    public void checkCourse(Room r)
    {
    	Random rand = new Random();
    	int numCourse;
    	
    	numCourse = rand.nextInt(4);
    	if (r.getSubject() == "OOP")
    	{
    		if (!player.hasOopCourse(oopCourses[numCourse].getSubject()))
			{
    			player.addCourse(oopCourses[numCourse].getSubject(), r.getSubject());
			}
    	}
    	else
    	{
    		if (!player.hasCourse(r.getSubject()))
    		{
    			player.addCourse(r.getSubject(), r.getSubject());
    		}
    	}
    			 
    }

    /**
     * "Quit" was entered. Check the rest of the command to see whether we
     * really quit the game.
     * 
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true; // signal that we want to quit
        }
    }
}
