import { IForm } from 'app/shared/model/form.model';

export interface IServer {
  id?: number;
  question1?: string | null;
  question2?: string | null;
  question3?: string | null;
  question4?: string | null;
  question5?: string | null;
  question6?: string | null;
  question7?: string | null;
  form?: IForm;
}

export const defaultValue: Readonly<IServer> = {};
