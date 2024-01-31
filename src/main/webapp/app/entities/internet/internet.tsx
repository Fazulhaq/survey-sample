import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './internet.reducer';

export const Internet = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const internetList = useAppSelector(state => state.internet.entities);
  const loading = useAppSelector(state => state.internet.loading);
  const totalItems = useAppSelector(state => state.internet.totalItems);

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
      <h2 id="internet-heading" data-cy="InternetHeading">
        <Translate contentKey="surveySampleApp.internet.home.title">Internets</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="surveySampleApp.internet.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/internet/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="surveySampleApp.internet.home.createLabel">Create new Internet</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {internetList && internetList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="surveySampleApp.internet.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('question1')}>
                  <Translate contentKey="surveySampleApp.internet.question1">Question 1</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('question1')} />
                </th>
                <th className="hand" onClick={sort('question2')}>
                  <Translate contentKey="surveySampleApp.internet.question2">Question 2</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('question2')} />
                </th>
                <th className="hand" onClick={sort('question3')}>
                  <Translate contentKey="surveySampleApp.internet.question3">Question 3</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('question3')} />
                </th>
                <th className="hand" onClick={sort('question4')}>
                  <Translate contentKey="surveySampleApp.internet.question4">Question 4</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('question4')} />
                </th>
                <th className="hand" onClick={sort('question5')}>
                  <Translate contentKey="surveySampleApp.internet.question5">Question 5</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('question5')} />
                </th>
                <th className="hand" onClick={sort('question6')}>
                  <Translate contentKey="surveySampleApp.internet.question6">Question 6</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('question6')} />
                </th>
                <th>
                  <Translate contentKey="surveySampleApp.internet.form">Form</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {internetList.map((internet, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/internet/${internet.id}`} color="link" size="sm">
                      {internet.id}
                    </Button>
                  </td>
                  <td>{internet.question1}</td>
                  <td>{internet.question2}</td>
                  <td>{internet.question3}</td>
                  <td>{internet.question4}</td>
                  <td>{internet.question5}</td>
                  <td>{internet.question6}</td>
                  <td>{internet.form ? <Link to={`/form/${internet.form.id}`}>{internet.form.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/internet/${internet.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/internet/${internet.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/internet/${internet.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="surveySampleApp.internet.home.notFound">No Internets found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={internetList && internetList.length > 0 ? '' : 'd-none'}>
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

export default Internet;
