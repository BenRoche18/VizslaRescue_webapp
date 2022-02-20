import { faEdit, faSave, faTrash, faUndo, faWindowClose } from "@fortawesome/free-solid-svg-icons";
import { AppBar, Button, Dialog, DialogTitle, DialogActions, DialogContent, IconButton, Slide, Toolbar, Typography, withStyles, TextField, Paper, Divider, Grid, CircularProgress, DialogContentText, MenuItem } from "@material-ui/core";
import DateAdapter from '@mui/lab/AdapterDateFns';
import { DesktopDatePicker, LocalizationProvider } from "@mui/lab"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from "react";
import axios from 'axios';
import flatten, { unflatten } from 'flat';

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
      record: props.record,
      error: null,
      canEdit: !props.record.id,
      loading: false,
      pendingDelete: false,
      dialogInput: null
    };
  }

  async componentDidMount() {
    if(this.state.record.id)
    {
      this.fetch();
    }
  }

  async fetch() {
    this.setState({ loading: true });

    axios.get(this.props.api + "/" + this.state.record.id)
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

    axios.delete(this.props.api + "/" + this.state.record.id)
    .then(this.props.onClose)
    .catch(err => {
      this.setState({
        loading: false,
        error: err.response.data
      })
    })
  }

  async create() {
    this.setState({ loading: true });

    axios.post(this.props.api, unflatten(this.state.record, { overwrite: true }))
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

  async edit() {
    this.setState({ loading: true });

    axios.put(this.props.api + "/" + this.state.record.id, unflatten(this.state.record, { overwrite: true }))
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

  async updateRecord(field, value)
  {
    let record = this.state.record;
    record[field] = value;
    return this.setState({ record: record });
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
          Type '{ this.state.record.id }' to confirm permanent deletion.
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
        <Button onClick={this.delete.bind(this)} color="secondary" disabled={this.state.dialogInput != this.state.record.id}>
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

  renderSaveButton(createMode = false) {
    return <Button 
      color="inherit"
      onClick={() => {
        this.setState({ canEdit: false })
        createMode ? this.create() : this.edit()
      }}
      startIcon={<FontAwesomeIcon icon={faSave} size="md" />}
    >
      Save
    </Button>
  }

  renderCancelButton(createMode = false) {
    return  <Button 
      color="inherit" 
      onClick={() => {
        this.setState({ canEdit: false })
        createMode ? this.props.onClose() : this.fetch();
      }}
      startIcon={<FontAwesomeIcon icon={faUndo} size="md" />}
    >
      Cancel
    </Button>
  }

  renderEditButton() {
    return  <Button 
      color="inherit" 
      onClick={() => this.setState({ canEdit: true })}
      startIcon={<FontAwesomeIcon icon={faEdit} size="md" />}
    >
      Edit
    </Button>
  }

  renderToolbar() {
    const { classes } = this.props;

    return <AppBar className={classes.appBar}>
      <Toolbar>
        <IconButton edge="start" color="inherit" onClick={this.props.onClose}>
          <FontAwesomeIcon icon={faWindowClose} />
        </IconButton>
        <Typography variant="h6" className={classes.title}>
          {this.state.record.id ?? "New*"}
        </Typography>
        {this.state.canEdit && this.state.record.id && this.renderDeleteButton()}
        {this.state.canEdit && this.renderSaveButton(!this.state.record.id)}
        {this.state.canEdit && this.renderCancelButton(!this.state.record.id)}
        {!this.state.canEdit && this.renderEditButton()}
      </Toolbar>
    </AppBar>
  }

  renderProperties() {
    let sections = {};

    this.props.columns.forEach((column) => {
      if(!!column.section)
      {
        if(sections[column.section] == undefined)
        {
          sections[column.section] = [];
        }
        sections[column.section].push(column)
      }
    })

    return <div>
      {Object.keys(sections).map((key) => this.renderSection(key, sections[key]))}
    </div>
  }

  renderSection(label, fields) {
    const { classes } = this.props;

    return <div>
      <Typography variant="overline">
        {label}
      </Typography>
      <Divider className={classes.formDivider} />
      <Grid container spacing={2} className={classes.formSection}>
        { fields.map((field) => this.renderField(field)) }
      </Grid>
    </div>
  }

  renderField(field) {
    console.log(field)

    const component = field.type != "date"
      ? <TextField
        label={field.headerName}
        select={field.options}
        value={this.state.record[field.field]}
        variant="outlined"
        onChange={(event) => this.updateRecord(field.field, event.target.value)}
        InputProps={{
          readOnly: !this.state.canEdit || field.readOnly
        }}
        type={field.type}
        multiline={field.multiline}
      >
        { field.options && field.options.map((option) => <MenuItem key={option} value={option}>
          {option}
        </MenuItem>) }
      </TextField>
      : <LocalizationProvider dateAdapter={DateAdapter}>
        <DesktopDatePicker
          label={field.headerName}
          inputFormat="MM/dd/yyyy"
          value={this.state.record[field.field]}
          onChange={(value) => this.updateRecord(field.field, value)}
          readOnly={!this.state.canEdit || field.readOnly}
          renderInput={(params) => <TextField 
            {...params} 
            variant="outlined"
          />}
        />
      </LocalizationProvider>

    return <Grid item xs={12}>
      { component }
    </Grid>
  }

  renderBody() {
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
          {this.renderProperties()}
        </form>
        { this.state.pendingDelete && this.renderDeleteConfirmationDialog() }
      </div>
    }
  }

  render() {
    const { classes } = this.props;

    return <Dialog
      fullScreen
      open
      onClose={this.props.onClose}
      TransitionComponent={Transition}
    >
      {this.renderToolbar()}
      <Paper className={classes.body}>
        {this.state.loading ? <CircularProgress /> : this.renderBody()}
      </Paper>
    </Dialog>
  }
}

export default withStyles(styles)(RecordView);