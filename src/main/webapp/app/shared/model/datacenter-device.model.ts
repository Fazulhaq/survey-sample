import { IForm } from 'app/shared/model/form.model';

export interface IDatacenterDevice {
  id?: number;
  deviceType?: string | null;
  quantity?: string | null;
  brandAndModel?: string | null;
  age?: string | null;
  purpose?: string | null;
  currentStatus?: string | null;
  form?: IForm;
}

export const defaultValue: Readonly<IDatacenterDevice> = {};
