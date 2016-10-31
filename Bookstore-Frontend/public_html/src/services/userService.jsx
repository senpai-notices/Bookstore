import reqwest from 'reqwest'
import base64 from 'hi-base64'
import * as config from 'config'
import {store} from 'index'

// service class to communicate with server's User resource
class UserService {
	login(username, password) {
		config.setAuthHeader('Basic ' + base64.encode(username + ":" + password, true))
		return reqwest({
			url: config.getServerAddress() + '/user',
			method: 'get',
			headers: {
				'Authorization': config.getAuthHeader()
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	fetchAccount() {
		return reqwest({
			url: config.getServerAddress() + '/user',
			method: 'get',
			headers: {
				'Authorization': config.getAuthHeader()
			},
			withCredentials: true,
			crossOrigin: true
		})	
	}

	logout() {
		config.setAuthHeader('Basic')
	}

	createAccount(account) {
		return reqwest({
			url: config.getServerAddress() + '/user',
			method: 'post',
			headers: {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data: {
				username: account.username,
				password: account.password,
				email: account.email,
				fullname: account.fullname
			},
			crossOrigin: true
		})
	}

	sendActivateEmailAfterRegister(username, password) {
		let authHeader = 'Basic ' + base64.encode(username + ":" + password, true)
		return reqwest({
			url: config.getServerAddress() + '/activation',
			method: 'get',
			headers: {
				'Authorization': authHeader
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	sendActivateEmail() {
		return reqwest({
			url: config.getServerAddress() + '/activation',
			method: 'get',
			headers: {
				'Authorization': config.getAuthHeader()
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	activateAccount(token, username){
		return reqwest({
			url: config.getServerAddress() + '/activation',
			method: 'post',
			headers: {
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data: {
				token: token,
				username: username
			},
			crossOrigin: true
		})
	}

	// upload id and Australia address evidence
	// documentType: id or residental
	// contentType: application/pdf, img/png, or img/jpeg
	uploadDocument(documentType, contentType, fileStream){
		return reqwest({
			url: config.getServerAddress() + '/document/' + documentType,
			method: 'post',
			headers: {
				'Content-Type': contentType,
				'Authorization': config.getAuthHeader()
			},
			data: fileStream,
			crossOrigin: true
		})
	}

	getDocument(documentType, username){
		return new Promise(function (resolve, reject) {
			var xhr = new XMLHttpRequest();
		    xhr.open("GET", config.getServerAddress() + `/document/${username}/${documentType}`)
		    xhr.setRequestHeader("Authorization", config.getAuthHeader()) 
		    xhr.responseType = "blob"
		    xhr.onload = function (e) {
		    	if (this.status >= 200 && this.status < 300) {
		    		console.log(e)
		    		resolve(xhr.response)
		    	} else {
		    		reject({
		    			status: this.status,
		    			statusText: xhr.statusText
		    		});
		    	}
		    };
		    xhr.onerror = function () {
		    	reject({
		    		status: this.status,
		    		statusText: xhr.statusText
		    	});
		    };

		    
		    xhr.send()
		});
	}

	findUsers(roles, username, fullname, email, offset, limit){
		return reqwest({
			url: config.getServerAddress() + '/user/list/',
			method: 'get',
			headers: {
				'Authorization': config.getAuthHeader()
			},
			data: {
				roles: roles,
				username: username,
				fullname: fullname,
				email: email,
				offset: offset,
				limit: limit
			},
			withCredentials: true,
			crossOrigin: true
		})
	}
}

export default UserService