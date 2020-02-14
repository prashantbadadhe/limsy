import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Location from './location';
import Category from './category';
import Dress from './dress';
import Suggestion from './suggestion';
import SelectedSuggestion from './selected-suggestion';
import UserAdditional from './user-additional';
import Used from './used';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/location`} component={Location} />
      <ErrorBoundaryRoute path={`${match.url}/category`} component={Category} />
      <ErrorBoundaryRoute path={`${match.url}/dress`} component={Dress} />
      <ErrorBoundaryRoute path={`${match.url}/suggestion`} component={Suggestion} />
      <ErrorBoundaryRoute path={`${match.url}/selected-suggestion`} component={SelectedSuggestion} />
      <ErrorBoundaryRoute path={`${match.url}/user-additional`} component={UserAdditional} />
      <ErrorBoundaryRoute path={`${match.url}/used`} component={Used} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
