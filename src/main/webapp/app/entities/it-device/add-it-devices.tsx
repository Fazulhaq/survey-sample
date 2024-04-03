import { Button, Col, Row, Table } from 'reactstrap';
import React, { useState } from 'react';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Translate, ValidatedField, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { createEntity } from './it-device.reducer';
import { incrementIndex } from '../stepper-index/stepper-index.reducer';
import { Tag } from 'primereact/tag';
import { ItDeviceType } from 'app/shared/model/enumerations/it-device-type.model';

export const AddItDevices = () => {
  const [desktopComputersPurpose, setDesktopComputersPurpose] = useState('');
  const handleDesktopComputersPurpose = event => {
    setDesktopComputersPurpose(event.target.value);
  };
  const [desktopComputersAge, setDesktopComputersAge] = useState('');
  const handleDesktopComputersAge = event => {
    setDesktopComputersAge(event.target.value);
  };
  const [desktopComputersQuantity, setDesktopComputersQuantity] = useState('');
  const handleDesktopComputersQuantity = event => {
    setDesktopComputersQuantity(event.target.value);
  };
  const [desktopComputersCurrentStatus, setDesktopComputersCurrentStatus] = useState('');
  const handleDesktopComputersCurrentStatus = event => {
    setDesktopComputersCurrentStatus(event.target.value);
  };
  const [desktopComputersBrandAndModel, setDesktopComputersBrandAndModel] = useState('');
  const handleDesktopComputersBrandAndModel = event => {
    setDesktopComputersBrandAndModel(event.target.value);
  };

  const [laptopsBrandAndModel, setLaptopsBrandAndModel] = useState('');
  const handleLaptopsBrandAndModel = event => {
    setLaptopsBrandAndModel(event.target.value);
  };
  const [laptopsCurrentStatus, setLaptopsCurrentStatus] = useState('');
  const handleLaptopsCurrentStatus = event => {
    setLaptopsCurrentStatus(event.target.value);
  };
  const [laptopsQuantity, setLaptopsQuantity] = useState('');
  const handleLaptopsQuantity = event => {
    setLaptopsQuantity(event.target.value);
  };
  const [laptopsAge, setLaptopsAge] = useState('');
  const handleLaptopsAge = event => {
    setLaptopsAge(event.target.value);
  };
  const [laptopsPurpose, setLaptopsPurpose] = useState('');
  const handleLaptopsPurpose = event => {
    setLaptopsPurpose(event.target.value);
  };

  const [serversBrandAndModel, setServersBrandAndModel] = useState('');
  const handleServersBrandAndModel = event => {
    setServersBrandAndModel(event.target.value);
  };
  const [serversCurrentStatus, setServersCurrentStatus] = useState('');
  const handleServersCurrentStatus = event => {
    setServersCurrentStatus(event.target.value);
  };
  const [serversQuantity, setServersQuantity] = useState('');
  const handleServersQuantity = event => {
    setServersQuantity(event.target.value);
  };
  const [serversAge, setServersAge] = useState('');
  const handleServersAge = event => {
    setServersAge(event.target.value);
  };
  const [serversPurpose, setServersPurpose] = useState('');
  const handleServersPurpose = event => {
    setServersPurpose(event.target.value);
  };

  const [printersBrandAndModel, setPrintersBrandAndModel] = useState('');
  const handlePrintersBrandAndModel = event => {
    setPrintersBrandAndModel(event.target.value);
  };
  const [printersCurrentStatus, setPrintersCurrentStatus] = useState('');
  const handlePrintersCurrentStatus = event => {
    setPrintersCurrentStatus(event.target.value);
  };
  const [printersQuantity, setPrintersQuantity] = useState('');
  const handlePrintersQuantity = event => {
    setPrintersQuantity(event.target.value);
  };
  const [printersAge, setPrintersAge] = useState('');
  const handlePrintersAge = event => {
    setPrintersAge(event.target.value);
  };
  const [printersPurpose, setPrintersPurpose] = useState('');
  const handlePrintersPurpose = event => {
    setPrintersPurpose(event.target.value);
  };

  const [scannersBrandAndModel, setScannersBrandAndModel] = useState('');
  const handleScannersBrandAndModel = event => {
    setScannersBrandAndModel(event.target.value);
  };
  const [scannersCurrentStatus, setScannersCurrentStatus] = useState('');
  const handleScannersCurrentStatus = event => {
    setScannersCurrentStatus(event.target.value);
  };
  const [scannersQuantity, setScannersQuantity] = useState('');
  const handleScannersQuantity = event => {
    setScannersQuantity(event.target.value);
  };
  const [scannersAge, setScannersAge] = useState('');
  const handleScannersAge = event => {
    setScannersAge(event.target.value);
  };
  const [scannersPurpose, setScannersPurpose] = useState('');
  const handleScannersPurpose = event => {
    setScannersPurpose(event.target.value);
  };

  const dispatch = useAppDispatch();

  const forms = useAppSelector(state => state.form.entities);
  const itDeviceEntity = useAppSelector(state => state.itDevice.entity);
  const updating = useAppSelector(state => state.datacenterDevice.updating);

  const lastFormId = forms.reduce((maxId: number, form: { id: number }) => {
    return form.id > maxId ? form.id : maxId;
  }, 0);

  const saveAllEntities = event => {
    event.preventDefault();
    saveDesktopComputersEntity(
      desktopComputersBrandAndModel,
      desktopComputersCurrentStatus,
      desktopComputersQuantity,
      desktopComputersAge,
      desktopComputersPurpose,
    );
    saveLaptopsEntity(laptopsBrandAndModel, laptopsCurrentStatus, laptopsQuantity, laptopsAge, laptopsPurpose);
    saveServersEntity(serversBrandAndModel, serversCurrentStatus, serversQuantity, serversAge, serversPurpose);
    savePrintersEntity(printersBrandAndModel, printersCurrentStatus, printersQuantity, printersAge, printersPurpose);
    saveScannersEntity(scannersBrandAndModel, scannersCurrentStatus, scannersQuantity, scannersAge, scannersPurpose);
    dispatch(incrementIndex(1));
  };
  const saveDesktopComputersEntity = async (
    DesktopComputersBrandAndModel,
    DesktopComputersCurrentStatus,
    DesktopComputersQuantity,
    DesktopComputersAge,
    DesktopComputersPurpose,
  ) => {
    const entity = {
      ...itDeviceEntity,
      deviceType: ItDeviceType.DesktopComputers,
      brandAndModel: DesktopComputersBrandAndModel,
      currentStatus: DesktopComputersCurrentStatus,
      quantity: DesktopComputersQuantity,
      age: DesktopComputersAge,
      purpose: DesktopComputersPurpose,
      form: forms.find((it: { id: any }) => it.id === lastFormId),
    };
    await dispatch(createEntity(entity));
  };
  const saveLaptopsEntity = async (LaptopsBrandAndModel, LaptopsCurrentStatus, LaptopsQuantity, LaptopsAge, LaptopsPurpose) => {
    const entity = {
      ...itDeviceEntity,
      deviceType: ItDeviceType.Laptops,
      brandAndModel: LaptopsBrandAndModel,
      currentStatus: LaptopsCurrentStatus,
      quantity: LaptopsQuantity,
      age: LaptopsAge,
      purpose: LaptopsPurpose,
      form: forms.find((it: { id: any }) => it.id === lastFormId),
    };
    await dispatch(createEntity(entity));
  };
  const saveServersEntity = async (ServersBrandAndModel, ServersCurrentStatus, ServersQuantity, ServersAge, ServersPurpose) => {
    const entity = {
      ...itDeviceEntity,
      deviceType: ItDeviceType.Servers,
      brandAndModel: ServersBrandAndModel,
      currentStatus: ServersCurrentStatus,
      quantity: ServersQuantity,
      age: ServersAge,
      purpose: ServersPurpose,
      form: forms.find((it: { id: any }) => it.id === lastFormId),
    };
    await dispatch(createEntity(entity));
  };
  const savePrintersEntity = async (PrintersBrandAndModel, PrintersCurrentStatus, PrintersQuantity, PrintersAge, PrintersPurpose) => {
    const entity = {
      ...itDeviceEntity,
      deviceType: ItDeviceType.Printers,
      brandAndModel: PrintersBrandAndModel,
      currentStatus: PrintersCurrentStatus,
      quantity: PrintersQuantity,
      age: PrintersAge,
      purpose: PrintersPurpose,
      form: forms.find((it: { id: any }) => it.id === lastFormId),
    };
    await dispatch(createEntity(entity));
  };
  const saveScannersEntity = async (ScannersBrandAndModel, ScannersCurrentStatus, ScannersQuantity, ScannersAge, ScannersPurpose) => {
    const entity = {
      ...itDeviceEntity,
      deviceType: ItDeviceType.Scanners,
      brandAndModel: ScannersBrandAndModel,
      currentStatus: ScannersCurrentStatus,
      quantity: ScannersQuantity,
      age: ScannersAge,
      purpose: ScannersPurpose,
      form: forms.find((it: { id: any }) => it.id === lastFormId),
    };
    await dispatch(createEntity(entity));
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="surveySampleApp.itDevice.home.createOrEditLabel" data-cy="ItDeviceCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.itDevice.home.createOrEditLabel">Create or edit a ItDevice</Translate>
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
                    <Translate contentKey="surveySampleApp.itDevice.deviceType">Device Type</Translate>
                  </th>
                  <th className="hand">
                    <Translate contentKey="surveySampleApp.itDevice.brandAndModel">Brand And Model</Translate>
                  </th>
                  <th className="hand">
                    <Translate contentKey="surveySampleApp.itDevice.currentStatus">Current Status</Translate>
                  </th>
                  <th className="hand">
                    <Translate contentKey="surveySampleApp.itDevice.quantity">Quantity</Translate>
                  </th>
                  <th className="hand">
                    <Translate contentKey="surveySampleApp.itDevice.age">Age</Translate>
                  </th>
                  <th className="hand">
                    <Translate contentKey="surveySampleApp.itDevice.purpose">Purpose</Translate>
                  </th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>
                    <Tag value="DesktopComputers"></Tag>
                  </td>
                  <td>
                    <ValidatedField
                      name="DesktopComputersBrandAndModel"
                      type="text"
                      required
                      onChange={handleDesktopComputersBrandAndModel}
                    />
                  </td>
                  <td>
                    <ValidatedField
                      name="DesktopComputersCurrentStatus"
                      type="text"
                      required
                      onChange={handleDesktopComputersCurrentStatus}
                    />
                  </td>
                  <td>
                    <ValidatedField name="DesktopComputersQuantity" type="number" required onChange={handleDesktopComputersQuantity} />
                  </td>
                  <td>
                    <ValidatedField name="DesktopComputersAge" type="text" required onChange={handleDesktopComputersAge} />
                  </td>
                  <td>
                    <ValidatedField name="DesktopComputersPurpose" type="text" required onChange={handleDesktopComputersPurpose} />
                  </td>
                </tr>
                <tr>
                  <td>
                    <Tag value="Laptops"></Tag>
                  </td>
                  <td>
                    <ValidatedField name="LaptopsBrandAndModel" type="text" required onChange={handleLaptopsBrandAndModel} />
                  </td>
                  <td>
                    <ValidatedField name="LaptopsCurrentStatus" type="text" required onChange={handleLaptopsCurrentStatus} />
                  </td>
                  <td>
                    <ValidatedField name="LaptopsQuantity" type="number" required onChange={handleLaptopsQuantity} />
                  </td>
                  <td>
                    <ValidatedField name="LaptopsAge" type="text" required onChange={handleLaptopsAge} />
                  </td>
                  <td>
                    <ValidatedField name="LaptopsPurpose" type="text" required onChange={handleLaptopsPurpose} />
                  </td>
                </tr>
                <tr>
                  <td>
                    <Tag value="Servers"></Tag>
                  </td>
                  <td>
                    <ValidatedField name="ServersBrandAndModel" type="text" required onChange={handleServersBrandAndModel} />
                  </td>
                  <td>
                    <ValidatedField name="ServersCurrentStatus" type="text" required onChange={handleServersCurrentStatus} />
                  </td>
                  <td>
                    <ValidatedField name="ServersQuantity" type="number" required onChange={handleServersQuantity} />
                  </td>
                  <td>
                    <ValidatedField name="ServersAge" type="text" required onChange={handleServersAge} />
                  </td>
                  <td>
                    <ValidatedField name="ServersPurpose" type="text" required onChange={handleServersPurpose} />
                  </td>
                </tr>
                <tr>
                  <td>
                    <Tag value="Printers"></Tag>
                  </td>
                  <td>
                    <ValidatedField name="PrintersBrandAndModel" type="text" required onChange={handlePrintersBrandAndModel} />
                  </td>
                  <td>
                    <ValidatedField name="PrintersCurrentStatus" type="text" required onChange={handlePrintersCurrentStatus} />
                  </td>
                  <td>
                    <ValidatedField name="PrintersQuantity" type="number" required onChange={handlePrintersQuantity} />
                  </td>
                  <td>
                    <ValidatedField name="PrintersAge" type="text" required onChange={handlePrintersAge} />
                  </td>
                  <td>
                    <ValidatedField name="PrintersPurpose" type="text" required onChange={handlePrintersPurpose} />
                  </td>
                </tr>
                <tr>
                  <td>
                    <Tag value="Scanners"></Tag>
                  </td>
                  <td>
                    <ValidatedField name="ScannersBrandAndModel" type="text" required onChange={handleScannersBrandAndModel} />
                  </td>
                  <td>
                    <ValidatedField name="ScannersCurrentStatus" type="text" required onChange={handleScannersCurrentStatus} />
                  </td>
                  <td>
                    <ValidatedField name="ScannersQuantity" type="number" required onChange={handleScannersQuantity} />
                  </td>
                  <td>
                    <ValidatedField name="ScannersAge" type="text" required onChange={handleScannersAge} />
                  </td>
                  <td>
                    <ValidatedField name="ScannersPurpose" type="text" required onChange={handleScannersPurpose} />
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

export default AddItDevices;
