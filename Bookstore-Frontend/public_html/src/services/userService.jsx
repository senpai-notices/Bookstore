import reqwest from 'reqwest'
import base64 from 'hi-base64'
import * as config from 'config'

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
			url: config.getServerAddress() + '/user/',
			method: 'post',
			headers: {
				'Content-Type' : 'application/json'
			},
			data: JSON.stringify(account),
			crossOrigin: true
		})
	}

	updateAddress(address){
		return reqwest({
			url: config.getServerAddress() + '/user/address',
			method: 'post',
			headers: {
				'Authorization': config.getAuthHeader(),
				'Content-Type': 'application/json'
			},
			data: JSON.stringify(address),
			crossOrigin: true,
			withCredentials: true
		})
	}

	sendActivateEmailAfterRegister(username, password) {
		let authHeader = 'Basic ' + base64.encode(username + ":" + password, true)
		return reqwest({
			url: config.getServerAddress() + '/email/activation',
			method: 'post',
			headers: {
				'Authorization': authHeader
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	sendActivateEmail() {
		return reqwest({
			url: config.getServerAddress() + '/email/activation/',
			method: 'post',
			headers: {
				'Authorization': config.getAuthHeader()
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	sendResetPasswordEmail(username, email) {
		return reqwest({
			url: config.getServerAddress() + "/email/reset/",
			method: 'post',
			crossOrigin: true,
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded'
			},
			data: {
				username: username,
				email: email
			}
		})
	}

	activateAccount(token, username){
		return reqwest({
			url: config.getServerAddress() + '/user/activate/',
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

	resetPassword(data){
		return reqwest({
			url: config.getServerAddress() + '/user/reset',
			method: 'post',
			crossOrigin: true,
			headers: {
				'Content-Type': 'application/json'
			},
			data: JSON.stringify(data)
		})
	}

	// upload id and Australia address evidence
	// documentType: id or residential
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
}

export default UserService