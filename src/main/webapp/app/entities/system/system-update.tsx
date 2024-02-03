import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getForms } from 'app/entities/form/form.reducer';
import { createEntity, reset } from './system.reducer';

interface SystemUpdateProps {
  formId: number;
}

export const SystemUpdate: React.FC<SystemUpdateProps> = ({ formId }) => {
  const dispatch = useAppDispatch();

  // const navigate = useNavigate();

  const forms = useAppSelector(state => state.form.entities);
  const systemEntity = useAppSelector(state => state.system.entity);
  const loading = useAppSelector(state => state.system.loading);
  const updating = useAppSelector(state => state.system.updating);
  const updateSuccess = useAppSelector(state => state.system.updateSuccess);

  const lastForm = forms.find(it => it.id.toString() === formId.toString());

  // const handleClose = () => {
  //   navigate('/system' + location.search);
  // };

  useEffect(() => {
    dispatch(reset());
    dispatch(getForms({}));
  }, []);

  // useEffect(() => {
  //   if (updateSuccess) {
  //     handleClose();
  //   }
  // }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...systemEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };
    dispatch(createEntity(entity));
  };

  const defaultValues = () =>
    true
      ? {}
      : {
          ...systemEntity,
          form: systemEntity?.form?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="surveySampleApp.system.home.createOrEditLabel" data-cy="SystemCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.system.home.createOrEditLabel">Create or edit a System</Translate>
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
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.system.form')}
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

export default SystemUpdate;
