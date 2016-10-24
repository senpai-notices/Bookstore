import reqwest from 'reqwest'
import base64 from 'hi-base64'
import * as config from 'config'
import {store} from 'index'

class UserService {
	login(username, password) {
		config.setAuthHeader('Basic ' + base64.encode(username + ":" + password, true))
		return reqwest({
			url: config.getServerAddress() + '/api/user',
			method: 'get',
			headers: {
				'Content-Type' : 'application/x-www-form-urlencoded',
				'Authorization': config.getAuthHeader()
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	logout() {
		config.setAuthHeader('Basic')
	}
}

export default UserService