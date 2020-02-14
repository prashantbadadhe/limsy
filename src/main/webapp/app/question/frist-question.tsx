/* eslint react/no-multi-comp: 0, react/prop-types: 0 */

import React from 'react';
import { Button, Modal, ModalBody, ModalFooter, ModalHeader } from 'reactstrap';

class FirstQuestion extends React.Component<{}, { modal: boolean }> {
  constructor(props) {
    super(props);
    this.state = {
      modal: true
    };
    this.toggle = this.toggle.bind(this);
    this.routeChange = this.routeChange.bind(this);
  }

  toggle() {
    this.setState({
      modal: !this.state.modal
    });
  }

  routeChange() {
    window.location.hash = '#/';
  }

  render() {
    return (
      <div>
        <Modal isOpen={this.state.modal} toggle={this.toggle}>
          <ModalHeader toggle={this.toggle}>First question</ModalHeader>
          <ModalBody>Ye lo tumhara pahla question popup !!!!!</ModalBody>
          <ModalFooter>
            <Button color="primary" onClick={this.routeChange}>
              Do Something
            </Button>{' '}
            <Button color="secondary" onClick={this.routeChange}>
              Cancel
            </Button>
          </ModalFooter>
        </Modal>
      </div>
    );
  }
}

export default FirstQuestion;
