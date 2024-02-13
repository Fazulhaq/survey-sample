import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { FormStatus } from 'app/shared/model/enumerations/form-status.model';
import { convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { getEntity, updateEntity } from './form.reducer';
import { incrementEditIndex } from './survey-edit-index-reducer';

interface SurveyUpdateProps {
  formId: string;
}

export const SurveyUpdate: React.FC<SurveyUpdateProps> = ({ formId }) => {
  const dispatch = useAppDispatch();

  const users = useAppSelector(state => state.userManagement.users);
  const account = useAppSelector(state => state.authentication.account);
  const formEntity = useAppSelector(state => state.form.entity);
  const loading = useAppSelector(state => state.form.loading);
  const updating = useAppSelector(state => state.form.updating);
  const formStatusValues = Object.keys(FormStatus);

  useEffect(() => {
    dispatch(getEntity(formId));
    dispatch(getUsers({}));
  }, []);

  const saveEntity = values => {
    values.updateDate = convertDateTimeToServer(values.updateDate);

    const entity = {
      ...formEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
    };

    dispatch(updateEntity(entity));
    dispatch(incrementEditIndex(1));
  };

  const defaultValues = () =>
    false
      ? {
          updateDate: displayDefaultDateTime(),
        }
      : {
          ...formEntity,
          updateDate: displayDefaultDateTime(),
          user: account.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="surveySampleApp.form.home.createOrEditLabel" data-cy="FormCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.form.detail.title">Edit Primary Data</Translate>
          </h2>
          <br />
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              <ValidatedField
                name="id"
                required
                hidden
                readOnly
                id="form-id"
                label={translate('global.field.id')}
                validate={{ required: false }}
              />
              <ValidatedField
                label={translate('surveySampleApp.form.futurePlan')}
                id="form-futurePlan"
                required
                name="futurePlan"
                data-cy="futurePlan"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.form.status')}
                id="form-status"
                hidden
                name="status"
                data-cy="status"
                type="select"
              >
                {formStatusValues.map(formStatus => (
                  <option value={formStatus} key={formStatus}>
                    {translate('surveySampleApp.FormStatus.' + formStatus)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('surveySampleApp.form.updateDate')}
                id="form-updateDate"
                hidden
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="form-user"
                hidden
                name="user"
                data-cy="user"
                label={translate('surveySampleApp.form.user')}
                type="select"
                required
              >
                <option value={account.id} key={account.id}>
                  {account.login}
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

export default SurveyUpdate;
