import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'

class ShoppingCartView extends BaseView{

	render(){
		const shoppingCart = (
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
			{shoppingCart}
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(ShoppingCartView)