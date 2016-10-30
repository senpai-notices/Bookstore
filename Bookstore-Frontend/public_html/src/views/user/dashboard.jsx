import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { FormInputText, FormAddressInput } from 'components'

class UserDashboardView extends BaseView {

	constructor(props){
		super(props)

		this.changePassword = this.changePassword.bind(this)
	}

	changePassword(event){
		event.preventDefault()
	}

	render(){
		console.log(this.state)
		const {errors, form_errors} = this.props.validationMessage
		const user = this.props.user

		const accountTab = (
			<bs.Tab eventKey={1} title="Account">
				<bs.Col md={3} xs={12}>
					<h2>Change password</h2>
					<hr/>
					<bs.Form horizontal onSubmit={this.changePassword}>
						<FormInputText label="Current password" type="password" name="password" value={this.state.password} 
										errorMessage={form_errors.password} onChange={this.handleChange} 
										onFocus={() => this.props.dispatch.setFormErrorMessage("password")} required/>

						<FormInputText label="New password" type="password" name="new_password" value={this.state.new_password} 
										errorMessage={form_errors.new_password} onChange={this.handleChange} 
										onFocus={() => this.props.dispatch.setFormErrorMessage("new_password")} required/>

						<FormInputText label="Confirm new password" type="password" name="confirm_new_password" 
										value={this.state.confirm_new_password} errorMessage={form_errors.confirm_new_password}
										onChange={this.handleChange} onFocus={() => this.props.dispatch.setFormErrorMessage("confirm_new_password")}
										required/>

						<bs.FormGroup bsSize="lg">
							<bs.Button type="submit" bsStyle="primary" bsSize="lg" block disabled={this.state.changingPassword}>
								{this.state.changingPassword && 'Updating your password' || 'Update password'}
							</bs.Button>
						</bs.FormGroup>
					</bs.Form>
				</bs.Col>

				<bs.Col md={8} mdOffset={1} xs={12}>
					<h2>Verify Account</h2>
					<hr/>

					<p>In order to sell your used books on our service, you must have your account verified first</p>
					<p>Please upload your ID (passpord, driver license, photo ID, etc), input your address and upload evidence of resident status in Australia</p>
					<p>An email will be sent to your account's email once the verification is completed</p>

					<bs.Form horizontal>
						<FormInputText label="Your ID" type="file" name="verification_id" value={this.state.verification_id} 
										errorMessage={form_errors.verification_id} onChange={this.handleChange} 
										onFocus={() => this.props.dispatch.setFormErrorMessage("verification_id")} required/>

						<FormInputText label="Your proof of address" type="file" name="address_proof" value={this.state.address_proof} 
										errorMessage={form_errors.address_proof} onChange={this.handleChange} 
										onFocus={() => this.props.dispatch.setFormErrorMessage("address_proof")} required/>

						<FormAddressInput label="Your Address" name="address" value={this.state.address} errorMessage={form_errors.address}
										onChange={this.handleChange} onFocus={() => this.props.dispatch.setFormErrorMessage("address")} required/>

						<bs.FormGroup bsSize="lg">
							<bs.Button type="submit" bsStyle="primary" bsSize="lg" block disabled={this.state.upladingFiles}>
								{this.state.upladingFiles && 'Uploading files' || 'Verify my account'}
							</bs.Button>
						</bs.FormGroup>

					</bs.Form>
				</bs.Col>
			</bs.Tab>
		)

		const sellTab = (
			<bs.Tab eventKey={2} title="Book selling" disabled={user.role !== "VERIFIED USER"}>
			</bs.Tab>
		)

		return(
			<bs.Tabs id="dashboard-menu">
				{accountTab}
				{sellTab}
			</bs.Tabs>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(UserDashboardView)