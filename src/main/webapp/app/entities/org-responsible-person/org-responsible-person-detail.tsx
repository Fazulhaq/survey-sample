import React, { useEffect, useState } from 'react';
import { TextFormat, Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

import { IOrgResponsiblePerson } from 'app/shared/model/org-responsible-person.model';
import axios from 'axios';

interface OrgResponsiblePersonDetailProps {
  formId: string;
}

export const OrgResponsiblePersonDetail: React.FC<OrgResponsiblePersonDetailProps> = ({ formId }) => {
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
      <Col md="1"></Col>
      <Col md="5">
        <br />
        <br />
        <h3 data-cy="orgResponsiblePersonDetailsHeading">
          <Translate contentKey="surveySampleApp.orgResponsiblePerson.detail.title">OrgResponsiblePerson</Translate>
        </h3>
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
        </dl>
      </Col>
      <Col md="5">
        <dl>
          <dt>
            <br />
            <br />
            <br />
            <br />
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
              <TextFormat value={orgResponsiblePersonEntity?.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
      </Col>
      <Col md="1"></Col>
    </Row>
  );
};

export default OrgResponsiblePersonDetail;
