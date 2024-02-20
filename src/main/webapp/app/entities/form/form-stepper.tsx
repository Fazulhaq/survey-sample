import { useAppSelector } from 'app/config/store';
import React from 'react';
import { Stepper } from 'react-form-stepper';
import BackupUpdate from '../backup/backup-update';
import DatacenterDeviceUpdate from '../datacenter-device/datacenter-device-update';
import InternetUpdate from '../internet/internet-update';
import ItDeviceUpdate from '../it-device/it-device-update';
import NetworkConfigCheckListUpdate from '../network-config-check-list/network-config-check-list-update';
import OrgResponsiblePersonUpdate from '../org-responsible-person/org-responsible-person-update';
import ServerUpdate from '../server/server-update';
import SystemUpdate from '../system/system-update';
import CompletionPage from './form-completion';
import FormUpdate from './form-update';
import { translate } from 'react-jhipster';

export default function InteractiveSteps() {
  const activeStep = useAppSelector(state => state.index.stepperIndex);

  const steps = [
    {
      label: translate('surveySampleApp.form.home.step1'),
    },
    {
      label: translate('surveySampleApp.form.home.step2'),
    },
    {
      label: translate('surveySampleApp.form.home.step3'),
    },
    {
      label: translate('surveySampleApp.form.home.step4'),
    },
    {
      label: translate('surveySampleApp.form.home.step5'),
    },
    {
      label: translate('surveySampleApp.form.home.step6'),
    },
    {
      label: translate('surveySampleApp.form.home.step7'),
    },
    {
      label: translate('surveySampleApp.form.home.step8'),
    },
    {
      label: translate('surveySampleApp.form.home.step9'),
    },
    {
      label: translate('surveySampleApp.form.home.step10'),
    },
  ];

  function renderComponent() {
    switch (activeStep) {
      case 0:
        return <FormUpdate />;
      case 1:
        return <ServerUpdate />;
      case 2:
        return <SystemUpdate />;
      case 3:
        return <BackupUpdate />;
      case 4:
        return <DatacenterDeviceUpdate />;
      case 5:
        return <InternetUpdate />;
      case 6:
        return <ItDeviceUpdate />;
      case 7:
        return <NetworkConfigCheckListUpdate />;
      case 8:
        return <OrgResponsiblePersonUpdate />;
      case 9:
        return <CompletionPage />;
      default:
        return null;
    }
  }

  return (
    <div className="card flex justify-content-center">
      <Stepper steps={steps} activeStep={activeStep} />
      <div style={{ padding: '10px' }}>
        {renderComponent()}
        &nbsp;&nbsp;
      </div>
    </div>
  );
}
