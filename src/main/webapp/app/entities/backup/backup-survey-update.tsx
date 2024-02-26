import React, { useEffect, useState } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBackup } from 'app/shared/model/backup.model';
import axios from 'axios';
import { incrementEditIndex } from '../form/survey-edit-index-reducer';
import { createEntity, updateEntity } from './backup.reducer';

interface BackupSurveyUpdateProps {
  formId: string;
}

export const BackupSurveyUpdate: React.FC<BackupSurveyUpdateProps> = ({ formId }) => {
  const dispatch = useAppDispatch();
  const [backupEntity, setBackupEntity] = useState<IBackup | null>(null);
  useEffect(() => {
    const getBackupEntity = async () => {
      const apiUrl = 'api/backups/form';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<IBackup>(requestUrl);
      setBackupEntity(response.data);
    };
    getBackupEntity();
  }, [formId]);

  const forms = useAppSelector(state => state.form.entities);
  const loading = useAppSelector(state => state.backup.loading);
  const updating = useAppSelector(state => state.backup.updating);

  const saveEntity = values => {
    const entity = {
      ...backupEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };
    if (backupEntity === null) {
      dispatch(createEntity(entity));
      dispatch(incrementEditIndex(1));
    } else {
      dispatch(updateEntity(entity));
      dispatch(incrementEditIndex(1));
    }
  };

  const defaultValues = () =>
    backupEntity === null
      ? {}
      : {
          ...backupEntity,
          form: formId,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h3 id="surveySampleApp.backup.home.createOrEditLabel" data-cy="BackupCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.backup.home.createOrEditLabel">Create or edit a Backup</Translate>
          </h3>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!backupEntity === null ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="backup-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('surveySampleApp.backup.question1')}
                id="backup-question1"
                name="question1"
                data-cy="question1"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question2')}
                id="backup-question2"
                name="question2"
                data-cy="question2"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question3')}
                id="backup-question3"
                name="question3"
                data-cy="question3"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question4')}
                id="backup-question4"
                name="question4"
                data-cy="question4"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question5')}
                id="backup-question5"
                name="question5"
                data-cy="question5"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question6')}
                id="backup-question6"
                name="question6"
                data-cy="question6"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.backup.question7')}
                id="backup-question7"
                name="question7"
                data-cy="question7"
                type="text"
              />
              <ValidatedField
                id="backup-form"
                hidden
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.backup.form')}
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

export default BackupSurveyUpdate;
