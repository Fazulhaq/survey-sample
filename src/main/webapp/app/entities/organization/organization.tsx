import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { InputText } from 'primereact/inputtext';
import React, { useEffect, useState } from 'react';
import { JhiItemCount, JhiPagination, Translate, ValidatedField, ValidatedForm, getPaginationState, translate } from 'react-jhipster';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { useTranslation } from 'react-i18next';
import { resetIndex } from '../stepper-index/stepper-index.reducer';
import { getEntities } from './organization.reducer';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';

export const Organization = () => {
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const { t } = useTranslation();
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const organizationList = useAppSelector(state => state.organization.entities);
  const loading = useAppSelector(state => state.organization.loading);
  const totalItems = useAppSelector(state => state.organization.totalItems);

  const handleClick = () => {
    dispatch(resetIndex(0));
  };

  const [searchString, setSearchString] = useState('');

  const handleChange = event => {
    setSearchString(event.target.value);
  };

  const filteredOrganizations = organizationList.filter(organization =>
    organization.name.toLowerCase().includes(searchString.toLowerCase()),
  );

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
      <h2 id="organization-heading" data-cy="OrganizationHeading">
        <Translate contentKey="surveySampleApp.organization.home.title">Organizations</Translate>
        {isAdmin && (
          <div className="d-flex justify-content-end">
            <Link to="/organization/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
              <FontAwesomeIcon icon="plus" />
              &nbsp;
              <Translate contentKey="surveySampleApp.organization.home.createLabel">Create new Organization</Translate>
            </Link>
          </div>
        )}
      </h2>
      <div className="d-flex">
        <h6>
          <ValidatedField
            label={translate('surveySampleApp.organization.home.orgsearchtitle')}
            id="organization-org_search"
            name="org-search"
            data-cy="org-search"
            type="search"
            placeholder={translate('surveySampleApp.organization.home.orgsearchname')}
            onChange={event => handleChange(event)}
          />
        </h6>
      </div>
      <div className="table-responsive">
        {organizationList && organizationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="surveySampleApp.organization.id">ID</Translate>{' '}
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="surveySampleApp.organization.name">Name</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th>
                  <Translate contentKey="surveySampleApp.organization.code">Code</Translate>{' '}
                </th>
                <th>
                  <Translate contentKey="surveySampleApp.organization.description">Description</Translate>{' '}
                </th>
                <th>
                  <Translate contentKey="surveySampleApp.organization.address">Address</Translate>{' '}
                </th>
                {isAdmin && (
                  <th>
                    <Translate contentKey="surveySampleApp.organization.user">User</Translate>
                  </th>
                )}
                <th />
              </tr>
            </thead>
            <tbody>
              {filteredOrganizations.map((organization, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{organization.id}</td>
                  <td>{organization.name}</td>
                  <td>{organization.code}</td>
                  <td>{organization.description}</td>
                  <td>{organization.address}</td>
                  {isAdmin && <td>{organization.user ? organization.user.login : ''}</td>}
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        onClick={handleClick}
                        tag={Link}
                        to={`/form/new/${organization.id}`}
                        color="primary"
                        size="sm"
                        data-cy="entitySurveyButton"
                      >
                        <FontAwesomeIcon icon="plus" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.addsurvey">Survey</Translate>
                        </span>
                      </Button>
                      &nbsp;
                      {isAdmin && (
                        <Button tag={Link} to={`/organization/${organization.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                      )}
                      &nbsp;
                      {isAdmin && (
                        <Button
                          tag={Link}
                          to={`/organization/${organization.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                      )}
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="surveySampleApp.organization.home.notFound">No Organizations found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={organizationList && organizationList.length > 0 ? '' : 'd-none'}>
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

export default Organization;
