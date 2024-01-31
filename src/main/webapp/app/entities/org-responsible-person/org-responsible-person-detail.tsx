import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './org-responsible-person.reducer';

export const OrgResponsiblePersonDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const orgResponsiblePersonEntity = useAppSelector(state => state.orgResponsiblePerson.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orgResponsiblePersonDetailsHeading">
          <Translate contentKey="surveySampleApp.orgResponsiblePerson.detail.title">OrgResponsiblePerson</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{orgResponsiblePersonEntity.id}</dd>
          <dt>
            <span id="fullName">
              <Translate contentKey="surveySampleApp.orgResponsiblePerson.fullName">Full Name</Translate>
            </span>
          </dt>
          <dd>{orgResponsiblePersonEntity.fullName}</dd>
          <dt>
            <span id="position">
              <Translate contentKey="surveySampleApp.orgResponsiblePerson.position">Position</Translate>
            </span>
          </dt>
          <dd>{orgResponsiblePersonEntity.position}</dd>
          <dt>
            <span id="contact">
              <Translate contentKey="surveySampleApp.orgResponsiblePerson.contact">Contact</Translate>
            </span>
          </dt>
          <dd>{orgResponsiblePersonEntity.contact}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="surveySampleApp.orgResponsiblePerson.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {orgResponsiblePersonEntity.date ? (
              <TextFormat value={orgResponsiblePersonEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="surveySampleApp.orgResponsiblePerson.form">Form</Translate>
          </dt>
          <dd>{orgResponsiblePersonEntity.form ? orgResponsiblePersonEntity.form.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/org-responsible-person" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/org-responsible-person/${orgResponsiblePersonEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrgResponsiblePersonDetail;
