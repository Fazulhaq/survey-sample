import React, { useEffect } from 'react';
import { TextFormat, Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './form.reducer';

interface FormDetailProps {
  formId: string;
}

export const FormDetail: React.FC<FormDetailProps> = ({ formId }) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(formId));
  }, []);

  const formEntity = useAppSelector(state => state.form.entity);
  return (
    <Row className="justify-content-center">
      <Col md="1"></Col>
      <Col md="5">
        <br />
        <br />
        <h3 data-cy="formDetailsHeading">
          <Translate contentKey="surveySampleApp.form.detail.title">Form</Translate>
        </h3>
        <dl className="jh-entity-details">
          <dt>
            <br />
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{formEntity.id}</dd>
          <dt>
            <br />
            <span id="futurePlan">
              <Translate contentKey="surveySampleApp.form.futurePlan">Future Plan</Translate>
            </span>
          </dt>
          <dd>{formEntity.futurePlan}</dd>
          <dt>
            <br />
            <span id="status">
              <Translate contentKey="surveySampleApp.form.status">Status</Translate>
            </span>
          </dt>
          <dd>{formEntity.status}</dd>
          <br />
          <dt>
            <Translate contentKey="surveySampleApp.form.user">User</Translate>
          </dt>
          <dd>{formEntity.user ? formEntity.user.login : ''}</dd>
        </dl>
      </Col>
      <Col md="5">
        <dl>
          <dt>
            <br />
            <br />
            <br />
            <br />
            <br />
            <span id="createDate">
              <Translate contentKey="surveySampleApp.form.createDate">Create Date</Translate>
            </span>
          </dt>
          <dd>{formEntity.createDate ? <TextFormat value={formEntity.createDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <br />
            <span id="updateDate">
              <Translate contentKey="surveySampleApp.form.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>{formEntity.updateDate ? <TextFormat value={formEntity.updateDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <br />
          <dt>
            <Translate contentKey="surveySampleApp.form.organization">Organization</Translate>
          </dt>
          <dd>{formEntity.organization ? formEntity.organization.name : ''}</dd>
        </dl>
      </Col>
      <Col md="1"></Col>
    </Row>
  );
};

export default FormDetail;
