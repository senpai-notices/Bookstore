import React from 'react'
import * as bs from 'react-bootstrap'
import { Glyphicon } from 'react-bootstrap'
import { connect } from 'react-redux'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { FormInputText } from 'components'

class RegisterForm extends BaseView {

	constructor(props){
		super(props)

		this.register = this.register.bind(this)
		this.sendActivateEmail = this.sendActivateEmail.bind(this)
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
				if (err.status === 409){ // conflict
					let validateResult = JSON.parse(err.response)
					this.props.dispatch.setValidationMessage(validateResult)
				} else if (err.status === 500) { // internal server error
					this.props.dispatch.addErrorMessage("Some unexpected error has occured, please try again or contact us for support")
				}
			})
			.always((resp) => {
				this.state.submitting = false
				this.setState(this.state)
			})
	}

	sendActivateEmail(event){
		if (event != null){
			event.preventDefault()
		}
		this.userService.sendActivateEmail(this.state.username, this.state.password)
	}

	render(){

		if (this.state.registerSuccess){
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
						<p>If you cannot receive the email, please check the spam-box or&nbsp; 
							<a href="" onClick={this.sendActivateEmail}>click here</a> to get another confirmation email </p>
						</h4>
					</bs.Alert>
				</bs.Col>
			)
		}

		const {errors, form_errors} = this.props.validationMessage

		return (
			<bs.Col xs={12}  md={6} mdOffset={3}>
				<div className='text-center'>
					<h2>Create new account</h2>
					<hr/>
				</div>

				<bs.Col xsOffset={1} xs={10}>
					<bs.Form horizontal onSubmit={this.register}>

						<FormInputText label="Fullname" addonBefore="glyph-user" 
										name="fullname" placeholder="Please enter your fullname"
										value={this.state.fullname} errorMessage={form_errors.fullname}
										onChange={this.handleChange} onFocus={() => this.props.dispatch.setFormErrorMessage("fullname")}
										required/>

						<FormInputText label="Username" addonBefore="glyph-user" 
										name="username" placeholder="Please enter your username"
										value={this.state.username} errorMessage={form_errors.username}
										onChange={this.handleChange} onFocus={() => this.props.dispatch.setFormErrorMessage("username")}
										required/>

						<FormInputText label="Email" addonBefore="strong-@" type="email"
										name="email" placeholder="Please enter your email address"
										value={this.state.email} errorMessage={form_errors.email}
										onChange={this.handleChange} onFocus={() => this.props.dispatch.setFormErrorMessage("email")}
										required/>

						<FormInputText label="Password" addonBefore="glyph-lock" type="password"
										name="password" placeholder="Please select a password for your account"
										value={this.state.password} errorMessage={form_errors.password}
										onChange={this.handleChange} onFocus={() => this.props.dispatch.setFormErrorMessage("password")}
										required/>

						<FormInputText label="Confirm password" addonBefore="glyph-lock" type="password"
										name="password_confirm" placeholder="Please retype your password"
										value={this.state.password_confirm} errorMessage={form_errors.password_confirm}
										onChange={this.handleChange} onFocus={() => this.props.dispatch.setFormErrorMessage("password_confirm")}
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