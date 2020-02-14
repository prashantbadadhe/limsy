import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import SelectedSuggestion from './selected-suggestion';
import SelectedSuggestionDetail from './selected-suggestion-detail';
import SelectedSuggestionUpdate from './selected-suggestion-update';
import SelectedSuggestionDeleteDialog from './selected-suggestion-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SelectedSuggestionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SelectedSuggestionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SelectedSuggestionDetail} />
      <ErrorBoundaryRoute path={match.url} component={SelectedSuggestion} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={SelectedSuggestionDeleteDialog} />
  </>
);

export default Routes;
