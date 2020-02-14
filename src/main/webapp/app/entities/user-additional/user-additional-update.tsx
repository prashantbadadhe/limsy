import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { getEntity, updateEntity, createEntity, reset } from './user-additional.reducer';
import { IUserAdditional } from 'app/shared/model/user-additional.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserAdditionalUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUserAdditionalUpdateState {
  isNew: boolean;
  addressId: string;
}

export class UserAdditionalUpdate extends React.Component<IUserAdditionalUpdateProps, IUserAdditionalUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      addressId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getLocations();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { userAdditionalEntity } = this.props;
      const entity = {
        ...userAdditionalEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/user-additional');
  };

  render() {
    const { userAdditionalEntity, locations, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="limsyApp.userAdditional.home.createOrEditLabel">Create or edit a UserAdditional</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : userAdditionalEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="user-additional-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="genderLabel" for="gender">
                    Gender
                  </Label>
                  <AvField id="user-additional-gender" type="text" name="gender" />
                </AvGroup>
                <AvGroup>
                  <Label id="birthDateLabel" for="birthDate">
                    Birth Date
                  </Label>
                  <AvField id="user-additional-birthDate" type="date" className="form-control" name="birthDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="phoneLabel" for="phone">
                    Phone
                  </Label>
                  <AvField id="user-additional-phone" type="text" name="phone" />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="email">
                    Email
                  </Label>
                  <AvField id="user-additional-email" type="text" name="email" />
                </AvGroup>
                <AvGroup>
                  <Label for="address.area">Address</Label>
                  <AvInput id="user-additional-address" type="select" className="form-control" name="address.id">
                    <option value="" key="0" />
                    {locations
                      ? locations.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.area}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/user-additional" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  locations: storeState.location.entities,
  userAdditionalEntity: storeState.userAdditional.entity,
  loading: storeState.userAdditional.loading,
  updating: storeState.userAdditional.updating,
  updateSuccess: storeState.userAdditional.updateSuccess
});

const mapDispatchToProps = {
  getLocations,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserAdditionalUpdate);
