import React, { Component } from 'react'
import * as bs from 'react-bootstrap'
import Geosuggest from 'react-geosuggest'

class FormAddressInput extends React.Component {

	constructor(props){
		super(props)
		this.state = { }

		this.updateAddress = this.updateAddress.bind(this)
		this.addressSelected = this.addressSelected.bind(this)
		this.geoSuggest = (<Geosuggest onSuggestSelect={this.addressSelected} onChange={this.updateAddress} country="au"/>)
	}

	updateAddress(value){
		let event = { target: { name: this.props.name, value: value } }
		this.props.onChange(event)
	}

	addressSelected(input){
		this.state.address = input.label
		this.setState(this.state)
		let event = { target: { name: this.props.name, value: input.label } }
		this.props.onChange(event)	
	}

	render() {
		let requiredAsterisk = ""
		if (this.props.required && !this.props.hideAsterisk){
			let asteriskStyle = {
				color: "#FF0000",
				marginLeft: ".25em"
			}
			requiredAsterisk = (
				<span style={asteriskStyle}>
					*
				</span>
			)
		}

		let size = this.props.size ? this.props.size : "lg"

		let validationState = ""
		if (this.props.errorMessage){
			validationState = {validationState: "error"}
		}

		return (
			<bs.FormGroup bsSize={size} {...validationState}>
				<bs.ControlLabel>{this.props.label}{requiredAsterisk}</bs.ControlLabel>
				{this.geoSuggest}
				{validationState && <bs.HelpBlock>{this.props.errorMessage}</bs.HelpBlock>}
			</bs.FormGroup>
		)
	}
}

export default FormAddressInput