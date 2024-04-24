import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

import { IInternet } from 'app/shared/model/internet.model';
import axios from 'axios';
import { Divider } from 'primereact/divider';

interface SurveyIntenetDetailProps {
  formId: string;
}

export const SurveyInternetDetail: React.FC<SurveyIntenetDetailProps> = ({ formId }) => {
  const [internetEntity, setInternetEntity] = useState<IInternet | null>(null);
  useEffect(() => {
    const getInternetEntity = async () => {
      const apiUrl = 'api/internets/form';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<IInternet>(requestUrl);
      setInternetEntity(response.data);
    };
    getInternetEntity();
  }, [formId]);

  return (
    <Row className="justify-content-center">
      <Col md="12">
        <br />
        <h3 data-cy="internetDetailsHeading">
          <Translate contentKey="surveySampleApp.internet.detail.title">Internet</Translate>
        </h3>
        <Divider />
        <dl className="jh-entity-details">
          <dt>
            <br />
            <span id="question1">
              1 - <Translate contentKey="surveySampleApp.internet.question1">Question 1</Translate>
            </span>
          </dt>
          <dd>{internetEntity?.question1}</dd>
          <dt>
            <br />
            <span id="question2">
              2 - <Translate contentKey="surveySampleApp.internet.question2">Question 2</Translate>
            </span>
          </dt>
          <dd>{internetEntity?.question2}</dd>
          <dt>
            <br />
            <span id="question3">
              3 - <Translate contentKey="surveySampleApp.internet.question3">Question 3</Translate>
            </span>
          </dt>
          <dd>{internetEntity?.question3}</dd>
          <dt>
            <br />
            <span id="question4">
              4 - <Translate contentKey="surveySampleApp.internet.question4">Question 4</Translate>
            </span>
          </dt>
          <dd>{internetEntity?.question4}</dd>
          <dt>
            <br />
            <span id="question5">
              5 - <Translate contentKey="surveySampleApp.internet.question5">Question 5</Translate>
            </span>
          </dt>
          <dd>{internetEntity?.question5}</dd>
          <dt>
            <br />
            <span id="question6">
              6 - <Translate contentKey="surveySampleApp.internet.question6">Question 6</Translate>
            </span>
          </dt>
          <dd>{internetEntity?.question6}</dd>
        </dl>
      </Col>
    </Row>
  );
};

export default SurveyInternetDetail;
