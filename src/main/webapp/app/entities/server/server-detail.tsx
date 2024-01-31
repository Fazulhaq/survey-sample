import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './server.reducer';

export const ServerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const serverEntity = useAppSelector(state => state.server.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serverDetailsHeading">
          <Translate contentKey="surveySampleApp.server.detail.title">Server</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{serverEntity.id}</dd>
          <dt>
            <span id="question1">
              <Translate contentKey="surveySampleApp.server.question1">Question 1</Translate>
            </span>
          </dt>
          <dd>{serverEntity.question1}</dd>
          <dt>
            <span id="question2">
              <Translate contentKey="surveySampleApp.server.question2">Question 2</Translate>
            </span>
          </dt>
          <dd>{serverEntity.question2}</dd>
          <dt>
            <span id="question3">
              <Translate contentKey="surveySampleApp.server.question3">Question 3</Translate>
            </span>
          </dt>
          <dd>{serverEntity.question3}</dd>
          <dt>
            <span id="question4">
              <Translate contentKey="surveySampleApp.server.question4">Question 4</Translate>
            </span>
          </dt>
          <dd>{serverEntity.question4}</dd>
          <dt>
            <span id="question5">
              <Translate contentKey="surveySampleApp.server.question5">Question 5</Translate>
            </span>
          </dt>
          <dd>{serverEntity.question5}</dd>
          <dt>
            <span id="question6">
              <Translate contentKey="surveySampleApp.server.question6">Question 6</Translate>
            </span>
          </dt>
          <dd>{serverEntity.question6}</dd>
          <dt>
            <span id="question7">
              <Translate contentKey="surveySampleApp.server.question7">Question 7</Translate>
            </span>
          </dt>
          <dd>{serverEntity.question7}</dd>
          <dt>
            <Translate contentKey="surveySampleApp.server.form">Form</Translate>
          </dt>
          <dd>{serverEntity.form ? serverEntity.form.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/server" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/server/${serverEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServerDetail;
