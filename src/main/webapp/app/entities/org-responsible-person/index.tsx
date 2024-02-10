import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import OrgResponsiblePerson from './org-responsible-person';
import OrgResponsiblePersonDeleteDialog from './org-responsible-person-delete-dialog';
import OrgResponsiblePersonUpdate from './org-responsible-person-update';

const OrgResponsiblePersonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<OrgResponsiblePerson />} />
    <Route path="new" element={<OrgResponsiblePersonUpdate />} />
    <Route path=":id">
      {/* <Route index element={<OrgResponsiblePersonDetail />} /> */}
      <Route path="edit" element={<OrgResponsiblePersonUpdate />} />
      <Route path="delete" element={<OrgResponsiblePersonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OrgResponsiblePersonRoutes;
