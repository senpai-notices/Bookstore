import React, { Component } from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import { LoadingSpinner, FormInputText, FormAddressInput } from 'components'
import { SalesListView } from 'views'
import * as bs from 'react-bootstrap'
import { browserHistory } from 'react-router'

class CheckoutView extends BaseView {
	constructor(props){
		super(props)

		this.calculateTotalPrice = this.calculateTotalPrice.bind(this)
		this.state.form_errors = {}
		this.onAddressSelected = this.onAddressSelected.bind(this)
	}

	componentWillReceiveProps(nextProps){
		if (nextProps.user.status !== 'loggedIn' 
			|| nextProps.shoppingCart.items.length === 0){
			browserHistory.replace("/")
		}
	}

	calculateTotalPrice(){
		let totalPrice = 0
		this.props.shoppingCart.items.forEach((item) =>{
			totalPrice += item.totalPrice
		})

		return totalPrice
	}

	onAddressSelected(event){
		let selectedAddress = event.target.value
		const addressParts = selectedAddress.split(',')

		this.state.address_1  = addressParts[0]
		this.state.address_city = addressParts[1]
		this.state.address_state = addressParts[2]
		this.state.address_country = addressParts[3]
		this.setState(this.state)

		this.addressSuggest.setValue(this.state.address_1)
	}

	render(){
		const temp = (<bs.Row>
					<bs.Col xsOffset={8} xs={4}>
						<h4>Total price: AU$ {this.calculateTotalPrice()}</h4>
					</bs.Col>
				</bs.Row>)
		const form_errors = this.state.form_errors

		const checkoutView = (
			<div>
				<SalesListView />

				<bs.Form style={{height: "500px"}}>
				<bs.Col xs={5}>
					<h3>Online payment</h3>
					<bs.Row>
						<bs.Col xs={12}>
							<FormInputText label="Card Number" name="card.number"
											value={this.state['card.number']} errorMessage={form_errors['card.number']}
											onChange={this.handleChange} onFocus={() => this.state.form_errors['card.number'] = ""}
											required hideAsterisk/>
						</bs.Col>
					</bs.Row>
					<bs.Row>
						<bs.Col xs={12}>
							<FormInputText label="Card Name" name="card.name"
											value={this.state['card.name']} errorMessage={form_errors['card.name']}
											onChange={this.handleChange} onFocus={() => this.state.form_errors['card.name'] = ""}
											required hideAsterisk/>
						</bs.Col>
					</bs.Row>
					<bs.Row>
						<bs.Col xs={3}>
							<FormInputText label="Expiry Month" name="card.expiry_month" type="number"
											value={this.state['card.expiry_month']} errorMessage={form_errors['card.expiry_month']}
											onChange={this.handleChange} onFocus={() => this.state.form_errors['card.expiry_month'] = ""}
											required hideAsterisk/>
						</bs.Col>

						<bs.Col xs={3}>
							<FormInputText label="Expiry Year" name="card.expiry_year" type="number"
											value={this.state['card.expiry_year']} errorMessage={form_errors['card.expiry_year']}
											onChange={this.handleChange} onFocus={() => this.state.form_errors['card.expiry_year'] = ""}
											required hideAsterisk/>
						</bs.Col>
					</bs.Row>
					<bs.Row>
						<bs.Col xs={3}>
							<FormInputText label="CVC" name="card.cvc"
											value={this.state['card.cvc']} errorMessage={form_errors['card.cvc']}
											onChange={this.handleChange} onFocus={() => this.state.form_errors.cvc = ""}
											required hideAsterisk/>
						</bs.Col>
					</bs.Row>

					<bs.Button type="submit" bsStyle="primary">Submit</bs.Button>
				</bs.Col>

				<bs.Col xs={6}>
				<h3>Shipping detail</h3>
					<bs.Row>
						<bs.Col xs={12}>
							<FormAddressInput label="Address line 1" name="address_1"
												value={this.state.address_1} errorMessage={form_errors.address_1}
												onAddressSelected={this.onAddressSelected}
												onChange={this.handleChange} ref={(input) => this.addressSuggest = input}
												required/>
						</bs.Col>
					</bs.Row>
					<bs.Row>
						<bs.Col xs={12}>
							<FormInputText label="Address line 2" name="address_2"
											value={this.state.address_2} errorMessage={form_errors.address_2}
											onChange={this.handleChange} onFocus={() => this.state.form_errors.address_2 = ""}
											style={{width: "203%"}}/>
						</bs.Col>
					</bs.Row>

					<bs.Row>
						<bs.Col xs={6}>
							<FormInputText label="City" name="address_city"
											value={this.state.address_city} errorMessage={form_errors.address_city}
											onChange={this.handleChange} onFocus={() => this.state.form_errors.address_city = ""}
											required/>
						</bs.Col>
						<bs.Col xs={6}>
							<FormInputText label="State" name="address_state"
											value={this.state.address_state} errorMessage={form_errors.address_state}
											onChange={this.handleChange} onFocus={() => this.state.form_errors.address_state = ""}
											required/>
						</bs.Col>
					</bs.Row>

					<bs.Row>
						<bs.Col xs={6}>
							<FormInputText label="Postcode" name="address_postcode"
											value={this.state.address_postcode} errorMessage={form_errors.address_postcode}
											onChange={this.handleChange} onFocus={() => this.state.form_errors.address_postcode = ""}
											required/>
						</bs.Col>
						<bs.Col xs={6}>
							<FormInputText label="Country" name="address_country"
											value={this.state.address_country} errorMessage={form_errors.address_country}
											onChange={this.handleChange} onFocus={() => this.state.form_errors.address_country = ""}
											required/>
						</bs.Col>

						<bs.Button type="submit" bsStyle="primary">Calculate cost</bs.Button>
					</bs.Row>
				</bs.Col>
				</bs.Form>
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