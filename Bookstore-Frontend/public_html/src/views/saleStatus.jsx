import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import InfiniteScroll from 'react-infinite-scroller'
import { StaticTable } from 'components'

class SaleStatusView extends BaseView {
	constructor(props){
		super(props)

		this.getSellingBooks = this.getSellingBooks.bind(this)
		this.getSoldBooks = this.getSoldBooks.bind(this)

		this.state.sellingBooks = []
		this.state.soldBooks = []
	}

	componentDidMount(){
		this.getSellingBooks()
		this.getSoldBooks()
	}

	getSellingBooks(){
		this.bookService.getSellingBooks()
			.then((resp) => {
				this.state.sellingBooks = resp
			})
			.always(() => {
				this.setState(this.state)
			})
	}

	getSoldBooks(){
		this.bookService.getSoldBooks()
			.then((resp) => {
				console.log(resp)
				this.state.soldBooks = resp
			})
			.always(() => {
				this.setState(this.state)
			})
	}

	render(){

		const tdStyle = {
			verticalAlign: "middle"
		}

		let sellingBooksData = []
		this.state.sellingBooks.forEach((sale) => {
			sellingBooksData.push({
				'isbn10': sale.book.isbn10,
				'isbn13': sale.book.isbn13,
				'title': sale.book.title,
				'author': sale.book.author,
				'bookCondition': sale.bookCondition,
				'quantity': sale.quantity,
				'price': sale.price
			})
		})

		const sellingBooksTable =(
			<StaticTable dataList={sellingBooksData} tdStyle={tdStyle}
				headers={['ISBN10', 'ISBN13', 'Title', 'Author', 'Book condition', 'Quantity', 'Price']}
				columns={['isbn10', 'isbn13', 'title', 'author', 'bookCondition', 'quantity', 'price']} />
		)

		const soldBooksData = []
		this.state.soldBooks.forEach((orderLine) => {
			soldBooksData.push({
				'title': orderLine.book.title,
				'author': orderLine.book.author,
				'quantity': orderLine.quantity,
				'unitPrice': orderLine.unitPrice,
				'totalPrice': orderLine.totalPrice,
				'shippingAddress': orderLine.shippingAddress
			})
		})

		const soldBooksTable =(
			<StaticTable dataList={soldBooksData} tdStyle={tdStyle}
				headers={['Title', 'Author', 'Quantity', 'Unit Price', 'Total Price', 'Shipping address']}
				columns={['title', 'author', 'quantity', 'unitPrice', 'totalPrice', 'shippingAddress']} />
		)

		let sellingBooksView = (
			<bs.Tab eventKey={1} title="Selling books">
				{sellingBooksTable}
			</bs.Tab>	
		)

		let soldOrderLinesView = (
			<bs.Tab eventKey={2} title="Sold books">
				{soldBooksTable}
			</bs.Tab>
		)

		return(
			<div>
				<bs.Tabs id="sale-status-menu">
					{sellingBooksView}
					{soldOrderLinesView}
				</bs.Tabs>
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(SaleStatusView)