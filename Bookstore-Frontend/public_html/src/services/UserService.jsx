import reqwest from 'reqwest'
import base64 from 'hi-base64'
import { SERVER_ADDRESS, BASIC_AUTH } from 'config'

class UserService {
	login(username, password) {
		BASIC_AUTH = 'Basic ' + base64.encode(username + ":" + password, true)

		return reqwest({
			url: SERVER_ADDRESS + '/api/user/login',
			method: 'post',
			data: {username: username, password: password},
			headers: {
				'Content-Type' : 'application/x-www-form-urlencoded',
				'Authorization': BASIC_AUTH
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	logout() {
		BASIC_AUTH = 'Basic'
	}
}

export default UserService