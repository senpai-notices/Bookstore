import { Provider } from 'react-redux'
import React from 'react'
import ReactDOM from 'react-dom'
import { Router, Route, browserHistory, IndexRoute } from 'react-router'
import * as views from 'views'
import { store } from 'config'

// setup router

const checkToken = (nextState, replaceState) => {
	const queries = nextState.location.query
	if ('token' in queries && 'username' in queries && 'action' in queries){
		if (queries.action === 'activation'){
			replaceState(`/account/activation?token=${queries.token}&username=${queries.username}`)
		} else if (queries.action === 'reset'){
			replaceState(`/account/reset?token=${queries.token}&username=${queries.username}`)
		}
	}
}


const checkAdmin = (nextState, replaceState) => {
	const user = store.getState().user
	if (user.role !== "ADMIN"){
		replaceState("/")
	}
}

const checkUser = (nextState, replaceState) => {
	const user = store.getState().user
	if (user.role === undefined || user.role.indexOf("USER") === -1){
		replaceState("/")
	}
}

const redirectToHome = () => {
	browserHistory.replace("/")
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
					<Route path="activation" component={views.ActivateAccountView}/>
					<Route path="reset" component={views.EnterNewPasswordView}/>
				</Route>
				<Route path="checkout" component={views.CheckoutView}/>
				<Route path="user" onEnter={checkUser} onLogout={redirectToHome}>
					<Route path="dashboard" component={views.UserDashboardView}/>
				</Route>
				<Route path="admin" onEnter={checkAdmin} onLogout={redirectToHome}>
					<Route path="users" component={views.ManageUsersView}/>
					<Route path="books" component={views.AdminDashboardView}/>
				</Route>
				<Route path="reset" component={views.ResetPasswordView}/>
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
