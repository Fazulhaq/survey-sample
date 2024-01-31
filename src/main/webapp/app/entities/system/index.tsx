import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import System from './system';
import SystemDetail from './system-detail';
import SystemUpdate from './system-update';
import SystemDeleteDialog from './system-delete-dialog';

const SystemRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<System />} />
    <Route path="new" element={<SystemUpdate />} />
    <Route path=":id">
      <Route index element={<SystemDetail />} />
      <Route path="edit" element={<SystemUpdate />} />
      <Route path="delete" element={<SystemDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SystemRoutes;
