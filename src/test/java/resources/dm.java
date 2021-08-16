package resources;

import org.openqa.selenium.WebDriver;

public class dm {

	  private static ThreadLocal < WebDriver > driverThread = new ThreadLocal < > ();

		public static WebDriver getDriver() {
	    return driverThread.get();
	  }

	  public static void setDriverThread(WebDriver driver) {
	    driverThread.set(driver);
	  }
	
}
