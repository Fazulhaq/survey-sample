import React, { useEffect, useState } from 'react';
import { TextFormat, Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

import { IOrgResponsiblePerson } from 'app/shared/model/org-responsible-person.model';
import axios from 'axios';
import { Divider } from 'primereact/divider';
import { displayDefaultDateTime } from 'app/shared/util/date-utils';

interface OrgResponsiblePersonDetailProps {
  formId: string;
}

export const SurveyOrgResponsiblePersonDetail: React.FC<OrgResponsiblePersonDetailProps> = ({ formId }) => {
  const [orgResponsiblePersonEntity, setOrgResponsiblePersonEntity] = useState<IOrgResponsiblePerson | null>(null);
  useEffect(() => {
    const getOrgResponsiblePersonEntity = async () => {
      const apiUrl = 'api/org-responsible-people/form';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<IOrgResponsiblePerson>(requestUrl);
      setOrgResponsiblePersonEntity(response.data);
    };
    getOrgResponsiblePersonEntity();
  }, [formId]);

  return (
    <Row className="justify-content-center">
      <Col md="12">
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <br />
        <h3 data-cy="orgResponsiblePersonDetailsHeading">
          <Translate contentKey="surveySampleApp.orgResponsiblePerson.detail.title">OrgResponsiblePerson</Translate>
        </h3>
      </Col>
      <Divider />
      <br />
      <br />
      <Col md="4">
        <dl className="jh-entity-details">
          <dt>
            <br />
            <span id="fullName">
              <Translate contentKey="surveySampleApp.orgResponsiblePerson.fullName">Full Name</Translate>
            </span>
          </dt>
          <dd>{orgResponsiblePersonEntity?.fullName}</dd>
          <dt>
            <br />
            <span id="position">
              <Translate contentKey="surveySampleApp.orgResponsiblePerson.position">Position</Translate>
            </span>
          </dt>
          <dd>{orgResponsiblePersonEntity?.position}</dd>
          <dt>
            <br />
            <br />
            <br />
            <span id="date">
              <Translate contentKey="surveySampleApp.orgResponsiblePerson.signature">Signature</Translate>
            </span>
            <Divider type="dotted" />
          </dt>
        </dl>
      </Col>
      <Col md="8">
        <dl>
          <dt>
            <br />
            <span id="contact">
              <Translate contentKey="surveySampleApp.orgResponsiblePerson.contact">Contact</Translate>
            </span>
          </dt>
          <dd>{orgResponsiblePersonEntity?.contact}</dd>
          <dt>
            <br />
            <span id="date">
              <Translate contentKey="surveySampleApp.orgResponsiblePerson.date">Date</Translate>
            </span>
          </dt>
          <dd>
            {orgResponsiblePersonEntity?.date ? (
              <TextFormat value={displayDefaultDateTime()} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
      </Col>
    </Row>
  );
};

export default SurveyOrgResponsiblePersonDetail;
