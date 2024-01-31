import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './it-device.reducer';

export const ItDeviceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const itDeviceEntity = useAppSelector(state => state.itDevice.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="itDeviceDetailsHeading">
          <Translate contentKey="surveySampleApp.itDevice.detail.title">ItDevice</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{itDeviceEntity.id}</dd>
          <dt>
            <span id="deviceType">
              <Translate contentKey="surveySampleApp.itDevice.deviceType">Device Type</Translate>
            </span>
          </dt>
          <dd>{itDeviceEntity.deviceType}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="surveySampleApp.itDevice.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{itDeviceEntity.quantity}</dd>
          <dt>
            <span id="brandAndModel">
              <Translate contentKey="surveySampleApp.itDevice.brandAndModel">Brand And Model</Translate>
            </span>
          </dt>
          <dd>{itDeviceEntity.brandAndModel}</dd>
          <dt>
            <span id="age">
              <Translate contentKey="surveySampleApp.itDevice.age">Age</Translate>
            </span>
          </dt>
          <dd>{itDeviceEntity.age}</dd>
          <dt>
            <span id="purpose">
              <Translate contentKey="surveySampleApp.itDevice.purpose">Purpose</Translate>
            </span>
          </dt>
          <dd>{itDeviceEntity.purpose}</dd>
          <dt>
            <span id="currentStatus">
              <Translate contentKey="surveySampleApp.itDevice.currentStatus">Current Status</Translate>
            </span>
          </dt>
          <dd>{itDeviceEntity.currentStatus}</dd>
          <dt>
            <Translate contentKey="surveySampleApp.itDevice.form">Form</Translate>
          </dt>
          <dd>{itDeviceEntity.form ? itDeviceEntity.form.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/it-device" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/it-device/${itDeviceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ItDeviceDetail;
