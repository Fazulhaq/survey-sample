import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import React, { useEffect } from 'react';
import { TextFormat, Translate } from 'react-jhipster';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';

import { getEntity } from './organization.reducer';

export const OrganizationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const organizationEntity = useAppSelector(state => state.organization.entity);

  return (
    <Row className="justify-content-center">
      <Col md="2"></Col>
      <Col md="4">
        <h2 data-cy="organizationDetailsHeading">
          <Translate contentKey="surveySampleApp.organization.detail.title">Organization</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <br />
            <span id="id">
              <Translate contentKey="surveySampleApp.organization.id">ID</Translate>
            </span>
          </dt>
          <dd>{organizationEntity.id}</dd>
          <dt>
            <br />
            <span id="name">
              <Translate contentKey="surveySampleApp.organization.name">Name</Translate>
            </span>
          </dt>
          <dd>{organizationEntity.name}</dd>
          <dt>
            <br />
            <span id="code">
              <Translate contentKey="surveySampleApp.organization.code">Code</Translate>
            </span>
          </dt>
          <dd>{organizationEntity.code}</dd>
          <dt>
            <br />
            <span id="description">
              <Translate contentKey="surveySampleApp.organization.description">Description</Translate>
            </span>
          </dt>
          <dd>{organizationEntity.description}</dd>
        </dl>
        <br />
        <br />
        <Button tag={Link} to="/organization" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/organization/${organizationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
      <Col md="4">
        <br />
        <dl>
          <br />
          <br />
          <dt>
            <span id="address">
              <Translate contentKey="surveySampleApp.organization.address">Address</Translate>
            </span>
          </dt>
          <dd>{organizationEntity.address}</dd>
          <dt>
            <br />
            <span id="createDate">
              <Translate contentKey="surveySampleApp.organization.createDate">Create Date</Translate>
            </span>
          </dt>
          <dd>
            {organizationEntity.createDate ? (
              <TextFormat value={organizationEntity.createDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <br />
            <span id="updateDate">
              <Translate contentKey="surveySampleApp.organization.updateDate">Update Date</Translate>
            </span>
          </dt>
          <dd>
            {organizationEntity.updateDate ? (
              <TextFormat value={organizationEntity.updateDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <br />
            <Translate contentKey="surveySampleApp.organization.user">User</Translate>
          </dt>
          <dd>{organizationEntity.user ? organizationEntity.user.login : ''}</dd>
        </dl>
      </Col>
      <Col md="2"></Col>
    </Row>
  );
};

export default OrganizationDetail;
