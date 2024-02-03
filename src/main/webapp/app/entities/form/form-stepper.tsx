import { useAppSelector } from 'app/config/store';
import React, { useState } from 'react';
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

export default function InteractiveSteps() {
  const forms = useAppSelector(state => state.form.entities);

  const lastFormId = forms.reduce((maxId, form) => {
    return form.id > maxId ? form.id : maxId;
  }, 0);

  const [activeStep, setActiveStep] = useState(0);
  const steps = [
    {
      label: 'Form',
    },
    {
      label: 'Server',
    },
    {
      label: 'System',
    },
    {
      label: 'Backup',
    },
    {
      label: 'DataCenter Devices',
    },
    {
      label: 'Internet',
    },
    {
      label: 'IT Devices',
    },
    {
      label: 'Network Checklist',
    },
    {
      label: 'Responsible',
    },
    {
      label: 'Completed',
    },
  ];

  function renderComponent() {
    switch (activeStep) {
      case 0:
        return <FormUpdate />;
      case 1:
        return <ServerUpdate formId={lastFormId} />;
      case 2:
        return <SystemUpdate formId={lastFormId} />;
      case 3:
        return <BackupUpdate formId={lastFormId} />;
      case 4:
        return <DatacenterDeviceUpdate formId={lastFormId} />;
      case 5:
        return <InternetUpdate formId={lastFormId} />;
      case 6:
        return <ItDeviceUpdate formId={lastFormId} />;
      case 7:
        return <NetworkConfigCheckListUpdate formId={lastFormId} />;
      case 8:
        return <OrgResponsiblePersonUpdate formId={lastFormId} />;
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
        {activeStep !== 0 && activeStep !== steps.length - 1 && (
          <button
            style={{
              backgroundColor: '#007bff',
              color: '#ffffff',
              border: 'none',
              cursor: 'pointer',
              fontSize: '16px',
              padding: '10px 20pxs',
              width: '80px',
              height: '38px',
              borderRadius: '3px',
            }}
            onClick={() => setActiveStep(activeStep - 1)}
          >
            Previous
          </button>
        )}
        &nbsp;&nbsp;
        {activeStep !== steps.length - 1 && (
          <button
            style={{
              backgroundColor: '#007bff',
              color: '#ffffff',
              border: 'none',
              cursor: 'pointer',
              fontSize: '16px',
              padding: '10px 20pxs',
              width: '80px',
              height: '38px',
              borderRadius: '3px',
            }}
            onClick={() => setActiveStep(activeStep + 1)}
          >
            Next
          </button>
        )}
      </div>
    </div>
  );
}
