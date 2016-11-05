import React, { Component } from 'react'
import * as bs from 'react-bootstrap'

class FormInputText extends React.Component {

	constructor(props){
		super(props)
		this.state = { }
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

		let addonBefore = ""
		if (this.props.addonBefore){
			let [addonType, addonValue] = this.props.addonBefore.split("-")
			
			if (addonType === "glyph"){
				addonBefore = (
					<bs.InputGroup.Addon>
						<bs.Glyphicon glyph={addonValue}/>
					</bs.InputGroup.Addon>
				)	
			} else if (addonType === "strong"){
				addonBefore = (
					<bs.InputGroup.Addon>
						<strong>{addonValue}</strong>
					</bs.InputGroup.Addon>
				)
			}
			
		}

		let addonAfter = ""
		if (this.props.addonAfter){
			let [addonType, addonValue] = this.props.addonAfter.split("-")
			if (addonType === "glyph"){
				addonAfter = (
					<bs.InputGroup.Addon>
						<bs.Glyphicon glyph={addonValue}/>
					</bs.InputGroup.Addon>
				)	
			} else if (addonType === "strong"){
				addonBefore = (
					<bs.InputGroup.Addon>
						<strong>{addonValue}</strong>
					</bs.InputGroup.Addon>
				)
			}
			
		}

		let validationState = ""
		if (this.props.errorMessage){
			validationState = {validationState: "error"}
		}

		return (
			<bs.FormGroup bsSize={size} {...validationState}>
				<bs.ControlLabel>{this.props.label}{requiredAsterisk}</bs.ControlLabel>
				<bs.InputGroup>
					{addonBefore}
					<bs.FormControl type={this.props.type} 
									name={this.props.name} 
									placeholder={this.props.placeholder} 
									value={this.props.value}
									disabled={this.props.disabled}
									onChange={this.props.onChange}
									onFocus={this.props.onFocus}
									onBlur={this.props.onBlur}
									required={this.props.required}
									style={this.props.style}/>
					{addonAfter}
				</bs.InputGroup>
				{validationState && <bs.HelpBlock>{this.props.errorMessage}</bs.HelpBlock>}
			</bs.FormGroup>
		)
	}
}

export default FormInputText