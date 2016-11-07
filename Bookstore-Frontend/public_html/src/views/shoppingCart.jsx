import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import { LoadingSpinner } from 'components'
import { SalesListView } from 'views'
import * as bs from 'react-bootstrap'

class ShoppingCartView extends BaseView{

	render(){
		return (
			<bs.Modal {...this.props} bsSize="lg" show onEnter={this.loadSalesInfo}>
				<bs.Modal.Header closeButton>
					<h3>Your shopping cart</h3>
				</bs.Modal.Header>
					
				<bs.Modal.Body>
					<SalesListView />
				</bs.Modal.Body>

				<bs.Modal.Footer>
					<bs.Button bsStyle="primary" onClick={this.props.onCheckout} 
						disabled={this.props.shoppingCart.items.length === 0 
							|| this.props.user.status !== 'loggedIn'
							|| this.props.user.role === "ADMIN"}>
						{(this.props.shoppingCart.items.length === 0 && "No item to checkout")
						||(this.props.user.status !== 'loggedIn' && "Please login before checkout")
						||(this.props.user.role === "ADMIN" && "Please use a non-admin account for checkout")
						|| "Checkout"}
					</bs.Button>
				</bs.Modal.Footer>
			</bs.Modal>
			
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(ShoppingCartView)