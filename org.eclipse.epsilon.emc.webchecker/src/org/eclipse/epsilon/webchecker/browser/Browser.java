package org.eclipse.epsilon.webchecker.browser;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.print.DocFlavor.URL;

import org.eclipse.epsilon.webchecker.WebCheckerModel;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Browser {
	
	protected WebCheckerModel model = null;
	private WebDriver driver;
	
	private static String GEOKODRIVERKEY = "webdriver.gecko.driver";
	private static String GEOKODRIVERVALUE = FileSystems.getDefault().getPath("drivers/geckodriver").toAbsolutePath().toString();
	
	public static void main(String[] args) {
		
		Browser browser = new Browser();
		WebDriver driver = browser.getDriver();
		driver.get("https://www.eclipse.org/epsilon/");
		
		driver.findElement(By.tagName("a").cssSelector("a[href='#languages']")).click();
		
//		WebElement elements = driver.findElement(By.tagName("a"));
//		
//		System.out.println(elements.getText());
//		
//		System.out.println(driver.getTitle());
//		
//		driver.quit();
		
		System.out.println("test...");
		
	}
	
	//By Default, for now it's Firefox driver
	public Browser() {
		System.setProperty(GEOKODRIVERKEY, GEOKODRIVERVALUE);
		driver = new FirefoxDriver();
	}
	public Browser(WebCheckerModel model) {
		this.model = model;
	}
	public WebCheckerModel getModel() {
		return model;
	}
	public void setModel(WebCheckerModel model) {
		this.model = model;
	}
	public WebDriver getDriver() {
		return driver;
	}
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
}
