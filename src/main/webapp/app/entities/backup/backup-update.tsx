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
import { IBackup } from 'app/shared/model/backup.model';
import { getEntity, updateEntity, createEntity, reset } from './backup.reducer';

export const BackupUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const forms = useAppSelector(state => state.form.entities);
  const backupEntity = useAppSelector(state => state.backup.entity);
  const loading = useAppSelector(state => state.backup.loading);
  const updating = useAppSelector(state => state.backup.updating);
  const updateSuccess = useAppSelector(state => state.backup.updateSuccess);

  const handleClose = () => {
    navigate('/backup' + location.search);
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
      ...backupEntity,
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
          ...backupEntity,
          form: backupEntity?.form?.id,
        };

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
              {!isNew ? (
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
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question2')}
                id="backup-question2"
                name="question2"
                data-cy="question2"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question3')}
                id="backup-question3"
                name="question3"
                data-cy="question3"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question4')}
                id="backup-question4"
                name="question4"
                data-cy="question4"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question5')}
                id="backup-question5"
                name="question5"
                data-cy="question5"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question6')}
                id="backup-question6"
                name="question6"
                data-cy="question6"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question7')}
                id="backup-question7"
                name="question7"
                data-cy="question7"
                type="text"
              />
              <ValidatedField
                id="backup-form"
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.backup.form')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/backup" replace color="info">
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

export default BackupUpdate;
