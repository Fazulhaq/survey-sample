import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { FormStatus } from 'app/shared/model/enumerations/form-status.model';
import { convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { incrementIndex } from '../stepper-index/stepper-index.reducer';
import { createEntity, reset } from './form.reducer';

interface FormUpdateProps {
  organizationId: string;
}

export const FormUpdate: React.FC<FormUpdateProps> = ({ organizationId }) => {
  const dispatch = useAppDispatch();

  const users = useAppSelector(state => state.userManagement.users);
  const account = useAppSelector(state => state.authentication.account);
  const organizations = useAppSelector(state => state.organization.entities);
  const formEntity = useAppSelector(state => state.form.entity);
  const loading = useAppSelector(state => state.form.loading);
  const updating = useAppSelector(state => state.form.updating);
  const formStatusValues = Object.keys(FormStatus);

  const foundOrganization = organizations.find(it => it.id.toString() === organizationId.toString());

  useEffect(() => {
    dispatch(reset());
    dispatch(getUsers({}));
    dispatch(getOrganizations({}));
  }, []);

  const saveEntity = async values => {
    values.createDate = convertDateTimeToServer(values.createDate);
    values.updateDate = convertDateTimeToServer(values.updateDate);

    const entity = {
      ...formEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      organization: organizations.find(it => it.id.toString() === values.organization.toString()),
    };
    await dispatch(createEntity(entity));
    dispatch(incrementIndex(1));
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
                label={translate('surveySampleApp.form.createDate')}
                id="form-createDate"
                name="createDate"
                hidden
                data-cy="createDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
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
              <ValidatedField
                id="form-organization"
                hidden
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
