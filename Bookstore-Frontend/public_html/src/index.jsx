import { Provider } from 'react-redux'
import React from 'react'
import ReactDOM from 'react-dom'
import { Router, Route, browserHistory, IndexRoute } from 'react-router'
import * as views from 'views'
import { store } from 'config'

// setup router

const checkToken = (nextState, replaceState) => {
	const queries = nextState.location.query
	if ('token' in queries && 'username' in queries){
		replaceState(`/account/activation?token=${queries.token}&username=${queries.username}`)
	}
}

const routerKey = Math.random()
const rootElement = (
	<Provider store={store}>
		<Router history={browserHistory} key={routerKey}>
			<Route path="/" component={views.Template}>
				<IndexRoute component={views.HomeView} onEnter={checkToken}/>
				<Route path="login" component={views.LoginForm}/>
				<Route path="register" component={views.RegisterForm}/>
				<Route path="account">
					<Route path="activation" component={views.AccountActivationView}/>
				</Route>
			</Route>
		</Router>
	</Provider>
);

if (module.hot){
	module.hot.accept()
}

// render app
var rootNode = document.getElementById("root")
ReactDOM.render(rootElement, rootNode)
