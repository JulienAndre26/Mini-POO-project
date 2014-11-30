package zuul.project;

import java.util.ArrayList;
import java.util.HashMap;

public class Player
{
	private int energyLvl;
	private String name;
	private boolean cheatSheet;
	private HashMap<Course, Boolean> learnedCourses;
	private ArrayList<String> inventory;
	
	public Player(String name)
	{
		this.inventory = new ArrayList<String>();
		this.learnedCourses = new HashMap<Course, Boolean>();
		this.energyLvl = 3;
		this.name = name;
		cheatSheet = false;
	}
	
	public ArrayList<String> getInventory()
	{
		return this.inventory;
	}
	
	public void addItem(String item)
	{
		this.inventory.add(item);
	}
	
	public void addCourse(String topic, String subject)
	{
		this.learnedCourses.put(new Course(topic, subject), false);
	}
	
	public void addLab(Course c)
	{
		this.learnedCourses.put(c, true);
	}
	
	public HashMap<Course, Boolean> getLearnedCourses()
	{
		return this.learnedCourses;
	}
	
	public Boolean hasCourse(String subject)
	{
		return this.learnedCourses.containsKey(subject);
	}
	
	public Boolean hasOopCourse(String topic)
	{
		return this.learnedCourses.containsValue(topic);
	}
}
