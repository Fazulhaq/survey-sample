import './home.scss';

import { Divider } from 'primereact/divider';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Link } from 'react-router-dom';
import { Alert, Col, Row } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="12">
        <div style={{ textAlign: 'justify' }}>
          <br />
          <h4>
            <Translate contentKey="home.Welcomesub">Dear Participant,</Translate>
          </h4>
          <br />
          <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <Translate contentKey="home.paragraph1">first paragraph</Translate>
          </p>
          <Divider />
          <br />
          <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <Translate contentKey="home.paragraph2">second paragraph</Translate>
          </p>
          <br />
          <Divider />
          <br />
          <br />
          <h6>
            <Translate contentKey="home.regardtext">Best regards,</Translate>
          </h6>
          <Translate contentKey="home.teamname">Survey team</Translate>
          <br />
          <Translate contentKey="home.directoratename">System Development Directorate</Translate>
          <br />
          <Translate contentKey="home.ministryname">Ministry of Communication and Information Technology</Translate>
          <br />
        </div>
      </Col>
      <Col md="12">
        {account?.login ? (
          <div>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
          </div>
        ) : (
          <div style={{ marginLeft: 'auto', marginRight: 'auto', textAlign: 'center', justifyContent: 'center', alignItems: 'center' }}>
            <Link to="/login" style={{ fontSize: '20px' }}>
              <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
            </Link>
          </div>
        )}
      </Col>
    </Row>
  );
};

export default Home;
