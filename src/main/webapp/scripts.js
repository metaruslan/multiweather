function postToWall(wallMessage) {
	VK.api('wall.post', {
		message : wallMessage
	}, function(data) {
		if (data.response) {
			//alert('Сообщение отправлено на стену.');
		} else {
			if (data.error.error_code == '10007') {
				alert('Не хочешь? Ну и ладно:-Р');
			} else {
				alert('Произошла ошибка. Пожалуйста, сообщите о ней в группе приложения. Код ошибки: ' + data.error.error_code);				
			}
					
		}
	});
}

function postToFriendsWall(wallMessage) {
	var ownerId = document.getElementById('friendSelect').value;
	if (ownerId == '-1') {
		alert('Выбери друга!');
	} else {	
		VK.api('wall.post', {
			message : wallMessage,
			owner_id : ownerId
		}, function(data) {
			if (data.response) {
				//alert('Сообщение отправлено на стену.');
			} else {
				if (data.error.error_code == '10007') {
					alert('Не хочешь? Ну и ладно:-Р');
				} else {
					alert('Произошла ошибка. Пожалуйста, сообщите о ней в группе приложения. Код ошибки: ' + data.error.error_code);				
				}
						
			}
		});
	}	
}

function weatherInit(counterValue) {
	VK.init(function() {
		VK.api('setCounter', {
			counter : counterValue
		}, function(data) {
			if (data.response) {
				//alert('Сообщение отправлено на стену.');
			} else {
				//alert('Произошла ошибка установки погоды в счетчике в меню слева. Пожалуйста, сообщите о ней в группе приложения. Код ошибки: ' + data.error.error_code);			
			}
		});
	  });
	
	VK.api('friends.get', {
			fields : 'uid,first_name,last_name'		
	}, function(data) {
		if (data.response) {			
			var friendSelect = document.getElementById('friendSelect');
			var optionsString = friendSelect.innerHTML;
			var friends = data.response;
			friends.sort(compareNames);
			for (var i in friends) {
				  var friend = friends[i];
				  optionsString = optionsString + 
				  '<option value="' + friend.uid + '">' + friend.first_name + ' ' + friend.last_name + '</option>';				  
			}
			friendSelect.innerHTML = optionsString;
		} else {			
			alert('Произошла ошибка. Пожалуйста, сообщите о ней в группе приложения. Код ошибки: ' + data.error.error_code);					
		}
	});
}

function compareNames(a, b) {
	var firstNameA = a.first_name.toLowerCase();
	var firstNameB = b.first_name.toLowerCase();
	if (firstNameA < firstNameB) {
		return -1;
	}
	if (firstNameA > firstNameB) {
		return 1;
	}
	if (firstNameA == firstNameB) {
		var lastNameA = a.last_name.toLowerCase();
		var lastNameB = b.last_name.toLowerCase();
		if (lastNameA < lastNameB) {
			return -1;
		}
		if (lastNameA > lastNameB) {
			return 1;
		}
		return 0;
	}
	return 0;
}