import { Nav, Navbar, NavDropdown, MenuItem, 
		Form, FormGroup, FormControl, InputGroup, 
		Button, Glyphicon, Badge, Collapse, Alert } from 'react-bootstrap'
import React, { Component } from 'react'
import { connect } from 'react-redux'
import { browserHistory, Link } from 'react-router'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import ShoppingCartView from 'views/shoppingCart'
import { LinkContainer } from 'react-router-bootstrap'

class Template extends BaseView{

	constructor(props){
		super(props)

		this.login = this.login.bind(this)
		this.logout = this.logout.bind(this)
		this.toggleShoppingCart = this.toggleShoppingCart.bind(this)
		this.sendActivateEmail = this.sendActivateEmail.bind(this)
	}

	login(event) {
		event.preventDefault()
		this.props.dispatch.loggingIn()
		this.userService.login(this.props.user.username, this.props.user.password)
			.then((loggedInUser) => {
				this.props.dispatch.login(loggedInUser)
			})
			.fail((err) => {
				this.props.dispatch.logout()
				browserHistory.push({ pathname: "/login", state: {errors: ["Cannot login, please try again"]}})
			})
	}

	logout() {
		this.props.dispatch.logout()
		this.props.route.childRoutes.forEach((route) =>{
			if (route.onLogout){
				route.onLogout()
			}
		})
	}

	sendActivateEmail(event){
		if (event){
			event.preventDefault()
		}
		this.state.requestingToken = true;
		this.setState(this.state)
		this.userService.sendActivateEmail()
						.always((resp) => {
							this.state.requestingToken = false;
							this.setState(this.state)
						})
	}

	toggleShoppingCart(event) {
		if (event){
			event.preventDefault()
		}

		this.state.showShoppingCart = !this.state.showShoppingCart;
		this.setState(this.state)
	}

	render(){
		const {user, shoppingCart} = this.props
		const logo = (
			<Navbar.Header>
				<Navbar.Brand>
					<Link to="/">Bookstore</Link>
				</Navbar.Brand>
			</Navbar.Header>
		)

		const searchBar = (
			<Navbar.Form pullLeft>
				<FormGroup>
					<InputGroup>
						<FormControl type="text" placeholder="Search" />
						<InputGroup.Button>
							<Button type="submit">&nbsp;<Glyphicon glyph="search"/></Button>
						</InputGroup.Button>
					</InputGroup>
		        </FormGroup>
			</Navbar.Form>
		)
		
		// if user is not logged in, display the quick login form
		let userPanel = "";
		let notification = ""
		if (user.status !== "loggedIn"){
			userPanel = (
			<Navbar.Form pullRight>
				<Form onSubmit={this.login}>
				<FormGroup>
					<FormControl type="text" name="username" placeholder="Username" required
								value={user.username} onChange={(e) => this.props.dispatch.setUser("username", e.target.value)}
								disabled={user.status === "loggingIn"}/>
					&nbsp;
					<FormControl type="password" name="password" placeholder="Password" required
								value={user.password} onChange={(e) => this.props.dispatch.setUser("password", e.target.value)}
								disabled={user.status === "loggingIn"}/>
					&nbsp;
					<Button type="submit" bsStyle="success" disabled={user.status === "loggingIn"}>
						{ (user.status === "loggingIn") && "Logging in" || "Login" }
					</Button>
					&nbsp;
					<Link to="/register"><Button bsStyle="primary" disabled={user.status === "loggingIn"}>Register</Button></Link>
				</FormGroup>
				</Form>
			</Navbar.Form>
			)	
		} else { // if user is logged in, display user control panel

			const adminActions = []
			adminActions.push(<LinkContainer key={1} to="/admin/users"><MenuItem>Manage Users</MenuItem></LinkContainer>)
			adminActions.push(<LinkContainer key={2} to="/admin/books"><MenuItem>Manage Books</MenuItem></LinkContainer>)
			const userActions = []
			userActions.push(<LinkContainer key={3} to="/user/dashboard"><MenuItem>Dashboard</MenuItem></LinkContainer>)

			let actions = []
			if (user.role === "ADMIN") {
				actions = adminActions;
			} else if (user.role.indexOf("USER") !== -1) {
				actions = userActions
			}  else if (user.role === "INACTIVATED") {

				let requestTokenButton = ""
				if (this.state.requestingToken){
					requestTokenButton = (
						<Button bsStyle="primary" disabled>
							Please wait a few seconds before requesting another email
						</Button>
					)
				} else {
					requestTokenButton = (
						<Button bsStyle="primary" onClick={this.sendActivateEmail}>
							Resend activation email
						</Button>
					)
				}

				notification = (
					<div>
						<Alert bsStyle="warning">Your account is not activated yet, please check your email at <a>{user.email}</a> to 
						activate your account, or request another verification email</Alert>
						{requestTokenButton}
						<hr/>
					</div>
				)
			}

			userPanel = (
				<Nav pullRight>
				<NavDropdown title={'Hello ' + user.fullname} id="user-panel">
					{actions}
					<MenuItem divider/>
					<MenuItem onClick={this.logout}>Logout</MenuItem>
				</NavDropdown>
				</Nav>
			)
		}

		const shoppingCartIcon = (
			<Nav pullRight>
				<a href="#" onClick={this.toggleShoppingCart}>
				<Glyphicon glyph="shopping-cart" style={ {color: "#FFF", fontSize: "50px"} }/>
				<Badge pullRight>{shoppingCart.items.length}</Badge>
				</a>
			</Nav>
		)

		let shoppingCartView = "";
		if (this.state.showShoppingCart){
			shoppingCartView = (
				<ShoppingCartView 
					onCheckout={() => {this.toggleShoppingCart() 
										browserHistory.push("/checkout")} 
								} 
					onHide={this.toggleShoppingCart}/>
				)
		}

		const header = (
			<Navbar inverse fixedTop> 
				{logo} {searchBar} {shoppingCartIcon} {userPanel}
			</Navbar>
		)

		return (
			<div>
				{shoppingCartView}
				{header}
				<div className="container" style={{marginTop: "70px"}}>
					{notification}
					{this.props.children}
					
				</div>
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(Template)