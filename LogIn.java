import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LogIn {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriver driver=new FirefoxDriver();
		driver.get("http://localhost:8087/timetracker/index.jsp");
		
		driver.findElement(By.name("uname")).sendKeys("krish");
		driver.findElement(By.name("pass")).sendKeys("8520");
		driver.findElement(By.id("loginbutton")).click();
		
	}
}
	
	
	
