import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { BooksGrid, LoadingSpinner, ModalDialog, StaticTable } from 'components'
import InfiniteScroll from 'react-infinite-scroller'

class HomeView extends BaseView {

	constructor(props){
		super(props)

		this.state.books = []
		this.state.hasMore = true
		this.loadMoreBooks = this.loadMoreBooks.bind(this)
		this.getBookDetail = this.getBookDetail.bind(this)
		this.addToCart = this.addToCart.bind(this)
		this.isItemInCart = this.isItemInCart.bind(this)
	}

	loadMoreBooks(){
		this.bookService.getLatestBook(this.state.books.length, 20)
			.then( (resp) => {
				this.state.books = this.state.books.concat(resp)
				if (resp.length < 20){
					this.state.hasMore = false
				}
				this.setState(this.state)
			})
	}

	getBookDetail(book, event){
		event.preventDefault()
		this.setState({loadingBookDetail: true})
		this.bookService.getBookDetail(book.isbn10, book.isbn13, book.title)
			.then((bookDetail) => {
				this.state.selectedBook = bookDetail
				this.setState(this.state)
			})
			.always(() => {
				this.setState({loadingBookDetail: false})
			})
	}

	addToCart(sale){
		this.props.dispatch.addItem(sale)
	}

	isItemInCart(saleId){
		let retValue = false
		this.props.shoppingCart.items.forEach((item) =>{
			if (item.id === saleId){
				retValue = true
				return
			}
		})

		return retValue
	}

	render(){
		const loading = <LoadingSpinner visible/>

		let bookDetailView = ""
		if (this.state.selectedBook || this.state.loadingBookDetail){
			const selectedBook = this.state.selectedBook
			let bookDetailModalHeader = "Book information"
			let bookDetailModalBody = ""

			if (selectedBook){
				let salesInfo = ""
				if (selectedBook.sales.length > 0){

					selectedBook.sales.forEach((sale) => {
						let isItemInCart = this.isItemInCart(sale.id)
						sale.actions = (
							<bs.Button bsStyle="primary" disabled={isItemInCart}
								onClick={() => this.addToCart(sale)}>
								{(isItemInCart && "Added to cart") || "Add to cart"}
							</bs.Button>
						)
					})

					salesInfo = (
						<div>
							<h2>Sales information</h2>
							<StaticTable dataList={selectedBook.sales}
								headers={['Seller', 'Quantity', 'Price (AUD)', 'Book Condition', 'Actions']}
								columns={['sellerId', 'quantity', 'price', 'bookCondition', 'actions']}
								tdStyle={{verticalAlign: "middle"}} />
						</div>
					)

				} else {
					salesInfo = (<h3>This book is not available at the moment</h3>)
				}
				bookDetailModalBody = (
					<div>
						<bs.Row>
							<bs.Col xs={4}>
								<bs.Thumbnail src={selectedBook.imgPath}/>
							</bs.Col>
							<bs.Col xs={8}>
								<h2>{selectedBook.title}</h2>
								<h4><em>Author</em>: <strong>{selectedBook.author}</strong></h4>
								<h4>Published by <em>{selectedBook.publisher}</em> in {selectedBook.publishYear}</h4>
								<h5>{selectedBook.pageCount} pages</h5>
							</bs.Col>
						</bs.Row>

						<bs.Row>
							<bs.Col xs={12}>
								{salesInfo}
							</bs.Col>
						</bs.Row>
					</div>
				)
			} else {
				bookDetailModalBody = loading
			}
			

			bookDetailView = (
				<ModalDialog header={bookDetailModalHeader} body={bookDetailModalBody}
					showCloseButton
					onHide={() => this.setState({selectedBook: null})} />
			)
		}

		return (
			<InfiniteScroll pageStart={0} loadMore={this.loadMoreBooks} hasMore={this.state.hasMore} loader={loading}>
				{bookDetailView}
				<BooksGrid books={this.state.books} onItemClick={this.getBookDetail}/>
			</InfiniteScroll>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(HomeView)