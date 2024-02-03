import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NetworkConfigCheckList from './network-config-check-list';
import NetworkConfigCheckListDeleteDialog from './network-config-check-list-delete-dialog';
import NetworkConfigCheckListDetail from './network-config-check-list-detail';

const NetworkConfigCheckListRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NetworkConfigCheckList />} />
    {/* <Route path="new" element={<NetworkConfigCheckListUpdate />} /> */}
    <Route path=":id">
      <Route index element={<NetworkConfigCheckListDetail />} />
      {/* <Route path="edit" element={<NetworkConfigCheckListUpdate />} /> */}
      <Route path="delete" element={<NetworkConfigCheckListDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NetworkConfigCheckListRoutes;
