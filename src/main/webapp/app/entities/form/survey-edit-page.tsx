import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Divider } from 'primereact/divider';
import React from 'react';
import { Step, Stepper } from 'react-form-stepper';
import { Translate, translate } from 'react-jhipster';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import BackupSurveyUpdate from '../backup/backup-survey-update';
import DatacenterDeviceSurveyUpdate from '../datacenter-device/datacenter-survey-update';
import InternetSurveyUpdate from '../internet/internet-survey-update';
import ItDeviceSurveyUpdate from '../it-device/it-device-survey-update';
import NetworkConfigCheckListSurveyUpdate from '../network-config-check-list/net-config-survey-update';
import OrgResponsiblePersonSurveyUpdate from '../org-responsible-person/org-res-person-survey-update';
import ServerSurveyUpdate from '../server/server-survey-update';
import SystemSurveyUpdate from '../system/system-survey-update';
import { decrementEditIndex, incrementEditIndex, resetEditIndex } from './survey-edit-index-reducer';
import SurveyUpdate from './survey-update';

export const SurveyEditPage = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  const activeStep = useAppSelector(state => state.surveyeditindex.surveyEditIndex);

  const handleNext = () => {
    dispatch(incrementEditIndex(1));
  };

  const handleBack = () => {
    dispatch(decrementEditIndex(1));
  };

  const handleResetIndex = () => {
    dispatch(resetEditIndex(0));
  };

  return (
    <Row className="justify-content-center">
      <Col md="11">
        <br />
        <h2 data-cy="formDetailsHeading">
          <Translate contentKey="surveySampleApp.form.detail.editsurveytitle">Survey Details</Translate>
        </h2>
        <Stepper activeStep={activeStep} hideConnectors={true}>
          <Step label={translate('surveySampleApp.form.home.step1')} />
          <Step label={translate('surveySampleApp.form.home.step2')} />
          <Step label={translate('surveySampleApp.form.home.step3')} />
          <Step label={translate('surveySampleApp.form.home.step4')} />
          <Step label={translate('surveySampleApp.form.home.step5')} />
          <Step label={translate('surveySampleApp.form.home.step6')} />
          <Step label={translate('surveySampleApp.form.home.step7')} />
          <Step label={translate('surveySampleApp.form.home.step8')} />
          <Step label={translate('surveySampleApp.form.home.step9')} />
        </Stepper>
        <Divider />
        <br />
        {activeStep === 0 && <SurveyUpdate formId={id} />}
        {activeStep === 1 && <ServerSurveyUpdate formId={id} />}
        {activeStep === 2 && <SystemSurveyUpdate formId={id} />}
        {activeStep === 3 && <BackupSurveyUpdate formId={id} />}
        {activeStep === 4 && <DatacenterDeviceSurveyUpdate formId={id} />}
        {activeStep === 5 && <InternetSurveyUpdate formId={id} />}
        {activeStep === 6 && <ItDeviceSurveyUpdate formId={id} />}
        {activeStep === 7 && <NetworkConfigCheckListSurveyUpdate formId={id} />}
        {activeStep === 8 && <OrgResponsiblePersonSurveyUpdate formId={id} />}
        <br />
        <Divider />
        <br />
        <Button tag={Link} to="/form" onClick={handleResetIndex} replace color="info" data-cy="entityDetailsBackButton">
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

export default SurveyEditPage;
