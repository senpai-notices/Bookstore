import React, { Component } from 'react'
import * as bs from 'react-bootstrap'

class ErrorDisplay extends React.Component {
	render() {
		let errors = this.props.errors
		if (!errors){
			errors = []
		}

		let errorDisplay = []

		let index = 0
		errors.forEach((error) => {
			let i = index
			errorDisplay.push(
				<bs.Alert key={index} bsStyle="danger" className={'text-center'}
					onDismiss={() => (this.props.onRemove && this.props.onRemove(i))}>
					{error}
				</bs.Alert>
			)
			index++
		})

		return(
			<div>
				{errorDisplay}
			</div>
		)
	}
}

export default ErrorDisplay