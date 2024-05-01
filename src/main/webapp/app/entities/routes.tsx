import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';
import Organization from './organization';
import Form from './form';
import OrgResponsiblePerson from './org-responsible-person';
import Server from './server';
import Internet from './internet';
import Backup from './backup';
import NetworkConfigCheckList from './network-config-check-list';
import DatacenterDevice from './datacenter-device';
import ItDevice from './it-device';
import System from './system';

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="organization/*" element={<Organization />} />
        <Route path="form/*" element={<Form />} />
        <Route path="org-responsible-person/*" element={<OrgResponsiblePerson />} />
        <Route path="server/*" element={<Server />} />
        <Route path="internet/*" element={<Internet />} />
        <Route path="backup/*" element={<Backup />} />
        <Route path="network-config-check-list/*" element={<NetworkConfigCheckList />} />
        <Route path="datacenter-device/*" element={<DatacenterDevice />} />
        <Route path="it-device/*" element={<ItDevice />} />
        <Route path="system/*" element={<System />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
