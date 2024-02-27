import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import React, { useEffect, useState } from 'react';
import { JhiItemCount, JhiPagination, TextFormat, Translate, ValidatedField, getPaginationState, translate } from 'react-jhipster';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';

import { InputText } from 'primereact/inputtext';
import { getEntities } from './form.reducer';
import { resetEditIndex } from './survey-edit-index-reducer';

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

  const [searchString, setSearchString] = useState('');

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
                <th>
                  <Translate contentKey="surveySampleApp.form.user">User</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="surveySampleApp.form.organization">Organization</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {filteredForms.map((form, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>{form.id}</td>
                  <td>{form.futurePlan}</td>
                  <td>{form.createDate ? <TextFormat type="date" value={form.createDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{form.updateDate ? <TextFormat type="date" value={form.updateDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{form.user ? form.user.login : ''}</td>
                  <td> {form.organization ? form.organization.name : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/form/${form.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
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
                      <Button tag={Link} to={`/form/${form.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
