import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getForms } from 'app/entities/form/form.reducer';
import { createEntity, reset } from './internet.reducer';

interface InternetUpdateProps {
  formId: number;
}

export const InternetUpdate: React.FC<InternetUpdateProps> = ({ formId }) => {
  const dispatch = useAppDispatch();

  const forms = useAppSelector(state => state.form.entities);
  const internetEntity = useAppSelector(state => state.internet.entity);
  const loading = useAppSelector(state => state.internet.loading);
  const updating = useAppSelector(state => state.internet.updating);
  const updateSuccess = useAppSelector(state => state.internet.updateSuccess);

  const lastForm = forms.find(it => it.id.toString() === formId.toString());

  useEffect(() => {
    dispatch(reset());
    dispatch(getForms({}));
  }, []);

  const saveEntity = values => {
    const entity = {
      ...internetEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };
    dispatch(createEntity(entity));
  };

  const defaultValues = () =>
    true
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
              {!true ? (
                <ValidatedField
                  name="id"
                  required
                  hidden
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

export default InternetUpdate;
