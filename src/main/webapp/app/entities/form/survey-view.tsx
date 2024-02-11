import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Divider } from 'primereact/divider';
import React, { useState } from 'react';
import { Step, Stepper } from 'react-form-stepper';
import { Translate } from 'react-jhipster';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import BackupDetail from '../backup/backup-detail';
import DatacenterDeviceDetail from '../datacenter-device/datacenter-device-detail';
import InternetDetail from '../internet/internet-detail';
import ItDeviceDetail from '../it-device/it-device-detail';
import NetworkConfigCheckListDetail from '../network-config-check-list/network-config-check-list-detail';
import OrgResponsiblePersonDetail from '../org-responsible-person/org-responsible-person-detail';
import ServerDetail from '../server/server-detail';
import SystemDetail from '../system/system-detail';
import FormDetail from './form-detail';

export const SurveyView = () => {
  const { id } = useParams<'id'>();
  const [activeStep, setActiveStep] = useState(0);

  const handleNext = () => {
    setActiveStep(prevStep => prevStep + 1);
  };

  const handleBack = () => {
    setActiveStep(prevStep => prevStep - 1);
  };

  return (
    <Row className="justify-content-center">
      <Col md="11">
        <br />
        <h2 data-cy="formDetailsHeading">
          <Translate contentKey="surveySampleApp.form.detail.maintitle">Survey Details</Translate>
        </h2>
        <Stepper activeStep={activeStep}>
          <Step label="Primary" />
          <Step label="Server" />
          <Step label="System" />
          <Step label="Backup" />
          <Step label="Data Center" />
          <Step label="Internet" />
          <Step label="IT Devices" />
          <Step label="Configurations" />
          <Step label="Responsible" />
        </Stepper>
        <Divider />
        {activeStep === 0 && <FormDetail formId={id} />}
        {activeStep === 1 && <ServerDetail formId={id} />}
        {activeStep === 2 && <SystemDetail formId={id} />}
        {activeStep === 3 && <BackupDetail formId={id} />}
        {activeStep === 4 && <DatacenterDeviceDetail formId={id} />}
        {activeStep === 5 && <InternetDetail formId={id} />}
        {activeStep === 6 && <ItDeviceDetail formId={id} />}
        {activeStep === 7 && <NetworkConfigCheckListDetail formId={id} />}
        {activeStep === 8 && <OrgResponsiblePersonDetail formId={id} />}
        <Divider />
        <br />
        <Button tag={Link} to="/form" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.surveylist">Back to Survey List</Translate>
          </span>
        </Button>
        &nbsp;
        {activeStep !== 0 && (
          <Button color="primary" data-cy="backButton" onClick={handleBack}>
            <Translate contentKey="surveySampleApp.form.detail.backstep">Back</Translate>
          </Button>
        )}
        &nbsp;
        {activeStep !== 8 && (
          <Button color="primary" data-cy="nextButton" onClick={handleNext}>
            <Translate contentKey="surveySampleApp.form.detail.nextstep">Next</Translate>
          </Button>
        )}
      </Col>
    </Row>
  );
};

export default SurveyView;
