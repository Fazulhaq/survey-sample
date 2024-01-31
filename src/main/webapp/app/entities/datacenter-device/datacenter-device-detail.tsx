import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './datacenter-device.reducer';

export const DatacenterDeviceDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const datacenterDeviceEntity = useAppSelector(state => state.datacenterDevice.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="datacenterDeviceDetailsHeading">
          <Translate contentKey="surveySampleApp.datacenterDevice.detail.title">DatacenterDevice</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{datacenterDeviceEntity.id}</dd>
          <dt>
            <span id="deviceType">
              <Translate contentKey="surveySampleApp.datacenterDevice.deviceType">Device Type</Translate>
            </span>
          </dt>
          <dd>{datacenterDeviceEntity.deviceType}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="surveySampleApp.datacenterDevice.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{datacenterDeviceEntity.quantity}</dd>
          <dt>
            <span id="brandAndModel">
              <Translate contentKey="surveySampleApp.datacenterDevice.brandAndModel">Brand And Model</Translate>
            </span>
          </dt>
          <dd>{datacenterDeviceEntity.brandAndModel}</dd>
          <dt>
            <span id="age">
              <Translate contentKey="surveySampleApp.datacenterDevice.age">Age</Translate>
            </span>
          </dt>
          <dd>{datacenterDeviceEntity.age}</dd>
          <dt>
            <span id="purpose">
              <Translate contentKey="surveySampleApp.datacenterDevice.purpose">Purpose</Translate>
            </span>
          </dt>
          <dd>{datacenterDeviceEntity.purpose}</dd>
          <dt>
            <span id="currentStatus">
              <Translate contentKey="surveySampleApp.datacenterDevice.currentStatus">Current Status</Translate>
            </span>
          </dt>
          <dd>{datacenterDeviceEntity.currentStatus}</dd>
          <dt>
            <Translate contentKey="surveySampleApp.datacenterDevice.form">Form</Translate>
          </dt>
          <dd>{datacenterDeviceEntity.form ? datacenterDeviceEntity.form.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/datacenter-device" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/datacenter-device/${datacenterDeviceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DatacenterDeviceDetail;
