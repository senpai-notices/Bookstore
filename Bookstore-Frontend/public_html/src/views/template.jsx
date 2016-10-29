import { Nav, Navbar, NavDropdown, MenuItem, Form, FormGroup, FormControl, InputGroup, Button, Glyphicon } from 'react-bootstrap'
import React, { Component } from 'react'
import { connect } from 'react-redux'
import { browserHistory, Link } from 'react-router'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'

class Template extends BaseView{

	constructor(props){
		super(props)

		this.login = this.login.bind(this)
		this.logout = this.logout.bind(this)
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
		this.state = { }
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

		const header = (
			<Navbar inverse> 
				{logo} {searchBar} {userPanel} 
			</Navbar>
		)

		return (
			<div>
				{header}
				{this.props.children}
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(Template)