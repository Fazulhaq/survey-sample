import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

import { IServer } from 'app/shared/model/server.model';
import axios from 'axios';

interface ServerDetailProps {
  formId: string;
}

export const ServerDetail: React.FC<ServerDetailProps> = ({ formId }) => {
  const [serverEntity, setServerEntity] = useState<IServer | null>(null);

  useEffect(() => {
    const getServerEntity = async () => {
      const apiUrl = 'api/servers/form';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<IServer>(requestUrl);
      setServerEntity(response.data);
    };

    getServerEntity();
  }, [formId]);

  return (
    <Row className="justify-content-center">
      <Col md="1"></Col>
      <Col md="10">
        <br />
        <br />
        <h3 data-cy="serverDetailsHeading">
          <Translate contentKey="surveySampleApp.server.detail.title">Server</Translate>
        </h3>
        <dl className="jh-entity-details">
          <dt>
            <br />
            <span id="question1">
              <Translate contentKey="surveySampleApp.server.question1">Question 1</Translate>
            </span>
          </dt>
          <dd>{serverEntity?.question1}</dd>
          <dt>
            <br />
            <span id="question2">
              <Translate contentKey="surveySampleApp.server.question2">Question 2</Translate>
            </span>
          </dt>
          <dd>{serverEntity?.question2}</dd>
          <dt>
            <br />
            <span id="question3">
              <Translate contentKey="surveySampleApp.server.question3">Question 3</Translate>
            </span>
          </dt>
          <dd>{serverEntity?.question3}</dd>
          <dt>
            <br />
            <span id="question4">
              <Translate contentKey="surveySampleApp.server.question4">Question 4</Translate>
            </span>
          </dt>
          <dd>{serverEntity?.question4}</dd>
          <dt>
            <br />
            <span id="question5">
              <Translate contentKey="surveySampleApp.server.question5">Question 5</Translate>
            </span>
          </dt>
          <dd>{serverEntity?.question5}</dd>
          <dt>
            <br />
            <span id="question6">
              <Translate contentKey="surveySampleApp.server.question6">Question 6</Translate>
            </span>
          </dt>
          <dd>{serverEntity?.question6}</dd>
          <dt>
            <br />
            <span id="question7">
              <Translate contentKey="surveySampleApp.server.question7">Question 7</Translate>
            </span>
          </dt>
          <dd>{serverEntity?.question7}</dd>
        </dl>
      </Col>
      <Col md="1"></Col>
    </Row>
  );
};

export default ServerDetail;
