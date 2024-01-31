import { IForm } from 'app/shared/model/form.model';

export interface IOrgResponsiblePerson {
  id?: number;
  fullName?: string;
  position?: string;
  contact?: string;
  date?: string;
  form?: IForm;
}

export const defaultValue: Readonly<IOrgResponsiblePerson> = {};
