import React, { useEffect, useState } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ItDeviceType } from 'app/shared/model/enumerations/it-device-type.model';
import { IItDevice } from 'app/shared/model/it-device.model';
import axios from 'axios';
import { incrementEditIndex } from '../form/survey-edit-index-reducer';
import { createEntity, updateEntity } from './it-device.reducer';

interface ItDeviceSurveyUpdateProps {
  formId: string;
}

export const ItDeviceSurveyUpdate: React.FC<ItDeviceSurveyUpdateProps> = ({ formId }) => {
  const dispatch = useAppDispatch();

  const [itDeviceEntity, setItDeviceEntity] = useState<IItDevice | null>(null);
  useEffect(() => {
    const getItDeviceEntity = async () => {
      const apiUrl = 'api/it-devices/form';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<IItDevice>(requestUrl);
      setItDeviceEntity(response.data);
    };
    getItDeviceEntity();
  }, [formId]);

  const forms = useAppSelector(state => state.form.entities);
  const loading = useAppSelector(state => state.itDevice.loading);
  const updating = useAppSelector(state => state.itDevice.updating);
  const itDeviceTypeValues = Object.keys(ItDeviceType);

  const saveEntity = values => {
    const entity = {
      ...itDeviceEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };

    if (itDeviceEntity === null) {
      dispatch(createEntity(entity));
      dispatch(incrementEditIndex(1));
    } else {
      dispatch(updateEntity(entity));
      dispatch(incrementEditIndex(1));
    }
  };

  const defaultValues = () =>
    itDeviceEntity === null
      ? {}
      : {
          deviceType: 'DesktopComputers',
          ...itDeviceEntity,
          form: formId,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h3 id="surveySampleApp.itDevice.home.createOrEditLabel" data-cy="ItDeviceCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.itDevice.home.createOrEditLabel">Create or edit a ItDevice</Translate>
          </h3>
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
                type="number"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.itDevice.brandAndModel')}
                id="it-device-brandAndModel"
                name="brandAndModel"
                data-cy="brandAndModel"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label={translate('surveySampleApp.itDevice.age')} id="it-device-age" name="age" data-cy="age" type="text" />
              <ValidatedField
                label={translate('surveySampleApp.itDevice.purpose')}
                id="it-device-purpose"
                name="purpose"
                data-cy="purpose"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('surveySampleApp.itDevice.currentStatus')}
                id="it-device-currentStatus"
                name="currentStatus"
                data-cy="currentStatus"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
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

export default ItDeviceSurveyUpdate;
