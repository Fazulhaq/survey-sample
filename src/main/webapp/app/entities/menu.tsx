import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      <MenuItem to="/organization">
        <Translate contentKey="global.menu.entities.organization" />
      </MenuItem>
      <MenuItem to="/form">
        <Translate contentKey="global.menu.entities.form" />
      </MenuItem>
    </>
  );
};

export default EntitiesMenu;
