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
}

export default UserService