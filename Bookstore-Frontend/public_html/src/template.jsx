import { Nav, Navbar, NavDropdown, MenuItem, Form, FormGroup, FormControl, InputGroup, Button, Glyphicon } from 'react-bootstrap'
import React, { Component } from 'react'
import { bindActionCreators } from 'redux'
import { UserService } from 'services'
import { connect } from 'react-redux'

class Template extends Component{

	constructor(props){
		super(props)

		this.state = {}
		this.userService = new UserService()
		this.login = this.login.bind(this)
		this.logout = this.logout.bind(this)
		this.handleChange = this.handleChange.bind(this)

		console.log(this.store)
	}

	handleChange(event) {
		this.state[event.target.name] = event.target.value
	    this.setState(this.state);
	}


	login(event) {
		event.preventDefault()
		this.userService.login(this.state.username, this.state.password).then((resp) =>{
			this.props.setUser(resp)
		})
	}

	logout() {
		this.props.removeUser()
		this.setState({password: ""})
	}

	render(){
		let user = this.props.user
		let shoppingCart = this.props.shoppingCart

		const logo = (
			<Navbar.Header>
				<Navbar.Brand>
					<a href="#">Bookstore</a>
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
					<Button bsStyle="primary">Register</Button>
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

const mapStateToProps = (state, ownProps) => {
	return {
		user: state.user,
		shoppingCart: state.shoppingCart
	}
}

const mapDispatchToProps = (dispatch) => ({
	setUser: (user) => {
		dispatch({
			type: "SET_USER",
			user
		})
	},
	removeUser: () => {
		dispatch({
			type: "REMOVE_USER"
		})
	}
})

export default connect(mapStateToProps, mapDispatchToProps)(Template)