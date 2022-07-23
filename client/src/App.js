import React from 'react';
import axios from 'axios';
import { AppBar, CssBaseline, Divider, Drawer, List, ListItem, ListItemIcon, ListItemText, Toolbar, Typography, withStyles, Button, Avatar, Tooltip } from '@material-ui/core';
import { BrowserRouter, Link, Route, Switch } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';

import Home from './views/Home';
import RecordView from './views/RecordView';
import ListView from './views/ListView';

library.add(fas);

const drawerWidth = 240;

const styles = (theme) => ({
  root: {
    display: 'flex',
  },
  appBar: {
    zIndex: theme.zIndex.drawer + 1,
  },
  drawer: {
    width: drawerWidth,
    flexShrink: 0,
  },
  drawerPaper: {
    width: drawerWidth,
  },
  drawerContainer: {
    overflow: 'auto',
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3),
  },
});

class App extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      metadata: {}
    }

    axios.get("/api/metadata")
    .then(res => {
      this.setState({ 
        metadata: Object.assign({}, ...res.data.map((it) => ({[it.technicalName]: it})))
      })
    })

    axios.get("/api/user")
    .then(res => {
      if(res.data.username)
      {
        this.setState({ 
          profile: res.data
        })
      }
    })
  }

  entityRoute(props) {
    if(props.match.params.id || new URLSearchParams(props.location.search).has("create")) {
      return <RecordView 
        metadata={this.state.metadata}
        entity={props.match.params.entity}
        key={props.match.params.entity}
      />
    } else {
      return <ListView 
        metadata={this.state.metadata}
        entity={props.match.params.entity}
        key={props.match.params.entity} 
      />
    }
  }

  renderUserControls() {
    if(this.state.profile)
    {
      return <span style={{ display: "flex" }}>
        <Button onClick={() => window.location.replace("/logout")} color="inherit">
          Logout
        </Button>
        <Tooltip title={this.state.profile.username}>
          <Avatar>
            <FontAwesomeIcon icon="user" />
          </Avatar>
        </Tooltip>
      </span>
    }
    else
    {
      return <Button onClick={() => window.location.replace("/login")} color="inherit">
        Login
      </Button>
    }
  }

  renderNavLink(entityMetadata) {
    return <Link to={"/" + entityMetadata.technicalName} style={{ color: "inherit", textDecoration: "none" }}>
      <ListItem button>
        <ListItemIcon>
          <FontAwesomeIcon size="2x" icon={entityMetadata.icon ?? "question"} />
        </ListItemIcon>
        <ListItemText primary={entityMetadata.businessName} />
      </ListItem>
    </Link>
  }

  render() {
    const { classes } = this.props;

    return <BrowserRouter>
      <div className={classes.root} >
        <CssBaseline />
        <AppBar position="fixed" className={classes.appBar}>
          <Toolbar style={{ justifyContent: "space-between" }}>
            <Link to="/" style={{ color: "inherit", textDecoration: "none" }}>
              <Typography variant="h6" noWrap>
                Vizsla Rescue Dashboard
              </Typography>
            </Link>
            { this.renderUserControls() }
          </Toolbar>
        </AppBar>
        <Drawer
          className={classes.drawer}
          variant="permanent"
          classes={{
            paper: classes.drawerPaper,
          }}
        >
          <Toolbar />
          <div className={classes.drawerContainer}>
            <List>
              { Object.values(this.state.metadata).map(entityMetadata => this.renderNavLink(entityMetadata)) }
            </List>
            <Divider />
          </div>
        </Drawer>
        <main className={classes.content}>
          <Toolbar />
          <Switch>
            <Route path="/:entity/:id" render={(props) => this.entityRoute(props)} />
            <Route path="/:entity" render={(props) => this.entityRoute(props)} />
            <Route path="" component={Home} />
          </Switch>
        </main>
      </div>
    </BrowserRouter>
  }
}

export default withStyles(styles)(App);
