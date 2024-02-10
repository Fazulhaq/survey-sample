import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

import { IItDevice } from 'app/shared/model/it-device.model';
import axios from 'axios';

interface ItDeviceDetailProps {
  formId: string;
}

export const ItDeviceDetail: React.FC<ItDeviceDetailProps> = ({ formId }) => {
  const [itDeviceEntity, setItDeviceEntity] = useState<IItDevice | null>(null);
  useEffect(() => {
    const getItDeviceEntity = async () => {
      const apiUrl = 'api/it-devices/form';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<IItDevice>(requestUrl);
      setItDeviceEntity(response.data);
    };
    getItDeviceEntity();
  }, [formId]);

  return (
    <Row className="justify-content-center">
      <Col md="1"></Col>
      <Col md="5">
        <br />
        <br />
        <h2 data-cy="itDeviceDetailsHeading">
          <Translate contentKey="surveySampleApp.itDevice.detail.title">ItDevice</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <br />
            <span id="deviceType">
              <Translate contentKey="surveySampleApp.itDevice.deviceType">Device Type</Translate>
            </span>
          </dt>
          <dd>{itDeviceEntity?.deviceType}</dd>
          <dt>
            <br />
            <span id="quantity">
              <Translate contentKey="surveySampleApp.itDevice.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{itDeviceEntity?.quantity}</dd>
          <dt>
            <br />
            <span id="brandAndModel">
              <Translate contentKey="surveySampleApp.itDevice.brandAndModel">Brand And Model</Translate>
            </span>
          </dt>
          <dd>{itDeviceEntity?.brandAndModel}</dd>
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
              <Translate contentKey="surveySampleApp.itDevice.age">Age</Translate>
            </span>
          </dt>
          <dd>{itDeviceEntity?.age}</dd>
          <dt>
            <br />
            <span id="purpose">
              <Translate contentKey="surveySampleApp.itDevice.purpose">Purpose</Translate>
            </span>
          </dt>
          <dd>{itDeviceEntity?.purpose}</dd>
          <dt>
            <br />
            <span id="currentStatus">
              <Translate contentKey="surveySampleApp.itDevice.currentStatus">Current Status</Translate>
            </span>
          </dt>
          <dd>{itDeviceEntity?.currentStatus}</dd>
        </dl>
      </Col>
      <Col md="1"></Col>
    </Row>
  );
};

export default ItDeviceDetail;
