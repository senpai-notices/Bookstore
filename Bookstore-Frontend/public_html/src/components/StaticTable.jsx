import React, { Component } from 'react'
import * as bs from 'react-bootstrap'
import { BooksRow } from 'components'

class StaticTable extends React.Component {

	render() {
		const { dataList, headers, columns, tdStyle, trStyle } = this.props

		let tableView = ""

		if (dataList.length > 0){
			let rowsView = []
			let key=0
			dataList.forEach((item) => {
				key++
				let columnsView = []
				columns.forEach((columnKey) =>{
					columnsView.push(
						<td key={columnKey} style={tdStyle}>
							{item[columnKey]}
						</td>
					)
				})

				rowsView.push(
					<tr key={key} style={trStyle} onClick={() => this.props.onRowClick(item)}>
						{columnsView}
					</tr>
				)
				
			})

			let headersView = []
			headers.forEach((header) => {
				headersView.push(
					<th key={header}>{header}</th>
				)
			})

			tableView = (
				<bs.Table striped bordered condensed hover>
					<thead>
						<tr>
							{headersView}
						</tr>
					</thead>
					<tbody>
						{rowsView}
					</tbody>
				</bs.Table>
			)
		}

		return (
			<div>
				{tableView}
			</div>
		)
	}
}

export default StaticTable