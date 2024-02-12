import backup from 'app/entities/backup/backup.reducer';
import datacenterDevice from 'app/entities/datacenter-device/datacenter-device.reducer';
import form from 'app/entities/form/form.reducer';
import internet from 'app/entities/internet/internet.reducer';
import itDevice from 'app/entities/it-device/it-device.reducer';
import networkConfigCheckList from 'app/entities/network-config-check-list/network-config-check-list.reducer';
import orgResponsiblePerson from 'app/entities/org-responsible-person/org-responsible-person.reducer';
import organization from 'app/entities/organization/organization.reducer';
import server from 'app/entities/server/server.reducer';
import system from 'app/entities/system/system.reducer';
import surveyeditindex from './form/survey-edit-index-reducer';
import index from './stepper-index/stepper-index.reducer';
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
  surveyeditindex,
  index,
  system,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
