import { Button, Col, Row, Table } from 'reactstrap';
import React, { useState } from 'react';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Translate, ValidatedField, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { createEntity } from './datacenter-device.reducer';
import { incrementIndex } from '../stepper-index/stepper-index.reducer';
import { DataCenterDeviceType } from 'app/shared/model/enumerations/data-center-device-type.model';
import { Tag } from 'primereact/tag';

export const AddDataCenterDevice = () => {
  const [racksPurpose, setRacksPurpose] = useState('');
  const handleRacksPurpose = event => {
    setRacksPurpose(event.target.value);
  };
  const [racksAge, setRacksAge] = useState('');
  const handleRacksAge = event => {
    setRacksAge(event.target.value);
  };
  const [racksQuantity, setRacksQuantity] = useState('');
  const handleRacksQuantity = event => {
    setRacksQuantity(event.target.value);
  };
  const [racksCurrentStatus, setRacksCurrentStatus] = useState('');
  const handleRacksCurrentStatus = event => {
    setRacksCurrentStatus(event.target.value);
  };
  const [racksBrandAndModel, setRacksBrandAndModel] = useState('');
  const handleRacksBrandAndModel = event => {
    setRacksBrandAndModel(event.target.value);
  };

  const [serverBrandAndModel, setServerBrandAndModel] = useState('');
  const handleServerBrandAndModel = event => {
    setServerBrandAndModel(event.target.value);
  };
  const [serverCurrentStatus, setServerCurrentStatus] = useState('');
  const handleServerCurrentStatus = event => {
    setServerCurrentStatus(event.target.value);
  };
  const [serverQuantity, setServerQuantity] = useState('');
  const handleServerQuantity = event => {
    setServerQuantity(event.target.value);
  };
  const [serverAge, setServerAge] = useState('');
  const handleServerAge = event => {
    setServerAge(event.target.value);
  };
  const [serverPurpose, setServerPurpose] = useState('');
  const handleServerPurpose = event => {
    setServerPurpose(event.target.value);
  };

  const [routersBrandAndModel, setRoutersBrandAndModel] = useState('');
  const handleRoutersBrandAndModel = event => {
    setRoutersBrandAndModel(event.target.value);
  };
  const [routersCurrentStatus, setRoutersCurrentStatus] = useState('');
  const handleRoutersCurrentStatus = event => {
    setRoutersCurrentStatus(event.target.value);
  };
  const [routersQuantity, setRoutersQuantity] = useState('');
  const handleRoutersQuantity = event => {
    setRoutersQuantity(event.target.value);
  };
  const [routersAge, setRoutersAge] = useState('');
  const handleRoutersAge = event => {
    setRoutersAge(event.target.value);
  };
  const [routersPurpose, setRoutersPurpose] = useState('');
  const handleRoutersPurpose = event => {
    setRoutersPurpose(event.target.value);
  };

  const [switchesBrandAndModel, setSwitchesBrandAndModel] = useState('');
  const handleSwitchesBrandAndModel = event => {
    setSwitchesBrandAndModel(event.target.value);
  };
  const [switchesCurrentStatus, setSwitchesCurrentStatus] = useState('');
  const handleSwitchesCurrentStatus = event => {
    setSwitchesCurrentStatus(event.target.value);
  };
  const [switchesQuantity, setSwitchesQuantity] = useState('');
  const handleSwitchesQuantity = event => {
    setSwitchesQuantity(event.target.value);
  };
  const [switchesAge, setSwitchesAge] = useState('');
  const handleSwitchesAge = event => {
    setSwitchesAge(event.target.value);
  };
  const [switchesPurpose, setSwitchesPurpose] = useState('');
  const handleSwitchesPurpose = event => {
    setSwitchesPurpose(event.target.value);
  };

  const [firewallsBrandAndModel, setFirewallsBrandAndModel] = useState('');
  const handleFirewallsBrandAndModel = event => {
    setFirewallsBrandAndModel(event.target.value);
  };
  const [firewallsCurrentStatus, setFirewallsCurrentStatus] = useState('');
  const handleFirewallsCurrentStatus = event => {
    setFirewallsCurrentStatus(event.target.value);
  };
  const [firewallsQuantity, setFirewallsQuantity] = useState('');
  const handleFirewallsQuantity = event => {
    setFirewallsQuantity(event.target.value);
  };
  const [firewallsAge, setFirewallsAge] = useState('');
  const handleFirewallsAge = event => {
    setFirewallsAge(event.target.value);
  };
  const [firewallsPurpose, setFirewallsPurpose] = useState('');
  const handleFirewallsPurpose = event => {
    setFirewallsPurpose(event.target.value);
  };

  const [dataStorageBrandAndModel, setDataStorageBrandAndModel] = useState('');
  const handleDataStorageBrandAndModel = event => {
    setDataStorageBrandAndModel(event.target.value);
  };
  const [dataStorageCurrentStatus, setDataStorageCurrentStatus] = useState('');
  const handleDataStorageCurrentStatus = event => {
    setDataStorageCurrentStatus(event.target.value);
  };
  const [dataStorageQuantity, setDataStorageQuantity] = useState('');
  const handleDataStorageQuantity = event => {
    setDataStorageQuantity(event.target.value);
  };
  const [dataStorageAge, setDataStorageAge] = useState('');
  const handleDataStorageAge = event => {
    setDataStorageAge(event.target.value);
  };
  const [dataStoragePurpose, setDataStoragePurpose] = useState('');
  const handleDataStoragePurpose = event => {
    setDataStoragePurpose(event.target.value);
  };

  const dispatch = useAppDispatch();

  const forms = useAppSelector(state => state.form.entities);
  const datacenterDeviceEntity = useAppSelector(state => state.datacenterDevice.entity);
  const updating = useAppSelector(state => state.datacenterDevice.updating);

  const lastFormId = forms.reduce((maxId: number, form: { id: number }) => {
    return form.id > maxId ? form.id : maxId;
  }, 0);

  const saveAllEntities = event => {
    event.preventDefault();
    saveRacksEntity(racksBrandAndModel, racksCurrentStatus, racksQuantity, racksAge, racksPurpose);
    saveServersEntity(serverBrandAndModel, serverCurrentStatus, serverQuantity, serverAge, serverPurpose);
    saveRoutersEntity(routersBrandAndModel, routersCurrentStatus, routersQuantity, routersAge, routersPurpose);
    saveSwitchesEntity(switchesBrandAndModel, switchesCurrentStatus, switchesQuantity, switchesAge, switchesPurpose);
    saveFirewallsEntity(firewallsBrandAndModel, firewallsCurrentStatus, firewallsQuantity, firewallsAge, firewallsPurpose);
    saveDataStorageEntity(dataStorageBrandAndModel, dataStorageCurrentStatus, dataStorageQuantity, dataStorageAge, dataStoragePurpose);
    dispatch(incrementIndex(1));
  };
  const saveRacksEntity = async (RacksBrandAndModel, RacksCurrentStatus, RacksQuantity, RacksAge, RacksPurpose) => {
    const entity = {
      ...datacenterDeviceEntity,
      deviceType: DataCenterDeviceType.Racks,
      brandAndModel: RacksBrandAndModel,
      currentStatus: RacksCurrentStatus,
      quantity: RacksQuantity,
      age: RacksAge,
      purpose: RacksPurpose,
      form: forms.find((it: { id: any }) => it.id === lastFormId),
    };
    await dispatch(createEntity(entity));
  };
  const saveServersEntity = async (ServerBrandAndModel, ServerCurrentStatus, ServerQuantity, ServerAge, ServerPurpose) => {
    const entity = {
      ...datacenterDeviceEntity,
      deviceType: DataCenterDeviceType.Servers,
      brandAndModel: ServerBrandAndModel,
      currentStatus: ServerCurrentStatus,
      quantity: ServerQuantity,
      age: ServerAge,
      purpose: ServerPurpose,
      form: forms.find((it: { id: any }) => it.id === lastFormId),
    };
    await dispatch(createEntity(entity));
  };
  const saveRoutersEntity = async (RoutersBrandAndModel, RoutersCurrentStatus, RoutersQuantity, RoutersAge, RoutersPurpose) => {
    const entity = {
      ...datacenterDeviceEntity,
      deviceType: DataCenterDeviceType.Routers,
      brandAndModel: RoutersBrandAndModel,
      currentStatus: RoutersCurrentStatus,
      quantity: RoutersQuantity,
      age: RoutersAge,
      purpose: RoutersPurpose,
      form: forms.find((it: { id: any }) => it.id === lastFormId),
    };
    await dispatch(createEntity(entity));
  };
  const saveSwitchesEntity = async (SwitchesBrandAndModel, SwitchesCurrentStatus, SwitchesQuantity, SwitchesAge, SwitchesPurpose) => {
    const entity = {
      ...datacenterDeviceEntity,
      deviceType: DataCenterDeviceType.Switches,
      brandAndModel: SwitchesBrandAndModel,
      currentStatus: SwitchesCurrentStatus,
      quantity: SwitchesQuantity,
      age: SwitchesAge,
      purpose: SwitchesPurpose,
      form: forms.find((it: { id: any }) => it.id === lastFormId),
    };
    await dispatch(createEntity(entity));
  };
  const saveFirewallsEntity = async (FirewallsBrandAndModel, FirewallsCurrentStatus, FirewallsQuantity, FirewallsAge, FirewallsPurpose) => {
    const entity = {
      ...datacenterDeviceEntity,
      deviceType: DataCenterDeviceType.Firewalls,
      brandAndModel: FirewallsBrandAndModel,
      currentStatus: FirewallsCurrentStatus,
      quantity: FirewallsQuantity,
      age: FirewallsAge,
      purpose: FirewallsPurpose,
      form: forms.find((it: { id: any }) => it.id === lastFormId),
    };
    await dispatch(createEntity(entity));
  };
  const saveDataStorageEntity = async (
    DataStorageBrandAndModel,
    DataStorageCurrentStatus,
    DataStorageQuantity,
    DataStorageAge,
    DataStoragePurpose,
  ) => {
    const entity = {
      ...datacenterDeviceEntity,
      deviceType: DataCenterDeviceType.DataStorage,
      brandAndModel: DataStorageBrandAndModel,
      currentStatus: DataStorageCurrentStatus,
      quantity: DataStorageQuantity,
      age: DataStorageAge,
      purpose: DataStoragePurpose,
      form: forms.find((it: { id: any }) => it.id === lastFormId),
    };
    await dispatch(createEntity(entity));
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="surveySampleApp.datacenterDevice.home.createOrEditLabel" data-cy="DatacenterDeviceCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.datacenterDevice.home.createOrEditLabel">Create or edit a DatacenterDevice</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center gap-2">
        <Col md="8">
          <form onSubmit={saveAllEntities}>
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand">
                    <Translate contentKey="surveySampleApp.datacenterDevice.deviceType">Device Type</Translate>
                  </th>
                  <th className="hand">
                    <Translate contentKey="surveySampleApp.datacenterDevice.brandAndModel">Brand And Model</Translate>
                  </th>
                  <th className="hand">
                    <Translate contentKey="surveySampleApp.datacenterDevice.currentStatus">Current Status</Translate>
                  </th>
                  <th className="hand">
                    <Translate contentKey="surveySampleApp.datacenterDevice.quantity">Quantity</Translate>
                  </th>
                  <th className="hand">
                    <Translate contentKey="surveySampleApp.datacenterDevice.age">Age</Translate>
                  </th>
                  <th className="hand">
                    <Translate contentKey="surveySampleApp.datacenterDevice.purpose">Purpose</Translate>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                    <Tag value="Racks"></Tag>
                  </td>
                  <td>
                    <ValidatedField name="RacksBrandAndModel" type="text" required onChange={handleRacksBrandAndModel} />
                  </td>
                  <td>
                    <ValidatedField name="RacksCurrentStatus" type="text" required onChange={handleRacksCurrentStatus} />
                  </td>
                  <td>
                    <ValidatedField name="RacksQuantity" type="number" required onChange={handleRacksQuantity} />
                  </td>
                  <td>
                    <ValidatedField name="RacksAge" type="text" required onChange={handleRacksAge} />
                  </td>
                  <td>
                    <ValidatedField name="RacksPurpose" type="text" required onChange={handleRacksPurpose} />
                  </td>
                </tr>
                <tr>
                  <td>
                    <Tag value="Servers"></Tag>
                  </td>
                  <td>
                    <ValidatedField name="ServerBrandAndModel" type="text" required onChange={handleServerBrandAndModel} />
                  </td>
                  <td>
                    <ValidatedField name="ServerCurrentStatus" type="text" required onChange={handleServerCurrentStatus} />
                  </td>
                  <td>
                    <ValidatedField name="ServerQuantity" type="number" required onChange={handleServerQuantity} />
                  </td>
                  <td>
                    <ValidatedField name="ServerAge" type="text" required onChange={handleServerAge} />
                  </td>
                  <td>
                    <ValidatedField name="ServerPurpose" type="text" required onChange={handleServerPurpose} />
                  </td>
                </tr>
                <tr>
                  <td>
                    <Tag value="Routers"></Tag>
                  </td>
                  <td>
                    <ValidatedField name="RoutersBrandAndModel" type="text" required onChange={handleRoutersBrandAndModel} />
                  </td>
                  <td>
                    <ValidatedField name="RoutersCurrentStatus" type="text" required onChange={handleRoutersCurrentStatus} />
                  </td>
                  <td>
                    <ValidatedField name="RoutersQuantity" type="number" required onChange={handleRoutersQuantity} />
                  </td>
                  <td>
                    <ValidatedField name="RoutersAge" type="text" required onChange={handleRoutersAge} />
                  </td>
                  <td>
                    <ValidatedField name="RoutersPurpose" type="text" required onChange={handleRoutersPurpose} />
                  </td>
                </tr>
                <tr>
                  <td>
                    <Tag value="Switches"></Tag>
                  </td>
                  <td>
                    <ValidatedField name="SwitchesBrandAndModel" type="text" required onChange={handleSwitchesBrandAndModel} />
                  </td>
                  <td>
                    <ValidatedField name="SwitchesCurrentStatus" type="text" required onChange={handleSwitchesCurrentStatus} />
                  </td>
                  <td>
                    <ValidatedField name="SwitchesQuantity" type="number" required onChange={handleSwitchesQuantity} />
                  </td>
                  <td>
                    <ValidatedField name="SwitchesAge" type="text" required onChange={handleSwitchesAge} />
                  </td>
                  <td>
                    <ValidatedField name="SwitchesPurpose" type="text" required onChange={handleSwitchesPurpose} />
                  </td>
                </tr>
                <tr>
                  <td>
                    <Tag value="Firewalls"></Tag>
                  </td>
                  <td>
                    <ValidatedField name="FirewallsBrandAndModel" type="text" required onChange={handleFirewallsBrandAndModel} />
                  </td>
                  <td>
                    <ValidatedField name="FirewallsCurrentStatus" type="text" required onChange={handleFirewallsCurrentStatus} />
                  </td>
                  <td>
                    <ValidatedField name="FirewallsQuantity" type="number" required onChange={handleFirewallsQuantity} />
                  </td>
                  <td>
                    <ValidatedField name="FirewallsAge" type="text" required onChange={handleFirewallsAge} />
                  </td>
                  <td>
                    <ValidatedField name="FirewallsPurpose" type="text" required onChange={handleFirewallsPurpose} />
                  </td>
                </tr>
                <tr>
                  <td>
                    <Tag value="DataStorage"></Tag>
                  </td>
                  <td>
                    <ValidatedField name="DataStorageBrandAndModel" type="text" required onChange={handleDataStorageBrandAndModel} />
                  </td>
                  <td>
                    <ValidatedField name="DataStorageCurrentStatus" type="text" required onChange={handleDataStorageCurrentStatus} />
                  </td>
                  <td>
                    <ValidatedField name="DataStorageQuantity" type="number" required onChange={handleDataStorageQuantity} />
                  </td>
                  <td>
                    <ValidatedField name="DataStorageAge" type="text" required onChange={handleDataStorageAge} />
                  </td>
                  <td>
                    <ValidatedField name="DataStoragePurpose" type="text" required onChange={handleDataStoragePurpose} />
                  </td>
                </tr>
              </tbody>
            </Table>
            <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
              <FontAwesomeIcon icon="save" />
              &nbsp;
              <Translate contentKey="entity.action.save">Save</Translate>
            </Button>
          </form>
        </Col>
      </Row>
    </div>
  );
};

export default AddDataCenterDevice;
