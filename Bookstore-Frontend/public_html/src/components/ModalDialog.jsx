import React, { Component } from 'react'
import * as bs from 'react-bootstrap'

class ModalDialog extends React.Component {
	render() {
		const props = this.props

		let title=""
		if (props.header){
			if (props.doNotUseTitle){
				title = props.header
			} else {
				title = (
					<bs.Modal.Title>
						{props.header}
					</bs.Modal.Title>
				)
			}
		}
		if (title){
			title=(
				<bs.Modal.Header closeButton={this.props.showCloseButton}>
					{title}
				</bs.Modal.Header>
			)
		}

		let body=""
		if (props.body){
			body=(
				<bs.Modal.Body>
					{props.body}
				</bs.Modal.Body>
			)
		}

		let footer=""
		if (props.footer){
			footer=(
				<bs.Modal.Footer>
					{props.footer}
				</bs.Modal.Footer>
			)
		}

		return (
			<bs.Modal show bsSize={this.props.size || "lg"}
			onHide={this.props.onHide}  backdrop={ (this.props.staticBackdrop && "static") || true}>
				{title}
				{body}
				{footer}
			</bs.Modal>
		)
	}
}

export default ModalDialog