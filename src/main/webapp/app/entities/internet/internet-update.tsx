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
import { IInternet } from 'app/shared/model/internet.model';
import { getEntity, updateEntity, createEntity, reset } from './internet.reducer';

export const InternetUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const forms = useAppSelector(state => state.form.entities);
  const internetEntity = useAppSelector(state => state.internet.entity);
  const loading = useAppSelector(state => state.internet.loading);
  const updating = useAppSelector(state => state.internet.updating);
  const updateSuccess = useAppSelector(state => state.internet.updateSuccess);

  const handleClose = () => {
    navigate('/internet' + location.search);
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
      ...internetEntity,
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
          ...internetEntity,
          form: internetEntity?.form?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="surveySampleApp.internet.home.createOrEditLabel" data-cy="InternetCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.internet.home.createOrEditLabel">Create or edit a Internet</Translate>
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
                  id="internet-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('surveySampleApp.internet.question1')}
                id="internet-question1"
                name="question1"
                data-cy="question1"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.internet.question2')}
                id="internet-question2"
                name="question2"
                data-cy="question2"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.internet.question3')}
                id="internet-question3"
                name="question3"
                data-cy="question3"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.internet.question4')}
                id="internet-question4"
                name="question4"
                data-cy="question4"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.internet.question5')}
                id="internet-question5"
                name="question5"
                data-cy="question5"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.internet.question6')}
                id="internet-question6"
                name="question6"
                data-cy="question6"
                type="text"
              />
              <ValidatedField
                id="internet-form"
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.internet.form')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/internet" replace color="info">
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

export default InternetUpdate;
