import { Moment } from 'moment';
import { ILocation } from 'app/shared/model/location.model';

export interface IUserAdditional {
  id?: number;
  gender?: string;
  birthDate?: Moment;
  phone?: string;
  email?: string;
  address?: ILocation;
}

export const defaultValue: Readonly<IUserAdditional> = {};
