import React from 'react'
import * as bs from 'react-bootstrap'
import { Glyphicon } from 'react-bootstrap'
import { connect } from 'react-redux'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { FormInputText, ErrorDisplay } from 'components'

class RegisterForm extends BaseView {

	constructor(props){
		super(props)

		this.register = this.register.bind(this)
		this.sendActivateEmail = this.sendActivateEmail.bind(this)
		this.state.errors = []
		this.state.formErrors = {}
	}

	register(event){
		event.preventDefault()

		this.state.submitting = true
		this.setState(this.state)
		this.userService.createAccount(this.state)
			.then((resp) => {
				this.state.registerSuccess = true
				this.setState(this.state)
				this.sendActivateEmail(null)
			})
			.fail((err) => {
				if (err.status === 400){ 
					const validateResult = JSON.parse(err.response)
					this.state.errors = validateResult.errors
					this.state.formErrors = validateResult.form_errors
				} else { 
					this.state.errors.push(<h3>Cannot create account, please try again</h3>)
				}
			})
			.always((resp) => {
				this.state.submitting = false
				this.setState(this.state)
			})
	}

	sendActivateEmail(event){
		if (event){
			event.preventDefault()
		}
		this.state.requestingToken = true;
		this.setState(this.state)
		this.userService.sendActivateEmailAfterRegister(this.state.username, this.state.password)
						.always((resp) => {
							this.state.requestingToken = false;
							this.setState(this.state)
						})
	}

	render(){

		if (this.state.registerSuccess){

			const requestTokenButton = (
				<bs.Button bsStyle="primary" disabled={this.state.requestingToken} onClick={this.sendActivateEmail}>
					{(this.state.requestingToken && "Sending email") || "Resend activation email"}
				</bs.Button>
			)

			return (
				<bs.Col xs={12} md={6} mdOffset={3}>

					<bs.Alert bsStyle="success">
						<h2>
							Account created
						</h2>

						<h4>
						<p>Dear <strong>{this.state.fullname}</strong>, thank you for joining us.</p>
						<p>An email with the activation link has been sent to your email address at <a>{this.state.email}</a> </p>
						<p>Please check your email inbox at and activate your account </p>
						<p>If you cannot receive the email, please check the spam-box or request another confirmation email </p>
						</h4>
					</bs.Alert>
					{requestTokenButton}
				</bs.Col>
			)
		}

		return (
			<bs.Col xs={12}  md={6} mdOffset={3}>
				<div className='text-center'>
					<h2>Create new account</h2>
					<hr/>
				</div>

				<bs.Col xsOffset={1} xs={10}>

					<ErrorDisplay errors={this.state.errors} 
						onRemove={(index) => this.removeErrorMessage(index)}/>
					<bs.Form horizontal onSubmit={this.register}>

						<FormInputText label="Fullname" addonBefore="glyph-user" 
										name="fullname" placeholder="Please enter your fullname"
										value={this.state.fullname} errorMessage={this.state.formErrors.fullname}
										onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage("fullname")}
										required/>

						<FormInputText label="Username" addonBefore="glyph-user" 
										name="username" placeholder="Please enter your username"
										value={this.state.username} errorMessage={this.state.formErrors.username}
										onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage("username")}
										required/>

						<FormInputText label="Email" addonBefore="strong-@" type="email"
										name="email" placeholder="Please enter your email address"
										value={this.state.email} errorMessage={this.state.formErrors.email}
										onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage("email")}
										required/>

						<FormInputText label="Password" addonBefore="glyph-lock" type="password"
										name="password" placeholder="Please select a password for your account"
										value={this.state.password} errorMessage={this.state.formErrors.password}
										onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage("password")}
										required/>

						<FormInputText label="Confirm password" addonBefore="glyph-lock" type="password"
										name="confirmPassword" placeholder="Please retype your password"
										value={this.state.confirmPassword} errorMessage={this.state.formErrors.passwordMatch}
										onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage("passwordMatch")}
										required/>

						<bs.FormGroup bsSize="lg">
							<bs.Button type="submit" bsStyle="primary" bsSize="lg" block disabled={this.state.submitting}>
								{this.state.submitting && 'Creating' || 'Create account'}
							</bs.Button>
						</bs.FormGroup>

					</bs.Form>
				</bs.Col>

			</bs.Col>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(RegisterForm)