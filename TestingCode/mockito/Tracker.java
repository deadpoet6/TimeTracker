package gokul.mock;



public class Tracker{
	private TimeTracker tracker;

	   public void setTimeTracker(TimeTracker tracker){
	      this.tracker = tracker;
	   }
	   public long refreshtime(String data){
		      return tracker.refreshtime(data);
		   }
	   public long start(String data){
	      return tracker.start(data);
	   }
	   
	   public long pause(String data){
		      return tracker.pause(data);
		   }
	   
	   public long finish(String data){
		      return tracker.start(data);
		   }
	   
	   
}
