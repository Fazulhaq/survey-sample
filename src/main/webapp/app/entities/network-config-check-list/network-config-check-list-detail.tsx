import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './network-config-check-list.reducer';

export const NetworkConfigCheckListDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const networkConfigCheckListEntity = useAppSelector(state => state.networkConfigCheckList.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="networkConfigCheckListDetailsHeading">
          <Translate contentKey="surveySampleApp.networkConfigCheckList.detail.title">NetworkConfigCheckList</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.id}</dd>
          <dt>
            <span id="dhcp">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.dhcp">Dhcp</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.dhcp ? 'true' : 'false'}</dd>
          <dt>
            <span id="dns">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.dns">Dns</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.dns ? 'true' : 'false'}</dd>
          <dt>
            <span id="activeDirectory">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.activeDirectory">Active Directory</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.activeDirectory ? 'true' : 'false'}</dd>
          <dt>
            <span id="sharedDrives">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.sharedDrives">Shared Drives</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.sharedDrives ? 'true' : 'false'}</dd>
          <dt>
            <span id="mailServer">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.mailServer">Mail Server</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.mailServer ? 'true' : 'false'}</dd>
          <dt>
            <span id="firewalls">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.firewalls">Firewalls</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.firewalls ? 'true' : 'false'}</dd>
          <dt>
            <span id="loadBalancing">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.loadBalancing">Load Balancing</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.loadBalancing ? 'true' : 'false'}</dd>
          <dt>
            <span id="networkMonitoring">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.networkMonitoring">Network Monitoring</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.networkMonitoring ? 'true' : 'false'}</dd>
          <dt>
            <span id="antivirus">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.antivirus">Antivirus</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.antivirus ? 'true' : 'false'}</dd>
          <dt>
            <span id="integratedSystems">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.integratedSystems">Integrated Systems</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.integratedSystems ? 'true' : 'false'}</dd>
          <dt>
            <span id="antiSpam">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.antiSpam">Anti Spam</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.antiSpam ? 'true' : 'false'}</dd>
          <dt>
            <span id="wpa">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.wpa">Wpa</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.wpa ? 'true' : 'false'}</dd>
          <dt>
            <span id="autoBackup">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.autoBackup">Auto Backup</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.autoBackup ? 'true' : 'false'}</dd>
          <dt>
            <span id="physicalSecurity">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.physicalSecurity">Physical Security</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.physicalSecurity ? 'true' : 'false'}</dd>
          <dt>
            <span id="storageServer">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.storageServer">Storage Server</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.storageServer ? 'true' : 'false'}</dd>
          <dt>
            <span id="securityAudit">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.securityAudit">Security Audit</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.securityAudit ? 'true' : 'false'}</dd>
          <dt>
            <span id="disasterRecovery">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.disasterRecovery">Disaster Recovery</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.disasterRecovery ? 'true' : 'false'}</dd>
          <dt>
            <span id="proxyServer">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.proxyServer">Proxy Server</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.proxyServer ? 'true' : 'false'}</dd>
          <dt>
            <span id="wdsServer">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.wdsServer">Wds Server</Translate>
            </span>
          </dt>
          <dd>{networkConfigCheckListEntity.wdsServer ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="surveySampleApp.networkConfigCheckList.form">Form</Translate>
          </dt>
          <dd>{networkConfigCheckListEntity.form ? networkConfigCheckListEntity.form.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/network-config-check-list" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/network-config-check-list/${networkConfigCheckListEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NetworkConfigCheckListDetail;
