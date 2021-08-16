package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pageObjects.LandingPage;
import resources.CommonUtilities;
import resources.dm;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class BlazeDemoLandingPageSteps extends CommonUtilities {
	public static Logger log = LogManager.getLogger(BlazeDemoLandingPageSteps.class.getName());
	
	@Given("^the application \"([^\"]*)\"$")
	public void the_application_something(String urlName) throws Throwable {
		instantiateDriver();
		dm.getDriver().get(readfromPropertiesFile(urlName));
		dm.getDriver().manage().window().maximize();
		dm.getDriver().manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
	}

	@Then("^user navigates to landing page successfully$")
	public void user_navigated_to_landing_page_successfully() throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		Assert.assertEquals(objLP.getfindFlight().isDisplayed(), true, "Find flight button is displayed");
		Assert.assertEquals(objLP.getDepCity().isDisplayed(), true, "Departure city dropdown is displayed");
		Assert.assertEquals(objLP.getDesCity().isDisplayed(), true, "Destination city dropdown is displayed");
	}

	@Then("^destination of the week! The Beach! is displayed on landing page$")
	public void linkDisplayed() throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		Assert.assertEquals(objLP.getDestLink().isDisplayed(), true, "destination of the week! The Beach!");
	}

	@Then("^click destination of the week! The Beach! link$")
	public void clickDestinationLink() throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		objLP.getDestLink().click();		
	}

	@Then("^destination of the week page is displayed$")
	public void destinationOfTheWeekPageIsDisplayed() throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		Assert.assertEquals(objLP.getPageTitle(), "BlazeDemo - vacation",
				"The page is redirected to BlazeDemo - vacation page");
		Assert.assertEquals(objLP.getdestOfWeekText().isDisplayed(), true);
		Assert.assertEquals(objLP.getimage().isDisplayed(), true);
	}

	@And("^select (.+) from departure city dropdown$")
	public void select_from_departure_city_dropdown(String departure) throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		objLP.selctFromDropDown(objLP.getDepCity(), departure);
	}

	@And("^select (.+) from destination city dropdown$")
	public void select_from_destination_city_dropdown(String destination) throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		objLP.selctFromDropDown(objLP.getDesCity(), destination);

	}

	@Then("^click Find Flights button$")
	public void clickFindFlightsButton() throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		objLP.getfindFlight().click();
	}

	@Then("^validate that flights are displayed between (.+) and (.+)$")
	public void validate_that_flights_are_displayed_between_and(String departure, String destination) throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		Assert.assertEquals(objLP.verifyflightsFromText(departure, destination), true,
				"Text Flights from " + departure + " to " + destination + ": is displayed");
		Assert.assertEquals(objLP.verifyDepartsText(departure), true, "column Departs is displayed with " + departure);
		Assert.assertEquals(objLP.verifyArrivesText(destination), true,
				"column Departs is displayed with " + destination);
	}

	@And("^click on Choose This Flight button for Flight Number (.+)$")
	public void click_on_choose_this_flight_button_for_flight_number(String flightnum) throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		objLP.chooseFlight(flightnum);
	}

	@And("^check Remember Me checkbox$")
	public void rememberMe() throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		objLP.selectCheckBox(objLP.getRememberMe());
	}

	@And("^user navigates to purchase page successfully$")
	public void navigationToPurchasePage() throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		String resText = objLP.getTextOfElement(objLP.getReservedText());
		Assert.assertEquals(resText, "Your flight from TLV to SFO has been reserved.",
				"user navigated to purchase page successfully");
	}

	@Then("^verify the details on purchase page (.+)$")
	public void verify_the_details_on_purchase_page(String data) throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		String flightNum = getDataFromJson(data, "flightNum").toString();
		String airline = getDataFromJson(data, "airline").toString();
		String departs = getDataFromJson(data, "departs").toString();
		String arrives = getDataFromJson(data, "arrives").toString();
		String price = getDataFromJson(data, "price").toString();
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(airline, objLP.getElementFromReservePage(flightNum, "airline").getText(),
				"Airline is displayed");
		sa.assertEquals(flightNum, objLP.getElementFromReservePage(flightNum, "flightNum").getText(),
				"Flight Number is displayed");
		sa.assertEquals(departs, objLP.getElementFromReservePage(flightNum, "departs").getText(),
				"Departure Time is displayed");
		sa.assertEquals(arrives, objLP.getElementFromReservePage(flightNum, "arrives").getText(),
				"Arrival Time is displayed");
		sa.assertEquals(price, objLP.getElementFromReservePage(flightNum, "price").getText(),
				"Price is displayed");
		sa.assertAll();
	}

	@Then("^enter the details (.+) in purchase page$")
	public void enter_the_details_in_purchase_page(String data) throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		objLP.sendKeysInTextBox(objLP.getName(), getDataFromJson(data, "name").toString());
		objLP.sendKeysInTextBox(objLP.getAddress(), getDataFromJson(data, "address").toString());
		objLP.sendKeysInTextBox(objLP.getCity(), getDataFromJson(data, "city").toString());
		objLP.sendKeysInTextBox(objLP.getState(), getDataFromJson(data, "state").toString());
		objLP.sendKeysInTextBox(objLP.getZipCode(), getDataFromJson(data, "zipCode").toString());
		objLP.selctFromDropDown(objLP.getCardTypeDropDown(), getDataFromJson(data, "cardType").toString());
		objLP.sendKeysInTextBox(objLP.getCreditCardNumber(), getDataFromJson(data, "creditCardNumber").toString());
		objLP.sendKeysInTextBox(objLP.getCreditCardMonth(), getDataFromJson(data, "month").toString());
		objLP.sendKeysInTextBox(objLP.getCreditCardYear(), getDataFromJson(data, "year").toString());
		objLP.sendKeysInTextBox(objLP.getNameOnCard(), getDataFromJson(data, "nameOnCard").toString());
	}

	@And("^click Purchase Flight button$")
	public void purchaseFlight() throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		objLP.clickPurchaseFlightButton(objLP.getPurchaseFlight());
			}

	@And("^validate that user navigates to confirmation page$")
	public void validateConfirmationPage() throws Throwable {
		LandingPage objLP = new LandingPage(dm.getDriver());
		SoftAssert sa = new SoftAssert();
		sa.assertEquals(objLP.getTextOfElement(objLP.getConfirmationText()), "Thank you for your purchase today!",
				"user navigated to confirmation page successfully");
		log.info("confirmation number is :" + objLP.getTextOfElement(objLP.getConfirmationID()));
		sa.assertTrue(objLP.getTextOfElement(objLP.getConfirmationID())!=null,"Confirmation id is displayed");
		sa.assertAll();
	}
	
    @Then("^Travel The World link is displayed on landing page$")
    public void travel_the_world_link_is_displayed_on_landing_page() throws Throwable {
    	LandingPage objLP = new LandingPage(dm.getDriver());
    	Assert.assertTrue(objLP.getTravelTheWorld().isDisplayed());
    }

    @Then("^Travel The World page is displayed$")
    public void travel_the_world_is_displayed() throws Throwable {
    	LandingPage objLP = new LandingPage(dm.getDriver());
    	Assert.assertEquals(objLP.getPageTitle(), "BlazeDemo");
    }

    @And("^click Travel The World link$")
    public void click_travel_the_world_link() throws Throwable {
    	LandingPage objLP = new LandingPage(dm.getDriver());
    	objLP.getTravelTheWorld().click();
    }
    
    @Then("^Home link is displayed on landing page$")
    public void home_link_is_displayed_on_landing_page() throws Throwable {
    	LandingPage objLP = new LandingPage(dm.getDriver());
    	Assert.assertTrue(objLP.getHome().isDisplayed());
    }

    @Then("^Blaze Demo Login page is displayed$")
    public void blaze_demo_login_page_is_displayed() throws Throwable {
    	LandingPage objLP = new LandingPage(dm.getDriver());
    	SoftAssert sa = new SoftAssert();
    	sa.assertEquals(objLP.getPageTitle(), "BlazeDemo");   
    	sa.assertTrue(objLP.getEmail().isDisplayed(), "Blaze Demo Login Page is displayed successfully");   
    	sa.assertAll();
    }

    @And("^click Home link$")
    public void click_home_link() throws Throwable {
    	LandingPage objLP = new LandingPage(dm.getDriver());
    	objLP.getHome().click();
    }


}
