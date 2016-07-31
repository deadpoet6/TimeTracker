package smaple_test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class timetracker {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		WebDriver driver=new FirefoxDriver();
		driver.get("http://productioncarrot.mrcc.ovgu.de/timetracker/timeTracker.jsp?state=RANDOM&code=e6216e03560a3c39b352#");
		driver.findElement(By.xpath("html/body/div/div[2]/div[1]/div[1]/div/ul/li[2]/a")).click();
		
		driver.findElement(By.xpath("html/body/div/div[2]/div[2]/div[1]/input")).sendKeys("krish2Back");
	     Thread.sleep(500);
		driver.findElement(By.xpath("html/body/div/div[2]/div[2]/div[1]/button")).click();
		driver.findElement(By.xpath("html/body/div/div[2]/div[2]/div[2]/h3[4]")).sendKeys(Keys.TAB);
		driver.findElement(By.xpath("html/body/div/div[2]/div[2]/div[2]/div[8]/div/input[1]")).click();
		Thread.sleep(6000);
		driver.findElement(By.xpath("html/body/div/div[2]/div[2]/div[2]/div[5]/div/input[2]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("html/body/div/div[2]/div[2]/div[2]/div[2]/div/input[3]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("html/body/div/div[2]/div[2]/div[2]/div[2]/div/input[4]")).click();
		
		
		
		
		
	         
	    

	}

}
