import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row, Table } from 'reactstrap';

import { IItDevice } from 'app/shared/model/it-device.model';
import axios from 'axios';

interface ItDeviceDetailProps {
  formId: string;
}

export const ItDeviceDetail: React.FC<ItDeviceDetailProps> = ({ formId }) => {
  const [itDeviceEntities, setItDeviceEntities] = useState<IItDevice[]>([]);
  useEffect(() => {
    const getItDeviceEntity = async () => {
      const apiUrl = 'api/it-devices/forms';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<IItDevice[]>(requestUrl);
      setItDeviceEntities(response.data);
    };
    getItDeviceEntity();
  }, [formId]);

  return (
    <Row className="justify-content-center">
      <Col md="1"></Col>
      <Col md="10">
        <br />
        <br />
        <h3 data-cy="itDeviceDetailsHeading">
          <Translate contentKey="surveySampleApp.itDevice.detail.title">ItDevice</Translate>
        </h3>
        <br />
        <Table responsive>
          <thead>
            <tr>
              <th>
                <Translate contentKey="surveySampleApp.itDevice.deviceType">Device Type</Translate>
              </th>
              <th>
                <Translate contentKey="surveySampleApp.itDevice.brandAndModel">Brand And Model</Translate>
              </th>
              <th>
                <Translate contentKey="surveySampleApp.itDevice.quantity">Quantity</Translate>
              </th>
              <th>
                <Translate contentKey="surveySampleApp.itDevice.age">Age</Translate>
              </th>
              <th>
                <Translate contentKey="surveySampleApp.itDevice.purpose">Purpose</Translate>
              </th>
              <th>
                <Translate contentKey="surveySampleApp.itDevice.currentStatus">Current Status</Translate>
              </th>
            </tr>
          </thead>
          <tbody>
            {itDeviceEntities.map((itDevice, index) => (
              <tr key={index}>
                <td>{itDevice.deviceType}</td>
                <td>{itDevice.brandAndModel}</td>
                <td>{itDevice.quantity}</td>
                <td>{itDevice.age}</td>
                <td>{itDevice.purpose}</td>
                <td>{itDevice.currentStatus}</td>
              </tr>
            ))}
          </tbody>
        </Table>
        <br />
      </Col>
      <Col md="1"></Col>
    </Row>
  );
};

export default ItDeviceDetail;
