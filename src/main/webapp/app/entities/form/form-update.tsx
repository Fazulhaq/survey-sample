import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';

import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { FormStatus } from 'app/shared/model/enumerations/form-status.model';
import { createEntity, reset } from './form.reducer';

export const FormUpdate = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  const users = useAppSelector(state => state.userManagement.users);
  const account = useAppSelector(state => state.authentication.account);
  const organizations = useAppSelector(state => state.organization.entities);
  const formEntity = useAppSelector(state => state.form.entity);
  const loading = useAppSelector(state => state.form.loading);
  const updating = useAppSelector(state => state.form.updating);
  const formStatusValues = Object.keys(FormStatus);

  const foundOrganization = organizations.find(it => it.id.toString() === id.toString());

  useEffect(() => {
    dispatch(reset());
    dispatch(getUsers({}));
    dispatch(getOrganizations({}));
  }, []);

  const saveEntity = values => {
    values.createDate = convertDateTimeToServer(values.createDate);
    values.updateDate = convertDateTimeToServer(values.updateDate);

    const entity = {
      ...formEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      organization: organizations.find(it => it.id.toString() === values.organization.toString()),
    };
    dispatch(createEntity(entity));
  };

  const defaultValues = () => ({
    createDate: displayDefaultDateTime(),
    updateDate: displayDefaultDateTime(),
  });

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="surveySampleApp.form.home.createOrEditLabel" data-cy="FormCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.form.home.createOrEditLabel">Create or edit a Form</Translate>
          </h2>
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
                hidden
                required
                readOnly
                id="form-id"
                label={translate('global.field.id')}
                validate={{ required: false }}
              />
              <ValidatedField
                label={translate('surveySampleApp.form.futurePlan')}
                id="form-futurePlan"
                name="futurePlan"
                data-cy="futurePlan"
                type="text"
              />
              <ValidatedField
                label={translate('surveySampleApp.form.status')}
                id="form-status"
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
                label={translate('surveySampleApp.form.createDate')}
                id="form-createDate"
                name="createDate"
                data-cy="createDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('surveySampleApp.form.updateDate')}
                id="form-updateDate"
                name="updateDate"
                data-cy="updateDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="form-user"
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
              <ValidatedField
                id="form-organization"
                name="organization"
                data-cy="organization"
                label={translate('surveySampleApp.form.organization')}
                type="select"
                required
              >
                <option value={foundOrganization.id} key={foundOrganization.id}>
                  {foundOrganization.name}
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

export default FormUpdate;
