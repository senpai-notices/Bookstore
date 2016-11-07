import React from 'react'
import BaseView, { mapStateToProps, mapDispatchToProps } from 'views/baseView'
import { connect } from 'react-redux'
import * as bs from 'react-bootstrap'
import { FormInputText, FormAddressInput } from 'components'
import { ManageBooksView, SaleStatusView } from 'views'

class AdminDashboardView extends BaseView {
	render(){

		const addBookSaleTab = (
			<bs.Tab eventKey={1} title="Add/Edit books">
				<ManageBooksView/>
			</bs.Tab>
		)

		const saleStatusTab = (
			<bs.Tab eventKey={2} title="Sale status">
				<SaleStatusView/>
			</bs.Tab>	
		)

		return (
			<bs.Tabs id="dashboard-menu">
				{addBookSaleTab}
				{saleStatusTab}
			</bs.Tabs>
		)
	}
}

export default connect(mapStateToProps, mapDispatchToProps)(AdminDashboardView)