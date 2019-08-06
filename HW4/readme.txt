Name:Deepak Kanuri
G01070295

File Description. 

1. SWE645hw4.war is the application which contains all the related files for the assignment. 
2. SOURCEFILES contains the source files used for building the application. 

Installation instructions. 

Localhost:

1. Drag and drop the SWE645hw3.war into your tomcat webapps directory under "apache-tomcat-7.0.70\webapps" and restart the tomcat server using IDE or if you are using linux - run command "sudo service tomcat7 restart" using the terminal if you have linux based computer. 

2. Ensure that your project is JSF 2.2 Compatible for the application to work. 
   Rightclick -> properties -> Project Facets -> check JSF 2.2
   Make sure to include the library for the project if you are planning to generate a fresh WAR file. 

Installation on EC2

1. Create an Instance of Unix server with a free-tier service provided by Amazon. 
2. Add the security permission for view and read only - so that everyone would be able to access your website. 
3. Access the Instance (machine) using putty and install Apache Tomcat using the command: sudo yum install tomcat7-webapps tomcat7-docs-webapp tomcat7-admin-webapps.
4. Set the tomcat7 server to autostart using the command - sudo chkconfig tomcat7 on.
5. Open WinSCP, configure the security, after logging in - navigate to webapps location and transfer the .war file from your system to the directory on the instance. 
6. run command "sudo service tomcat7 restart". 
7. Look for the public dns under the description tab in the EC2 instance management console. 
8. append the port number to the public dns and access your application by appending the war file name as /yourwarfilename to the public dns. 



https://s3.amazonaws.com/swe645-dk/index.html
This is the link for the homepage. This will redirect you to the survey page whose link is given below.

http://ec2-34-201-245-46.compute-1.amazonaws.com/swe645hw4/index.xhtml


The above link has links to survey page and survey list. The survey page contains  the following:
1. Develop a student survey form (a facelet) to allow a user to enter the survey data. In
Particular, the student survey form contains the following:
a. Text boxes for first name, last name, street address, city, state, zip, telephone number,
e-mail, and date of survey, which are required fields. In addition, make sure
o The name text boxes will contain only alphabets and should not allow more
than 15 characters.
o The address text boxes will contain only appropriate numeric, alphabet, or
alphanumeric characters.
o The length of street address cannot be more than 30.
o Zipcode should be exactly five digits.
o Telephone number will have the following pattern: (xxx)-xxx-xxxx
o The date of survey will have the following pattern: mm/dd/yyyy
o The Email Address should be valid.
b. Checkboxes that allow prospective students to indicate what they liked most about
the campus. The checkboxes should include: students, location, campus, atmosphere,
dorm rooms, and sports.
c. Radio buttons that allow the prospective students to indicate how they became
interested in the university. Options should include: friends, television, Internet, and
other.
d. A dropdown list of options for the user to select the likelihood of him/her
recommending this school to other prospective students. The three options of the
dropdown list are: Very Likely, Likely, Unlikely. 
e. An additional text box called Raffle. The user will be asked to enter at least ten
comma separated numbers ranging from 1 through100 in the Raffle field. This
information will be used to announce whether the student wins a free movie
ticket.
f. A text area for additional comments, and
g. A submit and cancel buttons. 

in addition we have implemented restful web service which renders the city and state when zipcode is entered.
prime face also has been used telephone mask and calendars have been used.

In the next step we have implemented RDS. We have inserted a search menu so that the records can be searched from the database and retreived. We have also implemented the delete heyperlink to delete the record and we have inmplemented reset button in the survey page also.

P.S. My EC2 is not working    please use war file only