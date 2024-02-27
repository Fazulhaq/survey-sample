import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getForms } from 'app/entities/form/form.reducer';
import { incrementIndex } from '../stepper-index/stepper-index.reducer';
import { createEntity, reset } from './system.reducer';

export const SystemUpdate = () => {
  const dispatch = useAppDispatch();

  const forms = useAppSelector(state => state.form.entities);
  const systemEntity = useAppSelector(state => state.system.entity);
  const loading = useAppSelector(state => state.system.loading);
  const updating = useAppSelector(state => state.system.updating);

  const lastFormId = forms.reduce((maxId, form) => {
    return form.id > maxId ? form.id : maxId;
  }, 0);

  useEffect(() => {
    dispatch(reset());
    dispatch(getForms({}));
  }, []);

  const saveEntity = async values => {
    const entity = {
      ...systemEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };
    await dispatch(createEntity(entity));
    dispatch(incrementIndex(1));
  };

  const defaultValues = () => ({});

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="surveySampleApp.system.home.createOrEditLabel" data-cy="SystemCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.system.home.createOrEditLabel">Create or edit a System</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!systemEntity === null ? (
                <ValidatedField
                  name="id"
                  required
                  hidden
                  readOnly
                  id="system-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('surveySampleApp.system.question1')}
                id="system-question1"
                name="question1"
                data-cy="question1"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.system.question2')}
                id="system-question2"
                name="question2"
                data-cy="question2"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.system.question3')}
                id="system-question3"
                name="question3"
                data-cy="question3"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.system.question4')}
                id="system-question4"
                name="question4"
                data-cy="question4"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.system.question5')}
                id="system-question5"
                name="question5"
                data-cy="question5"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="system-form"
                hidden
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.system.form')}
                type="select"
                required
              >
                <option value={lastFormId} key={lastFormId}>
                  {lastFormId}
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

export default SystemUpdate;
