import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './backup.reducer';

export const Backup = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const backupList = useAppSelector(state => state.backup.entities);
  const loading = useAppSelector(state => state.backup.loading);
  const totalItems = useAppSelector(state => state.backup.totalItems);

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
      <h2 id="backup-heading" data-cy="BackupHeading">
        <Translate contentKey="surveySampleApp.backup.home.title">Backups</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="surveySampleApp.backup.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/backup/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="surveySampleApp.backup.home.createLabel">Create new Backup</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {backupList && backupList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="surveySampleApp.backup.id">ID</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('question1')}>
                  <Translate contentKey="surveySampleApp.backup.question1">Question 1</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('question1')} />
                </th>
                <th className="hand" onClick={sort('question2')}>
                  <Translate contentKey="surveySampleApp.backup.question2">Question 2</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('question2')} />
                </th>
                <th className="hand" onClick={sort('question3')}>
                  <Translate contentKey="surveySampleApp.backup.question3">Question 3</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('question3')} />
                </th>
                <th className="hand" onClick={sort('question4')}>
                  <Translate contentKey="surveySampleApp.backup.question4">Question 4</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('question4')} />
                </th>
                <th className="hand" onClick={sort('question5')}>
                  <Translate contentKey="surveySampleApp.backup.question5">Question 5</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('question5')} />
                </th>
                <th className="hand" onClick={sort('question6')}>
                  <Translate contentKey="surveySampleApp.backup.question6">Question 6</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('question6')} />
                </th>
                <th className="hand" onClick={sort('question7')}>
                  <Translate contentKey="surveySampleApp.backup.question7">Question 7</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('question7')} />
                </th>
                <th>
                  <Translate contentKey="surveySampleApp.backup.form">Form</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {backupList.map((backup, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/backup/${backup.id}`} color="link" size="sm">
                      {backup.id}
                    </Button>
                  </td>
                  <td>{backup.question1}</td>
                  <td>{backup.question2}</td>
                  <td>{backup.question3}</td>
                  <td>{backup.question4}</td>
                  <td>{backup.question5}</td>
                  <td>{backup.question6}</td>
                  <td>{backup.question7}</td>
                  <td>{backup.form ? <Link to={`/form/${backup.form.id}`}>{backup.form.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/backup/${backup.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/backup/${backup.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/backup/${backup.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="surveySampleApp.backup.home.notFound">No Backups found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={backupList && backupList.length > 0 ? '' : 'd-none'}>
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

export default Backup;
