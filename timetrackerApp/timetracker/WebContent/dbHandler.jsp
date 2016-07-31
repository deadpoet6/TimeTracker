<%-- This page is handling requests for database and it will return response in JSON format--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="org.json.*"%>
<%@ page import="data.DB" %>  

<%

	JSONObject json = new JSONObject();

	
    String action  = request.getParameter("action");
	String data = request.getParameter("data");
	
	/**
	* creating databse connection by creating object
	*/
	
	DB db = new DB();

	 
	if(action.equals("refresh"))
	{
		
		
		long time = db.refreshTime(data);
		
		
		json.put("status", db.status);
		json.put("time", db.getDurationBreakdown(time));
			
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
		
		json.put("status", db.status);
		json.put("time", db.getDurationBreakdown(time));
			
	}
	
	if(action.equals("pause"))
	{
		long time = db.pause(data);
		
		json.put("status", db.status);
		json.put("time", db.getDurationBreakdown(time));
		
	}
	
	if(action.equals("finish"))
	{
		long time = db.finish(data);
		
		json.put("status", db.status);
		json.put("time", db.getDurationBreakdown(time));
			
	}
	
	response.getWriter().write(json.toString());
%>
    
