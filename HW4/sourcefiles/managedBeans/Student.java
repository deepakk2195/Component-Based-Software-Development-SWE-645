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
import java.awt.Event;
import java.io.Serializable;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.persistence.*;
import static supportingClasses.StudentService.student_list;

@Entity
@ManagedBean(name="studentBean", eager=true)
@Table(name = "STUDENTS")
@ApplicationScoped

public class Student {
	
private String firstname, lastname, city, state, email, interestedin, raffle, additionalComments, recommendation, streetaddress, phonenumber,zipcode; 
private List<String> campusOptions;
private List<Student> search_result; 
private List<Student> all_records;
private Date date, postartdate;
private int slno;
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

public void reset(){

firstname = null; 
lastname = null; 
streetaddress = null; 
city = null; 
state = null; 
zipcode = null;  
phonenumber = null; 
email = null; 
postartdate = null; 
campusOptions = null; 
interestedin = null; 
recommendation = null; 
raffle = null; 
additionalComments = null; 
date = null; 
}

public void searchreset(){
	firstname = null; 
	lastname = null; 
	city = null; 
	state = null; 
}

@Id
@Column(name="SLNO", nullable = false)
public int getSlno() {
	return slno;
}
public void setSlno(int slno) {
	this.slno = slno;
}

@Column(name="FIRST_NAME", nullable = false)
public String getFirstname() 
{
	return firstname;
}
public void setFirstname(String firstname) 
{
	this.firstname = firstname;
}

@Column(name="LAST_NAME", nullable = false)
public String getLastname()
{
	return lastname;
}
public void setLastname(String lastname) 
{
	this.lastname = lastname;
}

@Column(name="CITY", nullable = false)
public String getCity() 
{
	return city;
}
public void setCity(String city) 
{
	this.city = city;
}

@Column(name="STATE_LOC", nullable = false)
public String getState() 
{
	return state;
}
public void setState(String state) 
{
	this.state = state;
}

@Column(name="EMAIL_ID", nullable = false)
public String getEmail() 
{
	return email;
}
public void setEmail(String email) 
{
	this.email = email;
}

@Column(name="TELEPHONE", nullable = false)
public String getPhonenumber() {
	return phonenumber;
}
public void setPhonenumber(String phonenumber) 
{
	this.phonenumber = phonenumber;
}

@Column(name="SURVEY_DATE", nullable = false)
public Date getDate() 
{
	return date;
}
public void setDate(Date date) 
{
	this.date = date;
}

@Column(name="ZIPCODE", nullable = false)
public String getZipcode() 
{
	return zipcode;
}
@Transient
public void setZipcode(String zipcode) 
{
	this.zipcode = zipcode;
	restFunc();
}

@Transient
public List<String> getCampusOptions() 
{
	return campusOptions;
}
public void setCampusOptions(List<String> campusOptions) 
{
	this.campusOptions = campusOptions;
}

@Column(name="INTEREST_OPTION", nullable = false)
public String getInterestedin() 
{
	return interestedin;
}
public void setInterestedin(String interestedin) 
{
	this.interestedin = interestedin;
}

@Column(name="RECOMMEND_LEVEL", nullable = false)
public String getRecommendation() 
{
	return recommendation;
}
public void setRecommendation(String recommendation) {
	this.recommendation = recommendation;
}

@Column(name="RAFFLE_VALUES", nullable = false)
public String getRaffle() 
{
	return raffle;
}
public void setRaffle(String raffle) 
{
	this.raffle = raffle;
}

@Column(name="ADDITIONAL_COMMENTS", nullable = true)
public String getAdditionalComments() 
{
	return additionalComments;
}
public void setAdditionalComments(String additionalComments) 
{
	this.additionalComments = additionalComments;
}

@Column(name="STREET_ADDR", nullable = false)
public String getStreetaddress() 
{
	return streetaddress;
}
public void setStreetaddress(String streetaddress) 
{
	this.streetaddress = streetaddress;
}

@Transient
public Date getPostartdate() 
{
	return postartdate;
}
@Transient
public void setPostartdate(Date postartdate) 
{
	this.postartdate = postartdate; 
}

@Transient
public float getMean()
{
	return winner.getMean();
}

@Transient
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
    storedata(); 
	reset();
	if(winner.getMean() > 90)
		return "WinnerAcknowledgement";
	else
		return "SimpleAcknowledgement";
}

@Transient
public List<Student> getStudents(){
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("Student"); 
    EntityManager em = factory.createEntityManager();
    EntityTransaction userTransaction = em.getTransaction();
    userTransaction.begin();
    
    Query query = em.createQuery("from Student s");
	all_records = query.getResultList();
    System.out.println("Printing result array " + search_result);
    
    userTransaction.commit(); 
    em.close();
    factory.close();
    
	return all_records;
	//return student_list;
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
		WebTarget webTarget = client.target("http://localhost:8080/swe645hw4/api/identifycitystate").path(zipcode.toString()); 
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

@Transient
public List<Student> getSearchresults(){
	return search_result; 
}

public String search(){
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("Student"); 
    EntityManager em = factory.createEntityManager();
    EntityTransaction userTransaction = em.getTransaction();
    userTransaction.begin();
    
   //Query construction logic. 
    String query_construction = "from Student s where "; 
    if(getFirstname().length() != 0)
    	query_construction = query_construction.concat(" s.firstname = :FIRST_NAME and"); 
    
    if(getLastname().length() != 0)
    	query_construction = query_construction.concat(" s.lastname = :LAST_NAME and");
    	
    if(getCity().length() != 0)
    	query_construction = query_construction.concat(" s.city = :CITY and"); 
    	
    if(getState().length() != 0)
    	query_construction = query_construction.concat(" s.state = :STATE_LOC");
    
   //if you encounter and then just go back 4 characters to make it a valid query ;-). 
    if('d' == query_construction.charAt(query_construction.length()-1)){
    	query_construction = query_construction.substring(0, query_construction.length()-4); 
    }
    
    Query query;
  
    //if all fields are null just return all records. 
    if(getFirstname().length() == 0 &&
       getLastname().length() == 0 &&
       getCity().length() == 0 &&
       getState().length() == 0){
    	query = em.createQuery("from Student s");
    	System.out.println(query);
    }
    else{
    	   query = em.createQuery(query_construction); 
    	    if(getFirstname().length() != 0)
    	    query.setParameter("FIRST_NAME", firstname);
    	    
    	    if(getLastname().length() != 0)
    	    query.setParameter("LAST_NAME", lastname);
    	    
    	    if(getCity().length() != 0)
    	    query.setParameter("CITY", city);
    	    
    	    if(getState().length() != 0)
    	    query.setParameter("STATE_LOC", state);
    	    
	    System.out.println(query_construction);
	    System.out.println("First Name: " + firstname);
	    System.out.println("Last Name: " + lastname);
	    System.out.println("City : " + city);
	    System.out.println("State: " + state);
    }
    
    search_result = query.getResultList();
    
    searchreset();
    userTransaction.commit(); 
    em.close();
    factory.close();
	
    return "ListSurveyResults"; 
}

public void storedata(){
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("Student"); 
	EntityManager em = factory.createEntityManager();
    EntityTransaction userTransaction = em.getTransaction();
	        
    userTransaction.begin();
    
    //set the value of SLNO. 
    Query query = em.createQuery("from Student s"); 
    System.out.println("Query result " + query.getResultList().size());
    if(query.getResultList().size() != 0){
    //Obtain last student object/record in the sequence add one to obtain next unique value. 
    Student last_student = (Student)query.getResultList().get(query.getResultList().size()-1); 
    this.setSlno(last_student.getSlno() + 1);
    }else{
    	System.out.println("Result list is 0 so setting to 1");
    	this.setSlno(1);
    }
    em.persist(this);
    userTransaction.commit();
	        
    em.close();
    factory.close();
}

public String deletedata(Student delstudent){
	 System.out.println("The Slno to be deleted is " + delstudent.getSlno());
	    
	EntityManagerFactory factory = Persistence.createEntityManagerFactory("Student"); 
	EntityManager em = factory.createEntityManager();
    EntityTransaction userTransaction = em.getTransaction();
	        
    userTransaction.begin();
     
   
    Query query = em.createQuery("delete from Student s where s.slno = :SLNO");
    query.setParameter("SLNO", delstudent.getSlno());
    query.executeUpdate(); 
     
    search_result.remove(delstudent); 
    
    userTransaction.commit();
	        
    em.close();
    factory.close();
    
    return null; 
}

}
