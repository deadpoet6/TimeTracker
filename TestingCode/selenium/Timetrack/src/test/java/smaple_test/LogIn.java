package smaple_test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LogIn {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver=new FirefoxDriver();
		driver.get("https://www.wunderlist.com/login");
		driver.findElement(By.xpath("html/body/div[2]/form/div[1]/input")).sendKeys("krishh9001@gmail.com");
		driver.findElement(By.xpath("html/body/div[2]/form/div[2]/input")).sendKeys("bala8520");
		driver.findElement(By.xpath("html/body/div[2]/form/div[3]/input")).click();
		driver.findElement(By.xpath("html/body/div/div[3]/div[2]/div[1]/input")).sendKeys("movie");
		
		
		
	}
}

	
