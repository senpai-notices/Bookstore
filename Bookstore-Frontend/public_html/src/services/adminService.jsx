import reqwest from 'reqwest'
import * as config from 'config'

class AdminService {
	rejectAccountVerification(username){
		return reqwest({
			url: config.getServerAddress() + `/document/reject/${username}/`,
			method: 'post',
			headers: {
				'Authorization': config.getAuthHeader()
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	sendRejectEmail(username, reason){
		return reqwest({
			url: config.getServerAddress() + '/email/reject/',
			method: 'post',
			headers: {
				'Authorization': config.getAuthHeader(),
				'Content-Type' : 'application/x-www-form-urlencoded'
			},
			data: {
				username: username,
				reason: reason
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	approveAccountVerification(username) {
		return reqwest({
			url: config.getServerAddress() + `/document/approve/${username}`,
			method: 'post',
			headers: {
				'Authorization': config.getAuthHeader()
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	sendApproveEmail(username){
		return reqwest({
			url: config.getServerAddress() + `/email/approve/${username}`,
			method: 'post',
			headers: {
				'Authorization': config.getAuthHeader()
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	banAccount(username) {
		return reqwest({
			url: config.getServerAddress() + `/user/ban/${username}`,
			method: 'post',
			headers: {
				'Authorization': config.getAuthHeader()
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	sendBanEmail(username) {
		return reqwest({
			url: config.getServerAddress() + `/email/ban/${username}`,
			method: 'post',
			headers: {
				'Authorization': config.getAuthHeader()
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	unbanAccount(username) {
		return reqwest({
			url: config.getServerAddress() + `/user/unban/${username}`,
			method: 'post',
			headers: {
				'Authorization': config.getAuthHeader()
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	sendUnbanEmail(username) {
		return reqwest({
			url: config.getServerAddress() + `/email/unban/${username}`,
			method: 'post',
			headers: {
				'Authorization': config.getAuthHeader()
			},
			withCredentials: true,
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

export default AdminService