import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Server from './server';
import ServerDeleteDialog from './server-delete-dialog';
import ServerDetail from './server-detail';

const ServerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Server />} />
    {/* <Route path="new" element={<ServerUpdate />} /> */}
    <Route path=":id">
      <Route index element={<ServerDetail />} />
      {/* <Route path="edit" element={<ServerUpdate />} /> */}
      <Route path="delete" element={<ServerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ServerRoutes;
