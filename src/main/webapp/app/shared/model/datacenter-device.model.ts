import { IForm } from 'app/shared/model/form.model';
import { DataCenterDeviceType } from 'app/shared/model/enumerations/data-center-device-type.model';

export interface IDatacenterDevice {
  id?: number;
  deviceType?: keyof typeof DataCenterDeviceType | null;
  quantity?: string | null;
  brandAndModel?: string | null;
  age?: string | null;
  purpose?: string | null;
  currentStatus?: string | null;
  form?: IForm;
}

export const defaultValue: Readonly<IDatacenterDevice> = {};
