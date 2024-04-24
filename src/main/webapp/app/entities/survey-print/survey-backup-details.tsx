import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

import { IBackup } from 'app/shared/model/backup.model';
import axios from 'axios';
import { Divider } from 'primereact/divider';

interface SurveyBackupDetailProps {
  formId: string;
}

export const SurveyBackupDetail: React.FC<SurveyBackupDetailProps> = ({ formId }) => {
  const [backupEntity, setBackupEntity] = useState<IBackup | null>(null);
  useEffect(() => {
    const getBackupEntity = async () => {
      const apiUrl = 'api/backups/form';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<IBackup>(requestUrl);
      setBackupEntity(response.data);
    };
    getBackupEntity();
  }, [formId]);

  return (
    <Row className="justify-content-center">
      <Col md="12">
        <br />
        <h3 data-cy="backupDetailsHeading">
          <Translate contentKey="surveySampleApp.backup.detail.title">Backup</Translate>
        </h3>
      </Col>
      <Divider />
      <Col md="12">
        <dl className="jh-entity-details">
          <dt>
            <br />
            <span id="question1">
              1 - <Translate contentKey="surveySampleApp.backup.question1">Question 1</Translate>
            </span>
          </dt>
          <dd>{backupEntity?.question1}</dd>
          <dt>
            <br />
            <span id="question2">
              2 - <Translate contentKey="surveySampleApp.backup.question2">Question 2</Translate>
            </span>
          </dt>
          <dd>{backupEntity?.question2}</dd>
          <dt>
            <br />
            <span id="question3">
              3 - <Translate contentKey="surveySampleApp.backup.question3">Question 3</Translate>
            </span>
          </dt>
          <dd>{backupEntity?.question3}</dd>
          <dt>
            <br />
            <span id="question4">
              4 - <Translate contentKey="surveySampleApp.backup.question4">Question 4</Translate>
            </span>
          </dt>
          <dd>{backupEntity?.question4}</dd>
          <dt>
            <br />
            <span id="question5">
              5 - <Translate contentKey="surveySampleApp.backup.question5">Question 5</Translate>
            </span>
          </dt>
          <dd>{backupEntity?.question5}</dd>
          <dt>
            <br />
            <span id="question6">
              6 - <Translate contentKey="surveySampleApp.backup.question6">Question 6</Translate>
            </span>
          </dt>
          <dd>{backupEntity?.question6}</dd>
          <dt>
            <br />
            <span id="question7">
              7 - <Translate contentKey="surveySampleApp.backup.question7">Question 7</Translate>
            </span>
          </dt>
          <dd>{backupEntity?.question7}</dd>
        </dl>
      </Col>
    </Row>
  );
};

export default SurveyBackupDetail;
