import { Col, Row } from 'reactstrap';
import React from 'react';
import { Divider } from 'primereact/divider';

export const SurveyReport = () => {
  return (
    <Row className="justify-content-center">
      <Col md="12">
        <h2>Reports Page</h2>
        <Divider />
        <p>This page is only for creating different reports from survey data</p>
      </Col>
    </Row>
  );
};

export default SurveyReport;
