import { Provider } from 'react-redux'
import React from 'react'
import ReactDOM from 'react-dom'
import { Router, Route, browserHistory, IndexRoute } from 'react-router'
import * as views from 'views'
import { store } from 'config'

// setup router
const rootElement = (
	<Provider store={store}>
		<Router history={browserHistory}>
			<Route path="/" component={views.Template}>
				<Route path="/login" component= {views.LoginForm}/>
				<Route path="/register" component = {views.RegisterForm}/>
				<IndexRoute component={views.HomeView}/>
			</Route>
		</Router>
	</Provider>
);

// render app
var rootNode = document.getElementById("root")
ReactDOM.render(rootElement, rootNode)