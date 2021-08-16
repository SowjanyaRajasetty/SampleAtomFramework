package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.commons.io.FileUtils;

public class CommonUtilities {
	

	public WebDriver instantiateDriver() throws IOException {
		WebDriver driver = null;
		String driverPath = System.getProperty("user.dir") + "\\libs\\drivers\\";
		String browserName = System.getProperty("browserName");
		switch (browserName) {
		case "chrome":
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
		dm.setDriverThread(driver);
		return dm.getDriver();
	}

	public void navigateToURL(String url) {
		dm.getDriver().get(url);
	}

	public String readfromPropertiesFile(String propName) throws IOException {
		String propPath = System.getProperty("user.dir") + "\\src\\test\\java\\environment\\"
				+ System.getProperty("env") + "Env.properties";
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(propPath);
		prop.load(fis);
		return prop.getProperty(propName);
	}

	public Object getDataFromJson(String fileName, String fieldName) throws IOException, ParseException {
		String[] afile = fileName.split("\\.");
		String path = System.getProperty("user.dir") + "\\src\\test\\java\\testData\\" + afile[0] + ".json";
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(path));
		jsonObject = (JSONObject) jsonObject.get(afile[1]);
		return jsonObject.get(fieldName);

	}

	public synchronized String getScreenShotPath(String uuid, WebDriver driver) throws IOException {
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "\\reports\\" + uuid + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;

	}
}
