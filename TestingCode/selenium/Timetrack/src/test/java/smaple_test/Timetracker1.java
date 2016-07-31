package smaple_test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Timetracker1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver=new FirefoxDriver();
		driver.get("http://productioncarrot.mrcc.ovgu.de/timetracker/timeTracker.jsp?state=RANDOM&code=e6216e03560a3c39b352#");
	    driver.findElement(By.xpath("html/body/div/div[2]/div[1]/div[3]/input")).sendKeys("seminars");
	    driver.findElement(By.xpath("html/body/div/div[2]/div[1]/div[3]/button")).click();
	    driver.findElement(By.xpath("html/body/div/div[2]/div[1]/div[2]/div[1]/input")).sendKeys(Keys.TAB);
	    
	}

}
