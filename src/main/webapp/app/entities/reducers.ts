import organization from 'app/entities/organization/organization.reducer';
import form from 'app/entities/form/form.reducer';
import orgResponsiblePerson from 'app/entities/org-responsible-person/org-responsible-person.reducer';
import server from 'app/entities/server/server.reducer';
import internet from 'app/entities/internet/internet.reducer';
import backup from 'app/entities/backup/backup.reducer';
import networkConfigCheckList from 'app/entities/network-config-check-list/network-config-check-list.reducer';
import datacenterDevice from 'app/entities/datacenter-device/datacenter-device.reducer';
import itDevice from 'app/entities/it-device/it-device.reducer';
import system from 'app/entities/system/system.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  organization,
  form,
  orgResponsiblePerson,
  server,
  internet,
  backup,
  networkConfigCheckList,
  datacenterDevice,
  itDevice,
  system,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
