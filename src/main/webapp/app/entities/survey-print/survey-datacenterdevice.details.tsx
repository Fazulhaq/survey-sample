import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row, Table } from 'reactstrap';

import { IDatacenterDevice } from 'app/shared/model/datacenter-device.model';
import axios from 'axios';
import { Divider } from 'primereact/divider';

interface SurveyDatacenterDeviceDetailProps {
  formId: string;
}

export const SurveyDatacenterDeviceDetail: React.FC<SurveyDatacenterDeviceDetailProps> = ({ formId }) => {
  const [datacenterDeviceEntities, setDatacenterDeviceEntities] = useState<IDatacenterDevice[]>([]);
  useEffect(() => {
    const getDatacenterDeviceEntities = async () => {
      const apiUrl = 'api/datacenter-devices/forms';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<IDatacenterDevice[]>(requestUrl);
      setDatacenterDeviceEntities(response.data);
    };
    getDatacenterDeviceEntities();
  }, [formId]);

  return (
    <Row className="justify-content-center">
      <Col md="12">
        <br />
        <h3 data-cy="datacenterDeviceDetailsHeading">
          <Translate contentKey="surveySampleApp.datacenterDevice.detail.title">DatacenterDevice</Translate>
        </h3>
      </Col>
      <Divider />
      <Col md="11">
        <br />
        <Table responsive>
          <thead>
            <tr>
              <th>
                <Translate contentKey="surveySampleApp.datacenterDevice.deviceType">Device Type</Translate>
              </th>
              <th>
                <Translate contentKey="surveySampleApp.datacenterDevice.brandAndModel">Brand And Model</Translate>
              </th>
              <th>
                <Translate contentKey="surveySampleApp.datacenterDevice.quantity">Quantity</Translate>
              </th>
              <th>
                <Translate contentKey="surveySampleApp.datacenterDevice.age">Age</Translate>
              </th>
              <th>
                <Translate contentKey="surveySampleApp.datacenterDevice.purpose">Purpose</Translate>
              </th>
              <th>
                <Translate contentKey="surveySampleApp.datacenterDevice.currentStatus">Current Status</Translate>
              </th>
            </tr>
          </thead>
          <tbody>
            {datacenterDeviceEntities.map((datacenterDevice, index) => (
              <tr key={index}>
                <td>{datacenterDevice.deviceType}</td>
                <td>{datacenterDevice.brandAndModel}</td>
                <td>{datacenterDevice.quantity}</td>
                <td>{datacenterDevice.age}</td>
                <td>{datacenterDevice.purpose}</td>
                <td>{datacenterDevice.currentStatus}</td>
              </tr>
            ))}
          </tbody>
        </Table>
        <br />
      </Col>
    </Row>
  );
};

export default SurveyDatacenterDeviceDetail;
