@BlazeDemo
Feature: BlazeDemo Landing Page 

Background: Launch Blazedemo url 
	Given the application "BlazeDemo" 

@navigation
Scenario: Navigation to BlazeDemo Page 
 
	Then user navigates to landing page successfully 

@TravelTheWorld
Scenario: Validation of Travel The World link 

	Then Travel The World link is displayed on landing page 
	And click Travel The World link 
	Then Travel The World page is displayed 
	
@home
Scenario: Validation of home link 

	Then Home link is displayed on landing page 
	And click Home link 
	Then Blaze Demo Login page is displayed 
	
@destinationOfTheWeek	
Scenario: Validation of Destination of the week link 

	Then destination of the week! The Beach! is displayed on landing page 
	And click destination of the week! The Beach! link 
	Then destination of the week page is displayed 
	
@findFlights	
Scenario Outline: Validation that user is able to Find Flights successfully 

	Then user navigates to landing page successfully 
	And select <departure> from departure city dropdown 
	And select <destination> from destination city dropdown 
	And click Find Flights button 
	Then verify the details on purchase page <Data> 
	
	Examples: 
		| departure    | destination | flightNum | Data                | 
		| Philadelphia | London      | 43        | BlazeDemo.Scenario3 | 

@flightsBetweenSrcDes		
Scenario Outline: Validation that flights between depature and destination is displayed 

	Then user navigates to landing page successfully 
	And select <departure> from departure city dropdown 
	And select <destination> from destination city dropdown 
	And click Find Flights button 
	Then verify the details on purchase page <Data> 
	Then validate that flights are displayed between <departure> and <destination> 
	
	Examples: 
		| departure    | destination | flightNum | Data                | 
		| Philadelphia | London      | 43        | BlazeDemo.Scenario3 | 	
	
@selectFlight		
Scenario Outline: Validation that user is able to select flight successfully 

	Then user navigates to landing page successfully 
	And select <departure> from departure city dropdown 
	And select <destination> from destination city dropdown 
	And click Find Flights button 
	Then verify the details on purchase page <Data> 
	Then validate that flights are displayed between <departure> and <destination> 
	And click on Choose This Flight button for Flight Number <flightNum> 
	Then user navigates to purchase page successfully 
	
	Examples: 
		| departure    | destination | flightNum | Data                | 
		| Philadelphia | London      | 43        | BlazeDemo.Scenario3 | 

@successfulBooking
Scenario Outline: Validation that user is able to reserve Flights successfully 

	Then user navigates to landing page successfully 
	And select <departure> from departure city dropdown 
	And select <destination> from destination city dropdown 
	And click Find Flights button 
	Then verify the details on purchase page <Data> 
	Then validate that flights are displayed between <departure> and <destination> 
	And click on Choose This Flight button for Flight Number <flightNum> 
	Then user navigates to purchase page successfully 
	And enter the details <Data> in purchase page 
	And check Remember Me checkbox 
	And click Purchase Flight button 
	Then validate that user navigates to confirmation page 
	
	Examples: 
		| departure    | destination | flightNum | Data                | 
		| Philadelphia | London      | 43        | BlazeDemo.Scenario3 | 
		