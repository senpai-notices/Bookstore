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

	getAccount() {
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
			data: fileStream
		})
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
				offset: offset,
				limit: limit
			}
		})
	}
}

export default UserService