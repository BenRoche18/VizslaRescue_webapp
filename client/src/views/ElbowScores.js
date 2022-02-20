import { Button } from "@material-ui/core";
import React from "react";
import ListView from "../components/ListView";
import RecordView from "../components/RecordView";

class ElbowScores extends React.Component {
  state = {
    record: null
  }

  columns = [
    {
      field: "id",
      headerName: "ID",
      width: 200,
      type: 'number',
      renderCell: (cell) => <Button onClick={() => this.setState({ record: { id: cell.row.id } })}>
        {cell.row.id}
      </Button>
    },
    { 
      field: "brs", 
      headerName: "BRS", 
      width: 200,
      section: "General Information"
    },
    { 
      field: "date",
      headerName: "Date",
      width: 300,
      type: "date",
      section: "General Information"
    },
    {
      field: "score",
      headerName: "Score",
      width: 200,
      type: "number",
      section: "General Information"
    },
    {
      field: "dog",
      headerName: "Dog",
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
    }
  ]

  render() {
    if(this.state.record)
    {
      return <RecordView
        api="/api/elbow_scores"
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
        api="/api/elbow_scores"
        columns={this.columns}
        onCreate={() => this.setState({
          record: {}
        })}
      />
    }
  }
}

export default ElbowScores;
