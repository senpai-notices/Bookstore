export const SERVER_ADDRESS = 'http://localhost:8080/BookstoreService';

export var BASIC_AUTH = 'Basic';
export const setAuthHeader = (auth) => {
	BASIC_AUTH = auth
}