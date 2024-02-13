import React, { useEffect, useState } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { IServer } from 'app/shared/model/server.model';
import axios from 'axios';
import { incrementEditIndex } from '../form/survey-edit-index-reducer';
import { createEntity, updateEntity } from './server.reducer';

interface ServerSurveyUpdateProps {
  formId: string;
}

export const ServerSurveyUpdate: React.FC<ServerSurveyUpdateProps> = ({ formId }) => {
  const [serverEntity, setServerEntity] = useState<IServer | null>(null);

  useEffect(() => {
    const getServerEntity = async () => {
      const apiUrl = 'api/servers/form';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<IServer>(requestUrl);
      setServerEntity(response.data);
    };

    getServerEntity();
  }, [formId]);

  const dispatch = useAppDispatch();

  const forms = useAppSelector(state => state.form.entities);
  const loading = useAppSelector(state => state.server.loading);
  const updating = useAppSelector(state => state.server.updating);

  const saveEntity = values => {
    const entity = {
      ...serverEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };

    if (serverEntity === null) {
      dispatch(createEntity(entity));
      dispatch(incrementEditIndex(1));
    } else {
      dispatch(updateEntity(entity));
      dispatch(incrementEditIndex(1));
    }
  };

  const defaultValues = () =>
    serverEntity === null
      ? {}
      : {
          ...serverEntity,
          form: formId,
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
              {!true ? (
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
                hidden
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.server.form')}
                type="select"
                required
              >
                <option value={formId} key={formId}>
                  {formId}
                </option>
              </ValidatedField>
              &nbsp;
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

export default ServerSurveyUpdate;
