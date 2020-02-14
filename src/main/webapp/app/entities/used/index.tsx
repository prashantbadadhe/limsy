import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Used from './used';
import UsedDetail from './used-detail';
import UsedUpdate from './used-update';
import UsedDeleteDialog from './used-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UsedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UsedUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UsedDetail} />
      <ErrorBoundaryRoute path={match.url} component={Used} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UsedDeleteDialog} />
  </>
);

export default Routes;
