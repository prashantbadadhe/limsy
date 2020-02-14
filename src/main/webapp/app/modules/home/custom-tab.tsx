/*
import React, { Component } from 'react';
import { TabContent, TabPane, Nav, NavItem, NavLink, Card, Button, CardTitle, CardText, Row, Col } from 'reactstrap';
import classnames from 'classnames';
import Outfit from 'app/dashboard/outfit';
import Suggestion from 'app/dashboard/suggestion';
import DressUsed from 'app/dashboard/dress-used';

export default class CustomTab extends Component<{}, { activeTab: string }> {
  constructor(props) {
    super(props);

    this.toggle = this.toggle.bind(this);
    this.state = {
      activeTab: '1'
    };
  }

  toggle(tab) {
    if (this.state.activeTab !== tab) {
      this.setState({
        activeTab: tab
      });
    }
  }
  render() {
    return (
      <div>
        <Nav tabs>
          <NavItem>
            <NavLink
              className={classnames({ active: this.state.activeTab === '1' })}
              onClick={() => {
                this.toggle('1');
              }}
            >
              Outfits
            </NavLink>
          </NavItem>

          <NavItem>
            <NavLink
              className={classnames({ active: this.state.activeTab === '2' })}
              onClick={() => {
                this.toggle('2');
              }}
            >
              Suggestion
            </NavLink>
          </NavItem>

          <NavItem>
            <NavLink
              className={classnames({ active: this.state.activeTab === '3' })}
              onClick={() => {
                this.toggle('3');
              }}
            >
              Uploads
            </NavLink>
          </NavItem>

          <NavItem>
            <NavLink
              className={classnames({ active: this.state.activeTab === '4' })}
              onClick={() => {
                this.toggle('4');
              }}
            >
              Training
            </NavLink>
          </NavItem>
        </Nav>
        <TabContent activeTab={this.state.activeTab}>
          <TabPane tabId="1">
            <Row>
              <Col sm="12">
                <h4>Outfits</h4>
                <Outfit />
              </Col>
            </Row>
          </TabPane>

          <TabPane tabId="2">
            <Row>
              <Col sm="12">
                <h4>Suggestions</h4>
                <Suggestion />
              </Col>
            </Row>
          </TabPane>

          <TabPane tabId="3">
            <Row>
              <Col sm="12">
                <h4>Dress Used</h4>
                <DressUsed />
              </Col>
            </Row>
          </TabPane>

          <TabPane tabId="4">
            <br />
            <Row>
              <Col sm="4">
                <Card body>
                  <CardTitle>Colors</CardTitle>
                  <CardText>With supporting text below as a natural lead-in to additional content.</CardText>
                  <Button>Submit</Button>
                </Card>
              </Col>
              <Col sm="4">
                <Card body>
                  <CardTitle>Cloth Choices</CardTitle>
                  <CardText>With supporting text below as a natural lead-in to additional content.</CardText>
                  <Button>Submit</Button>
                </Card>
              </Col>
              <Col sm="4">
                <Card body>
                  <CardTitle>Other Accessories</CardTitle>
                  <CardText>With supporting text below as a natural lead-in to additional content.</CardText>
                  <Button>Submit</Button>
                </Card>
              </Col>
            </Row>
            <br />
            <Row>
              <Col sm="4">
                <Card body>
                  <CardTitle>Events</CardTitle>
                  <CardText>With supporting text below as a natural lead-in to additional content.</CardText>
                  <Button>Submit</Button>
                </Card>
              </Col>
            </Row>
          </TabPane>
        </TabContent>
      </div>
    );
  }
}
*/
