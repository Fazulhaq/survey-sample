import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Divider } from 'primereact/divider';
import React from 'react';
import { Translate } from 'react-jhipster';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
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
        <br />
        <br />
        <Button tag={Link} to="/form" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={'/form'} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SurveyView;
