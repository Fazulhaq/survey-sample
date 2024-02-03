import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getForms } from 'app/entities/form/form.reducer';
import { createEntity, reset } from './org-responsible-person.reducer';

interface OrgResponsiblePersonUpdateProps {
  formId: number;
}

export const OrgResponsiblePersonUpdate: React.FC<OrgResponsiblePersonUpdateProps> = ({ formId }) => {
  const dispatch = useAppDispatch();

  const forms = useAppSelector(state => state.form.entities);
  const orgResponsiblePersonEntity = useAppSelector(state => state.orgResponsiblePerson.entity);
  const loading = useAppSelector(state => state.orgResponsiblePerson.loading);
  const updating = useAppSelector(state => state.orgResponsiblePerson.updating);
  const updateSuccess = useAppSelector(state => state.orgResponsiblePerson.updateSuccess);

  const lastForm = forms.find(it => it.id.toString() === formId.toString());

  useEffect(() => {
    dispatch(reset());
    dispatch(getForms({}));
  }, []);

  const saveEntity = values => {
    const entity = {
      ...orgResponsiblePersonEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };
    dispatch(createEntity(entity));
  };

  const defaultValues = () =>
    true
      ? {}
      : {
          ...orgResponsiblePersonEntity,
          form: orgResponsiblePersonEntity?.form?.id,
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
                name="date"
                data-cy="date"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="org-responsible-person-form"
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.orgResponsiblePerson.form')}
                type="select"
                required
              >
                <option value={lastForm.id} key={lastForm.id}>
                  {lastForm.id}
                </option>
              </ValidatedField>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default OrgResponsiblePersonUpdate;
