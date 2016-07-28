
/**
* This is api for wunderlist application 
*
* @author  Zara Ali
* @version 1.0
* @since   2014-03-31 
*/

var wunderlistapi = function(access_token, client_id) {
	
	this.access_token = access_token;
	this.client_id = client_id;
	
	
	this.getAvatar = function(user_id) {
		$.ajax({
			url: 'https://a.wunderlist.com/api/v1/avatar',
			method: 'GET',
			dataType: 'json',
			contentType: 'application/json',
			//processData: false,
			headers: {
				'X-Client-ID': client_id
			},
			data: {
				'user_id': user_id
			}
		}).success(function(data) {
			console.log(data.getResponseHeader('Location'));
		}).error(function(data) {
			console.log(data.getAllResponseHeaders('Location'));
		});
	}
	
	this.getAllLists = function() {
		
		console.log("getalllist called");
		console.log("access_token : " + access_token);
		console.log("client_id : " + client_id);
		$.ajax({
			url: 'https://a.wunderlist.com/api/v1/lists',
			method: 'GET',
			contentType: 'application/json',
			headers: {
				'X-Access-Token': access_token,
				'X-Client-ID': client_id
			}
		}).success(function(data) {
			
			console.log(data);
			
			loadLists(data);
			
		}).error(function(err) {
			console.log(err);
			
			return err;
		});
	}
	
	this.getAllTasksOfList = function(list_id, completed) {
		$.ajax({
			url: 'https://a.wunderlist.com/api/v1/tasks',
			method: 'GET',
			contentType: 'application/json',
			headers: {
				'X-Access-Token': access_token,
				'X-Client-ID': client_id
			},
			data: {
				'list_id': list_id,
				'completed': completed
			}
		}).success(function(data) {
			
			console.log(data);
			
			
			loadTasks(data, list_id, completed);
			
		}).error(function(err) {
			console.log(err);
		});
	}
	
	this.addList = function(list) {
		console.log("adding list call");
		$.ajax({
			url: 'https://a.wunderlist.com/api/v1/lists',
			method: 'post',
			dataType: 'json',
			contentType: 'application/json',
			headers: {
				'X-Access-Token': access_token,
				'X-Client-ID': client_id
			},
			data: JSON.stringify({
				'title': list
			})
		}).success(function(data) {
			console.log(data);
			getAllListsWrapper();
			
		}).error(function(data) {
			console.log(data);
		});
	}
	
	this.getUserDetail = function() {
		$.ajax({
			url: 'https://a.wunderlist.com/api/v1/user',
			method: 'get',
			dataType: 'json',
			contentType: 'application/json',
			headers: {
				'X-Access-Token': access_token,
				'X-Client-ID': client_id
			}
		}).success(function(data) {
			console.log(data);
			setUserDetails(data);
			
		}).error(function(data) {
			console.log(data);
		});
	}
	
	this.finishTask = function(task_id) {
		$.ajax({
			url: 'https://a.wunderlist.com/api/v1/tasks/' + task_id,
			method: 'PATCH',
			dataType: 'json',
			contentType: 'application/json',
			processData: false,
			headers: {
				'X-Access-Token': access_token,
				'X-Client-ID': client_id
			},
			data: JSON.stringify({
				'revision': 1,
				'completed': true
			})
		}).success(function(data) {
			console.log(data);
			refreshList();
		}).error(function(data) {
			console.log(data);
		});
	}
	
	
	this.addTask = function(list_id, task) {
		$.ajax({
			url: 'https://a.wunderlist.com/api/v1/tasks',
			method: 'POST',
			dataType: 'json',
			contentType: 'application/json',
			processData: false,
			headers: {
				'X-Access-Token': access_token,
				'X-Client-ID': client_id
			},
			data: JSON.stringify({
				'list_id': parseInt(list_id),
				'title': task
			})
		}).success(function(data) {
			console.log(data);
			refreshList();
		}).error(function(data) {
			console.log(data);
		});
	}
	
}
