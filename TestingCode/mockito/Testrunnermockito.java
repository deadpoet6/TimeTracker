package gokul.mock;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Testrunnermockito {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(testfinish.class);
      
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      
      System.out.println(result.wasSuccessful());
   }
}  	