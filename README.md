# RideShareBonusPoints
##Title: Toogethr/Question 2: Test Automation to Test a REST API endpoint-rideshare response
## Table of contents
* General info
* Technologies/Tools used
* Setup
* Brief about the Code

General info:
Introduction: This is a test that will examine the below mentioned endpoint response and will verify that all users receive the correct amount of points based on certain rules, for user ID “f9d54ec1-3a7e-4a79-ae3c-f1fe1b2a8b30”.
API Endpoint: https://e018fed1-c416-4c3e-ba7d-66bfa479a5a9.mock.pstmn.io/rideshares

Technologies/Tools required:
Eclipse IDE
jre1.8.0_231/jdk1.8.0_231
TestNG 6.14.3

Setup:
1.	Please “clone or download” my project  from the below GitHub repository https://github.com/prasanth446/RideShareBonusPoints
2.	Will get a Zip file and please extract the Zip file into the local computer
3.	Open Eclipse and If you don’t installed the TestNG software plugin in Eclipse,Please click Help in Eclipse  Install new Software click on Add for work with  Give Name a TestNG , Location=http://beust.com/eclipse   click ADD then we can find  “TestNG” over there  select it and click on Next then the ‘Eclipse will automatically start installing the TestNG’.
4.	Open the Eclipse and import the Unzipped project into Eclipse Project Explorer,  by Clicking File  Importfind for Maven (Existing Maven Projects) --> click on Next and provide Root Directory= <Unzipped project location> then we can see /pom.xml file with SNAPSHOT: jar  select it  click Next. Then Eclipse will start importing the project into workspace by automatic installation of all the mentioned dependencies mentioned in pom.xml file.
Then the Project will be imported into the Eclipse workspace. If we are facing any issues with maven then please right click on project  Maven Update project.
5.	Please expand the imported project in Eclipse and click/open the testing.xml file and Run AS   TestNG Suite. 
So the project will start executing and will display the test results in Console.

Brief about the code:
Java Class:
Created a public class Evaluation2, and further created the methods in it to get, validate and display the Bonus points for the rideshare users.
Methods Using:
1.	getResponse()
     A method has been declared to get the data from the given API URL endpoint.
     This method is using to GET the response from the API endpoint by using the user Id = "f9d54ec1-3a7e-4a79-ae3c-f1fe1b2a8b30" and the response is getting saved in the variable ‘jsonResponse’

2.	validateResponse()
     A method has been declared and used to read the response saved in variable ‘jsonResponse’, 
a.	for every rideshare, get the number of people in the ride and checks that ride has  a driver or not 
b.	get the travel_distance from the response and calculate the bonus points as per the gives rules  
c.	Based on the number of passengers involved and driver presence, it calculates the final calculated points for all the involved users
d.	Validate the Final calculated points and Displayed points in response. If both the points are equal then our test is PASSED.

For Loops using:
1.	for (int i = 0; i < rideshares.size(); i++)
to get iterated for all the available number of rides in the response.

2.	for (int j = 0; j < people.size(); j++)
to be get iterated for all the involved people in the ride.


Advantage of this Test code:
This is a Generic/Dynamic test automation script, which will work even if we have increased the number of rides in the Rideshare response later on.
This script will work/evaluate all the possible test scenarios based on the API response.
