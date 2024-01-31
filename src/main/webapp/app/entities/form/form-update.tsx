import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import React, { useEffect } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, FormText, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';

import { getEntities as getOrganizations } from 'app/entities/organization/organization.reducer';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { FormStatus } from 'app/shared/model/enumerations/form-status.model';
import { createEntity, getEntity, reset, updateEntity } from './form.reducer';

export const FormUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const organizations = useAppSelector(state => state.organization.entities);
  const formEntity = useAppSelector(state => state.form.entity);
  const loading = useAppSelector(state => state.form.loading);
  const updating = useAppSelector(state => state.form.updating);
  const updateSuccess = useAppSelector(state => state.form.updateSuccess);
  const formStatusValues = Object.keys(FormStatus);

  const handleClose = () => {
    navigate('/form' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getOrganizations({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.createDate = convertDateTimeToServer(values.createDate);
    values.updateDate = convertDateTimeToServer(values.updateDate);

    const entity = {
      ...formEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      organization: organizations.find(it => it.id.toString() === values.organization.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          createDate: displayDefaultDateTime(),
          updateDate: displayDefaultDateTime(),
        }
      : {
          status: 'INPROGRESS',
          ...formEntity,
          createDate: convertDateTimeFromServer(formEntity.createDate),
          updateDate: convertDateTimeFromServer(formEntity.updateDate),
          user: formEntity?.user?.id,
          organization: formEntity?.organization?.id,
        };

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
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="form-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
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
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <ValidatedField
                id="form-organization"
                name="organization"
                data-cy="organization"
                label={translate('surveySampleApp.form.organization')}
                type="select"
                required
              >
                <option value="" key="0" />
                {organizations
                  ? organizations.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/form" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
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
