import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row, Table } from 'reactstrap';

import { IItDevice } from 'app/shared/model/it-device.model';
import axios from 'axios';
import { Divider } from 'primereact/divider';

interface SurveyItDeviceDetailProps {
  formId: string;
}

export const SurveyItDeviceDetail: React.FC<SurveyItDeviceDetailProps> = ({ formId }) => {
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
      <Col md="12">
        <br />
        <h3 data-cy="itDeviceDetailsHeading">
          <Translate contentKey="surveySampleApp.itDevice.detail.title">ItDevice</Translate>
        </h3>
        <Divider />
      </Col>
      <Col md="11">
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
    </Row>
  );
};

export default SurveyItDeviceDetail;
