package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class LandingPage {

	public WebDriver driver;
	
	By depCity = By.cssSelector("select[name='fromPort']");
	By desCity = By.cssSelector("select[name='toPort']");
	By findFlight = By.cssSelector(".btn.btn-primary");
	By destLink = By.xpath("//a[contains(text(),'destination')]");
	By destOfWeekText = By.xpath("//div[contains(text(),'Destination of the week: Hawaii !')]");
	By img = By.xpath("//img[@src]");
	By flightsFromText = By.xpath("//div[@class='container']/h3");
	By departsText = By.xpath("//div[@class='container']/table/thead/tr/th[contains(text(),'Departs')]");
	By arrivesText = By.xpath("//div[@class='container']/table/thead/tr/th[contains(text(),'Arrives')]");
	By name = By.id("inputName");
	By address = By.id("address");
	By city = By.id("city");
	By state = By.id("state");
	By zipCode = By.id("zipCode");
	By creditCardNumber = By.id("creditCardNumber");
	By creditCardMonth = By.id("creditCardMonth");
	By creditCardYear = By.id("creditCardYear");
	By nameOnCard = By.id("nameOnCard");
	By cardTypeDropDown= By.id("cardType");
	By reservedText = By.xpath("//div[@class='container']/h2");
	By rememberMe = By.name("rememberMe");
	By purchaseFlight = By.xpath("//div[@class='controls']/input[@type='submit']");
	By confirmationID = By.xpath("//tr/td[text()='Id']/following-sibling::td");
	By confirmationDate = By.xpath("//tr/td[text()='Date']/following-sibling::td");
	By confirmationText = By.xpath("//h1[text()='Thank you for your purchase today!']");
	By travelTheWorld = By.xpath("//a[text()='Travel The World']");
	By home = By.linkText("home");
	By email = By.id("email");
	
	public LandingPage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement getDepCity() {
		return driver.findElement(depCity);
	}

	public WebElement getDesCity() {
		return driver.findElement(desCity);
	}

	public WebElement getfindFlight() {
		return driver.findElement(findFlight);
	}

	public WebElement getdestOfWeekText() {
		return driver.findElement(destOfWeekText);
	}

	public WebElement getDestLink() {
		return driver.findElement(destLink);
	}

	public WebElement getimage() {
		return driver.findElement(img);
	}

	public WebElement getflightsFromText() {
		return driver.findElement(flightsFromText);
	}

	public WebElement getdepartsText() {
		return driver.findElement(departsText);
	}

	public WebElement getarrivesText() {
		return driver.findElement(arrivesText);
	}

	public WebElement getName() {
		return driver.findElement(name);
	}

	public WebElement getAddress() {
		return driver.findElement(address);
	}

	public WebElement getCity() {
		return driver.findElement(city);
	}

	public WebElement getState() {
		return driver.findElement(state);
	}
	
	public WebElement getZipCode() {
		return driver.findElement(zipCode);
	}

	public WebElement getCreditCardNumber() {
		return driver.findElement(creditCardNumber);
	}

	public WebElement getCreditCardMonth() {
		return driver.findElement(creditCardMonth);
	}

	public WebElement getCreditCardYear() {
		return driver.findElement(creditCardYear);
	}

	public WebElement getNameOnCard() {
		return driver.findElement(nameOnCard);
	}

	public WebElement getCardTypeDropDown() {
		return driver.findElement(cardTypeDropDown);
	}

	public WebElement getReservedText() {
		return driver.findElement(reservedText);
	}

	public WebElement getRememberMe() {
		return driver.findElement(rememberMe);
	}	

	public WebElement getPurchaseFlight() {
		return driver.findElement(purchaseFlight);
	}	
	
	public WebElement getConfirmationID() {
		return driver.findElement(confirmationID);
	}
	
	public WebElement getConfirmationDate() {
		return driver.findElement(confirmationDate);
	}
	
	public WebElement getConfirmationText() {
		return driver.findElement(confirmationText);
	}
	
	public WebElement getTravelTheWorld() {
		return driver.findElement(travelTheWorld);
	}	

	public WebElement getHome() {
		return driver.findElement(home);
	}	

	public WebElement getEmail() {
		return driver.findElement(email);
	}
	
	public String getPageTitle() {
		return driver.getTitle();
	}

	public String getTextOfElement(WebElement element) {
		return element.getText();
	}

	public void selctFromDropDown(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
	}
	
	public void sendKeysInTextBox(WebElement element, String value) {
		element.clear();
		element.sendKeys(value);	
	}
	
	public void selectCheckBox(WebElement element) {
		if(!element.isSelected()) {
			element.click();
		}
	}
	
	public void clickPurchaseFlightButton(WebElement element) {
		Actions ac = new Actions(driver);
		ac.moveToElement(element).click().build().perform();
	}

	public boolean verifyflightsFromText(String des, String arr) {
		String text = getTextOfElement(getflightsFromText());
		Boolean flag = false;
		if (text.equals("Flights from " + des + " to " + arr + ":")) {
			flag = true;
		}
		return flag;
	}

	public boolean verifyDepartsText(String des) {
		String text = getTextOfElement(getdepartsText());
		Boolean flag = false;
		if (text.equals("Departs: " + des)) {
			flag = true;
		}
		return flag;
	}

	public boolean verifyArrivesText(String arr) {
		String text = getTextOfElement(getarrivesText());
		Boolean flag = false;
		if (text.equals("Arrives: " + arr)) {
			flag = true;
		}
		return flag;
	}

	public void chooseFlight(String num) {
		driver.findElement(By.xpath("//td[text()=" + num + "]/preceding-sibling::td")).click();
	}

	public WebElement getElementFromReservePage(String num, String fName) {
		WebElement el;
		if (fName.equals("flightNum")) {
			el = driver.findElement(By.xpath("//tbody/tr/td[text()=" + num + "]"));
		}
		else if (fName.equals("airline")) {
			el = driver.findElement(By.xpath("//tbody/tr/td[text()=" + num + "]/../td[3]"));
		} else if (fName.equals("departs")) {
			el = driver.findElement(By.xpath("//tbody/tr/td[text()=" + num + "]/../td[4]"));
		} else if (fName.equals("arrives")) {
			el = driver.findElement(By.xpath("//tbody/tr/td[text()=" + num + "]/../td[5]"));
		} else {
			el = driver.findElement(By.xpath("//tbody/tr/td[text()=" + num + "]/../td[6]"));
		}
		return el;
	}
}
