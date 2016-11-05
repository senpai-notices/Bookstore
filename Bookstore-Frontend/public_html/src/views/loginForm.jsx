import React from 'react'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { Link, browserHistory } from 'react-router'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { FormInputText } from 'components'

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

							<FormInputText label="Username" addonBefore="glyph-user" value={user.username} required hideAsterisk 
										onChange={(e) => this.props.dispatch.setUser("username", e.target.value)} />

							<FormInputText label="Password" addonBefore="glyph-lock" value={user.password} type="password"
										onChange={(e) => this.props.dispatch.setUser("password", e.target.value)} required hideAsterisk/>

							<bs.FormGroup bsSize="lg">
								<bs.Button type="submit" bsStyle="success" bsSize="lg" block disabled={user.status === "loggingIn"}>
									{ (user.status === "loggingIn") && "Logging in" || "Login" }
								</bs.Button>
								<br/>
								<div className='text-center'>
									<Link to="/reset">Forgot password</Link>
									
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