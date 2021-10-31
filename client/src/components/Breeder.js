import { faEdit, faMars, faSave, faTrash, faUndo, faVenus, faWindowClose } from "@fortawesome/free-solid-svg-icons";
import { AppBar, Button, Dialog, DialogTitle, DialogActions, DialogContent, IconButton, Slide, Toolbar, Typography, withStyles, TextField, Paper, MenuItem, Divider, Grid, CircularProgress, DialogContentText } from "@material-ui/core";
import { Autocomplete } from '@material-ui/lab';
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

class Breeder extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      error: null,
      open: false,
      canEdit: false,
      loading: false,
      confirmationDialog: null
    };
  }

  async delete() {
    this.setState({ loading: true });

    axios.delete("/api/breeder/" + this.props.id)
    .then(res => {
      this.setState({ open: false })
    })
    .catch(err => {
      this.setState({
        loading: false,
        error: err.response.data
      })
    })
  }

  async save() {
    this.setState({ loading: true });

    axios.put("/api/breeder/" + this.props.id, unflatten(this.state).breeder)
    .then(res => {
      const flatRes = flatten(res.data);
      this.setState({ 
        ...flatRes,
        loading: false 
      })
    })
    .catch(err => {
      this.setState({
        loading: false,
        error: err.response.data
      })
    })
  }

  async open() {
    this.setState({ loading: true, open: true });

    axios.get("/api/breeder/" + this.props.id)
      .then(res => {
        const flatRes = flatten({ breeder: res.data });
        this.setState({ 
          ...flatRes,
          loading: false 
        })
      })
      .catch(err => {
        this.setState({
          loading: false,
          error: err.response.data
        })
      })
  }

  renderConfirmationDialog() {
    const onClose = () => {
      this.setState({ showConfirmationDialog: false, confirmationDialogInput: undefined })
    }

    if(this.state.showConfirmationDialog) {
      return <Dialog
        open
        onClose={onClose}
      >
        <DialogTitle>
          Are you sure?
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
            Type '{ this.state["breeder.names"] }' to confirm permanent deletion.
          </DialogContentText>
          <TextField
            value={this.confirmationDialogInput}
            onChange={(event) => this.setState({ confirmationDialogInput: event.target.value })}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={onClose} color="primary">
            Cancel
          </Button>
          <Button onClick={this.delete.bind(this)} color="secondary" disabled={this.state.confirmationDialogInput !== this.state["breeder.names"]}>
            Delete
          </Button>
        </DialogActions>
      </Dialog>
    }
  }

  renderEditButtons() {
    if(this.state.canEdit) {
      return <div>
        <Button
          color="inherit"
          onClick={() => this.setState({ showConfirmationDialog: true})}
          startIcon={<FontAwesomeIcon icon={faTrash} size="md"/>}
        >
          Delete
        </Button>
        <Button 
          color="inherit"
          onClick={() => {
            this.setState({ canEdit: false })
            this.save()
          }}
          startIcon={<FontAwesomeIcon icon={faSave} size="md" />}
        >
          Save
        </Button>
        <Button 
          color="inherit" 
          onClick={() => {
            this.setState({ canEdit: false })
            this.open();
          }}
          startIcon={<FontAwesomeIcon icon={faUndo} size="md" />}
        >
          Cancel
        </Button>
      </div>
    } else {
      return <Button 
        color="inherit" 
        onClick={() => this.setState({ canEdit: true })}
        startIcon={<FontAwesomeIcon icon={faEdit} size="md" />}
      >
        Edit
      </Button>
    }
  }

  renderAppBar() {
    const { classes } = this.props;

    return <AppBar className={classes.appBar}>
      <Toolbar>
        <IconButton edge="start" color="inherit" onClick={() => this.setState({ open: false })}>
          <FontAwesomeIcon icon={faWindowClose} />
        </IconButton>
        <Typography variant="h6" className={classes.title}>
          {this.props.name}
        </Typography>
        {this.renderEditButtons()}
      </Toolbar>
    </AppBar>
  }

  renderGeneralInformation() {
    const { classes } = this.props;

    return <>
      <Typography variant="overline">
        General Information
      </Typography>
      <Divider className={classes.formDivider} />
      <Grid container spacing={2} className={classes.formSection}>
        <Grid item xs={6}>
          <TextField
            label="Name"
            value={this.state["breeder.names"]}
            variant="outlined"
            onChange={(event) => this.setState({ "breeder.names": event.target.value })}
            InputProps={{
              readOnly: !this.state.canEdit
            }}
          />
        </Grid>

        <Grid item xs={12}>
          <TextField
            multiline
            minRows={3}
            label="Additional Information"
            value={this.state["breeder.additionalInformation"]}
            onChange={(event) => this.setState({ "breeder.additionalInformation": event.target.value })}
            variant="outlined"
            InputProps={{
              readOnly: !this.state.canEdit
            }}
          />
        </Grid>
      </Grid>
    </>
  }

  renderProperties() {
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
      return <>
        <form>
          {this.renderGeneralInformation()}
        </form>
        { this.renderConfirmationDialog() }
      </>
    }
  }

  render() {
    const { classes } = this.props;

    if(this.state.open) {
      return <Dialog
          fullScreen
          open
          onClose={() => this.setState({ open: false })}
          TransitionComponent={Transition}
        >
          {this.renderAppBar()}
          <Paper className={classes.body}>
            {this.state.loading ? <CircularProgress /> : this.renderProperties()}
          </Paper>
        </Dialog>
    } else {
      return <Button onClick={this.open.bind(this)}>
        { this.props.name }
      </Button>
    }
  }
}

export default withStyles(styles)(Breeder);