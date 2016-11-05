import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { FormInputText } from 'components'
import { browserHistory } from 'react-router'

class ResetPasswordView extends BaseView {
	constructor(props){
		super(props)

		this.sendResetToken = this.sendResetToken.bind(this)
	}

	componentDidMount(){
		if (this.props.user.status === 'loggedIn'){
			browserHistory.replace('/')
		}
	}

	componentWillReceiveProps(nextProps){
		if (nextProps.user.status === 'loggedIn'){
			browserHistory.replace('/')
		}
	}

	sendResetToken(event){
		event.preventDefault()

		this.state.submitting = true
		this.state.message = ""
		this.setState(this.state)
		this.userService.sendResetPasswordEmail(this.state.username, this.state.email)
			.then((resp) => {
				this.state.messageStyle = "success"
				this.state.message = "An email has been sent to your email. Please follow the instruction to reset your password"
			})
			.fail((err) => {
				this.state.messageStyle = "danger"
				if (err.status === 500){
					this.state.message = (<h3>Server error</h3>)
				} else {
					this.state.message = (<h3>{err.response}</h3>)
				}
			})
			.always(() => {
				this.state.submitting = false
				this.setState(this.state)
			})
	}

	render(){
		const user = this.props.user

		const message = this.state.message && (
			<bs.Alert bsStyle={this.state.messageStyle} onDismiss={() => this.setState({message: ""})}>
				{this.state.message}
			</bs.Alert>
		)

		return(
			<bs.Col xs={12} md={6} mdOffset={3}>
				{message}

				<bs.Panel>

					<bs.Col xsOffset={1} xs={10}>

						<div className='text-center'>
							<h2>Reset password</h2>
							<hr/>
						</div>

						<bs.Form horizontal onSubmit={this.sendResetToken}>
							<FormInputText label="Username" addonBefore="glyph-user"
											name="username" value={this.state.username} 
											required hideAsterisk placeholder="Username"
											onChange={this.handleChange} />

							<FormInputText label="Email" addonBefore="strong-@" 
											name="email" value={this.state.email} 
											required hideAsterisk placeholder="Email"
											onChange={this.handleChange} />

							<bs.FormGroup bsSize="lg">
								<bs.Button type="submit" bsStyle="primary" bsSize="lg" block disabled={this.state.submitting}>
									{this.state.submitting && 'Sending reset email' || 'Reset password'}
								</bs.Button>
							</bs.FormGroup>
						</bs.Form>

					</bs.Col>

				</bs.Panel>
			</bs.Col>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(ResetPasswordView)