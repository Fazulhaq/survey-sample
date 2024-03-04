import { Col, Row } from 'reactstrap';
import React from 'react';
import { useParams } from 'react-router-dom';

export const SurveyPrint = () => {
  const { id } = useParams<'id'>();

  return (
    <Row className="justify-content-center">
      <Col md="12">The Survey with ID: {id}, Must be printed on this page</Col>
    </Row>
  );
};

export default SurveyPrint;
