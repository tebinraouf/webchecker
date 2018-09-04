package org.eclipse.epsilon.webchecker;

import java.util.List;

import org.eclipse.epsilon.webchecker.browser.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestDriver {

	public static void main(String[] args) {
		
		//Step 1: Get the source to be validated
		Browser browser = new Browser();
		WebDriver driver = browser.getDriver();
		driver.get("https://www.eclipse.org/epsilon/");		
		WebElement element = driver.findElement(By.className("span5"));
		String html = element.getAttribute("outerHTML");
		
		//Step 2: Write your validation using Epsilon Validation Language
		String evl = "files/sampleEVL.evl";
		
		//Step 3: Check the validation against the html in step 1
		WebChecker checker = new WebChecker();
		checker.setSource(html);
		checker.setValidation(evl);
		checker.check();
		
		//Step 4: Check the result
		System.out.println("Does the html code pass validation? " + checker.isValid());
		List<String> errors = checker.errors();
		System.out.println("Validation Errors:");
		for (String error : errors) {
			System.out.println(error);
		}		
		driver.quit();
	}

}
