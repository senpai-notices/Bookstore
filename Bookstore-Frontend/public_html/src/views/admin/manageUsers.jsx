import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import Select from 'react-select'
import { FormInputText } from 'components'
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
		this.viewResidentalDocument = this.viewResidentalDocument.bind(this)

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
		
		this.userService.findUsers(roles, this.state.username, 
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
		event.preventDefault()

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
		this.userService.findUsers(roles, this.state.username, 
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
		this.userService.getDocument("id", username)
			.then((resp) => {
				let fileType = resp.type.split("/")[1];
				saveAs(resp, username +"_id." + fileType);
			})
			.catch((err) =>{
				console.log(err)
			})
	}

	viewResidentalDocument(username){
		this.userService.getDocument("residental", username)
			.then((resp) => {
				let fileType = resp.type.split("/")[1];
				saveAs(resp, username +"_residental." + fileType);
			})
			.catch((err) =>{
				console.log(err)
			})
	}

	render(){

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

			let actions=""
			if (user.role === "VERIFYING USER"){
				actions = (
					<div>
						<bs.Button bsStyle="primary" onClick={() => this.viewIDDocument(user.username)}>
							View ID
						</bs.Button>
						&nbsp;
						<bs.Button bsStyle="primary" onClick={() => this.viewResidentalDocument(user.username)}>
							View Residental Evidence
						</bs.Button>
						&nbsp;
						<bs.Button bsStyle="success">
							Approve
						</bs.Button>
						&nbsp;
						<bs.Button bsStyle="warning">
							Reject
						</bs.Button>
					</div>
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
				{this.state.test}
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