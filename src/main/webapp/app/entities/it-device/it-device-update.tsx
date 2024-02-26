import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getForms } from 'app/entities/form/form.reducer';
import { ItDeviceType } from 'app/shared/model/enumerations/it-device-type.model';
import { incrementIndex } from '../stepper-index/stepper-index.reducer';
import { createEntity, reset } from './it-device.reducer';

export const ItDeviceUpdate = () => {
  const dispatch = useAppDispatch();

  const forms = useAppSelector(state => state.form.entities);
  const itDeviceEntity = useAppSelector(state => state.itDevice.entity);
  const loading = useAppSelector(state => state.itDevice.loading);
  const updating = useAppSelector(state => state.itDevice.updating);
  const itDeviceTypeValues = Object.keys(ItDeviceType);

  const lastFormId = forms.reduce((maxId, form) => {
    return form.id > maxId ? form.id : maxId;
  }, 0);

  useEffect(() => {
    dispatch(reset());
    dispatch(getForms({}));
  }, []);

  const saveEntity = async values => {
    const entity = {
      ...itDeviceEntity,
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
              {!itDeviceEntity === null ? (
                <ValidatedField
                  name="id"
                  required
                  hidden
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
                hidden
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.itDevice.form')}
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

export default ItDeviceUpdate;
