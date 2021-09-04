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

class Dog extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      error: null,
      open: false,
      canEdit: false,
      loading: false,
      possibleSires: [],
      possibleDams: [],
      confirmationDialog: null
    };
  }

  async delete() {
    this.setState({ loading: true });

    axios.delete("/api/dog/" + this.props.id)
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

    axios.put("/api/dog/" + this.props.id, unflatten(this.state).dog)
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

    axios.get("/api/dog/" + this.props.id)
      .then(res => {
        const flatRes = flatten({ dog: res.data });
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

  async updatePossibleParents(isSire, name) {
    const params = {
      page: 0,
      pageSize: 20,
      sortKeys: "name;asc",
      filters: "gender equals " + (isSire ? "D" : "B") + ",name startsWith '" + name + "'"
    }

    axios.get("/api/dogs", { params })
    .then(res => {
      const possibleDogs = res.data.content.map((dog) => {
        return dog.name;
      })

      if(isSire) {
        this.setState({ possibleSires: possibleDogs })
      } else {
        this.setState({ possibleDams: possibleDogs })
      }
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
            Type '{ this.state["dog.name"] }' to confirm permanent deletion.
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
          <Button onClick={this.delete.bind(this)} color="secondary" disabled={this.state.confirmationDialogInput !== this.state["dog.name"]}>
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
            value={this.state["dog.name"]}
            variant="outlined"
            onChange={(event) => this.setState({ "dog.name": event.target.value })}
            InputProps={{
              readOnly: !this.state.canEdit
            }}
          />
        </Grid>
        
        <Grid item xs={6}>
          <TextField
            label="Breeders"
            value={this.state["dog.breeders"]}
            variant="outlined"
            onChange={(event) => this.setState({ "dog.breeders": event.target.value })}
            InputProps={{
              readOnly: !this.state.canEdit
            }}
          />
        </Grid>

        <Grid item xs={6}>
          <TextField
            select
            label="Gender"
            defaultValue={this.state["dog.gender"]}
            onChange={(event) => this.setState({ "dog.gender": event.target.value })}
            variant="outlined"
            InputProps={{
              readOnly: !this.state.canEdit
            }}
          >
            <MenuItem value=""></MenuItem>
            <MenuItem value="B">Bitch</MenuItem>
            <MenuItem value="D">Dog</MenuItem>
          </TextField>
        </Grid>

        <Grid item xs={6}>
          <TextField
            label="Date of Birth"
            value={new Date(this.state["dog.dob"]).toISOString().split('T')[0]}
            onChange={(event) => this.setState({ "dog.dob": new Date(event.target.value) })}
            variant="outlined"
            type="date"
            InputProps={{
              readOnly: !this.state.canEdit
            }}
            InputLabelProps={{
              shrink: true,
            }}
          />
        </Grid>

        <Grid item xs={12}>
          <TextField
            multiline
            minRows={3}
            label="Additional Information"
            value={this.state["dog.additionalInformation"]}
            onChange={(event) => this.setState({ "dog.additionalInformation": event.target.value })}
            variant="outlined"
            InputProps={{
              readOnly: !this.state.canEdit
            }}
          />
        </Grid>
      </Grid>
    </>
  }

  renderHealth() {
    const { classes } = this.props;

    return <>
      <Typography variant="overline">
        Health
      </Typography>
      <Divider className={classes.formDivider} />
      <Grid container spacing={2} className={classes.formSection}>
        <Grid item xs={4}>
          <TextField
            label="Date of Hip Score"
            value={this.state["dog.hipScore.date"] && new Date(this.state["dog.hipScore.date"]).toISOString().split('T')[0]}
            onChange={(event) => this.setState({ "dog.hipScore.date": new Date(event.target.value) })}
            variant="outlined"
            type="date"
            InputProps={{
              readOnly: !this.state.canEdit
            }}
            InputLabelProps={{
              shrink: true,
            }}
          />
        </Grid>

        <Grid item xs={4}>
          <TextField
            label="Left"
            value={this.state["dog.hipScore.left"]}
            onChange={(event) => this.setState({ "dog.hipScore.left": event.target.value })}
            variant="outlined"
            type="number"
            InputProps={{
              readOnly: !this.state.canEdit
            }}
          />
        </Grid>

        <Grid item xs={4}>
          <TextField
            label="Right"
            value={this.state["dog.hipScore.right"]}
            onChange={(event) => this.setState({ "dog.hipScore.right": event.target.value })}
            variant="outlined"
            type="number"
            InputProps={{
              readOnly: !this.state.canEdit
            }}
          />
        </Grid>
        
      </Grid>
    </>
  }

  renderParent(isSire) {
    const { classes } = this.props;

    if(this.state.canEdit) {
      return <Autocomplete
        options={isSire ? this.state.possibleSires : this.state.possibleDams}
        value={isSire ? this.state["dog.sire.name"] : this.state["dog.dam.name"]}
        onInputChange={(event, value) =>  this.updatePossibleParents(isSire, value)}
        onChange={(event, value) => isSire ? this.setState({ "dog.sire.name": value }) : this.setState({ "dog.dam.name": value })}
        renderInput={(params) =>
          <TextField
            {...params}
            label={isSire ? "Sire" : "Dam"}
            variant="outlined"
          />
        }
      />
    } else {
      return <div>
        <FontAwesomeIcon icon={isSire ? faMars : faVenus} size="lg"/>
        <Dog 
          name={isSire ? this.state["dog.sire.name"] : this.state["dog.dam.name"]} 
          id={isSire ? this.state["dog.sire.id"] : this.state["dog.dam.id"]} 
          classes={classes}
        />
      </div>
    }
  }

  renderPedigree() {
    const { classes } = this.props;

    return <>
      <Typography variant="overline">
        Pedigree
      </Typography>
      <Divider className={classes.formDivider} />
      <Grid container spacing={2}>
        <Grid item xs={6}>
          {this.renderParent(true)}
        </Grid>
        <Grid item xs={6}>
          {this.renderParent(false)}
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
          {this.renderHealth()}
          {this.renderPedigree()}
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

export default withStyles(styles)(Dog);