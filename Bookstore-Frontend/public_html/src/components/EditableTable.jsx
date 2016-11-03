import React, { Component } from 'react'
import * as bs from 'react-bootstrap'
import { BooksRow } from 'components'
import InlineEdit from 'react-edit-inline'

class EditableTable extends React.Component {

	constructor(props){
		super(props)
		this.onItemUpdated = this.onItemUpdated.bind(this)
	}

	onItemUpdated(item, data){
		for (let key in data) {
			item[key] = data[key]
		}
		this.props.onChange(this.props.dataList)
	}

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
					if (item[columnKey] instanceof Array || item[columnKey] instanceof Object){
						columnsView.push(
							<td key={columnKey} style={tdStyle}>{item[columnKey]}</td>
						)	
					} else {
						columnsView.push(
							<td key={columnKey} style={tdStyle}>
								<InlineEdit text={item[columnKey] + ""}  
									paramName={columnKey} 
									change={(data) => this.onItemUpdated(item, data)} />
							</td>
						)
					}
				})

				rowsView.push(
					<tr style={trStyle} key={key}>
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

export default EditableTable