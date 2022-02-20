import { Button } from "@material-ui/core";
import React from "react";
import ListView from "../components/ListView";
import RecordView from "../components/RecordView";

class Dogs extends React.Component {
  state = {
    record: null
  }

  columns = [
    {
      field: "id",
      headerName: "ID",
      width: 100,
      type: 'number',
      renderCell: (cell) => <Button onClick={() => this.setState({ record: { id: cell.row.id } })}>
        {cell.row.id}
      </Button>
    },
    { 
      field: "name", 
      headerName: "Name", 
      width: 500,
      section: "General Information"
    },
    { 
      field: "gender",
      headerName: "Gender",
      width: 150,
      section: "General Information",
      options: [
        "BITCH",
        "DOG"
      ]
    },
    { 
      field: "additionalDetails", 
      headerName: "Additional Details", 
      width: 500,
      hide: true,
      multiline: true,
      section: "General Information"
    },
    {
      field: "litter.id", 
      headerName: "ID", 
      width: 250,
      hide: true,
      type: "number",
      section: "Litter"
    },
    {
      field: "litter.date", 
      headerName: "Date of Birth", 
      width: 250,
      hide: true,
      readOnly: true,
      type: "date",
      section: "Litter"
    },
    {
      field: "wasCesarean",
      headerName: "Was Cesarean",
      width: 150,
      type: "boolean",
      hide: true,
      readOnly: true,
      section: "Litter"
    },
    {
      field: "litter.breeder.id", 
      headerName: "ID", 
      width: 250,
      hide: true,
      readOnly: true,
      type: "number",
      section: "Breeder"
    },
    {
      field: "litter.breeder.names", 
      headerName: "Names", 
      width: 250,
      hide: true,
      readOnly: true,
      section: "Breeder"
    }
  ]

  render() {
    if(this.state.record)
    {
      return <RecordView
        api="/api/dogs"
        columns={this.columns}
        record={this.state.record}
        onClose={() => this.setState({
          record: null
        })}
      />
    }
    else
    {
      return <ListView
        api="/api/dogs"
        columns={this.columns}
        onCreate={() => this.setState({
          record: {}
        })}
      />
    }
  }
}

export default Dogs;
