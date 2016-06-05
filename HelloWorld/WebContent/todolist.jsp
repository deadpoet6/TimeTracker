<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.net.*,java.io.*"%>    


<% 
out.println("Ikram : getting access token");

try{ 
Runtime r = Runtime.getRuntime(); 
String mycurl = "curl.exe -e http://www.ffiec.gov/geocode/default.htm -v www.ffiec.gov/geocode/GeocodeSearchmapping.htm"; 
Process p = r.exec(mycurl); 
InputStream is = p.getInputStream(); 
StringBuffer buf=new StringBuffer(); 
ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); 
int readByte = -1; 
while ((readByte = is.read()) > -1) { 
buf.append((char)readByte); 
} 
String mybuf = buf.toString(); 
out.println(mybuf); 
}catch(Exception e){ 
out.println(e.getMessage());
}

/*

String access_token = "b240fe68007a1c01b5f6da3e22f4d664cc9d57186fc39ce6a248174356cd";
      String client_id = "e56693ca9d2c248f275d";
      String client_secret = "e2fc71d1d58679cc27b4f46ae6b4f2399519e9fdc8c99253531e186bd17b";
      String code = "6b172602b36c9a669b83";
*/
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
      var code = 'b2043e5cdbd9ba7445e6';
      
	  $('document').ready(function(){
		  
		  /*
		  $.post('https://www.wunderlist.com/oauth/access_token', {'client_id': client_id, 'client_secret': client_secret, 'code': code}, function(data){
			  
			  alert(data);
			  
		  }).fail(function(response) {
			    alert('Ikram Error: ' + response.responseText);
		  });
	      */
	      
		  var link = 'https://www.wunderlist.com/oauth/access_token';
		  var req_data = {'client_id': client_id, 'client_secret': client_secret, 'code': code};
		  
		  $.ajax({
	            url: link,
	            type: 'post',
	            dataType: 'json',
	            data: JSON.stringify(req_data),
	            contentType: 'application/json',
	            headers: { 'X-Access-Token': access_token, 'X-Client-ID': client_id }
	           
	            
	       }).success(function (data) {
              	console.log("data : " + data.access_token);
           }).error(function (err) {
              	console.log("error : " + err);
           });
	      
		  //getAT();
	      getData();
	      //getParticularData('249470512');
	      //setData();
	      
	      
	  });
	 
	  
	  
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
		          url: 'https://a.wunderlist.com/api/v1/lists/' + list_id,
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
	 	

	  function getAT(){
	 		
	 		$.ajax({
		          url: 'https://www.wunderlist.com/oauth/access_token',
		          method: 'post',
		          dataType: 'json',
		          contentType: 'application/json',
		          headers: { 'X-Access-Token': access_token, 'X-Client-ID': client_id},
		          data: JSON.stringify({'client_id': client_id, 'client_secret': client_secret, 'code': code})
		          
		      }).success(function(data){
		         // $("#create_new_task_modal").modal('hide');
		          //swal("Task created!", "Your task was successfully created!", "success");
		          console.log(data);
		          
		          
		          
		          
		          
		      }).error(function(data){
		    	  
		    	  console.log(data);
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