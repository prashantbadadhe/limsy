import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/location">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Location
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/category">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Category
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/dress">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Dress
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/suggestion">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Suggestion
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/selected-suggestion">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Selected Suggestion
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/user-additional">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;User Additional
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/used">
      <FontAwesomeIcon icon="asterisk" fixedWidth />&nbsp;Used
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
