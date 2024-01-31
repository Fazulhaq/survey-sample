import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './internet.reducer';

export const InternetDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const internetEntity = useAppSelector(state => state.internet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="internetDetailsHeading">
          <Translate contentKey="surveySampleApp.internet.detail.title">Internet</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{internetEntity.id}</dd>
          <dt>
            <span id="question1">
              <Translate contentKey="surveySampleApp.internet.question1">Question 1</Translate>
            </span>
          </dt>
          <dd>{internetEntity.question1}</dd>
          <dt>
            <span id="question2">
              <Translate contentKey="surveySampleApp.internet.question2">Question 2</Translate>
            </span>
          </dt>
          <dd>{internetEntity.question2}</dd>
          <dt>
            <span id="question3">
              <Translate contentKey="surveySampleApp.internet.question3">Question 3</Translate>
            </span>
          </dt>
          <dd>{internetEntity.question3}</dd>
          <dt>
            <span id="question4">
              <Translate contentKey="surveySampleApp.internet.question4">Question 4</Translate>
            </span>
          </dt>
          <dd>{internetEntity.question4}</dd>
          <dt>
            <span id="question5">
              <Translate contentKey="surveySampleApp.internet.question5">Question 5</Translate>
            </span>
          </dt>
          <dd>{internetEntity.question5}</dd>
          <dt>
            <span id="question6">
              <Translate contentKey="surveySampleApp.internet.question6">Question 6</Translate>
            </span>
          </dt>
          <dd>{internetEntity.question6}</dd>
          <dt>
            <Translate contentKey="surveySampleApp.internet.form">Form</Translate>
          </dt>
          <dd>{internetEntity.form ? internetEntity.form.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/internet" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/internet/${internetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InternetDetail;
