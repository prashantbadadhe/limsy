import { ISuggestion } from 'app/shared/model/suggestion.model';
import { IDress } from 'app/shared/model/dress.model';

export interface IUsed {
  id?: number;
  selected?: boolean;
  manualSelect?: boolean;
  suggestion?: ISuggestion;
  dress?: IDress;
}

export const defaultValue: Readonly<IUsed> = {
  selected: false,
  manualSelect: false
};
