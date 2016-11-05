import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import Select from 'react-select'
import { FormInputText, ModalDialog, LoadingSpinner } from 'components'
import InfiniteScroll from 'react-infinite-scroller'
import { saveAs } from 'file-saver'

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

		this.banAccount = this.banAccount.bind(this)
		this.unbanAccount = this.unbanAccount.bind(this)

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

		let roles = this.state.selectedRoles
		if (!roles){
			roles = "USER,VERIFYING USER,VERIFIED USER,BANNED,INACTIVATED"
		}

		let offset = 0
		let limit = 20
		this.state.hasMore = true
		this.state.gettingUser = true
		this.state.userList = []
		this.state.initializedSearch = true
		this.setState(this.state)
		this.adminService.findUsers(roles, this.state.username, 
					this.state.fullname, this.state.email, offset, limit)
			.then((resp) => {
				this.state.userList = resp
				if (resp.length < limit){
					this.state.hasMore = false
				}
			}).always(() => {
				this.state.gettingUser = false
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
				// TODO; display error
				console.log(err)
			})
	}

	viewResidentialDocument(username){
		this.adminService.getDocument("residential", username)
			.then((resp) => {
				let fileType = resp.type.split("/")[1];
				saveAs(resp, username +"_residential." + fileType);
			})
			.catch((err) => {
				// TODO: display error
				console.log(err)
			})
	}

	rejectVerification(event){
		event.preventDefault()

		this.setState({ rejecting: true })
		this.adminService.rejectAccountVerification(this.state.rejectUser, this.state.reject_reason)
			.then((resp) => {
				this.adminService.sendRejectEmail(this.state.rejectUser, this.state.reject_reason)
			})
			.always(() => {
				this.getUserList()
				this.setState({ rejecting: false , showRejectReasonForm: false})
			})
	}

	approveVerification(event){
		event.preventDefault()

		this.setState({ approving: true})
		this.adminService.approveAccountVerification(this.state.approveUser)
			.then((resp) => {
				this.adminService.sendApproveEmail(this.state.approveUser)
			})
			.always(() =>{
				this.getUserList()
				this.setState({approving: false, showConfirmApproveDialog: false})
			})
	}

	banAccount(event){
		event.preventDefault()
		
		this.setState({ banning: true})
		this.adminService.banAccount(this.state.banUser)
			.then((resp) => {
				this.adminService.sendBanEmail(this.state.banUser)
			})
			.always(() => {
				this.getUserList()
				this.setState({banning: false, showConfirmBanDialog: false})
			})
	}

	unbanAccount(event){
		event.preventDefault()

		this.setState({ unbanning: true})
		this.adminService.unbanAccount(this.state.unbanUser)
			.then((resp) => {
				this.adminService.sendUnbanEmail(this.state.unbanUser)
			})
			.always(() => {
				this.getUserList()
				this.setState({unbanning: false, showConfirmUnbanDialog: false})
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
					<bs.Button bsStyle="success" bsSize="lg" disabled={this.state.approving}
						onClick={this.approveVerification}>
						Yes
					</bs.Button>
					&nbsp;
					<bs.Button bsStyle="warning" bsSize="lg" disabled={this.state.approving}
						onClick={() => this.setState({showConfirmApproveDialog: false})}>
						No
					</bs.Button> 
				</div>
			)
			confirmApproveDialog = (
				<ModalDialog body={confirmBody} footer={confirmFooter} staticBackdrop={this.state.approving}
					onHide={() => this.setState({showConfirmApproveDialog: false})}/>
			)
		}

		let confirmBanDialog = ""
		if (this.state.showConfirmBanDialog){
			let confirmBody = (
				<h3>Ban account <strong>{this.state.banUser}</strong>?</h3>
			)
			let confirmFooter = (
				<div>
					<bs.Button bsStyle="danger" bsSize="lg" onClick={this.banAccount} disabled={this.state.banning}>
						Yes
					</bs.Button>
					&nbsp;
					<bs.Button bsStyle="success" bsSize="lg" disabled={this.state.banning}
						onClick={() => this.setState({showConfirmBanDialog: false})}>
						No
					</bs.Button> 
				</div>
			)
			confirmBanDialog = (
				<ModalDialog body={confirmBody} footer={confirmFooter} staticBackdrop={this.state.banning}
					onHide={() => this.setState({showConfirmBanDialog: false})}/>
			)
		}

		let confirmUnbanDialog = ""
		if (this.state.showConfirmUnbanDialog){
			let confirmBody = (
				<h3>Unban account <strong>{this.state.unbanUser}</strong>?</h3>
			)
			let confirmFooter = (
				<div>
					<bs.Button bsStyle="success" bsSize="lg" onClick={this.unbanAccount} disabled={this.state.ubbanning}>
						Yes
					</bs.Button>
					&nbsp;
					<bs.Button bsStyle="warning" bsSize="lg" disabled={this.state.ubbanning}
						onClick={() => this.setState({showConfirmUnbanDialog: false})}>
						No
					</bs.Button> 
				</div>
			)
			confirmUnbanDialog = (
				<ModalDialog body={confirmBody} footer={confirmFooter} staticBackdrop={this.state.unbanning}
					onHide={() => this.setState({showConfirmUnbanDialog: false})}/>
			)
		}

		const loading = (<LoadingSpinner visible/>)

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

			if (user.role !== "BANNED"){
				actions.push(
					<span key={5}>
						<bs.Button bsStyle="danger" 
							onClick={() => this.setState({showConfirmBanDialog: true, banUser: user.username})}>
							Ban
						</bs.Button>
						&nbsp;
					</span>
				)
			} else {
				actions.push(
					<span key={5}>
						<bs.Button bsStyle="success" 
							onClick={() => this.setState({showConfirmUnbanDialog: true, unbanUser: user.username})}>
							Unban
						</bs.Button>
						&nbsp;
					</span>
				)
			}

			userListView.push(
				<tr key={key}>
					<td style={tdStyle}>{user.username}</td>
					<td style={tdStyle}>{user.fullname}</td>
					<td style={tdStyle}>{user.address}</td>
					<td style={tdStyle}>{user.email}</td>
					<td style={tdStyle}>{user.role}</td>
					<td style={tdStyle}>{actions}</td>
				</tr>
			)
		})

		const initialLoading = (<LoadingSpinner visible={this.state.gettingUser}/>)
		let userListTable = ""
		if (this.state.userList.length > 0){
			userListTable = (
				<bs.Table striped bordered condensed hover>
					<thead>
						<tr>
							<th className={"col-md-1"}>Username</th>
							<th className={"col-md-1"}>Fullname</th>
							<th className={"col-md-1"}>Address</th>
							<th className={"col-md-1"}>Email</th>
							<th className={"col-md-1"}>Role</th>
							<th className={"col-md-8"}>Actions</th>
						</tr>
					</thead>
					<tbody>		
						{userListView}
					</tbody>
				</bs.Table>
			)
		} else {
			if (this.state.initializedSearch && !this.state.gettingUser){
				userListTable = (
					<h3>No result found</h3>
				)
			}
		}

		return(
			<div>
				{rejectReasonForm}
				{confirmApproveDialog}
				{confirmBanDialog}
				{confirmUnbanDialog}

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
				{userListTable}
				{initialLoading}
				</InfiniteScroll>
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(ManageUsersView)