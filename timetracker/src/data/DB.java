package data;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DB {

	String dbUrl = "jdbc:mysql://localhost/testdb";
	String dbClass = "com.mysql.jdbc.Driver";
	//String query = "Select distinct(table_name) from INFORMATION_SCHEMA.TABLES";
	String username = "root";
	String password = "ikramulhaq!123";
	Connection connection;
	
	public String status = "";
 
	public DB(){
		
		System.out.println("connecting to db");
		 try {
			Class.forName(dbClass);
			this.connection = DriverManager.getConnection(dbUrl, username, password);
			
			System.out.println("Connection is : " + connection);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	}
	
	public boolean isRowExist(String query){
		System.out.println("is existing fun");
		try {

				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
				
				System.out.println("result set : " + resultSet.getFetchSize());
				
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
	
	
	
	
	public void executeInsertQuery(String query){
		
			try {

					Statement statement = connection.createStatement();
					//ResultSet resultSet = statement.executeQuery(query);
					int resultSet = statement.executeUpdate(query);
					
					System.out.println("Afected rows :  " + resultSet);
					
			}catch (SQLException e) {
				e.printStackTrace();
			}finally{
				
				
			}
	}
	
	public void addData(String data){
		System.out.println("add fun");
		
		String[] rows = data.split(";");
		String[] columns;
		String query = "", checkQuery = "";
		
		for(int i = 0; i < rows.length; i++){
			
			columns = rows[i].split(",");
			checkQuery = "SELECT * FROM fact WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
			System.out.println("Check Query : " + checkQuery);
			
			if(isRowExist(checkQuery))
				continue;
			
			System.out.println("new data is here");
			Date date = new Date();
	        java.sql.Timestamp currentTimeStamp = new java.sql.Timestamp(date.getTime());
	        
			query = "INSERT INTO fact VALUES('" + columns[0] + "', '" + columns[1] + "', '" + columns[2] + "', '" + currentTimeStamp + "', 'create', 0)";
			
			System.out.println("Add Query : " + query);
			executeInsertQuery(query);
		}
		
		
		
	}
	
	public void addFinishData(String data) throws SQLException{
		System.out.println("add finish fun");
		
		String[] rows = data.split(";");
		String[] columns;
		String query = "", checkQuery = "";
		
		for(int i = 0; i < rows.length; i++){
			
			columns = rows[i].split(",");
			checkQuery = "SELECT * FROM fact WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
			System.out.println("Check Query : " + checkQuery);
			
			if(isFinished(rows[i]))
				continue;
			if(isRowExist(checkQuery)){
				finishTask(rows[i]);
				
				continue;
			}
			
			System.out.println("new data is here");
			Date date = new Date();
	        java.sql.Timestamp currentTimeStamp = new java.sql.Timestamp(date.getTime());
	        
			query = "INSERT INTO fact VALUES('" + columns[0] + "', '" + columns[1] + "', '" + columns[2] + "', '" + currentTimeStamp + "', 'finish', 0)";
			
			System.out.println("Add Query : " + query);
			executeInsertQuery(query);
		}
		
		
		
	}
	
	
	public long refreshTime(String data) throws SQLException{
		
		 System.out.println("refrshing");
		
		String[] rows = data.split(";");
		String[] columns;
		String query = "", checkQuery = "";
		long totalTime = 0;
		
		for(int i = 0; i < rows.length; i++){
			
			columns = rows[i].split(",");
			query = "SELECT * FROM fact WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
			
			 System.out.println("refrsh Query : " +  query);
			 
			Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);
	        
	        while (resultSet.next()) {
		        totalTime = resultSet.getInt("total_time");
		        java.sql.Timestamp StartTimeStamp = resultSet.getTimestamp("start_time");
		        status = resultSet.getString("status");
		        
		        Date date = new Date();
		        java.sql.Timestamp currentTimeStamp = new java.sql.Timestamp(date.getTime());
		        
		        System.out.println("before updating refresh");
		        String updateQuery;
		        if(status.equals("start")){
			        totalTime += (currentTimeStamp.getTime() - StartTimeStamp.getTime());
			        java.sql.Timestamp updatedStamp = new java.sql.Timestamp(totalTime);
			        updateQuery = "UPDATE fact SET total_time=" + totalTime + ", start_time='" + currentTimeStamp + "' WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
		        }
		        else
		        	updateQuery = "UPDATE fact SET total_time=" + totalTime + ", start_time='" + currentTimeStamp + "' WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
		        
		        System.out.println("refrsh update : " + updateQuery);
		        
		        Statement updateStatement = connection.createStatement();
				//ResultSet resultSet = statement.executeQuery(query);
				updateStatement.executeUpdate(updateQuery);
	        }
			
			
		}
		
		return totalTime;
	}
	
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
				//ResultSet resultSet = statement.executeQuery(query);
				updateStatement.executeUpdate(updateQuery);
	        }
			
			
		}
		status = "start";
		
		return totalTime;
	}

	
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
		        
		        if(!status.equals("pause") && !status.equals("finish")){
		        	totalTime += (currentTimeStamp.getTime() - StartTimeStamp.getTime());
		        	java.sql.Timestamp updatedStamp = new java.sql.Timestamp(totalTime);
		        }
		        String updateQuery = "UPDATE fact SET total_time=" + totalTime + ", start_time='" + currentTimeStamp + "', status='pause' WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
		        
		        Statement updateStatement = connection.createStatement();
				//ResultSet resultSet = statement.executeQuery(query);
				updateStatement.executeUpdate(updateQuery);
	        }
			
			
		}
		status = "pause";
		
		return totalTime;
	}


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
		        
		        if(status.equals("pause")){
		        	totalTime += (currentTimeStamp.getTime() - StartTimeStamp.getTime());
		        	java.sql.Timestamp updatedStamp = new java.sql.Timestamp(totalTime);
		        }
		        
		        String updateQuery = "UPDATE fact SET status='finish', total_time=" + totalTime + ", start_time='" + currentTimeStamp + "' WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
		        
		        Statement updateStatement = connection.createStatement();
				//ResultSet resultSet = statement.executeQuery(query);
				updateStatement.executeUpdate(updateQuery);
	        }
			
			
		}
		
		status = "finish";
		
		return totalTime;
	}
	
	
	public boolean isFinished(String data) throws SQLException{
		
		String[] rows = data.split(";");
		String[] columns;
		String query = "", checkQuery = "";
		long totalTime = 0;
		
		for(int i = 0; i < rows.length; i++){
			
			columns = rows[i].split(",");
			query = "SELECT * FROM fact WHERE user=" + columns[0] + " AND list_id=" + columns[1] + " AND task_id=" + columns[2] + "";
			
			Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery(query);
	        
	        resultSet.next();
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
			 //millis =  db.pause(data1);
			  //System.out.println("Status : " + db.status + " time : " + getDurationBreakdown(millis));
		  
			  db.start(data1);
			  db.finish(data1);
			  
		  } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		 
	}
	
	

}