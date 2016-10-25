import React from 'react'
import * as Actions from 'actions'
import { connect } from 'react-redux'
import { UserService } from 'services'
import { bindActionCreators } from 'redux'

class BaseView extends React.Component {
	constructor(props){
		super(props)

		this.state = this.props.location.state
		if (this.state == null){
			this.state = { }
		}

		this.userService = new UserService()
		this.handleChange = this.handleChange.bind(this)
	}

	handleChange(event) {
		this.state[event.target.name] = event.target.value
	    this.setState(this.state)
	}

	componentWillUnmount(){
		this.props.dispatch.removeValidationMessage()
	}
}

export const mapStateToProps = (state, ownProps) => ({
	user: state.user,
	validationMessage: state.validationMessage,
	shoppingCart: state.shoppingCart
})

export const mapDispatchToProps = (dispatch) => ({
	dispatch: bindActionCreators(Actions, dispatch)
})

export default BaseView