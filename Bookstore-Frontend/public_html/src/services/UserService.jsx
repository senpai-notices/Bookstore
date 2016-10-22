import reqwest from 'reqwest'

class UserService {
	login(username, password) {
		return reqwest({
			url: 'http://localhost:8080/BookstoreService/api/user/login',
			method: 'post',
			data: {username: username, password: password},
			header: {
				'Content-Type' : 'application/x-www-form-urlencoded',
			}
		})
	}
}

export default UserService