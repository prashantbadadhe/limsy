import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import location, {
  LocationState
} from 'app/entities/location/location.reducer';
// prettier-ignore
import category, {
  CategoryState
} from 'app/entities/category/category.reducer';
// prettier-ignore
import dress, {
  DressState
} from 'app/entities/dress/dress.reducer';
// prettier-ignore
import suggestion, {
  SuggestionState
} from 'app/entities/suggestion/suggestion.reducer';
// prettier-ignore
import selectedSuggestion, {
  SelectedSuggestionState
} from 'app/entities/selected-suggestion/selected-suggestion.reducer';
// prettier-ignore
import userAdditional, {
  UserAdditionalState
} from 'app/entities/user-additional/user-additional.reducer';
// prettier-ignore
import used, {
  UsedState
} from 'app/entities/used/used.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly location: LocationState;
  readonly category: CategoryState;
  readonly dress: DressState;
  readonly suggestion: SuggestionState;
  readonly selectedSuggestion: SelectedSuggestionState;
  readonly userAdditional: UserAdditionalState;
  readonly used: UsedState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  location,
  category,
  dress,
  suggestion,
  selectedSuggestion,
  userAdditional,
  used,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
