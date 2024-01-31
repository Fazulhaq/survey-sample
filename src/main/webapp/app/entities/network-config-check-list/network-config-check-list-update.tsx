import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IForm } from 'app/shared/model/form.model';
import { getEntities as getForms } from 'app/entities/form/form.reducer';
import { INetworkConfigCheckList } from 'app/shared/model/network-config-check-list.model';
import { getEntity, updateEntity, createEntity, reset } from './network-config-check-list.reducer';

export const NetworkConfigCheckListUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const forms = useAppSelector(state => state.form.entities);
  const networkConfigCheckListEntity = useAppSelector(state => state.networkConfigCheckList.entity);
  const loading = useAppSelector(state => state.networkConfigCheckList.loading);
  const updating = useAppSelector(state => state.networkConfigCheckList.updating);
  const updateSuccess = useAppSelector(state => state.networkConfigCheckList.updateSuccess);

  const handleClose = () => {
    navigate('/network-config-check-list' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getForms({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...networkConfigCheckListEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...networkConfigCheckListEntity,
          form: networkConfigCheckListEntity?.form?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="surveySampleApp.networkConfigCheckList.home.createOrEditLabel" data-cy="NetworkConfigCheckListCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.networkConfigCheckList.home.createOrEditLabel">
              Create or edit a NetworkConfigCheckList
            </Translate>
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
                  id="network-config-check-list-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.dhcp')}
                id="network-config-check-list-dhcp"
                name="dhcp"
                data-cy="dhcp"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.dns')}
                id="network-config-check-list-dns"
                name="dns"
                data-cy="dns"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.activeDirectory')}
                id="network-config-check-list-activeDirectory"
                name="activeDirectory"
                data-cy="activeDirectory"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.sharedDrives')}
                id="network-config-check-list-sharedDrives"
                name="sharedDrives"
                data-cy="sharedDrives"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.mailServer')}
                id="network-config-check-list-mailServer"
                name="mailServer"
                data-cy="mailServer"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.firewalls')}
                id="network-config-check-list-firewalls"
                name="firewalls"
                data-cy="firewalls"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.loadBalancing')}
                id="network-config-check-list-loadBalancing"
                name="loadBalancing"
                data-cy="loadBalancing"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.networkMonitoring')}
                id="network-config-check-list-networkMonitoring"
                name="networkMonitoring"
                data-cy="networkMonitoring"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.antivirus')}
                id="network-config-check-list-antivirus"
                name="antivirus"
                data-cy="antivirus"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.integratedSystems')}
                id="network-config-check-list-integratedSystems"
                name="integratedSystems"
                data-cy="integratedSystems"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.antiSpam')}
                id="network-config-check-list-antiSpam"
                name="antiSpam"
                data-cy="antiSpam"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.wpa')}
                id="network-config-check-list-wpa"
                name="wpa"
                data-cy="wpa"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.autoBackup')}
                id="network-config-check-list-autoBackup"
                name="autoBackup"
                data-cy="autoBackup"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.physicalSecurity')}
                id="network-config-check-list-physicalSecurity"
                name="physicalSecurity"
                data-cy="physicalSecurity"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.storageServer')}
                id="network-config-check-list-storageServer"
                name="storageServer"
                data-cy="storageServer"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.securityAudit')}
                id="network-config-check-list-securityAudit"
                name="securityAudit"
                data-cy="securityAudit"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.disasterRecovery')}
                id="network-config-check-list-disasterRecovery"
                name="disasterRecovery"
                data-cy="disasterRecovery"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.proxyServer')}
                id="network-config-check-list-proxyServer"
                name="proxyServer"
                data-cy="proxyServer"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('surveySampleApp.networkConfigCheckList.wdsServer')}
                id="network-config-check-list-wdsServer"
                name="wdsServer"
                data-cy="wdsServer"
                check
                type="checkbox"
              />
              <ValidatedField
                id="network-config-check-list-form"
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.networkConfigCheckList.form')}
                type="select"
                required
              >
                <option value="" key="0" />
                {forms
                  ? forms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/network-config-check-list" replace color="info">
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

export default NetworkConfigCheckListUpdate;
