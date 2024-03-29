import React, { useEffect, useState } from 'react';
import { Translate, ValidatedField, ValidatedForm, translate } from 'react-jhipster';
import { Button, Col, Row } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INetworkConfigCheckList } from 'app/shared/model/network-config-check-list.model';
import axios from 'axios';
import { incrementEditIndex } from '../form/survey-edit-index-reducer';
import { createEntity, updateEntity } from './network-config-check-list.reducer';

interface NetworkConfigCheckListSurveyUpdateProps {
  formId: string;
}

export const NetworkConfigCheckListSurveyUpdate: React.FC<NetworkConfigCheckListSurveyUpdateProps> = ({ formId }) => {
  const dispatch = useAppDispatch();

  const [networkConfigCheckListEntity, setNetworkConfigCheckListEntity] = useState<INetworkConfigCheckList | null>(null);
  useEffect(() => {
    const getnetworkConfigCheckListEntity = async () => {
      const apiUrl = 'api/network-config-check-lists/form';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<INetworkConfigCheckList>(requestUrl);
      setNetworkConfigCheckListEntity(response.data);
    };
    getnetworkConfigCheckListEntity();
  }, [formId]);

  const forms = useAppSelector(state => state.form.entities);
  const loading = useAppSelector(state => state.networkConfigCheckList.loading);
  const updating = useAppSelector(state => state.networkConfigCheckList.updating);

  const saveEntity = values => {
    const entity = {
      ...networkConfigCheckListEntity,
      ...values,
      form: forms.find(it => it.id.toString() === values.form.toString()),
    };

    if (networkConfigCheckListEntity === null) {
      dispatch(createEntity(entity));
      dispatch(incrementEditIndex(1));
    } else {
      dispatch(updateEntity(entity));
      dispatch(incrementEditIndex(1));
    }
  };

  const defaultValues = () =>
    networkConfigCheckListEntity === null
      ? {}
      : {
          ...networkConfigCheckListEntity,
          form: formId,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h3 id="surveySampleApp.networkConfigCheckList.home.createOrEditLabel" data-cy="NetworkConfigCheckListCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.networkConfigCheckList.home.createOrEditLabel">
              Create or edit a NetworkConfigCheckList
            </Translate>
          </h3>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!networkConfigCheckListEntity === null ? (
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
                hidden
                name="form"
                data-cy="form"
                label={translate('surveySampleApp.networkConfigCheckList.form')}
                type="select"
                required
              >
                <option value={formId} key={formId}>
                  {formId}
                </option>
              </ValidatedField>
              <br />
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

export default NetworkConfigCheckListSurveyUpdate;
