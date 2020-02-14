import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Suggestion from './suggestion';
import SuggestionDetail from './suggestion-detail';
import SuggestionUpdate from './suggestion-update';
import SuggestionDeleteDialog from './suggestion-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SuggestionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SuggestionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SuggestionDetail} />
      <ErrorBoundaryRoute path={match.url} component={Suggestion} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={SuggestionDeleteDialog} />
  </>
);

export default Routes;
