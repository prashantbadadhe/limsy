import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Dress from './dress';
import DressDetail from './dress-detail';
import DressUpdate from './dress-update';
import DressDeleteDialog from './dress-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DressUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DressDetail} />
      <ErrorBoundaryRoute path={match.url} component={Dress} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={DressDeleteDialog} />
  </>
);

export default Routes;
