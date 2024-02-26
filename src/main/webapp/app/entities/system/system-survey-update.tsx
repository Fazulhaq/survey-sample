import React, { useEffect, useState } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISystem } from 'app/shared/model/system.model';
import axios from 'axios';
import { incrementEditIndex } from '../form/survey-edit-index-reducer';
import { createEntity, updateEntity } from './system.reducer';

interface SystemSurveyUpdateProps {
  formId: string;
}

export const SystemSurveyUpdate: React.FC<SystemSurveyUpdateProps> = ({ formId }) => {
  const dispatch = useAppDispatch();

  const [systemEntity, setSystemEntity] = useState<ISystem | null>(null);
  useEffect(() => {
    const getSystemEntity = async () => {
      const apiUrl = 'api/systems/form';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<ISystem>(requestUrl);
      setSystemEntity(response.data);
    };
    getSystemEntity();
  }, [formId]);

  const forms = useAppSelector(state => state.form.entities);
  const loading = useAppSelector(state => state.system.loading);
  const updating = useAppSelector(state => state.system.updating);

  const saveEntity = values => {
    const entity = {
      ...systemEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };

    if (systemEntity === null) {
      dispatch(createEntity(entity));
      dispatch(incrementEditIndex(1));
    } else {
      dispatch(updateEntity(entity));
      dispatch(incrementEditIndex(1));
    }
  };

  const defaultValues = () =>
    systemEntity === null
      ? {}
      : {
          ...systemEntity,
          form: formId,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h3 id="surveySampleApp.system.home.createOrEditLabel" data-cy="SystemCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.system.home.createOrEditLabel">Create or edit a System</Translate>
          </h3>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!systemEntity === null ? (
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
                hidden
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.system.form')}
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

export default SystemSurveyUpdate;
