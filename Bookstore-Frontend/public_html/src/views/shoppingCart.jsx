import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'

class ShoppingCartView extends BaseView{

	constructor(props){
		super(props)
	}

	render(){
		const shoppingCart = this.props.shoppingCart

		let itemListView = []

		shoppingCart.items.forEach((item) => {
			itemListView.push(
				<bs.Row>
					<bs.Col xs={12}>

					</bs.Col>
				</bs.Row>
			)
		})

		const shoppingCartView = (
			<bs.Modal {...this.props} bsSize="lg" show>
				<bs.Modal.Header closeButton>
					<bs.Modal.Title>Your shopping cart</bs.Modal.Title>
				</bs.Modal.Header>
					
				<bs.Modal.Body>
				</bs.Modal.Body>
			</bs.Modal>
		)

		return (
			<div>
			{shoppingCartView}
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(ShoppingCartView)