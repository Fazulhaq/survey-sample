import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Internet from './internet';
import InternetDetail from './internet-detail';
import InternetUpdate from './internet-update';
import InternetDeleteDialog from './internet-delete-dialog';

const InternetRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Internet />} />
    <Route path="new" element={<InternetUpdate />} />
    <Route path=":id">
      <Route index element={<InternetDetail />} />
      <Route path="edit" element={<InternetUpdate />} />
      <Route path="delete" element={<InternetDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InternetRoutes;
