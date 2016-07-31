package JspPagesTest.SimpleTest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
@Test
public class Time_Test {
	public static WebDriver driver;
	public void LoginTest(){
		driver=new FirefoxDriver();
       driver.get("http://localhost:8087/timetracker/index.jsp");
		
		driver.findElement(By.name("uname")).sendKeys("krish");
		driver.findElement(By.name("pass")).sendKeys("8520");
		driver.findElement(By.id("loginbutton")).click();
	}
	
	public void RegistrationTest(){
		driver=new FirefoxDriver();
		driver.get("http://localhost:8087/timetracker/reg.jsp");
		
	     driver.findElement(By.name("fname")).sendKeys("krish");
	     driver.findElement(By.name("lname")).sendKeys("pemm");
		
		driver.findElement(By.name("email")).sendKeys("krish9001@gmail.com");
		driver.findElement(By.name("uname")).sendKeys("krish");
		driver.findElement(By.name("pass")).sendKeys("8520");
		driver.findElement(By.id("submit")).click();	    
	    
	}

}
