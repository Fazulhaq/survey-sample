import { IUser } from 'app/shared/model/user.model';

export interface IOrganization {
  id?: number;
  name?: string;
  code?: string;
  description?: string | null;
  address?: string;
  createDate?: string | null;
  updateDate?: string | null;
  user?: IUser;
}

export const defaultValue: Readonly<IOrganization> = {};
