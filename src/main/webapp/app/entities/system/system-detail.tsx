import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './system.reducer';

export const SystemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const systemEntity = useAppSelector(state => state.system.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="systemDetailsHeading">
          <Translate contentKey="surveySampleApp.system.detail.title">System</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{systemEntity.id}</dd>
          <dt>
            <span id="question1">
              <Translate contentKey="surveySampleApp.system.question1">Question 1</Translate>
            </span>
          </dt>
          <dd>{systemEntity.question1}</dd>
          <dt>
            <span id="question2">
              <Translate contentKey="surveySampleApp.system.question2">Question 2</Translate>
            </span>
          </dt>
          <dd>{systemEntity.question2}</dd>
          <dt>
            <span id="question3">
              <Translate contentKey="surveySampleApp.system.question3">Question 3</Translate>
            </span>
          </dt>
          <dd>{systemEntity.question3}</dd>
          <dt>
            <span id="question4">
              <Translate contentKey="surveySampleApp.system.question4">Question 4</Translate>
            </span>
          </dt>
          <dd>{systemEntity.question4}</dd>
          <dt>
            <span id="question5">
              <Translate contentKey="surveySampleApp.system.question5">Question 5</Translate>
            </span>
          </dt>
          <dd>{systemEntity.question5}</dd>
          <dt>
            <Translate contentKey="surveySampleApp.system.form">Form</Translate>
          </dt>
          <dd>{systemEntity.form ? systemEntity.form.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/system" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/system/${systemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SystemDetail;
