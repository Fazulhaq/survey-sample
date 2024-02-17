import React, { useEffect, useState } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IOrgResponsiblePerson } from 'app/shared/model/org-responsible-person.model';
import { convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { resetEditIndex } from '../form/survey-edit-index-reducer';
import { createEntity, updateEntity } from './org-responsible-person.reducer';

interface OrgResponsiblePersonSurveyUpdateProps {
  formId: string;
}

export const OrgResponsiblePersonSurveyUpdate: React.FC<OrgResponsiblePersonSurveyUpdateProps> = ({ formId }) => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

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

  const forms = useAppSelector(state => state.form.entities);
  const loading = useAppSelector(state => state.orgResponsiblePerson.loading);
  const updating = useAppSelector(state => state.orgResponsiblePerson.updating);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...orgResponsiblePersonEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };
    if (orgResponsiblePersonEntity === null) {
      dispatch(createEntity(entity));
      dispatch(resetEditIndex(0));
      navigate('/form');
    } else {
      dispatch(updateEntity(entity));
      dispatch(resetEditIndex(0));
      navigate('/form');
    }
  };

  const defaultValues = () =>
    orgResponsiblePersonEntity === null
      ? {
          date: displayDefaultDateTime(),
        }
      : {
          ...orgResponsiblePersonEntity,
          form: formId,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="surveySampleApp.orgResponsiblePerson.home.createOrEditLabel" data-cy="OrgResponsiblePersonCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.orgResponsiblePerson.home.createOrEditLabel">
              Create or edit a OrgResponsiblePerson
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!true ? (
                <ValidatedField
                  name="id"
                  required
                  hidden
                  readOnly
                  id="org-responsible-person-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('surveySampleApp.orgResponsiblePerson.fullName')}
                id="org-responsible-person-fullName"
                name="fullName"
                data-cy="fullName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.orgResponsiblePerson.position')}
                id="org-responsible-person-position"
                name="position"
                data-cy="position"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.orgResponsiblePerson.contact')}
                id="org-responsible-person-contact"
                name="contact"
                data-cy="contact"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.orgResponsiblePerson.date')}
                id="org-responsible-person-date"
                hidden
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="org-responsible-person-form"
                hidden
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.orgResponsiblePerson.form')}
                type="select"
                required
              >
                <option value={formId} key={formId}>
                  {formId}
                </option>
              </ValidatedField>
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <Translate contentKey="entity.action.updatefinish">update & finish</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default OrgResponsiblePersonSurveyUpdate;
