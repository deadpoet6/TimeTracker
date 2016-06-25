<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="org.json.*"%>
<%@ page import="data.DB" %>  

<%

	JSONObject json = new JSONObject();

	
    String action  = request.getParameter("action");
	String data = request.getParameter("data");
	//out.println("data in dbhandler : " + data );
	
	DB db = new DB();

	if(action.equals("refresh"))
	{
		
		
		long time = db.refreshTime(data);
		
		
		json.put("status", db.status);
		json.put("time", db.getDurationBreakdown(time));
		//String json = "{'status' : '" + db.status + "', 'time' : '" + db.getDurationBreakdown(time) + "'}";
			
	}
	
	if(action.equals("addData"))
	{
		db.addData(data);
	}
	
	if(action.equals("addFinishData"))
	{
		db.addFinishData(data);
	}
	
	if(action.equals("start"))
	{
		long time = db.start(data);
		//out.println("status : " + db.status + " Time : " + db.getDurationBreakdown(time));
		
		json.put("status", db.status);
		json.put("time", db.getDurationBreakdown(time));
		//String json = "{'status' : '" + db.status + "', 'time' : '" + db.getDurationBreakdown(time) + "'}";
			
	}
	
	if(action.equals("pause"))
	{
		long time = db.pause(data);
		//out.println("status : " + db.status + " Time : " + db.getDurationBreakdown(time));
		
		json.put("status", db.status);
		json.put("time", db.getDurationBreakdown(time));
		//String json = "{'status' : '" + db.status + "', 'time' : '" + db.getDurationBreakdown(time) + "'}";
			
	}
	
	if(action.equals("finish"))
	{
		long time = db.finish(data);
		//out.println("status : " + db.status + " Time : " + db.getDurationBreakdown(time));
		
		json.put("status", db.status);
		json.put("time", db.getDurationBreakdown(time));
		//String json = "{'status' : '" + db.status + "', 'time' : '" + db.getDurationBreakdown(time) + "'}";
			
	}
	
	response.getWriter().write(json.toString());
%>
    
