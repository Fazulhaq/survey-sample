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
import { IDatacenterDevice } from 'app/shared/model/datacenter-device.model';
import { DataCenterDeviceType } from 'app/shared/model/enumerations/data-center-device-type.model';
import { getEntity, updateEntity, createEntity, reset } from './datacenter-device.reducer';

export const DatacenterDeviceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const forms = useAppSelector(state => state.form.entities);
  const datacenterDeviceEntity = useAppSelector(state => state.datacenterDevice.entity);
  const loading = useAppSelector(state => state.datacenterDevice.loading);
  const updating = useAppSelector(state => state.datacenterDevice.updating);
  const updateSuccess = useAppSelector(state => state.datacenterDevice.updateSuccess);
  const dataCenterDeviceTypeValues = Object.keys(DataCenterDeviceType);

  const handleClose = () => {
    navigate('/datacenter-device' + location.search);
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
      ...datacenterDeviceEntity,
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
          deviceType: 'Racks',
          ...datacenterDeviceEntity,
          form: datacenterDeviceEntity?.form?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="surveySampleApp.datacenterDevice.home.createOrEditLabel" data-cy="DatacenterDeviceCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.datacenterDevice.home.createOrEditLabel">Create or edit a DatacenterDevice</Translate>
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
                  id="datacenter-device-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('surveySampleApp.datacenterDevice.deviceType')}
                id="datacenter-device-deviceType"
                name="deviceType"
                data-cy="deviceType"
                type="select"
              >
                {dataCenterDeviceTypeValues.map(dataCenterDeviceType => (
                  <option value={dataCenterDeviceType} key={dataCenterDeviceType}>
                    {translate('surveySampleApp.DataCenterDeviceType.' + dataCenterDeviceType)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('surveySampleApp.datacenterDevice.quantity')}
                id="datacenter-device-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.datacenterDevice.brandAndModel')}
                id="datacenter-device-brandAndModel"
                name="brandAndModel"
                data-cy="brandAndModel"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.datacenterDevice.age')}
                id="datacenter-device-age"
                name="age"
                data-cy="age"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.datacenterDevice.purpose')}
                id="datacenter-device-purpose"
                name="purpose"
                data-cy="purpose"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.datacenterDevice.currentStatus')}
                id="datacenter-device-currentStatus"
                name="currentStatus"
                data-cy="currentStatus"
                type="text"
              />
              <ValidatedField
                id="datacenter-device-form"
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.datacenterDevice.form')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/datacenter-device" replace color="info">
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

export default DatacenterDeviceUpdate;
