import React from 'react'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { Link, browserHistory } from 'react-router'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'

class LoginForm extends BaseView{

	constructor(props){
		super(props)

		this.login = this.login.bind(this)
	}

	login(event){
		event.preventDefault()

		this.props.dispatch.loggingIn()
		this.userService.login(this.props.user.username, this.props.user.password)
			.then((loggedInUser) => {
				this.props.dispatch.login(loggedInUser)
			})
			.fail((err) => {
				this.props.dispatch.logout()
				this.props.dispatch.addErrorMessage("Cannot login, please try again")
			})
	}

	componentWillReceiveProps(nextProps){
		if (nextProps.user.status === "loggedIn") {
			browserHistory.replace({ pathname: "/"})
		}
	}

	render(){
		const validationMessage = this.props.validationMessage
		const user = this.props.user
		let errorDisplay = ""
		if (validationMessage.errors[0] !== undefined){
			errorDisplay = (
				<bs.Alert bsStyle="danger" onDismiss={this.props.dispatch.removeValidationMessage}>
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
									<bs.FormControl type="text" name="username" placeholder="Username" 
													onChange={this.props.dispatch.setUser} value={user.username}
													disabled={user.status === "loggingIn"}/>
								</bs.InputGroup>
							</bs.FormGroup>

							<bs.FormGroup bsSize="lg">
								<bs.ControlLabel>Password</bs.ControlLabel>
								<bs.InputGroup>
									<bs.InputGroup.Addon>
										<bs.Glyphicon glyph="lock"/>
									</bs.InputGroup.Addon>
									<bs.FormControl type="password" name="password" placeholder="Password" 
													onChange={this.props.dispatch.setUser} value={user.password}
													disabled={user.status === "loggingIn"}/>
								</bs.InputGroup>
							</bs.FormGroup>

							<bs.FormGroup bsSize="lg">
								<bs.Button type="submit" bsStyle="success" bsSize="lg" block disabled={user.status === "loggingIn"}>
									{ (user.status === "loggingIn") && "Logging in" || "Login" }
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
}

export default connect(mapStateToProps, mapDispatchToProps)(LoginForm)