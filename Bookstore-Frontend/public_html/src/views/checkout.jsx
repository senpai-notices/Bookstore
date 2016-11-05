import React, { Component } from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import { LoadingSpinner, FormInputText, FormAddressInput } from 'components'
import { SalesListView } from 'views'
import * as bs from 'react-bootstrap'

class CheckoutView extends BaseView {
	constructor(props){
		super(props)

		this.calculateTotalPrice = this.calculateTotalPrice.bind(this)
		this.state.form_errors = {}
	}

	calculateTotalPrice(){
		let totalPrice = 0
		this.props.shoppingCart.items.forEach((item) =>{
			totalPrice += item.totalPrice
		})

		return totalPrice
	}

	render(){
		const form_errors = this.state.form_errors

		const checkoutView = (
			<div>
				<SalesListView />

				<bs.Row>
					<bs.Col xsOffset={8} xs={4}>
						<h4>Total price: AU$ {this.calculateTotalPrice()}</h4>
					</bs.Col>
				</bs.Row>

				<h3>Online payment</h3>
					<bs.Col xs={8}>
						<bs.Form>
							<bs.Row>
								<bs.Col xs={4}>
									<FormInputText label="Card Number" name="card_number"
													value={this.state.card_number} errorMessage={form_errors.card_number}
													onChange={this.handleChange} onFocus={() => this.state.form_errors.card_number = ""}
													required hideAsterisk/>
								</bs.Col>
								<bs.Col xs={4}>
									<FormInputText label="Card Name" name="card_name"
													value={this.state.card_name} errorMessage={form_errors.card_name}
													onChange={this.handleChange} onFocus={() => this.state.form_errors.card_name = ""}
													required hideAsterisk/>
								</bs.Col>
							</bs.Row>

							<bs.Row>
								<bs.Col xs={2}>
									<FormInputText label="Expiry Month" name="expiry_month" type="number"
													value={this.state.expiry_month} errorMessage={form_errors.expiry_month}
													onChange={this.handleChange} onFocus={() => this.state.form_errors.expiry_month = ""}
													required hideAsterisk/>
								</bs.Col>

								<bs.Col xs={2}>
									<FormInputText label="Expiry Year" name="expiry_year" type="number"
													value={this.state.expiry_year} errorMessage={form_errors.expiry_year}
													onChange={this.handleChange} onFocus={() => this.state.form_errors.expiry_year = ""}
													required hideAsterisk/>
								</bs.Col>

								<bs.Col xs={2}>
									<FormInputText label="CVC" name="cvc"
													value={this.state.cvc} errorMessage={form_errors.cvc}
													onChange={this.handleChange} onFocus={() => this.state.form_errors.cvc = ""}
													required hideAsterisk/>
								</bs.Col>

								<bs.Col xs={8}>
									<FormAddressInput required hideAsterisk label="Address"/>
								</bs.Col>
							</bs.Row>

						</bs.Form>
					</bs.Col>
			</div>
		)

		return (
			<div>
				{checkoutView}
			</div>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(CheckoutView)