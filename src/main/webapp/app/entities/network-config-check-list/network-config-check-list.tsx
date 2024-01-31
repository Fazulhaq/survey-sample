import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './network-config-check-list.reducer';

export const NetworkConfigCheckList = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const networkConfigCheckListList = useAppSelector(state => state.networkConfigCheckList.entities);
  const loading = useAppSelector(state => state.networkConfigCheckList.loading);
  const totalItems = useAppSelector(state => state.networkConfigCheckList.totalItems);

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

  const handleSyncList = () => {
    sortEntities();
  };

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
      <h2 id="network-config-check-list-heading" data-cy="NetworkConfigCheckListHeading">
        <Translate contentKey="surveySampleApp.networkConfigCheckList.home.title">Network Config Check Lists</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="surveySampleApp.networkConfigCheckList.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/network-config-check-list/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="surveySampleApp.networkConfigCheckList.home.createLabel">Create new Network Config Check List</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {networkConfigCheckListList && networkConfigCheckListList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('dhcp')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.dhcp">Dhcp</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dhcp')} />
                </th>
                <th className="hand" onClick={sort('dns')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.dns">Dns</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('dns')} />
                </th>
                <th className="hand" onClick={sort('activeDirectory')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.activeDirectory">Active Directory</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('activeDirectory')} />
                </th>
                <th className="hand" onClick={sort('sharedDrives')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.sharedDrives">Shared Drives</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('sharedDrives')} />
                </th>
                <th className="hand" onClick={sort('mailServer')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.mailServer">Mail Server</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('mailServer')} />
                </th>
                <th className="hand" onClick={sort('firewalls')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.firewalls">Firewalls</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('firewalls')} />
                </th>
                <th className="hand" onClick={sort('loadBalancing')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.loadBalancing">Load Balancing</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('loadBalancing')} />
                </th>
                <th className="hand" onClick={sort('networkMonitoring')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.networkMonitoring">Network Monitoring</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('networkMonitoring')} />
                </th>
                <th className="hand" onClick={sort('antivirus')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.antivirus">Antivirus</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('antivirus')} />
                </th>
                <th className="hand" onClick={sort('integratedSystems')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.integratedSystems">Integrated Systems</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('integratedSystems')} />
                </th>
                <th className="hand" onClick={sort('antiSpam')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.antiSpam">Anti Spam</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('antiSpam')} />
                </th>
                <th className="hand" onClick={sort('wpa')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.wpa">Wpa</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('wpa')} />
                </th>
                <th className="hand" onClick={sort('autoBackup')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.autoBackup">Auto Backup</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('autoBackup')} />
                </th>
                <th className="hand" onClick={sort('physicalSecurity')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.physicalSecurity">Physical Security</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('physicalSecurity')} />
                </th>
                <th className="hand" onClick={sort('storageServer')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.storageServer">Storage Server</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('storageServer')} />
                </th>
                <th className="hand" onClick={sort('securityAudit')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.securityAudit">Security Audit</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('securityAudit')} />
                </th>
                <th className="hand" onClick={sort('disasterRecovery')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.disasterRecovery">Disaster Recovery</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('disasterRecovery')} />
                </th>
                <th className="hand" onClick={sort('proxyServer')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.proxyServer">Proxy Server</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('proxyServer')} />
                </th>
                <th className="hand" onClick={sort('wdsServer')}>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.wdsServer">Wds Server</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('wdsServer')} />
                </th>
                <th>
                  <Translate contentKey="surveySampleApp.networkConfigCheckList.form">Form</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {networkConfigCheckListList.map((networkConfigCheckList, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/network-config-check-list/${networkConfigCheckList.id}`} color="link" size="sm">
                      {networkConfigCheckList.id}
                    </Button>
                  </td>
                  <td>{networkConfigCheckList.dhcp ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.dns ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.activeDirectory ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.sharedDrives ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.mailServer ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.firewalls ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.loadBalancing ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.networkMonitoring ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.antivirus ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.integratedSystems ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.antiSpam ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.wpa ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.autoBackup ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.physicalSecurity ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.storageServer ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.securityAudit ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.disasterRecovery ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.proxyServer ? 'true' : 'false'}</td>
                  <td>{networkConfigCheckList.wdsServer ? 'true' : 'false'}</td>
                  <td>
                    {networkConfigCheckList.form ? (
                      <Link to={`/form/${networkConfigCheckList.form.id}`}>{networkConfigCheckList.form.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/network-config-check-list/${networkConfigCheckList.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/network-config-check-list/${networkConfigCheckList.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/network-config-check-list/${networkConfigCheckList.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="surveySampleApp.networkConfigCheckList.home.notFound">No Network Config Check Lists found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={networkConfigCheckListList && networkConfigCheckListList.length > 0 ? '' : 'd-none'}>
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

export default NetworkConfigCheckList;
