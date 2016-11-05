import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { FormInputText } from 'components'
import { browserHistory } from 'react-router'

class EnterNewPasswordView extends BaseView {
	constructor(props){
		super(props)

		this.setNewPassword = this.setNewPassword.bind(this)
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

	setNewPassword(event){
		event.preventDefault()

		if (this.state.newPassword !== this.state.confirmNewPassword){
			this.state.message = "Password can confirm new password do not match"
			this.state.messageStyle = "danger"
			this.setState(this.state)
			return
		}

		this.state.submitting = true
		this.state.message = ""
		this.setState(this.state)
		this.userService.resetPassword(this.props.location.query.token, this.props.location.query.username, this.state.newPassword)
			.then((resp) => {
				this.state.messageStyle = "success"
				this.state.message = "Your password has been reset, your can now login with the new password"
			})
			.fail((err) => {
				console.log(err)
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
			<bs.Alert bsStyle={this.state.messageStyle} className={'text-center'}
					onDismiss={() => this.setState({message: ""})}>
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

						<bs.Form horizontal onSubmit={this.setNewPassword}>
							<FormInputText label="New password" addonBefore="glyph-lock" type="password"
											name="newPassword" value={this.state.newPassword} 
											required hideAsterisk
											onChange={this.handleChange} />

							<FormInputText label="Confirm new password" addonBefore="glyph-lock" type="password" 
											name="confirmNewPassword" value={this.state.confirmNewPassword} 
											required hideAsterisk
											onChange={this.handleChange} />

							<bs.FormGroup bsSize="lg">
								<bs.Button type="submit" bsStyle="primary" bsSize="lg" block disabled={this.state.submitting}>
									{this.state.submitting && 'Updating password' || 'Update password'}
								</bs.Button>
							</bs.FormGroup>
						</bs.Form>

					</bs.Col>

				</bs.Panel>
			</bs.Col>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(EnterNewPasswordView)