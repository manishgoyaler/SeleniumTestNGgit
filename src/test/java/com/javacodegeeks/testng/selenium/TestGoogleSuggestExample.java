package com.javacodegeeks.testng.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

@ContextConfiguration("driver_context.xml")
public class TestGoogleSuggestExample extends AbstractTestNGSpringContextTests {
	@Autowired
	private WebDriver driver;
	
	@BeforeClass
	public void printBrowserUsed() {
		System.out.println("Driver used is: " + driver);
	}

	@Test
	public void googleSuggest() throws Exception {

		// Go to the Google Suggest home page
        driver.get("http://www.google.com/webhp?complete=1&hl=en");
        
        // Enter the query string "Cheese"
        WebElement query = driver.findElement(By.name("q"));
        query.sendKeys("Cheese");

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				WebElement resultsDiv = d.findElement(By.className("sbdd_b"));
				return resultsDiv.isDisplayed();
			}
		});
        
        // And now list the suggestions
        List<WebElement> allSuggestions = driver.findElements(By.xpath("//td[@class='gssb_a gbqfsf']"));
        
        for (WebElement suggestion : allSuggestions) {
            System.out.println(suggestion.getText());
        }

    }
	
	@AfterSuite
	public void quitDriver() throws Exception {
		if(driver != null)
		driver.quit();
	}
}