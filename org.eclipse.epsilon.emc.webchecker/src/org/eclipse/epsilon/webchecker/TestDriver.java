package org.eclipse.epsilon.webchecker;

import java.util.List;

import org.eclipse.epsilon.webchecker.browser.Browser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestDriver {

	public static void main(String[] args) {
		
		
		String html = "https://raw.githubusercontent.com/tebinraouf/samplefiles/master/sample.html";
		String evl = "/Users/Tebin/Desktop/Eclipse-Workspace/mydriver/webchecker/WebCheckerSample/sample.evl";
		
		WebChecker checker = new WebChecker();
		checker.setSource(html);
		checker.setValidation(evl);
		checker.check();
		System.out.println(checker.isValid());
		List<String> errors = checker.errors();
		
		for (String error : errors) {
			System.out.println(error);
		}
		
		
//		Browser browser = new Browser();
//		WebDriver driver = browser.getDriver();
//		driver.get("https://www.eclipse.org/epsilon/");
		
//		driver.findElement(By.tagName("a").cssSelector("a[href='#languages']")).click();
		
//		WebElement elements = driver.findElement(By.tagName("a"));
//		
//		System.out.println(elements.getText());
//		
//		System.out.println(driver.getTitle());
//		
//		driver.quit();
		
		
//		List<WebElement> elements = driver.findElements(By.tagName("div"));
//		
//		for (WebElement webElement : elements) {
//			String text = webElement.getText();
//			System.out.println("each element");
//		}
		
		
		
		
		System.out.println("test...");
		
	}

}
