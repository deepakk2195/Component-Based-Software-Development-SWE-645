/*survey page input is mapped here. the setters and getters are defined here. the fields are also validated here*/
package managedBeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import supportingClasses.StudentService;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import static supportingClasses.StudentService.student_list;


@ManagedBean(name="studentBean", eager=true)

public class Student {
	
private String firstname, lastname, city, state, email, interestedin, raffle, additionalComments, recommendation, streetaddress, phonenumber,zipcode; 
private List<String> campusOptions;
private Date date, postartdate;
private StudentService student;
private WinningResult winner;
public static final String likes="likely,very likely,unlikely";
//public static final String[] likesa=likes.split(",");
public static final String[] likesa = {"likely", "very likely", "unlikely"};

public Student()
{
    student = new StudentService();
	winner = new WinningResult();
}


public void setFirstname(String firstname) 
{
	this.firstname = firstname;
}
public void setLastname(String lastname) 
{
	this.lastname = lastname;
}
public void setCity(String city) 
{
	this.city = city;
}
public void setState(String state) 
{
	this.state = state;
}
public void setEmail(String email) 
{
	this.email = email;
}
public void setPhonenumber(String phonenumber) 
{
	this.phonenumber = phonenumber;
}
public void setDate(Date date) 
{
	this.date = date;
}
public void setZipcode(String zipcode) 
{
	this.zipcode = zipcode;
	restFunc();
}
public void setCampusOptions(List<String> campusOptions) 
{
	this.campusOptions = campusOptions;
}
public void setInterestedin(String interestedin) 
{
	this.interestedin = interestedin;
}
public void setRecommendation(String recommendation) {
	this.recommendation = recommendation;
}
public void setRaffle(String raffle) 
{
	this.raffle = raffle;
}
public void setAdditionalComments(String additionalComments) 
{
	this.additionalComments = additionalComments;
}

public String getStreetaddress() 
{
	return streetaddress;
}
public void setStreetaddress(String streetaddress) 
{
	this.streetaddress = streetaddress;
}
public Date getPostartdate() 
{
	return postartdate;
}
public void setPostartdate(Date postartdate) 
{
	this.postartdate = postartdate; 
}
public String getFirstname() 
{
	return firstname;
}
public String getLastname()
{
	return lastname;
}
public String getCity() 
{
	return city;
}
public String getState() 
{
	return state;
}
public String getEmail() 
{
	return email;
}
public String getPhonenumber() {
	return phonenumber;
}
public String getZipcode() 
{
	return zipcode;
}
public Date getDate() 
{
	return date;
}
public List<String> getCampusOptions() 
{
	return campusOptions;
}
public String getInterestedin() 
{
	return interestedin;
}
public String getRecommendation() 
{
	return recommendation;
}
public String getRaffle() 
{
	return raffle;
}
public String getAdditionalComments() 
{
	return additionalComments;
}
public float getMean()
{
	return winner.getMean();
}
public float getStandarddeviation()
{
	return winner.getStandarddeviation();
}

public void validateDate(FacesContext context,UIComponent componenttoValidate, Object value)
{	
	String validateString = (String)value; 
	context = FacesContext.getCurrentInstance();
	FacesMessage message = new FacesMessage("*Date must be in the form MM/DD/YYYY");
	Pattern pattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
    Matcher matcher = pattern.matcher(validateString);
	String validate_state = null;
    if(!(matcher.matches())){
		throw new ValidatorException(message);
	}else{
			validate_state = validatedatefull(validateString);
			if(validate_state.equals("1")){
			}else if(validate_state.equals("0")){
				throw new ValidatorException(new FacesMessage("*Enter a valid date"));
			}
	}
}
public void validateRaffle(FacesContext context, UIComponent componenttobeValidated,Object value)
{	
	String validateString = (String)value; 
	context = FacesContext.getCurrentInstance();
	FacesMessage message = new FacesMessage("*Invalid raffle ticket number, Enter 10 comma separated values between 1 and 100");

	if(!(validateRaffleno(validateString)))
		throw new ValidatorException(message);	
}

public String submit()
{	
	if(postartdate.before(date))
	{
		   System.out.println("Invalid Message for Start Date");
			FacesContext context = FacesContext.getCurrentInstance(); 
			FacesMessage message = new FacesMessage("Semester start date cannot be before survey date");
			context.addMessage("Survey:postartdateID", message); 
			return null; 
	   }
	winner.setMean(student.calculateMean(this.getRaffle()));
	winner.setStandarddeviation(student.calculateStandardDeviation(this.getRaffle()));
	
	student_list.add(this);
	if(winner.getMean() > 90)
		return "WinnerAcknowledgement";
	else
		return "SimpleAcknowledgement";
}

public ArrayList<Student> getStudents(){
	return student_list;
}

public boolean validateRaffleno(String validateString)
{
	
	String[] integerArray = validateString.split(",");

	for(int i=0 ;i<integerArray.length; i++){
		if(integerArray[i] == "")
			continue;
		else
			integerArray[i] = integerArray[i].trim();
	}
	
	try{
		if(integerArray.length > 10){
				return false;
			}
		else if(integerArray.length < 10){
				return false;
				}
		else{
				for(int i=0; i<integerArray.length; i++){
				   if(Integer.parseInt(integerArray[i]) > 100){
					   return false;
				   }
				}
				return true;
			}
	}catch(NumberFormatException e){
		return false;
	}
}

public String validatedatefull(String string)
{
	String[] intarray = string.split("/");
	String result = null;
	int count = 0;
	
	if(Integer.parseInt(intarray[count++]) > 12 || Integer.parseInt(intarray[count++]) > 31 || Integer.parseInt(intarray[count]) > 9999)	
		result = "0";
	else
		result = "1";
	
	
	return result;
}

public List<String> TestMethod(String prefix) {
	
	List<String> resultval = new ArrayList<String>();
	resultval.add("hello");
	resultval.add("world");
	
	return resultval;
	
}

public List<String> completeRec(String query) 
{
    List<String> results = new ArrayList<String>();
    System.out.println(query);
    for(String possibleLanguages:likesa)
    {
    	if(possibleLanguages.toUpperCase().startsWith(query.toUpperCase())) {
			results.add(possibleLanguages);
    	}
    }
    //results.add("Very Likely");
    //results.add("Likely"); 
    //results.add("Unlikely"); 
    System.out.println(results + " for query= " + query);
    return results;
}
public void restFunc()
{
	String result = null; 
	String state = null; 
	String city = null; 
	String[] rarray = null; 
	
	try{ 
		Client client = ClientBuilder.newClient(); 
		WebTarget webTarget = client.target("http://localhost:8080/swe645hw3/api/identifycitystate").path(zipcode.toString()); 
		Invocation.Builder invocationBuilder = webTarget.request(); 
		Response response = invocationBuilder.get(); 
		
		result = response.readEntity(String.class);  
		System.out.println(result);
		rarray = result.split(":");
		
		if(rarray.length == 2)
		{
		city = rarray[0]; 
		state = rarray[1]; 
		}
	  }catch(Exception e)
	  {
		e.printStackTrace(); 
	  }
	 
	if(rarray != null && rarray.length == 2)
	{
		this.setCity(city);
		this.setState(state);
	}
}

}
