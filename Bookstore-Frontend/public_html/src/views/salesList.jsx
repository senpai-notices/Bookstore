import React, { Component } from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import { LoadingSpinner, FormInputText } from 'components'
import * as bs from 'react-bootstrap'

class SalesListView extends BaseView {
	constructor(props){
		super(props)

		this.loadSalesInfo = this.loadSalesInfo.bind(this)
		this.removeItem = this.removeItem.bind(this)
	}

	componentDidMount(){
		this.loadSalesInfo()
	}

	loadSalesInfo(){
		if (this.props.shoppingCart.items.length === 0){
			return
		}
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

	changeItemQuantity(event, itemId){
		this.props.dispatch.updateItemQuantity(itemId, event.target.value)
	}

	removeItem(saleId){
		this.props.dispatch.removeItem(saleId)
	}

	render(){
		const shoppingCart = this.props.shoppingCart

		let salesListView = []
		if (this.state.loadingSalesInfo){
			salesListView = (<LoadingSpinner visible/>)
		} else {
			let index = -1
			shoppingCart.items.forEach((item) => {
				index++
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
								<bs.Row>
								
									{this.props.showShipping && (<bs.Col xs={6}>
										<bs.ControlLabel>Shipping method</bs.ControlLabel>
										<bs.Radio name={"shipping" + item.id} checked={item.shippingType === 'normal'}
											onChange={() => this.props.dispatch.updateShippingType(item.id, 'normal')}>
											Normal
										</bs.Radio>
										<bs.Radio name={"shipping" + item.id} checked={item.shippingType === 'express'}
											onChange={() => this.props.dispatch.updateShippingType(item.id, 'express')}>
											Express
										</bs.Radio>
										
									</bs.Col>)}

									<bs.Col xs={6}>
										<bs.Button bsStyle="warning" 
											onClick={() => this.props.dispatch.removeItem(item.id)}>
												Remove
										</bs.Button>
									</bs.Col>
								</bs.Row>
								
							</bs.Col>
						</bs.Row>
					</bs.Panel>
				)
			})
		}

		return (
			<div>
				{salesListView}
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(SalesListView)