import React, { Component } from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { LoadingSpinner, StaticTable, ModalDialog } from 'components'

class BuyOrdersView extends BaseView {
	constructor(props){
		super(props)

		this.getBuyOrders = this.getBuyOrders.bind(this)
		this.orderSelected = this.orderSelected.bind(this)
		this.state.ordersList = []
	}

	componentDidMount(){
		this.getBuyOrders()
	}

	getBuyOrders(){
		this.state.gettingOrders = true
		this.bookService.getBuyOrders()
			.then((resp) => {
				this.state.ordersList = resp
			})
			.fail((err) => {
				alert("Cannot fetch buy orders from server")
			})
			.always(() => {
				this.state.gettingOrders = false
				this.setState(this.state)
			})
	}

	orderSelected(order) {
		console.log(order)
		this.state.selectedOrder = order
		this.state.showOrder = true
		this.setState(this.state)
	}

	render(){

		const loading=(<LoadingSpinner visible={this.state.gettingOrders}/>)

		const tdStyle = {
			verticalAlign: "middle"
		}

		let orderDetailView = ""
		if (this.state.showOrder){
			let orderDetailHeader = <h3>Order {this.state.selectedOrder.id}</h3>
			let orderLines = []
			let index = 0
			let totalPrice = 0
			this.state.selectedOrder.lines.forEach((orderLine) => {
				index++
				totalPrice += +orderLine.totalPrice
				orderLines.push(
					<bs.Panel key={index}>
						<bs.Row>
							<bs.Col xs={2}>
								<bs.Thumbnail src={orderLine.book.imgPath}/>
							</bs.Col>
							<bs.Col xs={6}>
								<h3>{orderLine.book.title} <small>({orderLine.book.author})</small></h3>
								<h4>Seller: {orderLine.seller.username}</h4>
							</bs.Col>
							<bs.Col xs={4}>
								<h4>Unit price: {orderLine.unitPrice}</h4>
								<h4>Quantity: {orderLine.quantity}</h4>
								<h4>Extended price: {orderLine.totalPrice}</h4>
							</bs.Col>
						</bs.Row>
					</bs.Panel>
				)
			})

			let orderFooter = (
					<bs.Col>
						<h3>Total: AU$ {totalPrice}</h3>
						<h3>Shipping cost: AU$ {this.state.selectedOrder.postageCost}</h3>
					</bs.Col>
			)

			orderDetailView =(
				<ModalDialog showCloseButton doNotUseTitle header={orderDetailHeader} body={orderLines}
					onHide={() => this.setState({showOrder: false})} footer={orderFooter} />
			)
		}

		let ordersListView = ""
		if (!this.state.gettingOrders){
			ordersListView = (<StaticTable dataList={this.state.ordersList} headers={['Order Number', 'Purchase Time']}
					columns={['id', 'orderTimestamp']} tdStyle={tdStyle} trStyle={{cursor: "pointer"}}
					onRowClick={(order) => this.orderSelected(order)}/>
			)
		}

		return (
			<div>
				{orderDetailView}
				{loading}
				{ordersListView}
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(BuyOrdersView)