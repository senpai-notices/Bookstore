import { createStore, combineReducers } from 'redux'
import { UserReducer as user } from 'reducers/user'
import { ValidationReducer as validationMessage } from 'reducers/validation'

const SERVER_ADDRESS = 'http://localhost:8080/BookstoreService';
export const getServerAddress = () => {
	return SERVER_ADDRESS
}

var BASIC_AUTH = 'Basic';
export const setAuthHeader = (auth) => {
	BASIC_AUTH = auth
}
export const getAuthHeader = () => {
	return BASIC_AUTH
}

// redux config
const initialState = { 
}

const shoppingCart = (state, action) => {
	return state === undefined ? null : state
}

const mainReducer = combineReducers({
	user, shoppingCart, validationMessage
})

// redux store
export const store = createStore(mainReducer, initialState)