import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      <MenuItem icon="asterisk" to="/organization">
        <Translate contentKey="global.menu.entities.organization" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/form">
        <Translate contentKey="global.menu.entities.form" />
      </MenuItem>
    </>
  );
};

export default EntitiesMenu;
