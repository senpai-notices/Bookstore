import React from 'react'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { Link, browserHistory } from 'react-router'
import { UserService } from 'services'

class LoginForm extends React.Component{

	constructor(props){
		super(props)

		this.state = this.props.location.state
		if (this.state == null){
			this.state = { }
		}
		this.userService = new UserService()
		this.login = this.login.bind(this)
		this.handleChange = this.handleChange.bind(this)
	}

	login(event){
		event.preventDefault()
		console.log(this.state)
		this.userService.login(this.state.username, this.state.password)
			.then((loggedInUser) => {
				this.props.setUser(loggedInUser)
			})
			.fail((err) => {
				this.props.setLoginError("Your login details is not correct, please try again")
			})
	}

	handleChange(event) {
		this.state[event.target.name] = event.target.value
	    this.setState(this.state)
	}

	render(){
		if (this.props.user !== null){
			browserHistory.push("/")
		}

		const validationMessage = this.props.validationMessage

		let errorDisplay = ""
		if (validationMessage.errors[0] !== undefined){
			errorDisplay = (
				<bs.Alert bsStyle="danger" onDismiss={this.props.removeValidationError}>
					{validationMessage.errors[0]}
				</bs.Alert>
			)
		}

		return (
			<bs.Col xs={12} md={6} mdOffset={3}>
				<bs.Panel>

					<div className='text-center'>
						<h2>Login</h2>
						<hr/>
					</div>

					<bs.Col xsOffset={1} xs={10}>

						{errorDisplay}

						<bs.Form horizontal onSubmit={this.login}>

							<bs.FormGroup bsSize="lg">
								<bs.ControlLabel>Username</bs.ControlLabel>
								<bs.InputGroup>
									<bs.InputGroup.Addon>
										<bs.Glyphicon glyph="user"/>
									</bs.InputGroup.Addon>
									<bs.FormControl type="text" name="username" placeholder="Username" onChange={this.handleChange} value={this.state.username}/>
								</bs.InputGroup>
							</bs.FormGroup>

							<bs.FormGroup bsSize="lg">
								<bs.ControlLabel>Password</bs.ControlLabel>
								<bs.InputGroup>
									<bs.InputGroup.Addon>
										<bs.Glyphicon glyph="lock"/>
									</bs.InputGroup.Addon>
									<bs.FormControl type="password" name="password" placeholder="Password" onChange={this.handleChange} value={this.state.password}/>
								</bs.InputGroup>
							</bs.FormGroup>

							<bs.FormGroup bsSize="lg">
								<bs.Button type="submit" bsStyle="success" bsSize="lg" block>
									Login
								</bs.Button>
								<br/>
								<div className='text-center'>
									<Link to="/register"><h4>Register</h4></Link>
								</div>
							</bs.FormGroup>

						</bs.Form>
					</bs.Col>

				</bs.Panel>
			</bs.Col>
		)
	}

	componentWillUnmount(){
		this.props.removeValidationError()
	}
}

const mapStateToProps = (state, ownProps) => {
	return {
		user: state.user,
		validationMessage: state.validationMessage
	}
}

const mapDispatchToProps = (dispatch) => ({
	removeValidationError: () => {
		dispatch({ 
			type: "REMOVE_VALIDATE_MESSAGE" 
		})
	},
	setLoginError: (error) => {
		dispatch({
			type: "ADD_ERROR_MESSAGE",
			error
		})
	},
	setUser: (user) => {
		dispatch({
			type: "SET_USER",
			user
		})
	},
})

export default connect(mapStateToProps, mapDispatchToProps)(LoginForm)