import React from 'react';
import InteractiveSteps from './form-stepper';

const CompletionPage: React.FC = () => {
  const handleClick = () => {
    return <InteractiveSteps />;
  };
  return (
    <div style={{ textAlign: 'center', alignItems: 'center' }}>
      <br /> <br /> <br /> <br />
      <h2>Congratulations! You have completed the survey.</h2>
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
        Start New Survey
      </button>
    </div>
  );
};

export default CompletionPage;
