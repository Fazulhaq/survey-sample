import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Backup from './backup';
import BackupDetail from './backup-detail';
import BackupUpdate from './backup-update';
import BackupDeleteDialog from './backup-delete-dialog';

const BackupRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Backup />} />
    <Route path="new" element={<BackupUpdate />} />
    <Route path=":id">
      <Route index element={<BackupDetail />} />
      <Route path="edit" element={<BackupUpdate />} />
      <Route path="delete" element={<BackupDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BackupRoutes;
