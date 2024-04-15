import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row, Table } from 'reactstrap';

import { IDatacenterDevice } from 'app/shared/model/datacenter-device.model';
import axios from 'axios';

interface DatacenterDeviceDetailProps {
  formId: string;
}

export const DatacenterDeviceDetail: React.FC<DatacenterDeviceDetailProps> = ({ formId }) => {
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
      <Col md="1"></Col>
      <Col md="10">
        <br />
        <br />
        <h3 data-cy="datacenterDeviceDetailsHeading">
          <Translate contentKey="surveySampleApp.datacenterDevice.detail.title">DatacenterDevice</Translate>
        </h3>
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
      <Col md="1"></Col>
    </Row>
  );
};

export default DatacenterDeviceDetail;
