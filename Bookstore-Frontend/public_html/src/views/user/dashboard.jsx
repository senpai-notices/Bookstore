import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { FormInputText, FormAddressInput } from 'components'
import { ManageBooksView } from 'views'

class UserDashboardView extends BaseView {

	constructor(props){
		super(props)

		this.changePassword = this.changePassword.bind(this)
		this.uploadDocuments = this.uploadDocuments.bind(this)
		this.readFile = this.readFile.bind(this)
		this.onAddressSelected = this.onAddressSelected.bind(this)

		this.state.formErrors = {}
	}

	changePassword(event){
		event.preventDefault()
	}

	onAddressSelected(event){
		let selectedAddress = event.target.value
		const addressParts = selectedAddress.split(',')

		this.state.address_line1  = addressParts[0]
		this.state.address_city = addressParts[1]
		this.state.address_state = addressParts[2]
		this.state.address_country = addressParts[3]
		this.setState(this.state)

		this.addressSuggest.setValue(this.state.address_line1)
	}

	uploadDocuments(event){
		event.preventDefault()
		this.state.submitting = true
		this.setState(this.state)
		const readFilePromises = []
		readFilePromises.push(this.readFile("id", this.state.verification_id))
		readFilePromises.push(this.readFile("residential", this.state.address_proof))

		Promise.all(readFilePromises).then((results) =>{
			const callAPIPromises = []
			results.forEach((readFileResult) =>{
				const documentType = readFileResult[0]
				const fileStream = readFileResult[1].replace(/^data:[^;]+;base64,/, '')
				const contentType = readFileResult[2]

				callAPIPromises.push(this.userService.uploadDocument(documentType, contentType, fileStream))
			})

			const addressData = {
				address_line1: this.state.address_line1,
				address_line2: this.state.address_line2,
				address_city: this.state.address_city,
				address_state: this.state.address_state,
				address_country: this.state.address_country,
				address_postcode: this.state.address_postcode
			}
			callAPIPromises.push(this.userService.updateAddress(addressData))

			return Promise.all(callAPIPromises)
		}).then((resp) => {
			return this.userService.fetchAccount()
		}).then((resp) => {
			this.props.dispatch.login(resp)

			alert("Your documents have been upladed successfully")
			this.state.submitting = false
			this.setState(this.state)
		}).catch((err) => {
			alert("There was an error while trying to submit your documents")
			this.state.submitting = false
			this.setState(this.state)
		})
	}

	readFile(documentType, file){
		return new Promise((resolve, reject) => {
			const reader = new FileReader()
			reader.onload = event => resolve([documentType, event.target.result, file.type])
			reader.onerror = e => reject(e)
			reader.readAsDataURL(file)
		})
	}

	render(){
		const user = this.props.user
		const formErrors = this.state.formErrors
		const errors = this.state.errors

		let verifyAccountSection = "";
		
		if (user.role === "USER"){
			verifyAccountSection = (
				<div>
					<h2>Verify Account</h2>
					<hr/>

					<p>In order to sell your used books on our platform, you must have your account verified first</p>
					<p>Please upload your ID (passpord, driver license, photo ID, etc), enter your address and upload evidence of resident status in Australia</p>
					<p>An email will be sent to your account's email once the verification is completed</p>
					<br/>

					<bs.Form horizontal onSubmit={this.uploadDocuments} encType="multipart/form-data">
						
						<FormInputText label="Your ID" type="file" name="verification_id" 
										onChange={(event) => this.handleFileChange(event, "verification_id")} 
										required disabled={this.state.uploading}/>

						<FormInputText label="Your proof of address" type="file" name="address_proof" 
										onChange={(event) => this.handleFileChange(event, "address_proof")} 
										required disabled={this.state.uploading}/>

						<bs.Row>
						<bs.Col xs={7}>
								<FormAddressInput label="Address line 1" name="address_line1"
													value={this.state.address_line1} errorMessage={this.state.formErrors.address_line1}
													onAddressSelected={this.onAddressSelected}
													onFocus={() => this.removeFormErrorMessage('address_line1')}
													onChange={this.handleChange} ref={(input) => this.addressSuggest = input}
													required/>
							</bs.Col>
						</bs.Row>
						<bs.Row>
							<bs.Col xs={12}>
								<FormInputText label="Address line 2" name="address_line2"
												value={this.state.address_line2} errorMessage={formErrors.address_line2}
												onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('address_line2')}
												style={{width: "209%"}}/>
							</bs.Col>
						</bs.Row>

						<bs.Row>
							<bs.Col xs={4}>
								<FormInputText label="City" name="address_city"
												value={this.state.address_city} errorMessage={formErrors.address_city}
												onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('address_city')}
												required/>
							</bs.Col>
							<bs.Col xs={4}>
								<FormInputText label="State" name="address_state"
												value={this.state.address_state} errorMessage={formErrors.address_state}
												onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('address_state')}
												required/>
							</bs.Col>
						</bs.Row>

						<bs.Row>
							<bs.Col xs={4}>
								<FormInputText label="Postcode" name="address_postcode"
												value={this.state.address_postcode} errorMessage={formErrors.address_postcode}
												onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('address_postcode')}
												required/>
							</bs.Col>
							<bs.Col xs={4}>
								<FormInputText label="Country" name="address_country"
												value={this.state.address_country} errorMessage={formErrors.address_country}
												onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage('address_country')}
												required/>
							</bs.Col>

						</bs.Row>

						<bs.FormGroup bsSize="lg">
							<bs.Button type="submit" bsStyle="primary" bsSize="lg" block disabled={this.state.submitting}>
								{this.state.submitting && 'Uploading files' || 'Upload documents'}
							</bs.Button>
						</bs.FormGroup>

					</bs.Form>
				</div>
			)
		} else if (user.role === "VERIFYING USER"){
			verifyAccountSection =(
				<div>
				<h2>Your verify documents have been uploaded successfully</h2>
				<p>We are undergoing the verification process, please be patient</p>
				<p>An email will be sent to your account's email once the verification is completed</p>
				</div>
			)
		} else if (user.role === "VERIFIED USER"){
			verifyAccountSection = (
				<div>
					<h2>Your account is verified</h2>
					<p>You can start selling your books on our platform</p>
				</div>
			)
		}

		const changePasswordSection = (
			<div>
				<h2>Change password</h2>
				<hr/>
				<bs.Form horizontal onSubmit={this.changePassword}>
					<FormInputText label="Current password" type="password" name="password" value={this.state.password} 
									errorMessage={formErrors.password} onChange={this.handleChange} 
									onFocus={() => this.removeFormErrorMessage("password")} required/>

					<FormInputText label="New password" type="password" name="newPassword" value={this.state.newPassword} 
									errorMessage={formErrors.newPassword} onChange={this.handleChange} 
									onFocus={() => this.removeFormErrorMessage("newPassword")} required/>

					<FormInputText label="Confirm new password" type="password" name="confirmNewPassword" 
									value={this.state.confirmNewPassword} errorMessage={formErrors.passwordMatch}
									onChange={this.handleChange} onFocus={() => this.removeFormErrorMessage("passwordMatch")}
									required/>

					<bs.FormGroup bsSize="lg">
						<bs.Button type="submit" bsStyle="primary" bsSize="lg" block disabled={this.state.changingPassword}>
							{this.state.changingPassword && 'Updating your password' || 'Update password'}
						</bs.Button>
					</bs.FormGroup>
				</bs.Form>
			</div>
		)

		const accountTab = (
			<bs.Tab eventKey={1} title="Account">

				<bs.Col md={10} mdOffset={1} xs={12}>
					{verifyAccountSection}
				</bs.Col>
			</bs.Tab>
		)

		const newSalesTab = (
			<bs.Tab eventKey={2} title="Book selling" disabled={user.role !== "VERIFIED USER"}>
				<ManageBooksView/>
			</bs.Tab>
		)

		return(
			<bs.Tabs id="dashboard-menu">
				{accountTab}
				{newSalesTab}
			</bs.Tabs>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(UserDashboardView)