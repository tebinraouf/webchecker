package org.eclipse.epsilon.webchecker.examples;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.epsilon.webchecker.WebChecker;
import org.eclipse.epsilon.webchecker.browser.Browser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class WebCheckerExamples {
	public void e1() {
		//Step 1: Get the source to be validated
		Browser browser = new Browser();
		WebDriver driver = browser.getDriver();
		driver.get("https://www.eclipse.org/epsilon/");		
		WebElement element = driver.findElement(By.className("span5"));
		String html = element.getAttribute("outerHTML");
		
		//Step 2: Write your validation using Epsilon Validation Language
		String evl = "files/eclipseEpsilon.evl";
		
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
	public void e2() {
		//Step 1: Get the source to be validated
		String html = "files/bootstrap/bootstrap.html";
		
		//Step 2: Write your validation using Epsilon Validation Language
		String evl = "files/bootstrap/bootstrap.evl";
		
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
	}
	public void e3() {
		
	}
	public void plainJava() {
		//This is an example of using plain Java to check constraints. This is used to compare Plain Java vs. WebChecker driver 
		File input = new File("files/bootstrap/newCheck.html");
		try {
			Document doc = Jsoup.parse(input, "UTF-8");
			Elements elements = doc.getElementsByTag("div");
			for (Element element : elements) {
				if (element.hasClass("col-sm-4")) {					
					if (!element.parent().hasClass("row")) {
						System.out.println("A div element with class col should have a parent element with class row");
					} else {
						if (!element.parent().parent().hasClass("container")) {
							System.out.println("A div element with class col should have a parent element with class row, which has a parent with class container.");
						}
					}
				}
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
