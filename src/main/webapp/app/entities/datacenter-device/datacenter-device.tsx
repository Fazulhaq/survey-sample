import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './datacenter-device.reducer';

export const DatacenterDevice = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const datacenterDeviceList = useAppSelector(state => state.datacenterDevice.entities);
  const loading = useAppSelector(state => state.datacenterDevice.loading);
  const totalItems = useAppSelector(state => state.datacenterDevice.totalItems);

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
      <h2 id="datacenter-device-heading" data-cy="DatacenterDeviceHeading">
        <Translate contentKey="surveySampleApp.datacenterDevice.home.title">Datacenter Devices</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="surveySampleApp.datacenterDevice.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/datacenter-device/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="surveySampleApp.datacenterDevice.home.createLabel">Create new Datacenter Device</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {datacenterDeviceList && datacenterDeviceList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="surveySampleApp.datacenterDevice.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('deviceType')}>
                  <Translate contentKey="surveySampleApp.datacenterDevice.deviceType">Device Type</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('deviceType')} />
                </th>
                <th className="hand" onClick={sort('quantity')}>
                  <Translate contentKey="surveySampleApp.datacenterDevice.quantity">Quantity</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('quantity')} />
                </th>
                <th className="hand" onClick={sort('brandAndModel')}>
                  <Translate contentKey="surveySampleApp.datacenterDevice.brandAndModel">Brand And Model</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('brandAndModel')} />
                </th>
                <th className="hand" onClick={sort('age')}>
                  <Translate contentKey="surveySampleApp.datacenterDevice.age">Age</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('age')} />
                </th>
                <th className="hand" onClick={sort('purpose')}>
                  <Translate contentKey="surveySampleApp.datacenterDevice.purpose">Purpose</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('purpose')} />
                </th>
                <th className="hand" onClick={sort('currentStatus')}>
                  <Translate contentKey="surveySampleApp.datacenterDevice.currentStatus">Current Status</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('currentStatus')} />
                </th>
                <th>
                  <Translate contentKey="surveySampleApp.datacenterDevice.form">Form</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {datacenterDeviceList.map((datacenterDevice, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/datacenter-device/${datacenterDevice.id}`} color="link" size="sm">
                      {datacenterDevice.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`surveySampleApp.DataCenterDeviceType.${datacenterDevice.deviceType}`} />
                  </td>
                  <td>{datacenterDevice.quantity}</td>
                  <td>{datacenterDevice.brandAndModel}</td>
                  <td>{datacenterDevice.age}</td>
                  <td>{datacenterDevice.purpose}</td>
                  <td>{datacenterDevice.currentStatus}</td>
                  <td>{datacenterDevice.form ? <Link to={`/form/${datacenterDevice.form.id}`}>{datacenterDevice.form.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/datacenter-device/${datacenterDevice.id}`}
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
                        to={`/datacenter-device/${datacenterDevice.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/datacenter-device/${datacenterDevice.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="surveySampleApp.datacenterDevice.home.notFound">No Datacenter Devices found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={datacenterDeviceList && datacenterDeviceList.length > 0 ? '' : 'd-none'}>
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

export default DatacenterDevice;
