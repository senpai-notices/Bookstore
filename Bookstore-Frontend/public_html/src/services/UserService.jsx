import reqwest from 'reqwest'
import base64 from 'hi-base64'
import { SERVER_ADDRESS, BASIC_AUTH, setAuthHeader } from 'config'

class UserService {
	login(username, password) {
		setAuthHeader('Basic ' + base64.encode(username + ":" + password, true))

		return reqwest({
			url: SERVER_ADDRESS + '/api/user',
			method: 'get',
			headers: {
				'Content-Type' : 'application/x-www-form-urlencoded',
				'Authorization': BASIC_AUTH
			},
			withCredentials: true,
			crossOrigin: true
		})
	}

	logout() {
		setAuthHeader('Basic')
	}
}

export default UserService