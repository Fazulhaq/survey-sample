import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DatacenterDevice from './datacenter-device';
import DatacenterDeviceDeleteDialog from './datacenter-device-delete-dialog';
import DatacenterDeviceUpdate from './datacenter-device-update';

const DatacenterDeviceRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DatacenterDevice />} />
    <Route path="new" element={<DatacenterDeviceUpdate />} />
    <Route path=":id">
      {/* <Route index element={<DatacenterDeviceDetail />} /> */}
      <Route path="edit" element={<DatacenterDeviceUpdate />} />
      <Route path="delete" element={<DatacenterDeviceDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DatacenterDeviceRoutes;
