import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getForms } from 'app/entities/form/form.reducer';
import { incrementIndex } from '../stepper-index/stepper-index.reducer';
import { createEntity, reset } from './backup.reducer';

export const BackupUpdate = () => {
  const dispatch = useAppDispatch();

  const forms = useAppSelector(state => state.form.entities);
  const backupEntity = useAppSelector(state => state.backup.entity);
  const loading = useAppSelector(state => state.backup.loading);
  const updating = useAppSelector(state => state.backup.updating);

  const lastFormId = forms.reduce((maxId, form) => {
    return form.id > maxId ? form.id : maxId;
  }, 0);

  useEffect(() => {
    dispatch(reset());
    dispatch(getForms({}));
  }, []);

  const saveEntity = async values => {
    const entity = {
      ...backupEntity,
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
          <h2 id="surveySampleApp.backup.home.createOrEditLabel" data-cy="BackupCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.backup.home.createOrEditLabel">Create or edit a Backup</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!backupEntity === null ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="backup-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('surveySampleApp.backup.question1')}
                id="backup-question1"
                name="question1"
                data-cy="question1"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question2')}
                id="backup-question2"
                name="question2"
                data-cy="question2"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question3')}
                id="backup-question3"
                name="question3"
                data-cy="question3"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question4')}
                id="backup-question4"
                name="question4"
                data-cy="question4"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question5')}
                id="backup-question5"
                name="question5"
                data-cy="question5"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question6')}
                id="backup-question6"
                name="question6"
                data-cy="question6"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question7')}
                id="backup-question7"
                name="question7"
                data-cy="question7"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="backup-form"
                hidden
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.backup.form')}
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

export default BackupUpdate;
