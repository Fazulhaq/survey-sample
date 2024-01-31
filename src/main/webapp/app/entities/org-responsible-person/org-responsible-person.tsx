import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './org-responsible-person.reducer';

export const OrgResponsiblePerson = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const orgResponsiblePersonList = useAppSelector(state => state.orgResponsiblePerson.entities);
  const loading = useAppSelector(state => state.orgResponsiblePerson.loading);
  const totalItems = useAppSelector(state => state.orgResponsiblePerson.totalItems);

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
      <h2 id="org-responsible-person-heading" data-cy="OrgResponsiblePersonHeading">
        <Translate contentKey="surveySampleApp.orgResponsiblePerson.home.title">Org Responsible People</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="surveySampleApp.orgResponsiblePerson.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/org-responsible-person/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="surveySampleApp.orgResponsiblePerson.home.createLabel">Create new Org Responsible Person</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {orgResponsiblePersonList && orgResponsiblePersonList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="surveySampleApp.orgResponsiblePerson.id">ID</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('fullName')}>
                  <Translate contentKey="surveySampleApp.orgResponsiblePerson.fullName">Full Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('fullName')} />
                </th>
                <th className="hand" onClick={sort('position')}>
                  <Translate contentKey="surveySampleApp.orgResponsiblePerson.position">Position</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('position')} />
                </th>
                <th className="hand" onClick={sort('contact')}>
                  <Translate contentKey="surveySampleApp.orgResponsiblePerson.contact">Contact</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('contact')} />
                </th>
                <th className="hand" onClick={sort('date')}>
                  <Translate contentKey="surveySampleApp.orgResponsiblePerson.date">Date</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('date')} />
                </th>
                <th>
                  <Translate contentKey="surveySampleApp.orgResponsiblePerson.form">Form</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {orgResponsiblePersonList.map((orgResponsiblePerson, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/org-responsible-person/${orgResponsiblePerson.id}`} color="link" size="sm">
                      {orgResponsiblePerson.id}
                    </Button>
                  </td>
                  <td>{orgResponsiblePerson.fullName}</td>
                  <td>{orgResponsiblePerson.position}</td>
                  <td>{orgResponsiblePerson.contact}</td>
                  <td>
                    {orgResponsiblePerson.date ? (
                      <TextFormat type="date" value={orgResponsiblePerson.date} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {orgResponsiblePerson.form ? (
                      <Link to={`/form/${orgResponsiblePerson.form.id}`}>{orgResponsiblePerson.form.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/org-responsible-person/${orgResponsiblePerson.id}`}
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
                        to={`/org-responsible-person/${orgResponsiblePerson.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/org-responsible-person/${orgResponsiblePerson.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="surveySampleApp.orgResponsiblePerson.home.notFound">No Org Responsible People found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={orgResponsiblePersonList && orgResponsiblePersonList.length > 0 ? '' : 'd-none'}>
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

export default OrgResponsiblePerson;
