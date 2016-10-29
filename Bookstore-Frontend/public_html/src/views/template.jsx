import { Nav, Navbar, NavDropdown, MenuItem, Form, FormGroup, FormControl, InputGroup, Button, Glyphicon, Badge, Collapse } from 'react-bootstrap'
import React, { Component } from 'react'
import { connect } from 'react-redux'
import { browserHistory, Link } from 'react-router'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import ShoppingCartView from 'views/shoppingCart'

class Template extends BaseView{

	constructor(props){
		super(props)

		this.login = this.login.bind(this)
		this.logout = this.logout.bind(this)
		this.toggleShoppingCart = this.toggleShoppingCart.bind(this)
	}

	login(event) {
		event.preventDefault()
		this.userService.login(this.state.username, this.state.password)
			.then((loggedInUser) => {
				this.props.dispatch.setUser(loggedInUser)
			})
			.fail((err) => {
				this.props.dispatch.addErrorMessage("Cannot login, please try again")
				browserHistory.push({ pathname: "/login", props: { username: "TEST" } })
			})
	}

	logout() {
		this.props.dispatch.removeUser()
		this.state.username = ""
		this.state.password = ""
		this.setState(this.state)
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
		if (user === null){
			userPanel = (
			<Navbar.Form pullRight>
				<Form onSubmit={this.login}>
				<FormGroup>
					<FormControl type="text" name="username" placeholder="Username" value={this.state.username} onChange={this.handleChange}/>
					&nbsp;
					<FormControl type="password" name="password" placeholder="Password" value={this.state.password} onChange={this.handleChange}/>
					&nbsp;
					<Button type="submit" bsStyle="success">Login</Button>
					&nbsp;
					<Link to="/register"><Button bsStyle="primary">Register</Button></Link>
				</FormGroup>
				</Form>
			</Navbar.Form>
			)	
		} else { // if user is logged in, display user control panel
			userPanel = (
				<Nav pullRight>
				<NavDropdown title={'Hello ' + user.fullname} id="user-panel">
					<MenuItem>Some action</MenuItem>
					<MenuItem>Some action 2</MenuItem>
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
			shoppingCartView = (<ShoppingCartView show={this.state.showShoppingCart} onHide={this.toggleShoppingCart}/>)
		}

		const header = (
			<Navbar inverse fixedTop> 
				{logo} {searchBar} {shoppingCartIcon} {userPanel}
			</Navbar>
		)

		return (
			<div>
				{header}
				<div className="container" style={{marginTop: "70px"}}>
					{this.props.children}
					
				</div>
				{shoppingCartView}
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(Template)