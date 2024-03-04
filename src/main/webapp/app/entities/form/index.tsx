import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Form from './form';
import FormDeleteDialog from './form-delete-dialog';
import InteractiveSteps from './form-stepper';
import SurveyEditPage from './survey-edit-page';
import SurveyView from './survey-view';
import SurveyPrint from './survey-print';

const FormRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Form />} />
    <Route path="new/:id" element={<InteractiveSteps />} />
    <Route path=":id">
      <Route index element={<SurveyView />} />
      <Route path="edit" element={<SurveyEditPage />} />
      <Route path="print" element={<SurveyPrint />} />
      <Route path="delete" element={<FormDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FormRoutes;
