import React, { Component } from 'react'
import * as bs from 'react-bootstrap'
import Halogen from 'halogen'

class LoadingSpinner extends React.Component {

	render() {
		let loading = ""
		if (this.props.visible){
			loading = (
				<div>
					<bs.Row>
						<bs.Col xs={2} xsOffset={5}>
							<Halogen.RingLoader color="#000" size="100%"/>
						</bs.Col>
					</bs.Row>
					<bs.Row>
						<bs.Col className={'text-center'}>
							<h2>Loading...</h2>
						</bs.Col>
					</bs.Row>
				</div>
			)
		}
		return (
			<div>
			{loading}
			</div>
		)
	}
}

export default LoadingSpinner