import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ItDevice from './it-device';
import ItDeviceDetail from './it-device-detail';
import ItDeviceUpdate from './it-device-update';
import ItDeviceDeleteDialog from './it-device-delete-dialog';

const ItDeviceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ItDevice />} />
    <Route path="new" element={<ItDeviceUpdate />} />
    <Route path=":id">
      <Route index element={<ItDeviceDetail />} />
      <Route path="edit" element={<ItDeviceUpdate />} />
      <Route path="delete" element={<ItDeviceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ItDeviceRoutes;
