import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

import { IDatacenterDevice } from 'app/shared/model/datacenter-device.model';
import axios from 'axios';

interface DatacenterDeviceDetailProps {
  formId: string;
}

export const DatacenterDeviceDetail: React.FC<DatacenterDeviceDetailProps> = ({ formId }) => {
  const [datacenterDeviceEntity, setDatacenterDeviceEntity] = useState<IDatacenterDevice | null>(null);
  useEffect(() => {
    const getDatacenterDeviceEntity = async () => {
      const apiUrl = 'api/datacenter-devices/form';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<IDatacenterDevice>(requestUrl);
      setDatacenterDeviceEntity(response.data);
    };
    getDatacenterDeviceEntity();
  }, [formId]);

  return (
    <Row className="justify-content-center">
      <Col md="1"></Col>
      <Col md="5">
        <br />
        <br />
        <h3 data-cy="datacenterDeviceDetailsHeading">
          <Translate contentKey="surveySampleApp.datacenterDevice.detail.title">DatacenterDevice</Translate>
        </h3>
        <dl className="jh-entity-details">
          <dt>
            <br />
            <span id="deviceType">
              <Translate contentKey="surveySampleApp.datacenterDevice.deviceType">Device Type</Translate>
            </span>
          </dt>
          <dd>{datacenterDeviceEntity?.deviceType}</dd>
          <dt>
            <br />
            <span id="quantity">
              <Translate contentKey="surveySampleApp.datacenterDevice.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{datacenterDeviceEntity?.quantity}</dd>
          <dt>
            <br />
            <span id="brandAndModel">
              <Translate contentKey="surveySampleApp.datacenterDevice.brandAndModel">Brand And Model</Translate>
            </span>
          </dt>
          <dd>{datacenterDeviceEntity?.brandAndModel}</dd>
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
            <span id="age">
              <Translate contentKey="surveySampleApp.datacenterDevice.age">Age</Translate>
            </span>
          </dt>
          <dd>{datacenterDeviceEntity?.age}</dd>
          <dt>
            <br />
            <span id="purpose">
              <Translate contentKey="surveySampleApp.datacenterDevice.purpose">Purpose</Translate>
            </span>
          </dt>
          <dd>{datacenterDeviceEntity?.purpose}</dd>
          <dt>
            <br />
            <span id="currentStatus">
              <Translate contentKey="surveySampleApp.datacenterDevice.currentStatus">Current Status</Translate>
            </span>
          </dt>
          <dd>{datacenterDeviceEntity?.currentStatus}</dd>
        </dl>
      </Col>
      <Col md="1"></Col>
    </Row>
  );
};

export default DatacenterDeviceDetail;
