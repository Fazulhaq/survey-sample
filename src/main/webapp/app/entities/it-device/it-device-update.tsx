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
import { IItDevice } from 'app/shared/model/it-device.model';
import { ItDeviceType } from 'app/shared/model/enumerations/it-device-type.model';
import { getEntity, updateEntity, createEntity, reset } from './it-device.reducer';

export const ItDeviceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const forms = useAppSelector(state => state.form.entities);
  const itDeviceEntity = useAppSelector(state => state.itDevice.entity);
  const loading = useAppSelector(state => state.itDevice.loading);
  const updating = useAppSelector(state => state.itDevice.updating);
  const updateSuccess = useAppSelector(state => state.itDevice.updateSuccess);
  const itDeviceTypeValues = Object.keys(ItDeviceType);

  const handleClose = () => {
    navigate('/it-device' + location.search);
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
      ...itDeviceEntity,
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
          deviceType: 'DesktopComputers',
          ...itDeviceEntity,
          form: itDeviceEntity?.form?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="surveySampleApp.itDevice.home.createOrEditLabel" data-cy="ItDeviceCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.itDevice.home.createOrEditLabel">Create or edit a ItDevice</Translate>
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
                  id="it-device-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('surveySampleApp.itDevice.deviceType')}
                id="it-device-deviceType"
                name="deviceType"
                data-cy="deviceType"
                type="select"
              >
                {itDeviceTypeValues.map(itDeviceType => (
                  <option value={itDeviceType} key={itDeviceType}>
                    {translate('surveySampleApp.ItDeviceType.' + itDeviceType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('surveySampleApp.itDevice.quantity')}
                id="it-device-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.itDevice.brandAndModel')}
                id="it-device-brandAndModel"
                name="brandAndModel"
                data-cy="brandAndModel"
                type="text"
              />
              <ValidatedField label={translate('surveySampleApp.itDevice.age')} id="it-device-age" name="age" data-cy="age" type="text" />
              <ValidatedField
                label={translate('surveySampleApp.itDevice.purpose')}
                id="it-device-purpose"
                name="purpose"
                data-cy="purpose"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.itDevice.currentStatus')}
                id="it-device-currentStatus"
                name="currentStatus"
                data-cy="currentStatus"
                type="text"
              />
              <ValidatedField
                id="it-device-form"
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.itDevice.form')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/it-device" replace color="info">
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

export default ItDeviceUpdate;
