import React from 'react'
import * as Actions from 'actions'
import { connect } from 'react-redux'
import { UserService, BookService, AdminService } from 'services'
import { bindActionCreators } from 'redux'

class BaseView extends React.Component {
	constructor(props){
		super(props)

		if (this.props.location){
			this.state = this.props.location.state
		}
		if (!this.state){
			this.state = { }
		}

		this.userService = new UserService()
		this.bookService = new BookService()
		this.adminService = new AdminService()
		this.handleChange = this.handleChange.bind(this)
		this.handleFileChange = this.handleFileChange.bind(this)
		this.removeErrorMessage = this.removeErrorMessage.bind(this)
		this.removeFormErrorMessage = this.removeFormErrorMessage.bind(this)
	}

	removeErrorMessage(index){
		console.log(index)
		this.state.errors.splice(index, 1)
		this.setState(this.state)
	}

	removeFormErrorMessage(formName){
		delete this.state.formErrors[formName]
		this.setState(this.state)
	}

	handleChange(event) {
		this.state[event.target.name] = event.target.value
	    this.setState(this.state)
	}

	handleFileChange(event, name){
		this.state[name] = event.target.files[0]
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