import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'

class AccountActivationView extends BaseView{

	constructor(props){
		super(props)
		this.state = { activateStatus: "waiting" }
		
		this.userService.activateAccount(this.props.location.query.token, this.props.location.query.username)
			.then((resp) => {
				this.state.activateStatus = "success";
				this.setState(this.state)
			})
			.fail((err) => {
				if (err.status == 400) {
					this.state.activateStatus = "invalid token";	
				} else if (err.status == 401){
					this.state.activateStatus = "already activated";
				} else {
					this.state.activateStatus = "server error";
				}
				this.setState(this.state)
			})
	}

	render(){

		let activationStatusDisplay = this.state.activateStatus

		return (
			<h1>{activationStatusDisplay}</h1>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(AccountActivationView)