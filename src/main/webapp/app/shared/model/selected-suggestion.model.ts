import { ISuggestion } from 'app/shared/model/suggestion.model';
import { IDress } from 'app/shared/model/dress.model';

export interface ISelectedSuggestion {
  id?: number;
  selected?: boolean;
  manualSelect?: boolean;
  suggestion?: ISuggestion;
  dress?: IDress;
}

export const defaultValue: Readonly<ISelectedSuggestion> = {
  selected: false,
  manualSelect: false
};
