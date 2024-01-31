import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IForm } from 'app/shared/model/form.model';
import { getEntities as getForms } from 'app/entities/form/form.reducer';
import { IOrgResponsiblePerson } from 'app/shared/model/org-responsible-person.model';
import { getEntity, updateEntity, createEntity, reset } from './org-responsible-person.reducer';

export const OrgResponsiblePersonUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const forms = useAppSelector(state => state.form.entities);
  const orgResponsiblePersonEntity = useAppSelector(state => state.orgResponsiblePerson.entity);
  const loading = useAppSelector(state => state.orgResponsiblePerson.loading);
  const updating = useAppSelector(state => state.orgResponsiblePerson.updating);
  const updateSuccess = useAppSelector(state => state.orgResponsiblePerson.updateSuccess);

  const handleClose = () => {
    navigate('/org-responsible-person' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getForms({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...orgResponsiblePersonEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
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
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
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
                <option value="" key="0" />
                {forms
                  ? forms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/org-responsible-person" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
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
