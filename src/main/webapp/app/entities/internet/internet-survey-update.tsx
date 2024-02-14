import React, { useEffect, useState } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInternet } from 'app/shared/model/internet.model';
import axios from 'axios';
import { incrementEditIndex } from '../form/survey-edit-index-reducer';
import { createEntity, updateEntity } from './internet.reducer';

interface InternetSurveyUpdateProps {
  formId: string;
}

export const InternetSurveyUpdate: React.FC<InternetSurveyUpdateProps> = ({ formId }) => {
  const dispatch = useAppDispatch();

  const [internetEntity, setInternetEntity] = useState<IInternet | null>(null);
  useEffect(() => {
    const getInternetEntity = async () => {
      const apiUrl = 'api/internets/form';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<IInternet>(requestUrl);
      setInternetEntity(response.data);
    };
    getInternetEntity();
  }, [formId]);

  const forms = useAppSelector(state => state.form.entities);
  const loading = useAppSelector(state => state.internet.loading);
  const updating = useAppSelector(state => state.internet.updating);

  const saveEntity = values => {
    const entity = {
      ...internetEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };

    if (internetEntity === null) {
      dispatch(createEntity(entity));
      dispatch(incrementEditIndex(1));
    } else {
      dispatch(updateEntity(entity));
      dispatch(incrementEditIndex(1));
    }
  };

  const defaultValues = () =>
    internetEntity === null
      ? {}
      : {
          ...internetEntity,
          form: formId,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h3 id="surveySampleApp.internet.home.createOrEditLabel" data-cy="InternetCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.internet.home.createOrEditLabel">Create or edit a Internet</Translate>
          </h3>
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
                hidden
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.internet.form')}
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

export default InternetSurveyUpdate;
