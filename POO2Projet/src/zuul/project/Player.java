package zuul.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

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
	
	public Boolean addCourse(String topic, String subject)
	{
		if (this.energyLvl > 0)
		{
			this.learnedCourses.put(new Course(topic, subject), false);
			this.energyLvl--;
			return true;
		}
		return false;
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
	
	public Boolean forgetCourse()
	{
		Random rand = new Random();
		Set listKeys = this.learnedCourses.keySet();
		Iterator iterateur=listKeys.iterator();
		
		Course key = (Course) iterateur.next();
		if (key != null)
		{
			return this.learnedCourses.remove(key);
		}
		
		return false;
	}
	
	public int drinkCoffee()
	{
		this.energyLvl++;
		
		return this.energyLvl;
	}
}
