import React, { useEffect, useState } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDatacenterDevice } from 'app/shared/model/datacenter-device.model';
import { DataCenterDeviceType } from 'app/shared/model/enumerations/data-center-device-type.model';
import axios from 'axios';
import { incrementEditIndex } from '../form/survey-edit-index-reducer';
import { createEntity, updateEntity } from './datacenter-device.reducer';

interface DatacenterDeviceUpdateProps {
  formId: string;
}

export const DatacenterDeviceSurveyUpdate: React.FC<DatacenterDeviceUpdateProps> = ({ formId }) => {
  const dispatch = useAppDispatch();

  const [datacenterDeviceEntity, setDatacenterDeviceEntity] = useState<IDatacenterDevice | null>(null);
  useEffect(() => {
    const getDatacenterDeviceEntity = async () => {
      const apiUrl = 'api/datacenter-devices/form';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<IDatacenterDevice>(requestUrl);
      setDatacenterDeviceEntity(response.data);
    };
    getDatacenterDeviceEntity();
  }, [formId]);

  const forms = useAppSelector(state => state.form.entities);
  const loading = useAppSelector(state => state.datacenterDevice.loading);
  const updating = useAppSelector(state => state.datacenterDevice.updating);
  const dataCenterDeviceTypeValues = Object.keys(DataCenterDeviceType);

  const saveEntity = values => {
    const entity = {
      ...datacenterDeviceEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };
    if (datacenterDeviceEntity === null) {
      dispatch(createEntity(entity));
      dispatch(incrementEditIndex(1));
    } else {
      dispatch(updateEntity(entity));
      dispatch(incrementEditIndex(1));
    }
  };

  const defaultValues = () =>
    datacenterDeviceEntity === null
      ? {}
      : {
          deviceType: 'Racks',
          ...datacenterDeviceEntity,
          form: formId,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h3 id="surveySampleApp.datacenterDevice.home.createOrEditLabel" data-cy="DatacenterDeviceCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.datacenterDevice.home.createOrEditLabel">Create or edit a DatacenterDevice</Translate>
          </h3>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!datacenterDeviceEntity === null ? (
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
                hidden
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.datacenterDevice.form')}
                type="select"
                required
              >
                <option value={formId} key={formId}>
                  {formId}
                </option>
              </ValidatedField>
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <Translate contentKey="entity.action.updatenext">edit next</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default DatacenterDeviceSurveyUpdate;
