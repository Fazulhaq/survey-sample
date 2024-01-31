import { IForm } from 'app/shared/model/form.model';

export interface INetworkConfigCheckList {
  id?: number;
  dhcp?: boolean | null;
  dns?: boolean | null;
  activeDirectory?: boolean | null;
  sharedDrives?: boolean | null;
  mailServer?: boolean | null;
  firewalls?: boolean | null;
  loadBalancing?: boolean | null;
  networkMonitoring?: boolean | null;
  antivirus?: boolean | null;
  integratedSystems?: boolean | null;
  antiSpam?: boolean | null;
  wpa?: boolean | null;
  autoBackup?: boolean | null;
  physicalSecurity?: boolean | null;
  storageServer?: boolean | null;
  securityAudit?: boolean | null;
  disasterRecovery?: boolean | null;
  proxyServer?: boolean | null;
  wdsServer?: boolean | null;
  form?: IForm;
}

export const defaultValue: Readonly<INetworkConfigCheckList> = {
  dhcp: false,
  dns: false,
  activeDirectory: false,
  sharedDrives: false,
  mailServer: false,
  firewalls: false,
  loadBalancing: false,
  networkMonitoring: false,
  antivirus: false,
  integratedSystems: false,
  antiSpam: false,
  wpa: false,
  autoBackup: false,
  physicalSecurity: false,
  storageServer: false,
  securityAudit: false,
  disasterRecovery: false,
  proxyServer: false,
  wdsServer: false,
};
