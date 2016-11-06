import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { FormInputText, ErrorDisplay } from 'components'
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

		this.state.submitting = true
		this.state.message = ""
		this.setState(this.state)

		const data = {
			resetToken: this.props.location.query.token,
			username: this.props.location.query.username,
			newPassword: this.state.newPassword,
			confirmNewPassword: this.state.confirmNewPassword
		}

		this.userService.resetPassword(data)
			.then((resp) => {
				this.state.resetSuccess = true
				this.state.errors = []
			})
			.fail((err) => {
				console.log(err)
				if (err.status === 400){
					const validateResult = JSON.parse(err.response)
					this.state.formErrors = validateResult.formErrors
					this.state.errors = validateResult.errors
				} else {
					this.state.errors = []
					this.state.errors.push("Server error")
				}
			})
			.always(() => {
				this.state.submitting = false
				this.setState(this.state)
			})
	}

	render(){
		const user = this.props.user
		const formErrors = this.state.formErrors

		let message = ""
		if (this.state.resetSuccess){
			message = (
				<bs.Alert bsStyle="success" className={'text-center'} onDismiss={() => this.setState(resetSuccess: false)}>
					Your password has been reset, your can now login with the new password
				</bs.Alert>
			)
		} else {
			message = (<ErrorDisplay errors={this.state.errors} onRemove={(index) => this.removeErrorMessage(index)}/>)
		}

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
											errorMessage={formErrors.newPassword} 
											onFocus={() => this.removeFormErrorMessage("newPassword")}
											required hideAsterisk onChange={this.handleChange} />

							<FormInputText label="Confirm new password" addonBefore="glyph-lock" type="password" 
											name="confirmNewPassword" value={this.state.confirmNewPassword} 
											errorMessage={formErrors.passwordMatch} 
											onFocus={() => this.removeFormErrorMessage("passwordMatch")}
											required hideAsterisk onChange={this.handleChange} />

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