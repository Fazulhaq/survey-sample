import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

import axios from 'axios';
import { ISystem } from 'app/shared/model/system.model';
import { Divider } from 'primereact/divider';

interface SurveySystemDetailProps {
  formId: string;
}

export const SurveySystemDetail: React.FC<SurveySystemDetailProps> = ({ formId }) => {
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
      <Col md="12">
        <br />
        <h3 data-cy="systemDetailsHeading">
          <Translate contentKey="surveySampleApp.system.detail.title">System</Translate>
        </h3>
        <Divider />
        <dl className="jh-entity-details">
          <dt>
            <br />
            <span id="question1">
              1 - <Translate contentKey="surveySampleApp.system.question1">Question 1</Translate>
            </span>
          </dt>
          <dd>{systemEntity?.question1}</dd>
          <dt>
            <br />
            <span id="question2">
              2 - <Translate contentKey="surveySampleApp.system.question2">Question 2</Translate>
            </span>
          </dt>
          <dd>{systemEntity?.question2}</dd>
          <dt>
            <br />
            <span id="question3">
              3 - <Translate contentKey="surveySampleApp.system.question3">Question 3</Translate>
            </span>
          </dt>
          <dd>{systemEntity?.question3}</dd>
          <dt>
            <br />
            <span id="question4">
              4 - <Translate contentKey="surveySampleApp.system.question4">Question 4</Translate>
            </span>
          </dt>
          <dd>{systemEntity?.question4}</dd>
          <dt>
            <br />
            <span id="question5">
              5 - <Translate contentKey="surveySampleApp.system.question5">Question 5</Translate>
            </span>
          </dt>
          <dd>{systemEntity?.question5}</dd>
        </dl>
      </Col>
    </Row>
  );
};

export default SurveySystemDetail;
