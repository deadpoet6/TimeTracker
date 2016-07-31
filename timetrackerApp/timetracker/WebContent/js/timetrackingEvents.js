var wunapi; 
var user_id;
var list_id;


function init(access_token, client_id){
	wunapi = new wunderlistapi(access_token, client_id);
	
	wunapi.getUserDetail();
	getAllListsWrapper();
	
}


function setUserDetails(data){
	
	user_id = data.id;
}

$('document').ready(function(){
	
	
	$('#accordion').on('click', 'input.refresh', function(data){
		
		
		var task_id = $(this).parent().parent().attr("task-id");
		var re_data = user_id + "," + list_id + "," + task_id + ";";
			
		refreshTask(re_data, task_id);
	});
	
	$('#accordion').on('click', 'input.start', function(data){
		
		
		$(this).parent().find('.start').button("disable");
		$(this).parent().find('.pause').button("enable");
		$(this).parent().find('.finish').button("enable");
		var task_id = $(this).parent().parent().attr("task-id");
		var re_data = user_id + "," + list_id + "," + task_id + ";";
			
		startTask(re_data, task_id);
	});
	
	$('#accordion').on('click', 'input.pause', function(data){
		
		
		$(this).parent().find('.start').button("enable");
		$(this).parent().find('.pause').button("disable");
		$(this).parent().find('.finish').button("enable");
		var task_id = $(this).parent().parent().attr("task-id");
		var re_data = user_id + "," + list_id + "," + task_id + ";";
			
		pauseTask(re_data, task_id);
	});
	
	$('#accordion').on('click', 'input.finish', function(data){
		
		
		$(this).parent().find('.start').button("disable");
		$(this).parent().find('.pause').button("disable");
		$(this).parent().find('.finish').button("disable");
		var task_id = $(this).parent().parent().attr("task-id");
		var re_data = user_id + "," + list_id + "," + task_id + ";";
			
		finishTask(re_data, task_id);
	});
	
	
	$('#create-list').on('click', function(){
		
		console.log("clicked");
		var list = $('.create-list input').val();
		
		wunapi.addList(list);
		
		
	});
	
	$('#create-task').on('click', function(){
		
		console.log("clicked");
		var task = $('.create-task input').val();
		var list_id = $('.lists ul li.activeList').attr('list-id');
		
		wunapi.addTask(list_id, task);
		
		
	});
	
	$('#accordion').on('click', 'input.finish', function(){
		
		//console.log($(this));
		var task_id = $(this).parent().parent().attr('task-id');
		wunapi.finishTask(task_id);
	
		
		
	
		
	});
	
	$('input[type=radio][name=taskcheck]').change(function() {
		refreshList();
    });
	
	
});


function refreshList(){
	
	$('.lists ul li.activeList').trigger("click");
	
}

function getAllListsWrapper(){
	
	wunapi.getAllLists();
}

function loadTasks(data, list_id, completed){
	
	var list = "default";
	var info = "default";
	var initiatedBy = "default";
	var startTime = "default";
	var status = "default";
	var dbRequest = "";
	
	if($("#accordion").hasClass("ui-accordion"))
		$( "#accordion" ).accordion("destroy");
	$('#accordion').empty();
	
	console.log("making empty");
	if(data.toString() == "")
		return;
	
	$.each(data, function(key, val) {
		if(completed){
			
			var completed_at = val.completed_at.replace("T", " ");
			completed_at = completed_at.replace("T", " ").replace("Z", "");
			
			//console.log("completed at : " + completed_at);
			dbRequest += "" + user_id + "," + list_id + "," + val.id + "," + completed_at + ";";
		
		}else
			dbRequest += "" + user_id + "," + list_id + "," + val.id + ";";
			
		
		var task = val.title;
		var info = "Information about task";
		var created_at = val.created_at;
		
		var totalTime = "days :  hours:  ";
		var initiatedBy = val.owner_type;
		
		var status = "default";
		var html = "<h3>" + task + "</h3><div task-id='" + val.id + "'><p>" + info + "</p><ul><li>Task created at : " + created_at + " </li> <li class='time'>Task took time : " + totalTime + "</li><li class='status'>Task status : " + status + "</li></ul> <div> <input type='submit' class='start' value='Start'> <input type='submit' class='pause' value='Pause'><input type='submit' class='finish' value='Finish'><input type='submit' class='refresh' value='Refresh'> </div> </div>";
		$('#accordion').append(html);
	});
	
	setAccordian();
	
	if(completed)
		addFinishData(dbRequest);
	else
		addData(dbRequest);
	
}

function loadLists(data){
	
	$('.lists ul').empty();
	$.each(data, function(key, val) {
		
		var list = val.title;
		var id = val.id;
		var html = '<li list-id="' + id + '"><a  href="#">' + list + '</a></li>';
		$('.lists ul').append(html);
		
	});
	enableClick();
	$('.lists ul li').eq(0).trigger("click");
	
}


function enableClick(){
	
	$('.lists ul li').on('click', function(){
		
		var taskcheck = $("input[name=taskcheck]:checked").val();
		var completed;
		if(taskcheck == "1")
			completed = false;
		else
			completed = true;
		$('.activeList').removeClass('activeList');
		$(this).addClass('activeList');
		list_id = $(this).attr('list-id');
		wunapi.getAllTasksOfList(list_id, completed);
		
	});
}

function addData(data){
	
	console.log("adding data : " + data);
	$.get('dbHandler.jsp', {'action': 'addData', 'data' : data}, function(data){
		
		var json_res = $.parseJSON(data);
		console.log(json_res);
		//refreshList();
		
	});
	
}

function addFinishData(data){
	
	console.log("adding finish data : " + data);
	$.get('dbHandler.jsp', {'action': 'addFinishData', 'data' : data}, function(data){
		
		var json_res = $.parseJSON(data);
		console.log(json_res);
		//refreshList();
		
	});
	
}

function refreshTask(data, task_id){
	
	$.get('dbHandler.jsp', {'action': 'refresh', 'data' : data}, function(data){
		
		var json_res = $.parseJSON(data);
		console.log(json_res);
		
		$('div[task-id="' + task_id + '"] ul li.time').text("Task took time : " + json_res.time);
		$('div[task-id="' + task_id + '"] ul li.status').text("Task status : " + json_res.status);
		
		if(json_res.status == "start"){
			
			$('#accordion h3[aria-expanded="true"]').next().find('.start').button("disable");
			$('#accordion h3[aria-expanded="true"]').next().find('.pause').button("enable");
			$('#accordion h3[aria-expanded="true"]').next().find('.finish').button("enable");
			
		}
		
		if(json_res.status == "pause"){
			
			$('#accordion h3[aria-expanded="true"]').next().find('.start').button("enable");
			$('#accordion h3[aria-expanded="true"]').next().find('.pause').button("disable");
			$('#accordion h3[aria-expanded="true"]').next().find('.finish').button("enable");
			
		}
		
		if(json_res.status == "finish"){
			
			$('#accordion h3[aria-expanded="true"]').next().find('.start').button("disable");
			$('#accordion h3[aria-expanded="true"]').next().find('.pause').button("disable");
			$('#accordion h3[aria-expanded="true"]').next().find('.finish').button("disable");
			
		}
	
	});
}

function startTask(data, task_id){
	
	$.get('dbHandler.jsp', {'action': 'start', 'data' : data}, function(data){
		
		var json_res = $.parseJSON(data);
		console.log(json_res);
		
		$('div[task-id="' + task_id + '"] ul li.time').text("Task took time : " + json_res.time);
		$('div[task-id="' + task_id + '"] ul li.status').text("Task status : " + json_res.status);
		
		
	});
}

function pauseTask(data, task_id){
	
	$.get('dbHandler.jsp', {'action': 'pause', 'data' : data}, function(data){
		
		var json_res = $.parseJSON(data);
		console.log(json_res);
		
		$('div[task-id="' + task_id + '"] ul li.time').text("Task took time : " + json_res.time);
		$('div[task-id="' + task_id + '"] ul li.status').text("Task status : " + json_res.status);
		
	});
}

function finishTask(data, task_id){
	
	$.get('dbHandler.jsp', {'action': 'finish', 'data' : data}, function(data){
		
		var json_res = $.parseJSON(data);
		console.log(json_res);
		
		$('div[task-id="' + task_id + '"] ul li.time').text("Task took time : " + json_res.time);
		$('div[task-id="' + task_id + '"] ul li.status').text("Task status : " + json_res.status);
		
	});
}


function setAccordian(){
	
		$( "#accordion" ).accordion({
			heightStyle: "content",
			activate: function( event, ui ) {
				console.log($(this));
				
				var task_id = $('#accordion h3[aria-expanded="true"]').next().attr('task-id');
				var re_data = user_id + "," + list_id + "," + task_id + ";";
				refreshTask(re_data, task_id);
			}
		});
		
		var task_id = $('#accordion h3[aria-expanded="true"]').next().attr('task-id');
		var re_data = user_id + "," + list_id + "," + task_id + ";";
		refreshTask(re_data, task_id);
		
	    $( "input[type=submit], button" )
	    .button()
	    .click(function( event ) {
	      event.preventDefault();
	      
		     
	    });
	
}


//////////////////// databse functions ///////////////////////////



