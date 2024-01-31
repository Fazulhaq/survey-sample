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
import { ISystem } from 'app/shared/model/system.model';
import { getEntity, updateEntity, createEntity, reset } from './system.reducer';

export const SystemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const forms = useAppSelector(state => state.form.entities);
  const systemEntity = useAppSelector(state => state.system.entity);
  const loading = useAppSelector(state => state.system.loading);
  const updating = useAppSelector(state => state.system.updating);
  const updateSuccess = useAppSelector(state => state.system.updateSuccess);

  const handleClose = () => {
    navigate('/system' + location.search);
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
      ...systemEntity,
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
          ...systemEntity,
          form: systemEntity?.form?.id,
        };

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
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
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
              />
              <ValidatedField
                label={translate('surveySampleApp.system.question2')}
                id="system-question2"
                name="question2"
                data-cy="question2"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.system.question3')}
                id="system-question3"
                name="question3"
                data-cy="question3"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.system.question4')}
                id="system-question4"
                name="question4"
                data-cy="question4"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.system.question5')}
                id="system-question5"
                name="question5"
                data-cy="question5"
                type="text"
              />
              <ValidatedField
                id="system-form"
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.system.form')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/system" replace color="info">
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

export default SystemUpdate;
