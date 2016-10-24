import React from 'react'
import * as bs from 'react-bootstrap'
import { UserService } from 'services'

class RegisterForm extends React.Component{

	constructor(props){
		super(props)

		this.state = { }
		this.userService = new UserService()
	}

	register(event){
		event.preventDefault()
	}

	handleChange(event) {
		this.state[event.target.name] = event.target.value
	    this.setState(this.state)
	}

	render(){
		return (
			<bs.Col xs={12}  md={6} mdOffset={3}>
				<div className='text-center'>
					<h2>Create new account</h2>
					<hr/>
				</div>

				<bs.Col xsOffset={1} xs={10}>
					<bs.Form horizontal onSubmit={this.login}>

						<bs.FormGroup bsSize="lg">
							<bs.ControlLabel>Fullname</bs.ControlLabel>
							<bs.InputGroup>
								<bs.InputGroup.Addon>
									<bs.Glyphicon glyph="user"/>
								</bs.InputGroup.Addon>
								<bs.FormControl type="text" name="fullname" placeholder="Please enter your fullname" 
												onChange={this.handleChange} value={this.state.fullname}/>
							</bs.InputGroup>
						</bs.FormGroup>

						<bs.FormGroup bsSize="lg">
							<bs.ControlLabel>Username</bs.ControlLabel>
							<bs.InputGroup>
								<bs.InputGroup.Addon>
									<bs.Glyphicon glyph="user"/>
								</bs.InputGroup.Addon>
								<bs.FormControl type="text" name="username" placeholder="Please select your username" 
												onChange={this.handleChange} value={this.state.username}/>
							</bs.InputGroup>
						</bs.FormGroup>

						<bs.FormGroup bsSize="lg">
							<bs.ControlLabel>Email</bs.ControlLabel>
							<bs.InputGroup>
								<bs.InputGroup.Addon><strong>@</strong></bs.InputGroup.Addon>
								<bs.FormControl type="text" name="email" placeholder="Please enter your email address" 
												onChange={this.handleChange} value={this.state.email}/>
							</bs.InputGroup>
						</bs.FormGroup>

						<bs.FormGroup bsSize="lg">
							<bs.ControlLabel>Password</bs.ControlLabel>
							<bs.InputGroup>
								<bs.InputGroup.Addon>
									<bs.Glyphicon glyph="lock"/>
								</bs.InputGroup.Addon>
								<bs.FormControl type="password" name="password" placeholder="Please select a password" 
												onChange={this.handleChange} value={this.state.password}/>
							</bs.InputGroup>
						</bs.FormGroup>

						<bs.FormGroup bsSize="lg">
							<bs.ControlLabel>Confirm Password</bs.ControlLabel>
							<bs.InputGroup>
								<bs.InputGroup.Addon>
									<bs.Glyphicon glyph="lock"/>
								</bs.InputGroup.Addon>
								<bs.FormControl type="text" name="password_confirm" placeholder="Please re-type your password" 
												onChange={this.handleChange} value={this.state.password_confirm}/>
							</bs.InputGroup>
						</bs.FormGroup>

						<bs.FormGroup bsSize="lg">
							<bs.Button type="submit" bsStyle="primary" bsSize="lg" block>
								Create account
							</bs.Button>
						</bs.FormGroup>

					</bs.Form>
				</bs.Col>

			</bs.Col>
		)
	}
}

export default RegisterForm