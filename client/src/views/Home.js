import { Card, CardMedia, Container, withStyles } from "@material-ui/core";
import React from "react";


const styles = (theme) => ({
  
});

class Home extends React.Component {
  render() {
    return <Container>
      <Card>
        <CardMedia
          component="img"
          image="https://c.tenor.com/cXYPZhsqJlkAAAAC/chien-perplexe.gif"
          alt="gizsla"
        />
      </Card>
    </Container>
  }
}

export default withStyles(styles)(Home);