import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getForms } from 'app/entities/form/form.reducer';
import { DataCenterDeviceType } from 'app/shared/model/enumerations/data-center-device-type.model';
import { createEntity, reset } from './datacenter-device.reducer';

interface DatacenterDeviceUpdateProps {
  formId: number;
}

export const DatacenterDeviceUpdate: React.FC<DatacenterDeviceUpdateProps> = ({ formId }) => {
  const dispatch = useAppDispatch();

  const forms = useAppSelector(state => state.form.entities);
  const datacenterDeviceEntity = useAppSelector(state => state.datacenterDevice.entity);
  const loading = useAppSelector(state => state.datacenterDevice.loading);
  const updating = useAppSelector(state => state.datacenterDevice.updating);
  const dataCenterDeviceTypeValues = Object.keys(DataCenterDeviceType);

  const lastForm = forms.find(it => it.id.toString() === formId.toString());

  useEffect(() => {
    dispatch(reset());
    dispatch(getForms({}));
  }, []);

  const saveEntity = values => {
    const entity = {
      ...datacenterDeviceEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };
    dispatch(createEntity(entity));
  };

  const defaultValues = () =>
    true
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
              {!true ? (
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
                <option value={lastForm.id} key={lastForm.id}>
                  {lastForm.id}
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

export default DatacenterDeviceUpdate;
