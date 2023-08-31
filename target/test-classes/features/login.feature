Feature: Application login

Scenario Outline: Login with valid credentials
Given Open any browser
And Navigate to login page
When User enters username as "<username>" and password as "<password>" into the fields
And User clicks on the login button
Then Verify user login, it should depend on "<status>"
Examples:
|username								|password	 |status |
|arun.selenium@gmail.com|Second@123|success|
|dummy@gmail.com				|hello		 |failure|