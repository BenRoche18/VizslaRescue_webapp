import { Button } from "@material-ui/core";
import React from "react";
import ListView from "../components/ListView";
import RecordView from "../components/RecordView";

class Litters extends React.Component {
  state = {
    record: null
  }

  columns = [
    {
      field: "id",
      headerName: "ID",
      width: 150,
      type: 'number',
      renderCell: (cell) => <Button onClick={() => this.setState({ record: { id: cell.row.id } })}>
        {cell.row.id}
      </Button>
    },
    { 
      field: "brs", 
      headerName: "BRS", 
      width: 150,
      section: "General Information"
    },
    { 
      field: "date",
      headerName: "Date",
      width: 200,
      type: "date",
      section: "General Information"
    },
    { 
      field: "wasCesarean",
      headerName: "Was Cesarean",
      width: 150,
      type: "boolean",
      hide: true,
      section: "General Information"
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
      field: "breeder.id", 
      headerName: "ID", 
      width: 250,
      hide: true,
      type: "number",
      section: "Breeders"
    },
    {
      field: "breeder.names", 
      headerName: "Names", 
      width: 250,
      hide: true,
      readOnly: true,
      section: "Breeders"
    }
  ]

  render() {
    if(this.state.record)
    {
      return <RecordView
        api="/api/litters"
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
        api="/api/litters"
        columns={this.columns}
        onCreate={() => this.setState({
          record: {}
        })}
      />
    }
  }
}

export default Litters;
