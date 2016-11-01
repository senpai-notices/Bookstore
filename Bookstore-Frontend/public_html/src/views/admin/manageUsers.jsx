import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import Select from 'react-select'
import { FormInputText, ModalDialog } from 'components'
import Halogen from 'halogen'
import InfiniteScroll from 'react-infinite-scroller'
import { saveAs } from 'file-saver'
import * as config from 'config'

class ManageUsersView extends BaseView {

	constructor(props){
		super(props)

		this.onRoleChange = this.onRoleChange.bind(this)
		this.getUserList = this.getUserList.bind(this)
		this.loadMoreUser = this.loadMoreUser.bind(this)
		
		this.viewIDDocument = this.viewIDDocument.bind(this)
		this.viewResidentialDocument = this.viewResidentialDocument.bind(this)
		
		this.rejectVerification = this.rejectVerification.bind(this)
		this.approveVerification = this.approveVerification.bind(this)

		this.state.roleOptions = [
			{value: "INACTIVATED", label: "Inactivated"},
			{value: "USER", label: "Normal account"},
			{value: "VERIFYING USER", label: "Unverified"},
			{value: "VERIFIED USER", label: "Verified"},
			{value: "BANNED", label: "Banned"}]
		this.state.selectedRoles = ""
		this.state.userList = []
	}

	onRoleChange(roles){
		this.state.selectedRoles = roles
		this.setState(this.state)
	}

	loadMoreUser(){
		let offset = this.state.userList.length
		let limit = 20
		let roles = this.state.selectedRoles
		if (!roles){
			roles = "USER,VERIFYING USER,VERIFIED USER,BANNED,INACTIVATED"
		}
		
		this.adminService.findUsers(roles, this.state.username, 
					this.state.fullname, this.state.email, offset, limit)
			.then((resp) => {
				this.state.userList = this.state.userList.concat(resp)
				if (resp.length < limit){
					this.state.hasMore = false
				}
			}).always(() => {
				this.setState(this.state)
			})
	}

	getUserList(event){
		if (event){
			event.preventDefault()
		}

		let offset = 0
		let limit = 20
		this.state.hasMore = true;
		this.setState(this.state)

		let roles = this.state.selectedRoles
		if (!roles){
			roles = "USER,VERIFYING USER,VERIFIED USER,BANNED,INACTIVATED"
		}
		
		this.state.gettingUser = true;
		this.setState(this.state)
		this.adminService.findUsers(roles, this.state.username, 
					this.state.fullname, this.state.email, offset, limit)
			.then((resp) => {
				this.state.userList = resp
				if (resp.length < limit){
					this.state.hasMore = false
				}
			}).always(() => {
				this.state.gettingUser = false;
				this.setState(this.state)
			})
	}

	viewIDDocument(username){
		this.adminService.getDocument("id", username)
			.then((resp) => {
				let fileType = resp.type.split("/")[1];
				saveAs(resp, username +"_id." + fileType);
			})
			.catch((err) =>{
				console.log(err)
			})
	}

	viewResidentialDocument(username){
		this.adminService.getDocument("residential", username)
			.then((resp) => {
				let fileType = resp.type.split("/")[1];
				saveAs(resp, username +"_residential." + fileType);
			})
			.catch((err) =>{
				console.log(err)
			})
	}

	rejectVerification(event){
		event.preventDefault()

		this.setState({ rejecting: true })
		this.adminService.rejectAccountVerification(this.state.rejectUser, this.state.reject_reason)
			.then((resp) => {
				this.adminService.sendRejectEmail(this.state.rejectUser, this.state.reject_reason)
				this.getUserList()
			})
			.always(() => {
				this.setState({ rejecting: false , showRejectReasonForm: false})
			})
	}

	approveVerification(event){
		event.preventDefault()

		this.setState({ approving: true})
		this.adminService.approveAccountVerification(this.state.approveUser)
			.then((resp) => {
				this.adminService.sendApproveEmail(this.state.approveUser)
				this.getUserList()
			})
			.always(() =>{
				this.setState({approving: false, showConfirmApproveDialog: false})
			})
	}

	render(){
		
		let rejectReasonForm = ""
		if (this.state.showRejectReasonForm){
			let rejectHeader = (
				<h3>Reject verification request from account <strong>{this.state.rejectUser}</strong></h3>
			)
			let rejectBody = (
				<bs.Form horizontal onSubmit={this.rejectVerification}>
					<h4><strong>Please specify the reason why the verification request is denied</strong></h4>
					<textarea required name="reject_reason" disabled={this.state.rejecting}
								value={this.state.reject_reason} onChange={this.handleChange}
								style={{width: "100%", minHeight: "300px"}}>
					</textarea>
					<bs.Button type="submit" bsStyle="danger" disabled={this.state.rejecting}>
						{(this.state.rejecting && "Rejecting") || "Reject the documents"} 
					</bs.Button>
					&nbsp;
					<bs.Button bsStyle="success" disabled={this.state.rejecting}
						onClick={() => this.setState({showRejectReasonForm: false})}>
						Return
					</bs.Button>
				</bs.Form>
			)

			rejectReasonForm = (
				<ModalDialog header={rejectHeader} body={rejectBody} staticBackdrop={this.state.rejecting}
							onHide={() => this.setState({showRejectReasonForm: false})}/>
			)
		}

		let confirmApproveDialog = ""
		if (this.state.showConfirmApproveDialog){
			let confirmBody = (
				<h3>Approve verification request of account <strong>{this.state.approveUser}</strong>?</h3>
			)
			let confirmFooter = (
				<div>
					<bs.Button bsStyle="success" bsSize="lg" onClick={this.approveVerification}>
						Yes
					</bs.Button>
					&nbsp;
					<bs.Button bsStyle="warning" bsSize="lg" onClick={() => this.setState({showConfirmApproveDialog: false})}>
						No
					</bs.Button> 
				</div>
			)
			confirmApproveDialog = (
				<ModalDialog body={confirmBody} footer={confirmFooter}
					onHide={() => this.setState({showConfirmApproveDialog: false})}/>
			)
		}

		const loading = (
			<div>
				<bs.Row>
					<bs.Col xs={2} xsOffset={5}>
						<Halogen.RingLoader color="#000" size="100%"/>
					</bs.Col>
				</bs.Row>
				<bs.Row>
					<bs.Col className={'text-center'}>
						<h2>Loading...</h2>
					</bs.Col>
				</bs.Row>
			</div>
		)

		let userListView = []
		let key = 0;
		const tdStyle = {
			verticalAlign: "middle"
		}
		this.state.userList.forEach((user) =>{
			key++

			let actions=[]
			if (user.role === "VERIFYING USER"){

				actions.push(
					<span key={1}>
						<bs.Button bsStyle="primary" onClick={() => this.viewIDDocument(user.username)}>
							View ID
						</bs.Button>
						&nbsp;
					</span>
				)

				actions.push(
					<span key={2}>
						<bs.Button bsStyle="primary" onClick={() => this.viewResidentialDocument(user.username)}>
							View Residential Evidence
						</bs.Button>
						&nbsp;
					</span>
				)

				actions.push(
					<span key={3}>
						<bs.Button bsStyle="success"
							onClick={() => this.setState({showConfirmApproveDialog: true, approveUser: user.username})}>
							Approve
						</bs.Button>
						&nbsp;
					</span>
				)

				actions.push(
					<span key={4}>
					<bs.Button bsStyle="warning" 
							onClick={() => this.setState({showRejectReasonForm: true, rejectUser: user.username, reject_reason: ""})}>
							Reject
						</bs.Button>
					</span>
				)
			}

			userListView.push(
				<tr key={key}>
					<td style={tdStyle}>{user.username}</td>
					<td style={tdStyle}>{user.fullname}</td>
					<td style={tdStyle}>{user.email}</td>
					<td style={tdStyle}>{user.role}</td>
					<td style={tdStyle}>{actions}</td>
				</tr>
			)
		})


		return(
			<div>
				{rejectReasonForm}
				{confirmApproveDialog}

				<h3>Filter accounts by</h3>
				<bs.Form horizontal onSubmit={this.getUserList}>
				<bs.Row>
					<bs.Col xs={6}>
						<bs.ControlLabel>Username</bs.ControlLabel>
						<bs.FormControl type="text" name="username" placeholder="Username" onChange={this.handleChange}/>
					</bs.Col>
					<bs.Col xs={6}>
						<bs.ControlLabel>Email</bs.ControlLabel>
						<bs.FormControl type="text" name="email" placeholder="Email" onChange={this.handleChange}/>
					</bs.Col>
				</bs.Row>
				<br/>
				<bs.Row>
					<bs.Col xs={6}>
						<bs.ControlLabel>Fullname</bs.ControlLabel>
						<bs.FormControl type="text" name="fullname" placeholder="Fullname" onChange={this.handleChange}/>
						<br/>
						<bs.Button type="submit" bsStyle="success">Filter</bs.Button>
					</bs.Col>
					<bs.Col xs={6}>
						<bs.ControlLabel>Roles</bs.ControlLabel>
						<Select multi simpleValue value={this.state.selectedRoles} 
								placeholder="Roles" options={this.state.roleOptions} onChange={this.onRoleChange} />
					</bs.Col>
				</bs.Row>
				</bs.Form>

				<br/>
				<InfiniteScroll inita pageStart={0} loadMore={this.loadMoreUser} hasMore={this.state.hasMore && !this.state.gettingUser} loader={loading}>
				<bs.Table striped bordered condensed hover>
					<thead>
						<tr>
							<th className={"col-md-1"}>Username</th>
							<th className={"col-md-1"}>Fullname</th>
							<th className={"col-md-1"}>Email</th>
							<th className={"col-md-1"}>Role</th>
							<th className={"col-md-8"}>Actions</th>
						</tr>
					</thead>
					<tbody>
						
						{userListView}
						
					</tbody>
				</bs.Table>
				</InfiniteScroll>
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageUsersView)