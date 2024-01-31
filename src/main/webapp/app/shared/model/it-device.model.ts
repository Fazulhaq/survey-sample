import { IForm } from 'app/shared/model/form.model';
import { ItDeviceType } from 'app/shared/model/enumerations/it-device-type.model';

export interface IItDevice {
  id?: number;
  deviceType?: keyof typeof ItDeviceType | null;
  quantity?: string | null;
  brandAndModel?: string | null;
  age?: string | null;
  purpose?: string | null;
  currentStatus?: string | null;
  form?: IForm;
}

export const defaultValue: Readonly<IItDevice> = {};
