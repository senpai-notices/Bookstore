import { createStore, combineReducers } from 'redux'
import { Provider } from 'react-redux'
import React from 'react'
import ReactDOM from 'react-dom'
import { Router, Route, browserHistory, IndexRoute } from 'react-router'
import Template from './template'
import HomeView from 'views/home'
import reqwest from 'reqwest'
import { UserReducer as user } from 'reducers/user'

const initialState = { 
	shoppingCart:
	[{ 
		title : "test title", 
		author: "test author"
	}, {
		title : "title 2",
		author: "author 2"
	}],
	user: null
}

const shoppingCart = (state, action) => {
	return state === undefined ? null : state
}

const mainReducer = combineReducers({
	user, shoppingCart
})

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