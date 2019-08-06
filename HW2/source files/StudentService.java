/*this page calculates the mean and standard deviation so that we can decide if the user is a winner or not*/
package supportingClasses;
import java.util.ArrayList;

import managedBeans.Student;

public class StudentService 
{  
public static ArrayList<Student> student_list = new ArrayList<Student>();
public static void add(Student student)
{
student_list.add(student);
}

	public float calculateMean(String rafflestring)
	{
		float mean = 0;
		String[] array = rafflestring.split(",");
		int i = 0;
				do{
					mean = mean + Integer.parseInt(array[i].trim());
					i++;
			}while(i<array.length);
		return (mean/10);
	}
	public float calculateStandardDeviation(String rafflestring)
	{
		float standarddeviation = 0;
		String[] array = rafflestring.split(",");
		float mean = calculateMean(rafflestring);
		float[] difference = new float[10];
		int i=0;
		while(i<array.length)
		{
			difference[i] = mean - Integer.parseInt(array[i].trim());
			i++;
		}
		for(i=0; i<difference.length; i++){
			 difference[i]*=difference[i];
			 standarddeviation += difference[i];
		}
		return standarddeviation/10;
	}
}
