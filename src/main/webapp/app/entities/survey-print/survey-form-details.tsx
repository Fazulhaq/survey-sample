import React, { useEffect } from 'react';
import { TextFormat, Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity } from '../form/form.reducer';
import { Divider } from 'primereact/divider';

interface SurveyFormDetailProps {
  formId: string;
}

export const SurveyFormDetail: React.FC<SurveyFormDetailProps> = ({ formId }) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(formId));
  }, []);

  const formEntity = useAppSelector(state => state.form.entity);
  return (
    <Row className="justify-content-center">
      <Col md="10" className="justify-content-center">
        <br />
        <h3 data-cy="formDetailsHeading">
          <span id="futurePlan">
            <Translate contentKey="surveySampleApp.form.futurePlan">Future Plan</Translate>
          </span>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{formEntity.futurePlan}
        </h3>
        <Divider />
        <dl className="jh-entity-details">
          <br />
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{formEntity.id}</dd>
          <br />
          <dt>
            <Translate contentKey="surveySampleApp.organization.name">Name</Translate>
          </dt>
          <dd>{formEntity.organization ? formEntity.organization.name : ''}</dd>
          <br />
          <dt>
            <Translate contentKey="surveySampleApp.organization.address">Address</Translate>
          </dt>
          <dd>{formEntity.organization ? formEntity.organization.address : ''}</dd>
          <br />
          <dt>
            <span id="createDate">
              <Translate contentKey="surveySampleApp.form.createDate">Create Date</Translate>
            </span>
          </dt>
          <dd>{formEntity.createDate ? <TextFormat value={formEntity.createDate} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <br />
          <br />
        </dl>
      </Col>
    </Row>
  );
};

export default SurveyFormDetail;
