package data;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
* This class is responsible for performing database operations
*
* @author  Ikram ul haq
* @version 1.0
* @since   15-07-2016 
*/



public class DB {

	String dbUrl = "jdbc:mysql://127.0.0.1:3306/timetracker";
	String dbClass = "com.mysql.jdbc.Driver";
	String username = "root";
	String password = "root";
	Connection connection;
	
	public String status = "";
 
	/**
	 * constructor, creating database connection
	 */
	public DB(){
		
		 try {
			Class.forName(dbClass);
			this.connection = DriverManager.getConnection(dbUrl, username, password);
			
			System.out.println("Connection is : " + connection);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		   
	}
	

  /**
   * This function confirms the existence of particular record
   * 
   * @param query This contains sql query 
   * @return boolean This returns true if record exists otherwise false.
   */
	
	public boolean isRowExist(String query){
		System.out.println("is existing fun");
		//added by johannes hintsch, 15.8.2016 to test continuous integration
		System.out.println("test output");
		try {

				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
				
				System.out.println("is exixt result set : " + resultSet.getFetchSize());
				
				if(resultSet.next()){
					this.status = resultSet.getString("status");
					
					return true;
				} else return false;
					
				
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
		
		}
		
		return false;
	}
	
	

  /**
   * It is helping function for inserting record into database
   * 
   * @param query This contains sql query.
   */

	
	public void executeInsertQuery(String query){
		
			try {

					Statement statement = connection.createStatement();
					int resultSet = statement.executeUpdate(query);
					
					
			}catch (SQLException e) {
				e.printStackTrace();
			}finally{
				
				
			}
	}
	
  /**
   * It is adding lists and tasks which are not added yet into database
   * 
   * @param data csv data .
   */
	
	public void addData(String data){
		
		String[] rows = data.split(";");
		String[] columns;
		String query = "", checkQuery = "";
		
		
		for(int i = 0; i < rows.length; i++){
			
			columns = rows[i].split(",");
			System.out.println("add fun columns : " + columns.toString());
			checkQuery = "SELECT * FROM fact WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
		
			
			if(isRowExist(checkQuery))
				continue;
			
			Date date = new Date();
	        java.sql.Timestamp currentTimeStamp = new java.sql.Timestamp(date.getTime());
	        
			query = "INSERT INTO fact VALUES('" + columns[0] + "', '" + columns[1] + "', '" + columns[2] + "', '" + currentTimeStamp + "', 'create', 0)";
		
			executeInsertQuery(query);
			
			status = "create";
		}
		
		
		
	}
	
  /**
   * It is adding lists and finished tasks which are not added yet into database
   * 
   * @param data csv data .
   */
	public void addFinishData(String data) throws SQLException, ParseException{
		System.out.println("add finish fun");
		
		String[] rows = data.split(";");
		String[] columns;
		String query = "", checkQuery = "";
		
		for(int i = 0; i < rows.length; i++){
			
			System.out.println("addFD row : " + rows[i]);
			columns = rows[i].split(",");
			checkQuery = "SELECT * FROM fact WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
			System.out.println("addFD Check Query : " + checkQuery);
			
			boolean exist = isRowExist(checkQuery);
			
			System.out.println("exist : " + exist);
			
			if(exist){
				
				if(isFinished(rows[i]))
					continue;
				
				System.out.println("before finishTaskByWunder : " + rows[i]);
				finishTaskByWunder(rows[i]);
				
				continue;
			}
			
			System.out.println("new data is here");
			Date date = new Date();
	        java.sql.Timestamp currentTimeStamp = new java.sql.Timestamp(date.getTime());
	        
			query = "INSERT INTO fact VALUES('" + columns[0] + "', '" + columns[1] + "', '" + columns[2] + "', '" + currentTimeStamp + "', 'finish', 0)";
			
			System.out.println("Add Query : " + query);
			executeInsertQuery(query);
			
			status = "finish";
		}
		
		
		
	}
	
  /**
   * It is synchronizing data of wunderlist and database .
   * 
   * @param data csv data .
   */
	
	public long refreshTime(String data) throws SQLException{
		
		 //System.out.println("refrshing in db data :" + data);
		
		String[] rows = data.split(";");
		String[] columns;
		String query = "", checkQuery = "";
		long totalTime = 0;
		
		for(int i = 0; i < rows.length; i++){
			
			columns = rows[i].split(",");
			query = "SELECT * FROM fact WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
			
			Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);
	        
	        while (resultSet.next()) {
		        totalTime = resultSet.getInt("total_time");
		        java.sql.Timestamp StartTimeStamp = resultSet.getTimestamp("start_time");
		        status = resultSet.getString("status");
		        
		        Date date = new Date();
		        java.sql.Timestamp currentTimeStamp = new java.sql.Timestamp(date.getTime());
		     
		        String updateQuery;
		        if(status.equals("start")){
			        totalTime += (currentTimeStamp.getTime() - StartTimeStamp.getTime());
			        java.sql.Timestamp updatedStamp = new java.sql.Timestamp(totalTime);
			        updateQuery = "UPDATE fact SET total_time=" + totalTime + ", start_time='" + currentTimeStamp + "' WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
		        }
		        else
		        	updateQuery = "UPDATE fact SET total_time=" + totalTime + ", start_time='" + currentTimeStamp + "' WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
		        
		     
		        Statement updateStatement = connection.createStatement();
				updateStatement.executeUpdate(updateQuery);
	        }
			
			
		}
		
		return totalTime;
	}
	
  /**
   * It is starting task by changing its status and updating start time
   * 
   * @param data csv data .
   */
	public long startTask(String data) throws SQLException{
		
		String[] rows = data.split(";");
		String[] columns;
		String query = "", checkQuery = "";
		long totalTime = 0;
		
		for(int i = 0; i < rows.length; i++){
			
			columns = rows[i].split(",");
			query = "SELECT * FROM fact WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
			
			Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);
	        
	        while (resultSet.next()) {
		        totalTime = resultSet.getInt("total_time");
		        java.sql.Timestamp StartTimeStamp = resultSet.getTimestamp("start_time");
		        status = resultSet.getString("status");
		        
		        Date date = new Date();
		        java.sql.Timestamp currentTimeStamp = new java.sql.Timestamp(date.getTime());
		        
		       
		        String updateQuery = "UPDATE fact SET total_time=" + totalTime + ", start_time='" + currentTimeStamp + "', status='start' WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
		        
		        Statement updateStatement = connection.createStatement();
				updateStatement.executeUpdate(updateQuery);
	        }
			
			
		}
		status = "start";
		
		return totalTime;
	}

  /**
   * It pauses task by changing its status and updating start time and total time
   * 
   * @param data csv data .
   */
	public long pauseTask(String data) throws SQLException{
		
		String[] rows = data.split(";");
		String[] columns;
		String query = "", checkQuery = "";
		long totalTime = 0;
		
		for(int i = 0; i < rows.length; i++){
			
			columns = rows[i].split(",");
			query = "SELECT * FROM fact WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
			
			Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);
	        
	        while (resultSet.next()) {
		        totalTime = resultSet.getInt("total_time");
		        java.sql.Timestamp StartTimeStamp = resultSet.getTimestamp("start_time");
		        status = resultSet.getString("status");
		        
		        Date date = new Date();
		        java.sql.Timestamp currentTimeStamp = new java.sql.Timestamp(date.getTime());
		        
		        if(status.equals("start")){
		        	totalTime += (currentTimeStamp.getTime() - StartTimeStamp.getTime());
		        	java.sql.Timestamp updatedStamp = new java.sql.Timestamp(totalTime);
		        }
		        String updateQuery = "UPDATE fact SET total_time=" + totalTime + ", start_time='" + currentTimeStamp + "', status='pause' WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
		        
		        Statement updateStatement = connection.createStatement();
				updateStatement.executeUpdate(updateQuery);
	        }
			
			
		}
		status = "pause";
		
		return totalTime;
	}

  /**
   * It is finishing task by changing its status and updating start time.
   * this function is triggered when task is finished by using our time tracker application
   * 
   * @param data csv data .
   */
	public long finishTask(String data) throws SQLException{
		
		String[] rows = data.split(";");
		String[] columns;
		String query = "", checkQuery = "";
		long totalTime = 0;
		
		for(int i = 0; i < rows.length; i++){
			
			columns = rows[i].split(",");
			query = "SELECT * FROM fact WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
			
			Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);
	        
	        while (resultSet.next()) {
		        totalTime = resultSet.getInt("total_time");
		        java.sql.Timestamp StartTimeStamp = resultSet.getTimestamp("start_time");
		        status = resultSet.getString("status");
		        
		        Date date = new Date();
		        java.sql.Timestamp currentTimeStamp = new java.sql.Timestamp(date.getTime());
		        
		        if(status.equals("start")){
		        	totalTime += (currentTimeStamp.getTime() - StartTimeStamp.getTime());
		        	java.sql.Timestamp updatedStamp = new java.sql.Timestamp(totalTime);
		        }
		        
		        String updateQuery = "UPDATE fact SET status='finish', total_time=" + totalTime + ", start_time='" + currentTimeStamp + "' WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
		        
		        Statement updateStatement = connection.createStatement();
				updateStatement.executeUpdate(updateQuery);
	        }
			
			
		}
		
		status = "finish";
		
		return totalTime;
	}
	
  /**
   * It is finishing task by changing its status and updating start time.
   * this function is triggered when task is finished by using wunderlist application 
   * 
   * @param data csv data .
   */
	public long finishTaskByWunder(String data) throws SQLException, ParseException{
		
		String[] rows = data.split(";");
		String[] columns;
		String query = "", checkQuery = "";
		long totalTime = 0;
		
		for(int i = 0; i < rows.length; i++){
			
			columns = rows[i].split(",");
			query = "SELECT * FROM fact WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
			
			Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);
	        
	        while (resultSet.next()) {
		        totalTime = resultSet.getInt("total_time");
		        java.sql.Timestamp StartTimeStamp = resultSet.getTimestamp("start_time");
		        status = resultSet.getString("status");
		        
		        
		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
				
				Date date = format.parse(columns[3]);
		        java.sql.Timestamp currentTimeStamp = new java.sql.Timestamp(date.getTime());
		        
		        System.out.println("finsihing time : " + currentTimeStamp);
		        
		        if(status.equals("start")){
		        	totalTime += (currentTimeStamp.getTime() - StartTimeStamp.getTime());
		        	java.sql.Timestamp updatedStamp = new java.sql.Timestamp(totalTime);
		        }
		        
		        String updateQuery = "UPDATE fact SET status='finish', total_time=" + totalTime + ", start_time='" + currentTimeStamp + "' WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
		        
		        Statement updateStatement = connection.createStatement();
				updateStatement.executeUpdate(updateQuery);
	        }
			
			
		}
		
		status = "finish";
		
		return totalTime;
	}
	
  /**
   * It is inquiring that if task is finished or not.
   * 
   * @param data csv data .
   */
	
	public boolean isFinished(String data) throws SQLException{
		
		System.out.println("isfinished");
		
		String[] rows = data.split(";");
		String[] columns;
		String query = "", checkQuery = "";
		long totalTime = 0;
		
		for(int i = 0; i < rows.length; i++){
			
			System.out.println("isF : row : " + rows[i]);
			columns = rows[i].split(",");
			query = "SELECT * FROM fact WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
			
			System.out.println("isF : query : " + query);
			Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);
	        
	        if(resultSet.next())
	        	status = resultSet.getString("status");
	        
	        if(status.equals("finish"))
	        	return true;
	        else
	        	return false;
			
		}
		
		return false;
	}
	
	
	/**
     * Convert a millisecond duration to a string format
     * 
     * @param millis A duration to convert to a string form
     * @return A string of the form "X Days Y Hours Z Minutes A Seconds".
     */
    public String getDurationBreakdown(long millis)
    {
        if(millis < 0)
        {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" Days ");
        sb.append(hours);
        sb.append(" Hours ");
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        sb.append(" Seconds");

        return(sb.toString());
    }

	public long start(String data) throws SQLException{
		
		if(isFinished(data))
			return 0;
		
		return startTask(data);
	}
	
	public long pause(String data) throws SQLException{
		
		if(isFinished(data))
			return 0;
		
		return pauseTask(data);
	}
	
	public long finish(String data) throws SQLException{
		
		if(isFinished(data))
			return 0;
		
		return finishTask(data);
		
	}
	
	public static void main(String[] args) {
  
			System.out.println("Started working");
		  String data = "1,1,1,start;2,2,2,start;3,3,3,start";
		  String data1 = "1,1,1";
		  
		  DB db= new DB();
		  
		  
		  
		  
		  try {
			  db.addData(data);
			  long millis = db.refreshTime(data1);
			  System.out.println("Status : " + db.status + " time : " + db.getDurationBreakdown(millis));

			  db.start(data1);
			  db.finish(data1);
			  
		  } catch (SQLException e) {
			e.printStackTrace();
		  }
		 
	}
	
	

}

