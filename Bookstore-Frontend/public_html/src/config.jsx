import { createStore, combineReducers } from 'redux'
import { UserReducer as user } from 'reducers/user'
import { ValidationReducer as validationMessage } from 'reducers/validation'
import { ShoppingCartReducer as shoppingCart } from 'reducers/shoppingCart'

const SERVER_ADDRESS = 'http://localhost:8080/BookstoreService/api';
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

const mainReducer = combineReducers({
	user, shoppingCart, validationMessage
})

// redux store
export const store = createStore(mainReducer, initialState)