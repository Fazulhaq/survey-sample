import { Button, Col, Row } from 'reactstrap';
import React, { useEffect, useRef, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { useReactToPrint } from 'react-to-print';
import SurveyFormDetail from '../survey-print/survey-form-details';
import SurveyServerDetail from '../survey-print/survey-server-details';
import SurveySystemDetail from '../survey-print/survey-system-details';
import SurveyBackupDetail from '../survey-print/survey-backup-details';
import SurveyDatacenterDeviceDetail from '../survey-print/survey-datacenterdevice.details';
import SurveyInternetDetail from '../survey-print/survey-internet-details';
import SurveyItDeviceDetail from '../survey-print/survey-itdevice-details';
import SurveyNetworkConfigCheckListDetail from '../survey-print/survey-netconfiglist.details';
import SurveyOrgResponsiblePersonDetail from '../survey-print/survey-orgresperson.details';
import { Translate } from 'react-jhipster';
import { useAppSelector } from 'app/config/store';

type Direction = 'ltr' | 'rtl';

export const SurveyPrint = () => {
  const { id } = useParams<'id'>();

  const CompToPrintRef = useRef(null);

  const [printdirection, setPrintdirection] = useState<Direction>('ltr');
  const currentLocale = useAppSelector(state => state.locale.currentLocale);
  useEffect(() => {
    if (currentLocale === 'en') {
      setPrintdirection('ltr');
    } else {
      setPrintdirection('rtl');
    }
  }, [currentLocale]);

  const pageBreakDiv = `
    @media print {
      .page-break {
        page-break-before: always;
      }
    }
  `;

  const handlePrint = useReactToPrint({
    documentTitle: 'Survey Document',
    content: () => CompToPrintRef.current,
    removeAfterPrint: true,
  });

  return (
    <Row className="justify-content-center">
      <Col md="12">
        <Button tag={Link} to="/form" replace color="info" data-cy="entityDetailsBackButton">
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.surveylist">Back to Survey List</Translate>
          </span>
        </Button>
        &nbsp;
        <Button color="dark" onClick={handlePrint}>
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.print">Print</Translate>
          </span>
        </Button>
        <br />
        <div ref={CompToPrintRef} style={{ margin: '25px', direction: printdirection }}>
          <style>{pageBreakDiv}</style>
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
          <br />
          <SurveyFormDetail formId={id} />
          <div className="page-break" />
          <SurveyServerDetail formId={id} />
          <div className="page-break" />
          <SurveySystemDetail formId={id} />
          <div className="page-break" />
          <SurveyBackupDetail formId={id} />
          <div className="page-break" />
          <SurveyDatacenterDeviceDetail formId={id} />
          <div className="page-break" />
          <SurveyInternetDetail formId={id} />
          <div className="page-break" />
          <SurveyItDeviceDetail formId={id} />
          <div className="page-break" />
          <SurveyNetworkConfigCheckListDetail formId={id} />
          <div className="page-break" />
          <SurveyOrgResponsiblePersonDetail formId={id} />
          <br />
        </div>
        <br />
        <Button tag={Link} to="/form" replace color="info" data-cy="entityDetailsBackButton">
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.surveylist">Back to Survey List</Translate>
          </span>
        </Button>
        &nbsp;
        <Button color="dark" onClick={handlePrint}>
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.print">Print</Translate>
          </span>
        </Button>
        <br />
      </Col>
    </Row>
  );
};

export default SurveyPrint;
