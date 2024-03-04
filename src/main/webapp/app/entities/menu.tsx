import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';
import { useAppSelector } from 'app/config/store';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';

const EntitiesMenu = () => {
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));

  return (
    <>
      <MenuItem to="/organization">
        <Translate contentKey="global.menu.entities.organization" />
      </MenuItem>
      <MenuItem to="/form">
        <Translate contentKey="global.menu.entities.form" />
      </MenuItem>
      {isAdmin && (
        <MenuItem to="/report">
          <Translate contentKey="global.menu.entities.report" />
        </MenuItem>
      )}
    </>
  );
};

export default EntitiesMenu;
