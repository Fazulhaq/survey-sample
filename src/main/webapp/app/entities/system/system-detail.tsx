import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

import { ISystem } from 'app/shared/model/system.model';
import axios from 'axios';

interface SystemDetailProps {
  formId: string;
}

export const SystemDetail: React.FC<SystemDetailProps> = ({ formId }) => {
  const [systemEntity, setSystemEntity] = useState<ISystem | null>(null);
  useEffect(() => {
    const getSystemEntity = async () => {
      const apiUrl = 'api/systems/form';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<ISystem>(requestUrl);
      setSystemEntity(response.data);
    };
    getSystemEntity();
  }, [formId]);

  return (
    <Row className="justify-content-center">
      <Col md="1"></Col>
      <Col md="10">
        <br />
        <br />
        <h3 data-cy="systemDetailsHeading">
          <Translate contentKey="surveySampleApp.system.detail.title">System</Translate>
        </h3>
        <dl className="jh-entity-details">
          <dt>
            <br />
            <span id="question1">
              <Translate contentKey="surveySampleApp.system.question1">Question 1</Translate>
            </span>
          </dt>
          <dd>{systemEntity?.question1}</dd>
          <dt>
            <br />
            <span id="question2">
              <Translate contentKey="surveySampleApp.system.question2">Question 2</Translate>
            </span>
          </dt>
          <dd>{systemEntity?.question2}</dd>
          <dt>
            <br />
            <span id="question3">
              <Translate contentKey="surveySampleApp.system.question3">Question 3</Translate>
            </span>
          </dt>
          <dd>{systemEntity?.question3}</dd>
          <dt>
            <br />
            <span id="question4">
              <Translate contentKey="surveySampleApp.system.question4">Question 4</Translate>
            </span>
          </dt>
          <dd>{systemEntity?.question4}</dd>
          <dt>
            <br />
            <span id="question5">
              <Translate contentKey="surveySampleApp.system.question5">Question 5</Translate>
            </span>
          </dt>
          <dd>{systemEntity?.question5}</dd>
        </dl>
      </Col>
      <Col md="1"></Col>
    </Row>
  );
};

export default SystemDetail;
