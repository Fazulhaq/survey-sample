import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Divider } from 'primereact/divider';
import React from 'react';
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

  return (
    <Row className="justify-content-center">
      <Col md="11">
        <br />
        <h2 data-cy="formDetailsHeading">
          <Translate contentKey="surveySampleApp.form.detail.maintitle">Survey Details</Translate>
        </h2>
        <Divider />
        <FormDetail formId={id} />
        <Divider />
        <ServerDetail formId={id} />
        <Divider />
        <SystemDetail formId={id} />
        <Divider />
        <BackupDetail formId={id} />
        <Divider />
        <DatacenterDeviceDetail formId={id} />
        <Divider />
        <InternetDetail formId={id} />
        <Divider />
        <ItDeviceDetail formId={id} />
        <Divider />
        <NetworkConfigCheckListDetail formId={id} />
        <Divider />
        <OrgResponsiblePersonDetail formId={id} />
        <Divider />
        <br />
        <Button tag={Link} to="/form" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.surveylist">Back to Survey List</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SurveyView;
