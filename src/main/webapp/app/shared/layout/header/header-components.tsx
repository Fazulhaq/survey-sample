import React from 'react';
import { Translate } from 'react-jhipster';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavLink as Link } from 'react-router-dom';
import { NavItem, NavLink, NavbarBrand } from 'reactstrap';

export const Brand = () => (
  <NavbarBrand tag={Link} to="/" className="brand-logo">
    <span className="brand-title">
      <Translate contentKey="global.title">Survey-sample</Translate>
    </span>
  </NavbarBrand>
);

export const Home = () => (
  <NavItem>
    <NavLink tag={Link} to="/" className="d-flex align-items-center">
      <FontAwesomeIcon icon="home" />
      <span>
        <Translate contentKey="global.menu.home">Home</Translate>
      </span>
    </NavLink>
  </NavItem>
);
