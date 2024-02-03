import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getForms } from 'app/entities/form/form.reducer';
import { createEntity, reset } from './server.reducer';

interface ServerUpdateProps {
  formId: number;
}

export const ServerUpdate: React.FC<ServerUpdateProps> = ({ formId }) => {
  const dispatch = useAppDispatch();

  const forms = useAppSelector(state => state.form.entities);
  const serverEntity = useAppSelector(state => state.server.entity);
  const loading = useAppSelector(state => state.server.loading);
  const updating = useAppSelector(state => state.server.updating);

  const lastForm = forms.find(it => it.id.toString() === formId.toString());

  useEffect(() => {
    dispatch(reset());
    dispatch(getForms({}));
  }, []);

  const saveEntity = values => {
    const entity = {
      ...serverEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };
    dispatch(createEntity(entity));
  };

  const defaultValues = () =>
    true
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
              {!true ? (
                <ValidatedField
                  name="id"
                  hidden
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

export default ServerUpdate;
