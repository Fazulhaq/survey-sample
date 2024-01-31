import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './form.reducer';

export const FormDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const formEntity = useAppSelector(state => state.form.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="formDetailsHeading">
          <Translate contentKey="surveySampleApp.form.detail.title">Form</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{formEntity.id}</dd>
          <dt>
            <span id="futurePlan">
              <Translate contentKey="surveySampleApp.form.futurePlan">Future Plan</Translate>
            </span>
          </dt>
          <dd>{formEntity.futurePlan}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="surveySampleApp.form.status">Status</Translate>
            </span>
          </dt>
          <dd>{formEntity.status}</dd>
          <dt>
            <span id="createDate">
              <Translate contentKey="surveySampleApp.form.createDate">Create Date</Translate>
            </span>
          </dt>
          <dd>{formEntity.createDate ? <TextFormat value={formEntity.createDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updateDate">
              <Translate contentKey="surveySampleApp.form.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>{formEntity.updateDate ? <TextFormat value={formEntity.updateDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="surveySampleApp.form.user">User</Translate>
          </dt>
          <dd>{formEntity.user ? formEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="surveySampleApp.form.organization">Organization</Translate>
          </dt>
          <dd>{formEntity.organization ? formEntity.organization.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/form" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/form/${formEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FormDetail;
