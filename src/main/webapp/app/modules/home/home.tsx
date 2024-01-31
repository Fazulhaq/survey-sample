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
          <h4>Dear Participant,</h4>
          <br />
          <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Thank you for taking the time to join our survey and sharing your valuable insights
            with us. Your participation is essential in helping us understand and improve our products/services to better cater to your
            needs. At MCIT, we believe in continuously enhancing the experiences we provide to our customers. Your feedback plays a vital
            role in shaping our future endeavors, and we are grateful for your input. This survey aims to gather your thoughts, opinions,
            and suggestions regarding software requirements survey. By sharing your honest feedback, you will help us gain a deeper
            understanding of what matters most to you, enabling us to serve you better in the future.
          </p>
          <Divider />
          <br />
          <p>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;The survey should take approximately 10 minutes to complete. We understand your time
            is valuable, so we greatly appreciate your willingness to participate. To access the survey, please click on the following link.
            If you encounter any technical issues or have any questions, please feel free to reach out to our dedicated support team at
            info@mcit.gov.af. Once again, thank you for your involvement. Your feedback is invaluable to us, and we genuinely appreciate
            your contribution. We look forward to hearing your thoughts and working towards creating an even better experience for you.
          </p>
          <br />
          <Divider />
          <br />
          <br />
          <h6>Best regards,</h6>
          Survey Team
          <br />
          System Development Directorate
          <br />
          Ministry of Communication and Information Technology
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
