import { createStore, combineReducers } from 'redux'
import { Provider } from 'react-redux'
import React from 'react'
import ReactDOM from 'react-dom'
import { Router, Route, browserHistory, IndexRoute } from 'react-router'
import Template from './template'
import HomeView from 'views/home'
import reqwest from 'reqwest'

const initialState = { 
	shoppingCart:
	[{ 
		title : "test title", 
		author: "test author"
	}, {
		title : "title 2",
		author: "author 2"
	}],
	user: 
	{ 
		username: "" ,
		password: "",
		isLoggedIn: false
	}
}

const user = (state = null, action) => {
	console.log("IN USER REDUCER")
	console.log(action)
	let newState = Object.assign({}, state)
	console.log(action)
	if (action.type == "SET_USER"){
		return action.user
	}
	return state
}

const shoppingCart = (state = null, action) => {
	return state
}

const mainReducer = combineReducers({
	user, shoppingCart
})

/*
const mainReducer = (state=initialState, action) => {
	if (action.type == "login"){
		reqwest({
			url: 'http://localhost:8080/BookstoreService/api/user/login',
			method: 'post',
			data: {username: "username1", password: "1234561"},
			header: {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		}).then((resp) => {
			console.log(resp)
			let newState = Object.assign({}, state)
			newState.user.isLoggedIn = true
			return newState
		})
	}
	return state
}*/

// redux store
const store = createStore(mainReducer, initialState)

// setup router
const rootElement = (
	<Provider store={store}>
		<Router history={browserHistory}>
			<Route path="/" component={Template}>
				<IndexRoute component={HomeView}/>
				<Route path="/Home" component={HomeView}/>
			</Route>
		</Router>
	</Provider>
);

// render app
var rootNode = document.getElementById("root")
ReactDOM.render(rootElement, rootNode)