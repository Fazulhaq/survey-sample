import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getForms } from 'app/entities/form/form.reducer';
import { incrementIndex } from '../stepper-index/stepper-index.reducer';
import { createEntity, reset } from './org-responsible-person.reducer';

export const OrgResponsiblePersonUpdate = () => {
  const dispatch = useAppDispatch();

  const forms = useAppSelector(state => state.form.entities);
  const orgResponsiblePersonEntity = useAppSelector(state => state.orgResponsiblePerson.entity);
  const loading = useAppSelector(state => state.orgResponsiblePerson.loading);
  const updating = useAppSelector(state => state.orgResponsiblePerson.updating);

  const lastForm = forms.reduce((maxId, form) => {
    return form.id > maxId ? form.id : maxId;
  }, 0);

  useEffect(() => {
    dispatch(reset());
    dispatch(getForms({}));
  }, []);

  const saveEntity = async values => {
    const entity = {
      ...orgResponsiblePersonEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };
    await dispatch(createEntity(entity));
    dispatch(incrementIndex(1));
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
                hidden
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.orgResponsiblePerson.form')}
                type="select"
                required
              >
                <option value={lastForm} key={lastForm}>
                  {lastForm}
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
