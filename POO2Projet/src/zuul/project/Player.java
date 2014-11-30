package zuul.project;

import java.util.ArrayList;
import java.util.HashMap;

public class Player
{
	private int energyLvl;
	private String name;
	private boolean cheatSheet;
	private HashMap<String, String> learnedCourses;
	private ArrayList<String> inventory;
	
	public Player(String name)
	{
		this.inventory.clear();
		this.energyLvl = 3;
		this.name = name;
		cheatSheet = false;
	}
	
	public ArrayList<String> getInventory()
	{
		return this.inventory;
	}
	
	//public void addCourse()
}
