import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import Halogen from 'halogen'
import * as bs from 'react-bootstrap'
import { LoginForm } from 'views'

class ActivateAccountView extends BaseView{

	constructor(props){
		super(props)
		this.state = { activateStatus: "waiting" }
		
		this.userService.activateAccount(this.props.location.query.token, this.props.location.query.username)
			.then((resp) => {
				this.state.activateStatus = "success";
				this.setState(this.state)
				this.props.dispatch.setUser("username", this.props.location.query.username)
			})
			.fail((err) => {
				if (err.status === 500) {
					this.state.activateStatus = "Server Error"
				} else {
					this.state.activateStatus = err.response
				}
				this.setState(this.state)
			})
	}

	render(){

		let activationStatusDisplay = ""
		let loginForm = ""

		if (this.state.activateStatus == "waiting"){
			activationStatusDisplay = (
				<bs.Jumbotron style={ {height: "250px"} }>
					<bs.Row>
						<bs.Col xs={2} xsOffset={5}>
							<Halogen.RingLoader color="#000" size="100%"/>
						</bs.Col>
					</bs.Row>
					<bs.Row>
						<bs.Col className={'text-center'}>
							<h2>Activating your account...</h2>
						</bs.Col>
					</bs.Row>
				</bs.Jumbotron>
			)
		} else if (this.state.activateStatus == "success") {
			activationStatusDisplay = (
				<bs.Alert bsStyle="success" className={'text-center'}>
					<h2>Your account has been activated successfully</h2>
				</bs.Alert>
			)
			loginForm = (<LoginForm username={this.props.location.query.username}/>)
		} else {
			activationStatusDisplay = (
			<bs.Alert bsStyle="danger" className={'text-center'}>
				<h2>{this.state.activateStatus}</h2>
			</bs.Alert>
			)
		}

		return (
			<div>
				<bs.Col xs={12} md={6} mdOffset={3}>
					{activationStatusDisplay}
				</bs.Col>
				{loginForm}
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(ActivateAccountView)