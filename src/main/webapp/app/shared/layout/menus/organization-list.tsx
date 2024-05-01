import React from 'react';
import { Translate } from 'react-jhipster';
import { NavItem, NavLink } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Link } from 'react-router-dom';

export const FormList = () => (
  <NavItem>
    <NavLink tag={Link} to="/form" className="d-flex align-items-center">
      <FontAwesomeIcon icon="th-list" />
      <span>
        <Translate contentKey="global.menu.entities.form">Form</Translate>
      </span>
    </NavLink>
  </NavItem>
);
