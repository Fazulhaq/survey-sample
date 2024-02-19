import { useAppDispatch } from 'app/config/store';
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { resetIndex } from '../stepper-index/stepper-index.reducer';
import { Translate } from 'react-jhipster';

const CompletionPage: React.FC = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const handleClick = () => {
    dispatch(resetIndex(0));
    navigate('/organization');
  };

  return (
    <div style={{ textAlign: 'center', alignItems: 'center' }}>
      <br /> <br /> <br /> <br />
      <h2>
        <Translate contentKey="surveySampleApp.form.home.formcongrates">Congrates</Translate>
      </h2>
      <button
        style={{
          backgroundColor: '#007bff',
          color: '#ffffff',
          border: 'none',
          cursor: 'pointer',
          fontSize: '16px',
          padding: '10px 20pxs',
          width: '140px',
          height: '38px',
          borderRadius: '3px',
        }}
        onClick={handleClick}
      >
        <Translate contentKey="surveySampleApp.form.home.formcongratesbtn">Start New Survey</Translate>
      </button>
    </div>
  );
};

export default CompletionPage;
