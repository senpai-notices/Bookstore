import { Navbar, FormGroup, FormControl, InputGroup, Button, Glyphicon } from 'react-bootstrap'
import React, { Component } from 'react'
import { bindActionCreators } from 'redux'
import { UserService } from 'services'
import { connect } from 'react-redux'

class Template extends Component{

	constructor(props){
		super(props)

		this.userService = new UserService()
		this.login = this.login.bind(this)
	}

	login(username, password) {
		this.userService.login(username, password).then((loggedUser) =>{
			this.props.setUser(loggedUser)
		})
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
		let userPanelContent = "";
		if (user == undefined || user.isLoggedIn == false){
			userPanelContent = (
				<FormGroup>
					<FormControl type="text" placeholder="Username"/>
					&nbsp;
					<FormControl type="password" placeholder="Password" />
					&nbsp;
					<Button type="submit" bsStyle="success" onClick={() => this.login("sondang2412", "qwerty")}>Login</Button>
					&nbsp;
					<Button bsStyle="primary">Register</Button>
				</FormGroup>
			)
		} else { // if user is logged in, display user control panel
		}

		const userPanel = (
			<Navbar.Form pullRight>
				{userPanelContent}
			</Navbar.Form>
		)

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

const mapDispatchToProps = (dispatch) => {
	return {
		setUser: (user) => {
			dispatch({
				type: "SET_USER",
				user
			})
		}
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(Template)