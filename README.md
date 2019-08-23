# GoGo-Assignment

PreRequisites for both the Projects: Java 8, Maven 

Maven is used for ease handling of dependencies in the project.

******************************************************************************************************

List of Maven dependencies added in to that project 'AmazonTest': 
   - Selenium WebDriver
   - TestNg
   - JUnit
   
List of Maven dependencies added in to that project 'RestAssuredTest': 
   - RestAssured
   - TestNg
   - RestAssured_JSON-Path
   - JSON-Simple
 
******************************************************************************************************

Project Description (AmazonTest):
---------------------------------
FrameWork used: Simple TestNG framework with Page Object Model design pattern

   
Folder Structure (AmazonTest):
------------------------------
Tests		-	For TestSuites
PageObjects	- For Page related actions
Utils		- For common functionalities
   
datas		- Folder inside project location is used to store screenshots of all test results
target->surefire-reports	- For default TestNG Reports


Notes (AmazonTest):
-------------------
Project results output(Product details) will be added into 'BookDetails.properties' file inside project directory

Common values like url, category selection, nth result selection and so on are available in TestNG xml(testng.xml) file. It can be modified. 
'ProdNo' specifies which result to be selected. Ex. 1 means select first result.


Execute the project through Maven (AmazonTest): 
-----------------------------------------------
Command line execution for Windows:
Unzip the project and goto its directory location having pom.xml file. Open the same location in command prompt.
Folder location - '\AmazonTest\pom.xml\AmazonTest\'
Enter the command 'mvn test' to execute the project

******************************************************************************************************

Project Description (RestAssuredTest):
--------------------------------------
FrameWork used: Simple TestNG framework

   
Folder Structure (RestAssuredTest):
-----------------------------------
Tests		-	For TestSuites
   
target->surefire-reports	- For default TestNG Reports


Notes (RestAssuredTest):
------------------------
I have not worked on RestAssured before. So i learnt for this assignment and did the project.
Hence could not cover extensively and also not able to cover more number of test cases as per point 3 in API automation assignment.

Common values like uri, token, and api are available in TestNG xml file(GoGoTest.xml). It can be modified. 


Execute the project through Maven (RestAssuredTest): 
-----------------------------------------------------
Command line execution for Windows:
Unzip the project and goto its directory location having pom.xml file. Open the same location in command prompt.
Folder location - '\RestAssuredTest\pom.xml\'
Enter the command 'mvn test' to execute the project

					  
	
