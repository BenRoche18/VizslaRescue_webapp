import { AppBar, CssBaseline, Divider, Drawer, List, ListItem, ListItemIcon, ListItemText, Toolbar, Typography, withStyles } from '@material-ui/core';
import React from 'react';
import { BrowserRouter, Link, Route, Switch } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDog } from '@fortawesome/free-solid-svg-icons';

import Dogs from './views/Dogs';
import Home from './views/Home';

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

  renderNavLink(icon, to, text) {
    return <Link to={to} style={{ color: "inherit", textDecoration: "none" }}>
      <ListItem button>
        <ListItemIcon>
          <FontAwesomeIcon size="2x" icon={icon} />
        </ListItemIcon>
        <ListItemText primary={text} />
      </ListItem>
    </Link>
  }

  render() {
    const { classes } = this.props;

    return <BrowserRouter>
      <div className={classes.root} >
        <CssBaseline />
        <AppBar position="fixed" className={classes.appBar}>
          <Toolbar>
            <Typography variant="h6" noWrap>
              Vizsla Rescue Dashboard
            </Typography>
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
              {this.renderNavLink(faDog, "/dogs", "Dogs")}
            </List>
            <Divider />
          </div>
        </Drawer>
        <main className={classes.content}>
          <Toolbar />
          <Switch>
            <Route path="/dogs" component={Dogs} />
            <Route path="/" component={Home} />
          </Switch>
        </main>
      </div>
    </BrowserRouter>
  }
}

export default withStyles(styles)(App);
