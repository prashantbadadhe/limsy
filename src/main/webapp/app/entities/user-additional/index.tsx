import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserAdditional from './user-additional';
import UserAdditionalDetail from './user-additional-detail';
import UserAdditionalUpdate from './user-additional-update';
import UserAdditionalDeleteDialog from './user-additional-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserAdditionalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserAdditionalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserAdditionalDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserAdditional} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UserAdditionalDeleteDialog} />
  </>
);

export default Routes;
