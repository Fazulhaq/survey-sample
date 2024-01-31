import { IUser } from 'app/shared/model/user.model';
import { IOrganization } from 'app/shared/model/organization.model';
import { FormStatus } from 'app/shared/model/enumerations/form-status.model';

export interface IForm {
  id?: number;
  futurePlan?: string | null;
  status?: keyof typeof FormStatus | null;
  createDate?: string | null;
  updateDate?: string | null;
  user?: IUser;
  organization?: IOrganization;
}

export const defaultValue: Readonly<IForm> = {};
