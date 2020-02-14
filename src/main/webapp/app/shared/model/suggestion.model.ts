import { Moment } from 'moment';

export interface ISuggestion {
  id?: number;
  date?: Moment;
}

export const defaultValue: Readonly<ISuggestion> = {};
