import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './backup.reducer';

export const BackupDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const backupEntity = useAppSelector(state => state.backup.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="backupDetailsHeading">
          <Translate contentKey="surveySampleApp.backup.detail.title">Backup</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{backupEntity.id}</dd>
          <dt>
            <span id="question1">
              <Translate contentKey="surveySampleApp.backup.question1">Question 1</Translate>
            </span>
          </dt>
          <dd>{backupEntity.question1}</dd>
          <dt>
            <span id="question2">
              <Translate contentKey="surveySampleApp.backup.question2">Question 2</Translate>
            </span>
          </dt>
          <dd>{backupEntity.question2}</dd>
          <dt>
            <span id="question3">
              <Translate contentKey="surveySampleApp.backup.question3">Question 3</Translate>
            </span>
          </dt>
          <dd>{backupEntity.question3}</dd>
          <dt>
            <span id="question4">
              <Translate contentKey="surveySampleApp.backup.question4">Question 4</Translate>
            </span>
          </dt>
          <dd>{backupEntity.question4}</dd>
          <dt>
            <span id="question5">
              <Translate contentKey="surveySampleApp.backup.question5">Question 5</Translate>
            </span>
          </dt>
          <dd>{backupEntity.question5}</dd>
          <dt>
            <span id="question6">
              <Translate contentKey="surveySampleApp.backup.question6">Question 6</Translate>
            </span>
          </dt>
          <dd>{backupEntity.question6}</dd>
          <dt>
            <span id="question7">
              <Translate contentKey="surveySampleApp.backup.question7">Question 7</Translate>
            </span>
          </dt>
          <dd>{backupEntity.question7}</dd>
          <dt>
            <Translate contentKey="surveySampleApp.backup.form">Form</Translate>
          </dt>
          <dd>{backupEntity.form ? backupEntity.form.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/backup" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/backup/${backupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BackupDetail;
