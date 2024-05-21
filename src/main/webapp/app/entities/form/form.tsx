import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { APP_LOCAL_DATE_FORMAT, AUTHORITIES } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import React, { useEffect, useState } from 'react';
import { JhiItemCount, JhiPagination, TextFormat, Translate, ValidatedField, getPaginationState, translate } from 'react-jhipster';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { getEntities } from './form.reducer';
import { resetEditIndex } from './survey-edit-index-reducer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import axios from 'axios';
import * as XLSX from 'xlsx';

export const Form = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const formList = useAppSelector(state => state.form.entities);
  const loading = useAppSelector(state => state.form.loading);
  const totalItems = useAppSelector(state => state.form.totalItems);
  const account = useAppSelector(state => state.authentication.account);
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));

  const [searchString, setSearchString] = useState('');

  const [storedSurveyData, setStoredSurveyData] = useState([]);
  useEffect(() => {
    const getSurveyData = async () => {
      const result = [];
      const requestedData = await axios.get('api/forms/surveydata');
      const keys = Object.keys(requestedData.data);
      for (const key of keys) {
        const obj = {
          object0: requestedData.data[key][0],
          object1: requestedData.data[key][1],
          object2: requestedData.data[key][2],
          object3: requestedData.data[key][3],
          object4: requestedData.data[key][4][0],
          object5: requestedData.data[key][4][1],
          object6: requestedData.data[key][4][2],
          object7: requestedData.data[key][4][3],
          object8: requestedData.data[key][4][4],
          object9: requestedData.data[key][4][5],
          object10: requestedData.data[key][5],
          object11: requestedData.data[key][6][0],
          object12: requestedData.data[key][6][1],
          object13: requestedData.data[key][6][2],
          object14: requestedData.data[key][6][3],
          object15: requestedData.data[key][6][4],
          object16: requestedData.data[key][7],
          object17: requestedData.data[key][8],
        };
        result.push(obj);
      }
      const reformedData = result.map(data => ({
        SurveyID: data.object0.id,
        SurveyTitle: data.object0.futurePlan,
        CreatedDate: data.object0.createDate.split('T')[0],
        OrganizationName: data.object0.organization.name,
        OrganizationAddress: data.object0.organization.address,
        TypeOfServers: data.object1.question1,
        TypeOfDataStorage: data.object1.question2,
        AgeOfServerHardware: data.object1.question3,
        AlertTypeHandling: data.object1.question4,
        ServerOperatingSystem: data.object1.question5,
        ServerMonitoringSystem: data.object1.question6,
        DisasterRecoveryPlan: data.object1.question7,
        TechnologicalMonitoringDepartment: data.object2.question1,
        ITProfessionalsQuantity: data.object2.question2,
        BudgetAllocatedForITDepartment: data.object2.question3,
        OfficialWebsiteURL: data.object2.question5,
        TypeOfDataCenter: data.object3.question1,
        ReliabilityAndAvailability: data.object3.question2,
        BackupStorageLocation: data.object3.question3,
        BackupFrequency: data.object3.question4,
        BackupMethod: data.object3.question5,
        BackupTestingFrequency: data.object3.question6,
        DisasterRecoveryPlanAvailability: data.object3.question7,
        DataCenterDevice1:
          data.object4.quantity +
          ' - ' +
          data.object4.currentStatus +
          ' ' +
          data.object4.brandAndModel +
          ' ' +
          data.object4.deviceType +
          ' for ' +
          data.object4.purpose,
        DataCenterDevice2:
          data.object5.quantity +
          ' - ' +
          data.object5.currentStatus +
          ' ' +
          data.object5.brandAndModel +
          ' ' +
          data.object5.deviceType +
          ' for ' +
          data.object5.purpose,
        DataCenterDevice3:
          data.object6.quantity +
          ' - ' +
          data.object6.currentStatus +
          ' ' +
          data.object6.brandAndModel +
          ' ' +
          data.object6.deviceType +
          ' for ' +
          data.object6.purpose,
        DataCenterDevice4:
          data.object7.quantity +
          ' - ' +
          data.object7.currentStatus +
          ' ' +
          data.object7.brandAndModel +
          ' ' +
          data.object7.deviceType +
          ' for ' +
          data.object7.purpose,
        DataCenterDevice5:
          data.object8.quantity +
          ' - ' +
          data.object8.currentStatus +
          ' ' +
          data.object8.brandAndModel +
          ' ' +
          data.object8.deviceType +
          ' for ' +
          data.object8.purpose,
        DataCenterDevice6:
          data.object9.quantity +
          ' - ' +
          data.object9.currentStatus +
          ' ' +
          data.object9.brandAndModel +
          ' ' +
          data.object9.deviceType +
          ' for ' +
          data.object9.purpose,
        InternetServiceProvider: data.object10.question1,
        ConnectionType_FiberOptic_P2P: data.object10.question2,
        DownloadSpeed_Mps: data.object10.question3,
        UploadSpeed_Mps: data.object10.question4,
        NetworkAccessControlMethod: data.object10.question5,
        NetworkOutages_FrequencyDuration: data.object10.question6,
        IT_Device1:
          data.object11.quantity +
          ' - ' +
          data.object11.currentStatus +
          ' ' +
          data.object11.brandAndModel +
          ' ' +
          data.object11.deviceType +
          ' for ' +
          data.object11.purpose,
        IT_Device2:
          data.object12.quantity +
          ' - ' +
          data.object12.currentStatus +
          ' ' +
          data.object12.brandAndModel +
          ' ' +
          data.object12.deviceType +
          ' for ' +
          data.object12.purpose,
        IT_Device3:
          data.object13.quantity +
          ' - ' +
          data.object13.currentStatus +
          ' ' +
          data.object13.brandAndModel +
          ' ' +
          data.object13.deviceType +
          ' for ' +
          data.object13.purpose,
        IT_Device4:
          data.object14.quantity +
          ' - ' +
          data.object14.currentStatus +
          ' ' +
          data.object14.brandAndModel +
          ' ' +
          data.object14.deviceType +
          ' for ' +
          data.object14.purpose,
        IT_Device5:
          data.object15.quantity +
          ' - ' +
          data.object15.currentStatus +
          ' ' +
          data.object15.brandAndModel +
          ' ' +
          data.object15.deviceType +
          ' for ' +
          data.object15.purpose,
        Network_Tools_List:
          (data.object16.activeDirectory ? 'Active Directory, ' : '') +
          (data.object16.antiSpam ? 'Anti Spam Mechanism, ' : '') +
          (data.object16.antivirus ? 'Anti Virus, ' : '') +
          (data.object16.autoBackup ? 'Auto Backup, ' : '') +
          (data.object16.dhcp ? 'DHCP, ' : '') +
          (data.object16.disasterRecovery ? 'Disaster Recovery, ' : '') +
          (data.object16.dns ? 'DNS Server, ' : '') +
          (data.object16.firewalls ? 'FireWalls, ' : '') +
          (data.object16.integratedSystems ? 'Integrated Systems, ' : '') +
          (data.object16.loadBalancing ? 'Load Balancing, ' : '') +
          (data.object16.mailServer ? 'Mail Server, ' : '') +
          (data.object16.networkMonitoring ? 'Network Monitoring, ' : '') +
          (data.object16.physicalSecurity ? 'Physical Security, ' : '') +
          (data.object16.proxyServer ? 'Proxy Server, ' : '') +
          (data.object16.securityAudit ? 'Security Audit, ' : '') +
          (data.object16.sharedDrives ? 'Shared Drives, ' : '') +
          (data.object16.storageServer ? 'Storage Server, ' : '') +
          (data.object16.wdsServer ? 'WDS Server, ' : '') +
          (data.object16.wpa ? 'and WPA' : ''),
        Survey_Responsible: data.object17.fullName,
        Position: data.object17.position,
        Contact_Number: data.object17.contact,
      }));
      setStoredSurveyData(reformedData);
    };
    getSurveyData();
  }, []);

  const handleXLSXfile = () => {
    const header = [
      'ID',
      'Title(System_Name)',
      'Established Date',
      'Organization Name',
      'Organization Address',
      'Type of Servers',
      'Type of Data Storage',
      'Age of Server Hardware',
      'Alert Type Handling',
      'Servers Operating Systems',
      'Servers Monitoring System',
      'Disaster Recovery Plan',
      'Technological Monitoring Department',
      'IT Professionals Personnel',
      'Budget Allocated For IT Department',
      'OfficialWebsiteURL',
      'Type of Data Center',
      'Reliability And Availability of System',
      'Backup Storage Location',
      'Backup Frequency Details',
      'Backup Method',
      'Backup Testing Frequency',
      'Disaster Recovery Plan Availability',
      'Data Center Device1',
      'Data Center Device2',
      'Data Center Device3',
      'Data Center Device4',
      'Data Center Device5',
      'Data Center Device6',
      'Internet Service Provider',
      'Connection Type like: FiberOptic,P2P...',
      'Download Speed(Mps)',
      'Upload Speed(Mps)',
      'Network Access Control Method',
      'Network Outages(Frequency Duration)',
      'IT Device1',
      'IT Device2',
      'IT Device3',
      'IT Device4',
      'IT Device5',
      'Network Tools List',
      'Surveyer Name',
      'Surveyer Position',
      'Surveyer Contact',
    ];
    const worksheetData: (string | number)[][] = [header];
    storedSurveyData.forEach(item => {
      const row: (string | number)[] = [
        item.SurveyID,
        item.SurveyTitle,
        item.CreatedDate,
        item.OrganizationName,
        item.OrganizationAddress,
        item.TypeOfServers,
        item.TypeOfDataStorage,
        item.AgeOfServerHardware,
        item.AlertTypeHandling,
        item.ServerOperatingSystem,
        item.ServerMonitoringSystem,
        item.DisasterRecoveryPlan,
        item.TechnologicalMonitoringDepartment,
        item.ITProfessionalsQuantity,
        item.BudgetAllocatedForITDepartment,
        item.OfficialWebsiteURL,
        item.TypeOfDataCenter,
        item.ReliabilityAndAvailability,
        item.BackupStorageLocation,
        item.BackupFrequency,
        item.BackupMethod,
        item.BackupTestingFrequency,
        item.DisasterRecoveryPlanAvailability,
        item.DataCenterDevice1,
        item.DataCenterDevice2,
        item.DataCenterDevice3,
        item.DataCenterDevice4,
        item.DataCenterDevice5,
        item.DataCenterDevice6,
        item.InternetServiceProvider,
        item.ConnectionType_FiberOptic_P2P,
        item.DownloadSpeed_Mps,
        item.UploadSpeed_Mps,
        item.NetworkAccessControlMethod,
        item.NetworkOutages_FrequencyDuration,
        item.IT_Device1,
        item.IT_Device2,
        item.IT_Device3,
        item.IT_Device4,
        item.IT_Device5,
        item.Network_Tools_List,
        item.Survey_Responsible,
        item.Position,
        item.Contact_Number,
      ];
      worksheetData.push(row);
    });
    const worksheet = XLSX.utils.aoa_to_sheet(worksheetData);
    const range = XLSX.utils.decode_range(worksheet['!ref']);
    for (let i = range.s.c; i <= range.e.c; i++) {
      let maxColWidth = 0;
      for (let j = range.s.r; j <= range.e.r; j++) {
        const cellAddress = XLSX.utils.encode_cell({ r: j, c: i });
        const cellValue = worksheet[cellAddress] ? worksheet[cellAddress].v.toString() : '';
        if (cellValue.length > maxColWidth) {
          maxColWidth = cellValue.length;
        }
      }
      worksheet['!cols'] = worksheet['!cols'] || [];
      worksheet['!cols'][i] = { wch: maxColWidth + 3 };
    }
    worksheet['!autofilter'] = { ref: `A1:AR1` };
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'All Survey Records');
    XLSX.writeFile(workbook, 'Survey-Exported-File.xlsx');
  };

  const handleChange = event => {
    setSearchString(event.target.value);
  };

  const handleClick = () => {
    dispatch(resetEditIndex(0));
  };

  const filteredForms = formList.filter(form => form.futurePlan.toLowerCase().includes(searchString.toLowerCase()));

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="form-heading" data-cy="FormHeading">
        <Translate contentKey="surveySampleApp.form.home.title">Forms</Translate>
        <div className="d-flex justify-content-end">
          &nbsp;
          <br />
        </div>
      </h2>
      <div className="d-flex justify-content-end">
        {isAdmin && (
          <Button onClick={handleXLSXfile} color="primary" size="sm">
            <span className="d-none d-md-inline">
              <Translate contentKey="surveySampleApp.form.home.formexport">Survey Excel Data</Translate>
            </span>
          </Button>
        )}
      </div>
      <div className="d-flex">
        <h6>
          <ValidatedField
            label={translate('surveySampleApp.form.home.formsearchtitle')}
            id="form-form_search"
            name="form-search"
            data-cy="form-search"
            type="search"
            placeholder={translate('surveySampleApp.form.home.formsearchname')}
            onChange={event => handleChange(event)}
          />
        </h6>
      </div>
      <div className="table-responsive">
        {formList && formList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="surveySampleApp.form.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('futurePlan')}>
                  <Translate contentKey="surveySampleApp.form.futurePlan">Future Plan</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('futurePlan')} />
                </th>
                <th className="hand" onClick={sort('createDate')}>
                  <Translate contentKey="surveySampleApp.form.createDate">Create Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('createDate')} />
                </th>
                <th className="hand" onClick={sort('updateDate')}>
                  <Translate contentKey="surveySampleApp.form.updateDate">Update Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('updateDate')} />
                </th>
                {isAdmin && (
                  <th>
                    <Translate contentKey="surveySampleApp.form.user">User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                )}
                <th>
                  <Translate contentKey="surveySampleApp.form.organization">Organization</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {filteredForms.map(
                (form, i) =>
                  ((form.user && form.user.login === account.login) || isAdmin) && (
                    <tr key={`entity-${i}`} data-cy="entityTable">
                      <td>{form.id}</td>
                      <td>{form.futurePlan}</td>
                      <td>{form.createDate ? <TextFormat type="date" value={form.createDate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                      <td>{form.updateDate ? <TextFormat type="date" value={form.updateDate} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                      {isAdmin && <td>{form.user.login}</td>}
                      <td> {form.organization ? form.organization.name : ''}</td>
                      <td className="text-end">
                        <div className="btn-group flex-btn-group-container">
                          <Button tag={Link} to={`/form/${form.id}/print`} color="info" size="sm" data-cy="entityDetailsButton">
                            <FontAwesomeIcon icon="eye" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.surveyprint">Print View</Translate>
                            </span>
                          </Button>
                          &nbsp;
                          <Button
                            tag={Link}
                            to={`/form/${form.id}/edit`}
                            color="primary"
                            onClick={handleClick}
                            size="sm"
                            data-cy="entityEditButton"
                          >
                            <FontAwesomeIcon icon="pencil-alt" />{' '}
                            <span className="d-none d-md-inline">
                              <Translate contentKey="entity.action.edit">Edit</Translate>
                            </span>
                          </Button>
                          &nbsp;
                          {isAdmin && (
                            <Button tag={Link} to={`/form/${form.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                              <FontAwesomeIcon icon="trash" />{' '}
                              <span className="d-none d-md-inline">
                                <Translate contentKey="entity.action.delete">Delete</Translate>
                              </span>
                            </Button>
                          )}
                        </div>
                      </td>
                    </tr>
                  ),
              )}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="surveySampleApp.form.home.notFound">No Forms found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={formList && formList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Form;
