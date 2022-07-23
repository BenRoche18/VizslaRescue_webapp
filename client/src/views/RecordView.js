import { faEdit, faSave, faTrash, faUndo, faWindowClose } from "@fortawesome/free-solid-svg-icons";
import { AppBar, Button, Dialog, DialogTitle, DialogActions, DialogContent, IconButton, Slide, Toolbar, Typography, withStyles, TextField, Paper, Divider, Grid, CircularProgress, DialogContentText } from "@material-ui/core";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { withRouter } from 'react-router-dom'
import React from "react";
import axios from 'axios';
import flatten, { unflatten } from 'flat';
import Field from '../components/Field';

const styles = (theme) => ({
  appBar: {
    position: 'relative',
  },
  title: {
    marginLeft: theme.spacing(2),
    flex: 1,
  },
  body: {
    margin: theme.spacing(3),
    padding: theme.spacing(5),
    '& .MuiTextField-root': {
      width: '100%'
    }
  },
  formDivider: {
    marginBottom: theme.spacing(4)
  },
  formSection: {
    marginBottom: theme.spacing(5)
  }
});

const Transition = React.forwardRef(function Transition(props, ref) {
  return <Slide direction="up" ref={ref} {...props} />;
});

class RecordView extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      record: {},
      error: undefined,
      loading: false,
      pendingDelete: false,
      dialogInput: undefined
    };
  }

  get id() {
    return this.props.match.params.id;
  }

  get createMode() {
    return new URLSearchParams(this.props.location.search).has("create");
  }

  get editMode() {
    return new URLSearchParams(this.props.location.search).has("edit");
  }

  async componentDidMount() {
    if(!this.createMode)
    {
      this.fetch();
    }
  }

  async fetch() {
    this.setState({ loading: true });

    axios.get("/api/" + this.props.entity + "/" + this.id)
    .then(res => {
      this.setState({ 
      record: flatten(res.data),
      loading: false 
      })
    })
    .catch(err => {
        this.setState({
        error: err.response.data,
        loading: false
        })
    })
  }

  async delete() {
    this.setState({ loading: true });

    axios.delete("/api/" + this.props.entity + "/" +  this.id)
    .then(this.onClose.bind(this))
    .catch(err => {
      this.setState({
        loading: false,
        error: err.response.data
      })
    })
  }

  async create() {
    this.setState({ loading: true });

    axios.post("/api/" + this.props.entity, unflatten(this.state.record, { overwrite: true }))
    .then(res => {
      this.setState({
        record: flatten(res.data),
        loading: false 
      }, this.props.history.push({
        search: "",
        pathname: this.props.location.pathname + "/" + res.data.id
      }))
    })
    .catch(err => {
        this.setState({
        error: err.response.data,
        loading: false
        })
    })
  }

  async edit() {
    this.setState({ loading: true });

    axios.put("/api/" + this.props.entity + "/" +  this.id, unflatten(this.state.record, { overwrite: true }))
    .then(res => {
        this.setState({ 
        record: flatten(res.data),
        loading: false 
        }, () => this.props.history.push({ search: "" }))
    })
    .catch(err => {
        this.setState({
        error: err.response.data,
        loading: false
        })
    })
  }

  async updateRecord(field, value)
  {
    let record = this.state.record;
    record[field] = value;
    return this.setState({ record: flatten(record) });
  }

  onClose() {
    if(this.createMode) {
      // remove ?create
      this.props.history.push({ search: "" })
    }
    if(!!this.id) {
      // strip /id from path
      const path = this.props.location.pathname;
      this.props.history.push(path.substring(0, path.lastIndexOf('/')));
    }
  }

  renderDeleteConfirmationDialog() {
    const onClose = () => {
      this.setState({ pendingDelete: false, dialogInput: null })
    }

    return <Dialog
      open
      onClose={onClose}
    >
      <DialogTitle>
        Are you sure?
      </DialogTitle>
      <DialogContent>
        <DialogContentText>
          Type '{  this.id }' to confirm permanent deletion.
        </DialogContentText>
        <TextField
          value={this.state.dialogInput}
          onChange={(event) => this.setState({ dialogInput: event.target.value })}
        />
      </DialogContent>
      <DialogActions>
        <Button onClick={onClose} color="primary">
          Cancel
        </Button>
        <Button onClick={this.delete.bind(this)} color="secondary" disabled={this.state.dialogInput !== this.id}>
          Delete
        </Button>
      </DialogActions>
    </Dialog>
  }

  renderDeleteButton() {
    return <Button
      color="inherit"
      onClick={() => this.setState({ pendingDelete: true})}
      startIcon={<FontAwesomeIcon icon={faTrash} size="md"/>}
    >
      Delete
    </Button>
  }

  renderSaveButton() {
    return <Button 
      color="inherit"
      onClick={() => this.createMode ? this.create() : this.edit()}
      startIcon={<FontAwesomeIcon icon={faSave} size="md" />}
    >
      Save
    </Button>
  }

  renderCancelButton() {
    return  <Button 
      color="inherit" 
      onClick={() => {
        if(this.createMode) {
          this.onClose()
        } else {
          this.props.history.push({ search: "" });
          this.fetch();
        }
      }}
      startIcon={<FontAwesomeIcon icon={faUndo} size="md" />}
    >
      Cancel
    </Button>
  }

  renderEditButton() {
    return  <Button 
      color="inherit" 
      onClick={() =>  this.props.history.push({ search: "?edit" })}
      startIcon={<FontAwesomeIcon icon={faEdit} size="md" />}
    >
      Edit
    </Button>
  }

  renderToolbar() {
    const { classes } = this.props;

    return <AppBar className={classes.appBar}>
      <Toolbar>
        <IconButton edge="start" color="inherit" onClick={this.onClose.bind(this)}>
          <FontAwesomeIcon icon={faWindowClose} />
        </IconButton>
        <Typography variant="h6" className={classes.title}>
          {this.id ?? "New*"}
        </Typography>
        {this.editMode && this.renderDeleteButton()}
        {(this.editMode || this.createMode) && this.renderSaveButton(!this.state.id)}
        {(this.editMode || this.createMode) && this.renderCancelButton(!this.state.id)}
        {!this.editMode && !this.createMode && this.renderEditButton()}
      </Toolbar>
    </AppBar>
  }

  renderProperties(entityMetadata) {
    const { classes } = this.props;

    return <div>
      <Typography variant="overline">
        General Information
      </Typography>
      <Divider className={classes.formDivider} />
      <Grid container spacing={2} className={classes.formSection}>
        { entityMetadata.fields.map((fieldMetadata) => <Grid item xs={12}>
          <Field 
            metadata={this.props.metadata}
            entity={this.props.entity}
            fieldMetadata={fieldMetadata}
            readOnly={!this.editMode && !this.createMode}
            value={unflatten(this.state.record, { overwrite: true })[fieldMetadata.technicalName]}
            onChange={(value) => this.updateRecord(fieldMetadata.technicalName, value)}
          />
        </Grid>) }
      </Grid>
    </div>
  }

  renderBody(entityMetadata) {
    if(this.state.error) {
      return <div>
        <Typography variant="h6">
          {this.state.error.status + " - " + this.state.error.error}
        </Typography>
        <br />
        <Typography variant="body1">
          {this.state.error.message}
        </Typography>
      </div>
    } else {
      return <div>
        <form>
          {this.renderProperties(entityMetadata)}
        </form>
        { this.state.pendingDelete && this.renderDeleteConfirmationDialog() }
      </div>
    }
  }

  render() {
    const { classes } = this.props;
    const entityMetadata = this.props.metadata[this.props.entity];

    if(entityMetadata) {
      return <Dialog
        fullScreen
        open
        onClose={this.onClose.bind(this)}
        TransitionComponent={Transition}
      >
        {this.renderToolbar()}
        <Paper className={classes.body}>
          {this.state.loading ? <CircularProgress /> : this.renderBody(entityMetadata)}
        </Paper>
      </Dialog>
    } else {
      return <Typography>
      Requested Entity ({this.props.entity}) not found
    </Typography>
    }
  }
}

export default withRouter(withStyles(styles)(RecordView));