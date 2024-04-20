import React, { useEffect, useState } from 'react';
import { Translate, ValidatedField } from 'react-jhipster';
import { Button, Col, Row, Table } from 'reactstrap';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDatacenterDevice } from 'app/shared/model/datacenter-device.model';
import axios from 'axios';
import { incrementEditIndex } from '../form/survey-edit-index-reducer';
import { updateEntity } from './datacenter-device.reducer';

interface DatacenterDeviceUpdateProps {
  formId: string;
}

export const DatacenterDeviceSurveyUpdate: React.FC<DatacenterDeviceUpdateProps> = ({ formId }) => {
  const dispatch = useAppDispatch();

  const [firstId, setFirstId] = useState(null);
  const [firstDeviceType, setFirstDeviceType] = useState('');
  const [firstBrandAndModel, setFirstBrandAndModel] = useState('');
  const [firstCurrentStatus, setFirstCurrentStatus] = useState('');
  const [firstQuantity, setFirstQuantity] = useState('');
  const [firstAge, setFirstAge] = useState('');
  const [firstPurpose, setFirstPurpose] = useState('');
  const [secondId, setSecondId] = useState(null);
  const [secondDeviceType, setSecondDeviceType] = useState('');
  const [secondBrandAndModel, setSecondBrandAndModel] = useState('');
  const [secondCurrentStatus, setSecondCurrentStatus] = useState('');
  const [secondQuantity, setSecondQuantity] = useState('');
  const [secondAge, setSecondAge] = useState('');
  const [secondPurpose, setSecondPurpose] = useState('');
  const [thirdId, setThirdId] = useState(null);
  const [thirdDeviceType, setThirdDeviceType] = useState('');
  const [thirdBrandAndModel, setThirdBrandAndModel] = useState('');
  const [thirdCurrentStatus, setThirdCurrentStatus] = useState('');
  const [thirdQuantity, setThirdQuantity] = useState('');
  const [thirdAge, setThirdAge] = useState('');
  const [thirdPurpose, setThirdPurpose] = useState('');
  const [fourthId, setFourthId] = useState(null);
  const [fourthDeviceType, setFourthDeviceType] = useState('');
  const [fourthBrandAndModel, setFourthBrandAndModel] = useState('');
  const [fourthCurrentStatus, setFourthCurrentStatus] = useState('');
  const [fourthQuantity, setFourthQuantity] = useState('');
  const [fourthAge, setFourthAge] = useState('');
  const [fourthPurpose, setFourthPurpose] = useState('');
  const [fifthId, setFifthId] = useState(null);
  const [fifthDeviceType, setFifthDeviceType] = useState('');
  const [fifthBrandAndModel, setFifthBrandAndModel] = useState('');
  const [fifthCurrentStatus, setFifthCurrentStatus] = useState('');
  const [fifthQuantity, setFifthQuantity] = useState('');
  const [fifthAge, setFifthAge] = useState('');
  const [fifthPurpose, setFifthPurpose] = useState('');
  const [sixthId, setSixthId] = useState(null);
  const [sixthDeviceType, setSixthDeviceType] = useState('');
  const [sixthBrandAndModel, setSixthBrandAndModel] = useState('');
  const [sixthCurrentStatus, setSixthCurrentStatus] = useState('');
  const [sixthQuantity, setSixthQuantity] = useState('');
  const [sixthAge, setSixthAge] = useState('');
  const [sixthPurpose, setSixthPurpose] = useState('');

  const [datacenterDeviceEntities, setDatacenterDeviceEntities] = useState<IDatacenterDevice[]>([]);
  useEffect(() => {
    const getDatacenterDeviceEntities = async () => {
      const apiUrl = 'api/datacenter-devices/forms';
      const requestUrl = `${apiUrl}/${formId}`;
      const response = await axios.get<IDatacenterDevice[]>(requestUrl);
      if (response.data.length > 0) {
        setFirstId(response.data[0].id);
        setFirstDeviceType(response.data[0].deviceType);
        setFirstBrandAndModel(response.data[0].brandAndModel);
        setFirstCurrentStatus(response.data[0].currentStatus);
        setFirstQuantity(response.data[0].quantity);
        setFirstAge(response.data[0].age);
        setFirstPurpose(response.data[0].purpose);
        setSecondId(response.data[1].id);
        setSecondDeviceType(response.data[1].deviceType);
        setSecondBrandAndModel(response.data[1].brandAndModel);
        setSecondCurrentStatus(response.data[1].currentStatus);
        setSecondQuantity(response.data[1].quantity);
        setSecondAge(response.data[1].age);
        setSecondPurpose(response.data[1].purpose);
        setThirdId(response.data[2].id);
        setThirdDeviceType(response.data[2].deviceType);
        setThirdBrandAndModel(response.data[2].brandAndModel);
        setThirdCurrentStatus(response.data[2].currentStatus);
        setThirdQuantity(response.data[2].quantity);
        setThirdAge(response.data[2].age);
        setThirdPurpose(response.data[2].purpose);
        setFourthId(response.data[3].id);
        setFourthDeviceType(response.data[3].deviceType);
        setFourthBrandAndModel(response.data[3].brandAndModel);
        setFourthCurrentStatus(response.data[3].currentStatus);
        setFourthQuantity(response.data[3].quantity);
        setFourthAge(response.data[3].age);
        setFourthPurpose(response.data[3].purpose);
        setFifthId(response.data[4].id);
        setFifthDeviceType(response.data[4].deviceType);
        setFifthBrandAndModel(response.data[4].brandAndModel);
        setFifthCurrentStatus(response.data[4].currentStatus);
        setFifthQuantity(response.data[4].quantity);
        setFifthAge(response.data[4].age);
        setFifthPurpose(response.data[4].purpose);
        setSixthId(response.data[5].id);
        setSixthDeviceType(response.data[5].deviceType);
        setSixthBrandAndModel(response.data[5].brandAndModel);
        setSixthCurrentStatus(response.data[5].currentStatus);
        setSixthQuantity(response.data[5].quantity);
        setSixthAge(response.data[5].age);
        setSixthPurpose(response.data[5].purpose);
      }
      setDatacenterDeviceEntities(response.data);
    };
    getDatacenterDeviceEntities();
  }, [formId]);

  const handleFirstId = event => {
    setFirstId(event.target.value);
  };
  const handleFirstDeviceType = event => {
    setFirstDeviceType(event.target.value);
  };
  const handleFirstBrandAndModel = event => {
    setFirstBrandAndModel(event.target.value);
  };
  const handleFirstCurrentStatus = event => {
    setFirstCurrentStatus(event.target.value);
  };
  const handleFirstQuantity = event => {
    setFirstQuantity(event.target.value);
  };
  const handleFirstAge = event => {
    setFirstAge(event.target.value);
  };
  const handleFirstPurpose = event => {
    setFirstPurpose(event.target.value);
  };
  const handleSecondId = event => {
    setSecondId(event.target.value);
  };
  const handleSecondDeviceType = event => {
    setSecondDeviceType(event.target.value);
  };
  const handleSecondBrandAndModel = event => {
    setSecondBrandAndModel(event.target.value);
  };
  const handleSecondCurrentStatus = event => {
    setSecondCurrentStatus(event.target.value);
  };
  const handleSecondQuantity = event => {
    setSecondQuantity(event.target.value);
  };
  const handleSecondAge = event => {
    setSecondAge(event.target.value);
  };
  const handleSecondPurpose = event => {
    setSecondPurpose(event.target.value);
  };
  const handleThirdId = event => {
    setThirdId(event.target.value);
  };
  const handleThirdDeviceType = event => {
    setThirdDeviceType(event.target.value);
  };
  const handleThirdBrandAndModel = event => {
    setThirdBrandAndModel(event.target.value);
  };
  const handleThirdCurrentStatus = event => {
    setThirdCurrentStatus(event.target.value);
  };
  const handleThirdQuantity = event => {
    setThirdQuantity(event.target.value);
  };
  const handleThirdAge = event => {
    setThirdAge(event.target.value);
  };
  const handleThirdPurpose = event => {
    setThirdPurpose(event.target.value);
  };
  const handleFourthId = event => {
    setFourthId(event.target.value);
  };
  const handleFourthDeviceType = event => {
    setFourthDeviceType(event.target.value);
  };
  const handleFourthBrandAndModel = event => {
    setFourthBrandAndModel(event.target.value);
  };
  const handleFourthCurrentStatus = event => {
    setFourthCurrentStatus(event.target.value);
  };
  const handleFourthQuantity = event => {
    setFourthQuantity(event.target.value);
  };
  const handleFourthAge = event => {
    setFourthAge(event.target.value);
  };
  const handleFourthPurpose = event => {
    setFourthPurpose(event.target.value);
  };
  const handleFifthId = event => {
    setFifthId(event.target.value);
  };
  const handleFifthDeviceType = event => {
    setFifthDeviceType(event.target.value);
  };
  const handleFifthBrandAndModel = event => {
    setFifthBrandAndModel(event.target.value);
  };
  const handleFifthCurrentStatus = event => {
    setFifthCurrentStatus(event.target.value);
  };
  const handleFifthQuantity = event => {
    setFifthQuantity(event.target.value);
  };
  const handleFifthAge = event => {
    setFifthAge(event.target.value);
  };
  const handleFifthPurpose = event => {
    setFifthPurpose(event.target.value);
  };
  const handleSixthId = event => {
    setSixthId(event.target.value);
  };
  const handleSixthDeviceType = event => {
    setSixthDeviceType(event.target.value);
  };
  const handleSixthBrandAndModel = event => {
    setSixthBrandAndModel(event.target.value);
  };
  const handleSixthCurrentStatus = event => {
    setSixthCurrentStatus(event.target.value);
  };
  const handleSixthQuantity = event => {
    setSixthQuantity(event.target.value);
  };
  const handleSixthAge = event => {
    setSixthAge(event.target.value);
  };
  const handleSixthPurpose = event => {
    setSixthPurpose(event.target.value);
  };
  const forms = useAppSelector(state => state.form.entities);
  const updating = useAppSelector(state => state.datacenterDevice.updating);

  const updateAllEntities = event => {
    event.preventDefault();
    updateFirstEntity(firstId, firstDeviceType, firstBrandAndModel, firstCurrentStatus, firstQuantity, firstAge, firstPurpose);
    updateSecondEntity(secondId, secondDeviceType, secondBrandAndModel, secondCurrentStatus, secondQuantity, secondAge, secondPurpose);
    updateThirdEntity(thirdId, thirdDeviceType, thirdBrandAndModel, thirdCurrentStatus, thirdQuantity, thirdAge, thirdPurpose);
    updateFourthEntity(fourthId, fourthDeviceType, fourthBrandAndModel, fourthCurrentStatus, fourthQuantity, fourthAge, fourthPurpose);
    updateFifthEntity(fifthId, fifthDeviceType, fifthBrandAndModel, fifthCurrentStatus, fifthQuantity, fifthAge, fifthPurpose);
    updateSixthEntity(sixthId, sixthDeviceType, sixthBrandAndModel, sixthCurrentStatus, sixthQuantity, sixthAge, sixthPurpose);
    dispatch(incrementEditIndex(1));
  };
  const updateFirstEntity = async (Id, DeviceType, BrandAndModel, CurrentStatus, Quantity, Age, Purpose) => {
    const entity = {
      id: Id,
      deviceType: DeviceType,
      brandAndModel: BrandAndModel,
      currentStatus: CurrentStatus,
      quantity: Quantity,
      age: Age,
      purpose: Purpose,
      form: forms.find(it => it.id.toString() === formId),
    };
    await dispatch(updateEntity(entity));
  };
  const updateSecondEntity = async (Id, DeviceType, BrandAndModel, CurrentStatus, Quantity, Age, Purpose) => {
    const entity = {
      id: Id,
      deviceType: DeviceType,
      brandAndModel: BrandAndModel,
      currentStatus: CurrentStatus,
      quantity: Quantity,
      age: Age,
      purpose: Purpose,
      form: forms.find(it => it.id.toString() === formId),
    };
    await dispatch(updateEntity(entity));
  };
  const updateThirdEntity = async (Id, DeviceType, BrandAndModel, CurrentStatus, Quantity, Age, Purpose) => {
    const entity = {
      id: Id,
      deviceType: DeviceType,
      brandAndModel: BrandAndModel,
      currentStatus: CurrentStatus,
      quantity: Quantity,
      age: Age,
      purpose: Purpose,
      form: forms.find(it => it.id.toString() === formId),
    };
    await dispatch(updateEntity(entity));
  };
  const updateFourthEntity = async (Id, DeviceType, BrandAndModel, CurrentStatus, Quantity, Age, Purpose) => {
    const entity = {
      id: Id,
      deviceType: DeviceType,
      brandAndModel: BrandAndModel,
      currentStatus: CurrentStatus,
      quantity: Quantity,
      age: Age,
      purpose: Purpose,
      form: forms.find(it => it.id.toString() === formId),
    };
    await dispatch(updateEntity(entity));
  };
  const updateFifthEntity = async (Id, DeviceType, BrandAndModel, CurrentStatus, Quantity, Age, Purpose) => {
    const entity = {
      id: Id,
      deviceType: DeviceType,
      brandAndModel: BrandAndModel,
      currentStatus: CurrentStatus,
      quantity: Quantity,
      age: Age,
      purpose: Purpose,
      form: forms.find(it => it.id.toString() === formId),
    };
    await dispatch(updateEntity(entity));
  };
  const updateSixthEntity = async (Id, DeviceType, BrandAndModel, CurrentStatus, Quantity, Age, Purpose) => {
    const entity = {
      id: Id,
      deviceType: DeviceType,
      brandAndModel: BrandAndModel,
      currentStatus: CurrentStatus,
      quantity: Quantity,
      age: Age,
      purpose: Purpose,
      form: forms.find(it => it.id.toString() === formId),
    };
    await dispatch(updateEntity(entity));
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h3 id="surveySampleApp.datacenterDevice.home.createOrEditLabel" data-cy="DatacenterDeviceCreateUpdateHeading">
            <Translate contentKey="surveySampleApp.datacenterDevice.home.createOrEditLabel">Create or edit a DatacenterDevice</Translate>
          </h3>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          <form onSubmit={updateAllEntities}>
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
              {datacenterDeviceEntities.length !== 0 && (
                <tbody>
                  <tr>
                    <td hidden>
                      <ValidatedField defaultValue={datacenterDeviceEntities[0].id} name="firstId" type="text" onChange={handleFirstId} />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[0].deviceType}
                        name="firstDeviceType"
                        type="text"
                        required
                        disabled
                        onChange={handleFirstDeviceType}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[0].brandAndModel}
                        name="firstBrandAndModel"
                        type="text"
                        required
                        onChange={handleFirstBrandAndModel}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[0].currentStatus}
                        name="firstCurrentStatus"
                        type="text"
                        required
                        onChange={handleFirstCurrentStatus}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[0].quantity}
                        name="firstQuantity"
                        type="number"
                        required
                        onChange={handleFirstQuantity}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[0].age}
                        name="firstAge"
                        type="text"
                        required
                        onChange={handleFirstAge}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[0].purpose}
                        name="firstPurpose"
                        type="text"
                        required
                        onChange={handleFirstPurpose}
                      />
                    </td>
                  </tr>
                  <tr>
                    <td hidden>
                      <ValidatedField defaultValue={datacenterDeviceEntities[1].id} name="SecondId" type="text" onChange={handleSecondId} />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[1].deviceType}
                        name="SecondDeviceType"
                        type="text"
                        required
                        disabled
                        onChange={handleSecondDeviceType}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[1].brandAndModel}
                        name="SecondBrandAndModel"
                        type="text"
                        required
                        onChange={handleSecondBrandAndModel}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[1].currentStatus}
                        name="SecondCurrentStatus"
                        type="text"
                        required
                        onChange={handleSecondCurrentStatus}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[1].quantity}
                        name="SecondQuantity"
                        type="number"
                        required
                        onChange={handleSecondQuantity}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[1].age}
                        name="SecondAge"
                        type="text"
                        required
                        onChange={handleSecondAge}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[1].purpose}
                        name="SecondPurpose"
                        type="text"
                        required
                        onChange={handleSecondPurpose}
                      />
                    </td>
                  </tr>
                  <tr>
                    <td hidden>
                      <ValidatedField defaultValue={datacenterDeviceEntities[2].id} name="ThirdId" type="text" onChange={handleThirdId} />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[2].deviceType}
                        name="ThirdDeviceType"
                        type="text"
                        required
                        disabled
                        onChange={handleThirdDeviceType}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[2].brandAndModel}
                        name="ThirdBrandAndModel"
                        type="text"
                        required
                        onChange={handleThirdBrandAndModel}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[2].currentStatus}
                        name="ThirdCurrentStatus"
                        type="text"
                        required
                        onChange={handleThirdCurrentStatus}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[2].quantity}
                        name="ThirdQuantity"
                        type="number"
                        required
                        onChange={handleThirdQuantity}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[2].age}
                        name="ThirdAge"
                        type="text"
                        required
                        onChange={handleThirdAge}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[2].purpose}
                        name="ThirdPurpose"
                        type="text"
                        required
                        onChange={handleThirdPurpose}
                      />
                    </td>
                  </tr>
                  <tr>
                    <td hidden>
                      <ValidatedField defaultValue={datacenterDeviceEntities[3].id} name="FourthId" type="text" onChange={handleFourthId} />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[3].deviceType}
                        name="FourthDeviceType"
                        type="text"
                        required
                        disabled
                        onChange={handleFourthDeviceType}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[3].brandAndModel}
                        name="FourthBrandAndModel"
                        type="text"
                        required
                        onChange={handleFourthBrandAndModel}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[3].currentStatus}
                        name="FourthCurrentStatus"
                        type="text"
                        required
                        onChange={handleFourthCurrentStatus}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[3].quantity}
                        name="FourthQuantity"
                        type="number"
                        required
                        onChange={handleFourthQuantity}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[3].age}
                        name="FourthAge"
                        type="text"
                        required
                        onChange={handleFourthAge}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[3].purpose}
                        name="FourthPurpose"
                        type="text"
                        required
                        onChange={handleFourthPurpose}
                      />
                    </td>
                  </tr>
                  <tr>
                    <td hidden>
                      <ValidatedField defaultValue={datacenterDeviceEntities[4].id} name="FifthId" type="text" onChange={handleFifthId} />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[4].deviceType}
                        name="FifthDeviceType"
                        type="text"
                        required
                        disabled
                        onChange={handleFifthDeviceType}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[4].brandAndModel}
                        name="FifthBrandAndModel"
                        type="text"
                        required
                        onChange={handleFifthBrandAndModel}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[4].currentStatus}
                        name="FifthCurrentStatus"
                        type="text"
                        required
                        onChange={handleFifthCurrentStatus}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[4].quantity}
                        name="FifthQuantity"
                        type="number"
                        required
                        onChange={handleFifthQuantity}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[4].age}
                        name="FifthAge"
                        type="text"
                        required
                        onChange={handleFifthAge}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[4].purpose}
                        name="FifthPurpose"
                        type="text"
                        required
                        onChange={handleFifthPurpose}
                      />
                    </td>
                  </tr>
                  <tr>
                    <td hidden>
                      <ValidatedField defaultValue={datacenterDeviceEntities[5].id} name="SixthId" type="text" onChange={handleSixthId} />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[5].deviceType}
                        name="SixthDeviceType"
                        type="text"
                        required
                        disabled
                        onChange={handleSixthDeviceType}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[5].brandAndModel}
                        name="SixthBrandAndModel"
                        type="text"
                        required
                        onChange={handleSixthBrandAndModel}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[5].currentStatus}
                        name="SixthCurrentStatus"
                        type="text"
                        required
                        onChange={handleSixthCurrentStatus}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[5].quantity}
                        name="SixthQuantity"
                        type="number"
                        required
                        onChange={handleSixthQuantity}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[5].age}
                        name="SixthAge"
                        type="text"
                        required
                        onChange={handleSixthAge}
                      />
                    </td>
                    <td>
                      <ValidatedField
                        defaultValue={datacenterDeviceEntities[5].purpose}
                        name="SixthPurpose"
                        type="text"
                        required
                        onChange={handleSixthPurpose}
                      />
                    </td>
                  </tr>
                </tbody>
              )}
            </Table>
            <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
              <Translate contentKey="entity.action.updatenext">edit next</Translate>
            </Button>
          </form>
        </Col>
      </Row>
    </div>
  );
};

export default DatacenterDeviceSurveyUpdate;
