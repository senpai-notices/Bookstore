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
	}

	changePassword(event){
		event.preventDefault()
	}

	uploadDocuments(event){
		event.preventDefault()
		this.state.uploading = true
		this.setState(this.state)
		const readFilePromises = []
		readFilePromises.push(this.readFile("id", this.state.verification_id))
		readFilePromises.push(this.readFile("residential", this.state.address_proof))

		Promise.all(readFilePromises).then((results) =>{
			const uploadFilePromises = []
			results.forEach((readFileResult) =>{
				const documentType = readFileResult[0]
				const fileStream = readFileResult[1].replace(/^data:[^;]+;base64,/, '')
				const contentType = readFileResult[2]

				uploadFilePromises.push(this.userService.uploadDocument(documentType, contentType, fileStream))
			})

			return Promise.all(uploadFilePromises)
		}).then((resp) => {
			return this.userService.fetchAccount()
		}).then((resp) => {
			this.props.dispatch.login(resp)

			alert("Your documents have been upladed successfully")
			this.state.uploading = false
			this.setState(this.state)
		}).catch((err) => {
			alert("There was an error while trying to upload your documents")
			this.state.uploading = false
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
		const {errors, form_errors} = this.props.validationMessage
		const user = this.props.user
		const addressInput = (<FormAddressInput label="Your Address" name="address" value={this.state.address} errorMessage={form_errors.address}
									onChange={this.handleChange} onFocus={() => this.props.dispatch.setFormErrorMessage("address")} required />)

		let verifyAccountSection = "";
		
		if (user.role === "USER"){
			verifyAccountSection = (
				<div>
					<h2>Verify Account</h2>
					<hr/>

					<p>In order to sell your used books on our platform, you must have your account verified first</p>
					<p>Please upload your ID (passpord, driver license, photo ID, etc), and upload evidence of resident status in Australia</p>
					<p>An email will be sent to your account's email once the verification is completed</p>
					<br/>

					<bs.Form horizontal onSubmit={this.uploadDocuments} encType="multipart/form-data">
						
						<FormInputText label="Your ID" type="file" name="verification_id" errorMessage={form_errors.verification_id} 
										onChange={(event) => this.handleFileChange(event, "verification_id")} 
										onFocus={() => this.props.dispatch.setFormErrorMessage("verification_id")} required disabled={this.state.uploading}/>

						<FormInputText label="Your proof of address" type="file" name="address_proof" 
										errorMessage={form_errors.address_proof} onChange={(event) => this.handleFileChange(event, "address_proof")} 
										onFocus={() => this.props.dispatch.setFormErrorMessage("address_proof")} required disabled={this.state.uploading}/>

						<bs.FormGroup bsSize="lg">
							<bs.Button type="submit" bsStyle="primary" bsSize="lg" block disabled={this.state.uploading}>
								{this.state.uploading && 'Uploading files' || 'Upload documents'}
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