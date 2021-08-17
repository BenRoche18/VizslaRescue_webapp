import { faWindowClose } from "@fortawesome/free-solid-svg-icons";
import { AppBar, Button, Dialog, IconButton, Slide, Toolbar, Typography, withStyles, TextField, Paper, MenuItem, Divider, Grid, CircularProgress, FormControl, InputLabel } from "@material-ui/core";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React from "react";
import axios from 'axios';
import flatten from 'flat';


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
      loading: false
    };
  }

  async open() {
    this.setState({ loading: true, open: true });

    axios.get("/api/dog/" + this.props.id)
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

  renderEditButtons() {
    if(this.state.canEdit) {
      return <div>
        <Button 
          color="inherit"
        >
          Save
        </Button>
        <Button 
          color="inherit" 
          onClick={() => {
            this.setState({ canEdit: false })
            this.open();
          }}
        >
          Cancel
        </Button>
      </div>
    } else {
      return <Button 
        color="inherit" 
        onClick={() => this.setState({ canEdit: true })}
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
            value={this.state.name}
            variant="outlined"
            onChange={(event) => this.setState({ name: event.target.value })}
            InputProps={{
              readOnly: !this.state.canEdit
            }}
          />
        </Grid>
        
        <Grid item xs={6}>
          <TextField
            label="Breeders"
            value={this.state.breeders}
            variant="outlined"
            onChange={(event) => this.setState({ breeders: event.target.value })}
            InputProps={{
              readOnly: !this.state.canEdit
            }}
          />
        </Grid>

        <Grid item xs={6}>
          <TextField
            select
            label="Gender"
            defaultValue={this.state.gender}
            onChange={(event) => this.setState({ gender: event.target.value })}
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
            value={new Date(this.state.dob).toISOString().split('T')[0]}
            onChange={(event) => this.setState({ dob: new Date(event.target.value) })}
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
            value={this.state.additionalInformation}
            onChange={(event) => this.setState({ additionalInformation: event.target.value })}
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
            value={this.state.hipScore?.date && new Date(this.state.hipScore.date).toISOString().split('T')[0]}
            onChange={(event) => this.setState({ hipScore: { date: new Date(event.target.value) }})}
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
            value={this.state.hipScore?.left}
            onChange={(event) => this.setState({ hipScore: { left: event.target.value }})}
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
            value={this.state.hipScore?.right}
            onChange={(event) => this.setState({ hipScore: { right: event.target.value }})}
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

  renderPedigree() {
    const { classes } = this.props;

    const sire = <Dog name={this.state['sire.name']} id={this.state['sire.id']} classes={classes}/>

    return <>
      <Typography variant="overline">
        Pedigree
      </Typography>
      <Divider className={classes.formDivider} />
      <Grid container spacing={2}>
        <Grid item xs={6}>
          <TextField
            label="Sire"
            variant="outlined"
            input={sire}
          />
        </Grid>

        <Grid item xs={6}>
          <TextField
            label="Dam"
            value={this.state.damId}
            variant="outlined"
            onChange={(event) => this.setState({ damId: event.target.value })}
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
      console.log(this.state.error)
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
      return <form>
        {this.renderGeneralInformation()}
        {this.renderHealth()}
        {this.renderPedigree()}
      </form>
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