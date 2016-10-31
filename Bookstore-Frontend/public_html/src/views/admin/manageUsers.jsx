import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import Select from 'react-select'
import { FormInputText } from 'components'
import Halogen from 'halogen'
import InfiniteScroll from 'react-infinite-scroller'

class ManageUsersView extends BaseView {

	constructor(props){
		super(props)

		this.onRoleChange = this.onRoleChange.bind(this)
		this.getUserList = this.getUserList.bind(this)
		this.loadMoreUser = this.loadMoreUser.bind(this)
		this.state.roleOptions = [
			{value: "INACTIVATED", label: "Inactivated accounts"},
			{value: "USER", label: "Unverified accounts"},
			{value: "VERIFYING USER", label: "Accounts need verification"},
			{value: "VERIFIED USER", label: "Verified accounts"},
			{value: "BANNED", label: "Banned accounts"}]
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
		
		this.userService.findUsers(roles, this.state.username, 
					this.state.fullname, this.state.email, offset, limit)
			.then((resp) => {
				this.state.userList = resp
				if (resp.length < limit){
					this.state.hasMore = false
				}
			}).always(() => {
				this.setState(this.state)
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
		this.state.userList.forEach((user) =>{
			userListView.push(
				<tr key={user.username}>
					<td>{user.username}</td>
					<td>{user.email}</td>
					<td>{user.fullname}</td>
					<td>{user.role}</td>
					<td></td>
				</tr>
			)
		})


		return(
			<div>
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
				<InfiniteScroll inita pageStart={0} loadMore={this.loadMoreUser} hasMore={this.state.hasMore} loader={loading}>
				<bs.Table striped bordered condensed hover>
					<thead>
						<tr>
							<th>Username</th>
							<th>Fullname</th>
							<th>Email</th>
							<th>Role</th>
							<th>Actions</th>
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