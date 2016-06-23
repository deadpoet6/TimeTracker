

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.net.*,java.io.*"%>  
<%@ page import="request.Curl" %>


<% 

out.println("Ikram : getting access token on server");

String access_token = "b240fe68007a1c01b5f6da3e22f4d664cc9d57186fc39ce6a248174356cd";
String client_id = "e56693ca9d2c248f275d";
String client_secret = "e2fc71d1d58679cc27b4f46ae6b4f2399519e9fdc8c99253531e186bd17b";
String code = request.getParameter("code");

code="4be18766d47d4d60bfe9";
Curl curl = new Curl();

out.println(curl.testWunderlistAuthentication(code, client_id, client_secret));



%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>jQuery UI Accordion - Default functionality</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script>
  $(function() {
	  var html;
	  var access_token = 'b240fe68007a1c01b5f6da3e22f4d664cc9d57186fc39ce6a248174356cd';
      var client_id = 'e56693ca9d2c248f275d';
      var client_secret = 'e2fc71d1d58679cc27b4f46ae6b4f2399519e9fdc8c99253531e186bd17b';
      var code = '<%= request.getParameter("code") %>';
      
	  $('document').ready(function(){
		  
		  
	      console.log("code" + code);
		  
		 
	      var task_id = 1594572028;
	      var list_id = 233628035;
	      var user_id = 18681572;
	      getData();
	      //getParticularData(list_id);
	      //setData();
	      //getUserDetail();
	      //updateTask(task_id);
	      //getAvatar(user_id);
	      
	      
	  });
	 
	  
	  function getAvatar(user_id){
		  
		  $.ajax({
	 			
	          url: 'https://a.wunderlist.com/api/v1/avatar',
	          method: 'GET',
	          dataType: 'json',
	          contentType: 'application/json',
	          //processData: false,
	          headers: { 'X-Client-ID': client_id},
	          data: {'user_id': user_id}	
	          
	      }).success(function(data){
	         // $("#create_new_task_modal").modal('hide');
	          //swal("Task created!", "Your task was successfully created!", "success");
	          console.log(data.getResponseHeader('Location'));
	          
	          
	      }).error(function(data){
	    	  
	    	  console.log(data.getAllResponseHeaders('Location'));
	      });
		  
	  }
	  
 	function getData(){
 		
 		$.ajax({
	          url: 'https://a.wunderlist.com/api/v1/lists',
	          method: 'GET',
	          contentType: 'application/json',
	          headers: { 'X-Access-Token': access_token, 'X-Client-ID': client_id }
	      }).success(function(data){
	         // $("#create_new_task_modal").modal('hide');
	          //swal("Task created!", "Your task was successfully created!", "success");
	          console.log(data);
	          
	          var list = "default";
	          var info = "default";
	          var initiatedBy = "default";
	          var startTime = "default";
	          var status = "default";
	          $('#accordion').empty();
	          $.each(data, function(key, val){
	        	
	        	  list = val.title;
	              info = "default";
	              initiatedBy = val.owner_type;
	              startTime = val.created_at;
	              status = "default";
	              
	              html = "<h3>" + list + "</h3><div><p>" + info + "</p><ul><li>Task initiated by : " + initiatedBy + " </li> <li>Task start time : " + startTime + "</li><li>Task status : " + status + "</li></ul> <div> <input type='submit' value='Finish'> <input type='submit' value='Delete'> </div> </div>";
	              $('#accordion').append(html);
	        	  
	          });
	          
	          
      	  
      	  $( "#accordion" ).accordion();
      	    $( "input[type=submit], a, button" )
      	    .button()
      	    .click(function( event ) {
      	      event.preventDefault();
      	      
      		     
      	    });
	          
	          
	          
	      }).error(function(err){
	    	  
	    	  console.log(err);
	    	  
	      });
	
 		
 	}
 	
 	
	  function getParticularData(list_id){
	 		
	 		$.ajax({
		          url: 'https://a.wunderlist.com/api/v1/tasks',
		          method: 'GET',
		          contentType: 'application/json',
		          headers: { 'X-Access-Token': access_token, 'X-Client-ID': client_id },
		          data: {'list_id': list_id}
		      }).success(function(data){
		         // $("#create_new_task_modal").modal('hide');
		          //swal("Task created!", "Your task was successfully created!", "success");
		          console.log(data);
		          
		          var list = "default";
		          var info = "default";
		          var initiatedBy = "default";
		          var startTime = "default";
		          var status = "default";
		          $('#accordion').empty();
		          $.each(data, function(key, val){
		        	
		        	  list = val.title;
		              info = "default";
		              initiatedBy = val.owner_type;
		              startTime = val.created_at;
		              status = "default";
		              
		              html = "<h3>" + list + "</h3><div><p>" + info + "</p><ul><li>Task initiated by : " + initiatedBy + " </li> <li>Task start time : " + startTime + "</li><li>Task status : " + status + "</li></ul> <div> <input type='submit' value='Finish'> <input type='submit' value='Delete'> </div> </div>";
		              $('#accordion').append(html);
		        	  
		          });
		          
		          
	      	  
	      	  $( "#accordion" ).accordion();
	      	    $( "input[type=submit], a, button" )
	      	    .button()
	      	    .click(function( event ) {
	      	      event.preventDefault();
	      	      
	      		     
	      	    });
		          
		          
		          
		      }).error(function(err){
		    	  
		    	  console.log(err);
		    	  
		      });
		
	 		
	 	}
	 	

	  
 	
function setData(){
 		
 		$.ajax({
 			
	          url: 'https://a.wunderlist.com/api/v1/lists',
	          method: 'post',
	          dataType: 'json',
	          contentType: 'application/json',
	          headers: { 'X-Access-Token': access_token, 'X-Client-ID': client_id},
	          data: JSON.stringify({'title': ''})
	          
	      }).success(function(data){
	         // $("#create_new_task_modal").modal('hide');
	          //swal("Task created!", "Your task was successfully created!", "success");
	          console.log(data);
	          
	          
	      }).error(function(data){
	    	  
	    	  console.log(data);
	      });
	
 		
 	}
 	
 	
 	function getParameter( name, url ) {
 		
 	      if (!url) url = location.href;
 	      name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
 	      var regexS = "[\\?&]"+name+"=([^&#]*)";
 	      var regex = new RegExp( regexS );
 	      var results = regex.exec( url );
 	      return results == null ? null : results[1];
 	      
 	    }
 	
 	function getUserDetail(){
 		
 		$.ajax({
 			
	          url: 'https://a.wunderlist.com/api/v1/user',
	          method: 'get',
	          dataType: 'json',
	          contentType: 'application/json',
	          headers: { 'X-Access-Token': access_token, 'X-Client-ID': client_id}
	          
	      }).success(function(data){
	         // $("#create_new_task_modal").modal('hide');
	          //swal("Task created!", "Your task was successfully created!", "success");
	          console.log(data);
	          
	          
	      }).error(function(data){
	    	  
	    	  console.log(data);
	      });
	
 		
 	}
 	
 	function updateTask(task_id){
 		
 		$.ajax({
 			
	          url: 'https://a.wunderlist.com/api/v1/tasks/' + task_id,
	          method: 'PATCH',
	          dataType: 'json',
	          contentType: 'application/json',
	          processData: false,
	          headers: { 'X-Access-Token': access_token, 'X-Client-ID': client_id},
	          data: JSON.stringify({'revision': 1, 'completed': true})
	          
	      }).success(function(data){
	         // $("#create_new_task_modal").modal('hide');
	          //swal("Task created!", "Your task was successfully created!", "success");
	          console.log(data);
	          
	          
	      }).error(function(data){
	    	  
	    	  console.log(data);
	      });
 		
 	}
    
   
  });
  </script>
</head>
<body>
 
<div id="accordion">
  <h3>Task 1</h3>
  <div>
    <p>
    	Information about task 1
   </p>
    <ul>
      <li>Task initiated by : Manager </li>
      <li>Task start time : 15:00, 21.02.2015</li>
      <li>Task status : active</li>
    </ul>
    <div>
    	<input type="submit" value="Finish">
    	<input type="submit" value="Delete">
    </div>
  </div>
  <h3>Task 2</h3>
  <div>
    <p>
    	Information about task 2
   </p>
    <ul>
      <li>Task initiated by : user </li>
      <li>Task start time : 15:00, 21.02.2015</li>
      <li>Task status : active</li>
    </ul>
    <div>
    	<input type="submit" value="Finish">
    	<input type="submit" value="Delete">
    </div>
  </div>
  <h3>Task 3</h3>
  <div>
    <p>
    	Information about task 3
   </p>
    <ul>
      <li>Task initiated by : Ikram </li>
      <li>Task start time : 15:00, 21.02.2015</li>
      <li>Task status : active</li>
    </ul>
    <div>
    	<input type="submit" value="Finish">
    	<input type="submit" value="Delete">
    </div>
  </div>
  <h3>Task 4</h3>
  <div>
    <p>
    	Information about task 4
   </p>
    <ul>
      <li>Task initiated by : user </li>
      <li>Task start time : 15:00, 21.02.2015</li>
      <li>Task status : active</li>
    </ul>
    <div>
    	<input type="submit" value="Finish">
    	<input type="submit" value="Delete">
    </div>
  </div>
</div>
 
 
</body>
</html>