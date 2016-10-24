import React from 'react'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { Link } from 'react-router'

class LoginForm extends React.Component{

	constructor(props){
		super(props)
	}

	render(){
		const validationMessage = this.props.validationMessage

		let errorDisplay = ""
		if (validationMessage.errors[0] !== undefined){
			errorDisplay = (
				<bs.Alert bsStyle="danger" onDismiss={this.props.removeValidationError}>
					{validationMessage.errors[0]}
				</bs.Alert>
			)
		}

		return (
			<bs.Col xs={12} md={6} mdOffset={3}>
				<bs.Panel>

					<div className='text-center'>
						<h2>Login</h2>
						<hr/>
					</div>

					<bs.Col xsOffset={1} xs={10}>

						{errorDisplay}

						<bs.Form horizontal>

							<bs.FormGroup bsSize="lg">
								<bs.ControlLabel>Username</bs.ControlLabel>
								<bs.InputGroup>
									<bs.InputGroup.Addon>
										<bs.Glyphicon glyph="user"/>
									</bs.InputGroup.Addon>
									<bs.FormControl type="text" />
								</bs.InputGroup>
							</bs.FormGroup>

							<bs.FormGroup bsSize="lg">
								<bs.ControlLabel>Password</bs.ControlLabel>
								<bs.InputGroup>
									<bs.InputGroup.Addon>
										<bs.Glyphicon glyph="lock"/>
									</bs.InputGroup.Addon>
									<bs.FormControl type="password" />
								</bs.InputGroup>
							</bs.FormGroup>

							<bs.FormGroup bsSize="lg">
								<bs.Button type="submit" bsStyle="success" bsSize="lg" block>
									Login
								</bs.Button>
								<br/>
								<div className='text-center'>
									<Link to="/register"><h4>Register</h4></Link>
								</div>
							</bs.FormGroup>

						</bs.Form>
					</bs.Col>

				</bs.Panel>
			</bs.Col>
		)
	}
}

const mapStateToProps = (state, ownProps) => {
	return {
		user: state.user,
		validationMessage: state.validationMessage
	}
}

const mapDispatchToProps = (dispatch) => ({
	removeValidationError: () => {
		dispatch({ 
			type: "REMOVE_VALIDATE_MESSAGE" 
		})
	}
})

export default connect(mapStateToProps, mapDispatchToProps)(LoginForm)