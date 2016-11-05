import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import { LoadingSpinner } from 'components'
import * as bs from 'react-bootstrap'

class ShoppingCartView extends BaseView{

	constructor(props){
		super(props)

		this.loadSalesInfo = this.loadSalesInfo.bind(this)
		this.removeItem = this.removeItem.bind(this)
		this.changeItemQuantity = this.changeItemQuantity.bind(this)

		this.state.salesList = []
	}

	loadSalesInfo(){

		this.state.salesList = []
		this.state.loadingSalesInfo = true
		this.setState(this.state)
		this.bookService.getBookSales(this.props.shoppingCart.items)
			.then((resp) => {
				this.props.dispatch.updateCart(resp)
			})
			.always(() => {
				this.state.loadingSalesInfo = false
				this.setState(this.state)
			})
	}

	removeItem(saleId){
		this.props.dispatch.removeItem(saleId)
	}

	changeItemQuantity(event, itemId){
		this.props.dispatch.updateItemQuantity(itemId, event.target.value)
	}

	render(){
		const shoppingCart = this.props.shoppingCart

		let salesListView = []

		if (this.state.loadingSalesInfo){
			salesListView = (<LoadingSpinner visible/>)
		}
		else {
			shoppingCart.items.forEach((item) => {
			salesListView.push(
				<bs.Panel key={item.id}>
					<bs.Row>
						<bs.Col xs={2}>
							<bs.Thumbnail src={item.book.imgPath}/>
						</bs.Col>
						<bs.Col xs={6}>
							<h3>{item.book.title} <small>({item.book.author})</small></h3>
							<h4>Seller: {item.seller.username}</h4>
							<h4><bs.Label>{item.bookCondition}</bs.Label></h4>
						</bs.Col>
						<bs.Col xs={4}>
							<h4>Item in stock: {item.maxQuantity}</h4>
							<h4>Item price: AU$ {item.price}</h4>
							<bs.Row>
								<bs.Col xs={5}>
									<bs.ControlLabel>Quantity</bs.ControlLabel>
									<bs.FormControl type="number" value={item.quantity}
										onChange={(event) => this.changeItemQuantity(event, item.id)}/>
								</bs.Col>

								<bs.Col xs={7}>
									<bs.ControlLabel>Total Price</bs.ControlLabel><br/>
									<h4>AU$ {item.totalPrice}</h4>
								</bs.Col>
							</bs.Row>
							<br/>
							<bs.Button bsStyle="warning" onClick={() => this.props.dispatch.removeItem(item.id)}>Remove</bs.Button>
						</bs.Col>
					</bs.Row>
				</bs.Panel>
			)
		})
		}

		const shoppingCartView = (
			<bs.Modal {...this.props} bsSize="lg" show onEnter={this.loadSalesInfo}>
				<bs.Modal.Header closeButton>
					<h3>Your shopping cart</h3>
				</bs.Modal.Header>
					
				<bs.Modal.Body>
					{salesListView}
				</bs.Modal.Body>

				<bs.Modal.Footer>
					checkout section here
				</bs.Modal.Footer>
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