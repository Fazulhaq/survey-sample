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
import { IServer } from 'app/shared/model/server.model';
import { getEntity, updateEntity, createEntity, reset } from './server.reducer';

export const ServerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const forms = useAppSelector(state => state.form.entities);
  const serverEntity = useAppSelector(state => state.server.entity);
  const loading = useAppSelector(state => state.server.loading);
  const updating = useAppSelector(state => state.server.updating);
  const updateSuccess = useAppSelector(state => state.server.updateSuccess);

  const handleClose = () => {
    navigate('/server' + location.search);
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
      ...serverEntity,
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
          ...serverEntity,
          form: serverEntity?.form?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="surveySampleApp.server.home.createOrEditLabel" data-cy="ServerCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.server.home.createOrEditLabel">Create or edit a Server</Translate>
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
                  id="server-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('surveySampleApp.server.question1')}
                id="server-question1"
                name="question1"
                data-cy="question1"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.server.question2')}
                id="server-question2"
                name="question2"
                data-cy="question2"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.server.question3')}
                id="server-question3"
                name="question3"
                data-cy="question3"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.server.question4')}
                id="server-question4"
                name="question4"
                data-cy="question4"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.server.question5')}
                id="server-question5"
                name="question5"
                data-cy="question5"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.server.question6')}
                id="server-question6"
                name="question6"
                data-cy="question6"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.server.question7')}
                id="server-question7"
                name="question7"
                data-cy="question7"
                type="text"
              />
              <ValidatedField
                id="server-form"
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.server.form')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/server" replace color="info">
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

export default ServerUpdate;
