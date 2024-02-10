import React, { useEffect, useState } from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

import { INetworkConfigCheckList } from 'app/shared/model/network-config-check-list.model';
import axios from 'axios';

interface NetworkConfigCheckListDetailProps {
  formId: string;
}

export const NetworkConfigCheckListDetail: React.FC<NetworkConfigCheckListDetailProps> = ({ formId }) => {
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

  return (
    <Row className="justify-content-center">
      <Col md="2"></Col>
      <br />
      <br />
      <h2 data-cy="networkConfigCheckListDetailsHeading">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <Translate contentKey="surveySampleApp.networkConfigCheckList.detail.title">NetworkConfigCheckList</Translate>
      </h2>
      <Col md="3">
        <dl className="jh-entity-details">
          <dt>
            <br />
            <span id="dhcp">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.dhcp">Dhcp</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.dhcp} />
          <dt>
            <br />
            <span id="dns">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.dns">Dns</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.dns} />
          <dt>
            <br />
            <span id="securityAudit">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.securityAudit">Security Audit</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.securityAudit} />
          <dt>
            <br />
            <span id="activeDirectory">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.activeDirectory">Active Directory</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.activeDirectory} />
          <dt>
            <br />
            <span id="sharedDrives">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.sharedDrives">Shared Drives</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.sharedDrives} />
          <dt>
            <br />
            <span id="mailServer">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.mailServer">Mail Server</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.mailServer} />
          <dt>
            <br />
            <span id="wdsServer">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.wdsServer">Wds Server</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.wdsServer} />
        </dl>
      </Col>
      <Col md="3">
        <dl className="jh-entity-details">
          <dt>
            <br />
            <span id="loadBalancing">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.loadBalancing">Load Balancing</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.loadBalancing} />
          <dt>
            <br />
            <span id="firewalls">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.firewalls">Firewalls</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.firewalls} />
          <dt>
            <br />
            <span id="physicalSecurity">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.physicalSecurity">Physical Security</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.physicalSecurity} />
          <dt>
            <br />
            <span id="disasterRecovery">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.disasterRecovery">Disaster Recovery</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.disasterRecovery} />
          <dt>
            <br />
            <span id="networkMonitoring">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.networkMonitoring">Network Monitoring</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.networkMonitoring} />
          <dt>
            <br />
            <span id="antivirus">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.antivirus">Antivirus</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.antivirus} />
        </dl>
      </Col>
      <Col md="3">
        <dl className="jh-entity-details">
          <dt>
            <br />
            <span id="autoBackup">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.autoBackup">Auto Backup</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.autoBackup} />
          <dt>
            <br />
            <span id="integratedSystems">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.integratedSystems">Integrated Systems</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.integratedSystems} />
          <dt>
            <br />
            <span id="antiSpam">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.antiSpam">Anti Spam</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.antiSpam} />
          <dt>
            <br />
            <span id="proxyServer">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.proxyServer">Proxy Server</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.proxyServer} />
          <dt>
            <br />
            <span id="wpa">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.wpa">Wpa</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.wpa} />
          <dt>
            <br />
            <span id="storageServer">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.storageServer">Storage Server</Translate>
            </span>
          </dt>
          <input type="checkbox" disabled checked={networkConfigCheckListEntity?.storageServer} />
        </dl>
      </Col>
      <Col md="1"></Col>
    </Row>
  );
};

export default NetworkConfigCheckListDetail;
