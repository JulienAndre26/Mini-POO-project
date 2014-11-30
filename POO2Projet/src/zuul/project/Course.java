package zuul.project;

public class Course
{
	private String topic; // nom du cours
	private String subject; // matière
	
	public Course(String topic, String subject)
	{
		this.topic = topic;
		this.subject = subject;
	}
	
	public String getSubject()
	{
		return this.subject;
	}
	
	public String toString()
	{
		String result = topic;

		if (topic != subject)
			result += " and the subject is " + subject;
		
		return result;
	}
}
