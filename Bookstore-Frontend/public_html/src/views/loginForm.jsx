import React from 'react'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { Link, browserHistory } from 'react-router'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { FormInputText, ErrorDisplay } from 'components'

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
				this.state.errors = []
				this.state.errors.push(["Cannot login, please try again"])
				this.setState(this.state)
			})
	}

	componentDidMount(){
		if (this.props.user.status === 'loggedIn'){
			browserHistory.replace('/')
		}
	}

	componentWillReceiveProps(nextProps){
		if (nextProps.user.status === "loggedIn") {
			browserHistory.replace('/')
		}
	}

	render(){
		const user = this.props.user
		return (
			<bs.Col xs={12} md={6} mdOffset={3}>
				<bs.Panel>

					<div className='text-center'>
						<h2>Login</h2>
						<hr/>
					</div>

					<bs.Col xsOffset={1} xs={10}>

						<ErrorDisplay errors={this.state.errors} onRemove={(index) => this.removeErrorMessage(index)}/>

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