import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISuggestion } from 'app/shared/model/suggestion.model';
import { getEntities as getSuggestions } from 'app/entities/suggestion/suggestion.reducer';
import { IDress } from 'app/shared/model/dress.model';
import { getEntities as getDresses } from 'app/entities/dress/dress.reducer';
import { getEntity, updateEntity, createEntity, reset } from './used.reducer';
import { IUsed } from 'app/shared/model/used.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUsedUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUsedUpdateState {
  isNew: boolean;
  suggestionId: string;
  dressId: string;
}

export class UsedUpdate extends React.Component<IUsedUpdateProps, IUsedUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      suggestionId: '0',
      dressId: '0',
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

    this.props.getSuggestions();
    this.props.getDresses();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { usedEntity } = this.props;
      const entity = {
        ...usedEntity,
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
    this.props.history.push('/entity/used');
  };

  render() {
    const { usedEntity, suggestions, dresses, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="limsyApp.used.home.createOrEditLabel">Create or edit a Used</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : usedEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="used-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="selectedLabel" check>
                    <AvInput id="used-selected" type="checkbox" className="form-control" name="selected" />
                    Selected
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="manualSelectLabel" check>
                    <AvInput id="used-manualSelect" type="checkbox" className="form-control" name="manualSelect" />
                    Manual Select
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="suggestion.date">Suggestion</Label>
                  <AvInput id="used-suggestion" type="select" className="form-control" name="suggestion.id">
                    <option value="" key="0" />
                    {suggestions
                      ? suggestions.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.date}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="dress.image">Dress</Label>
                  <AvInput id="used-dress" type="select" className="form-control" name="dress.id">
                    <option value="" key="0" />
                    {dresses
                      ? dresses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.image}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/used" replace color="info">
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
  suggestions: storeState.suggestion.entities,
  dresses: storeState.dress.entities,
  usedEntity: storeState.used.entity,
  loading: storeState.used.loading,
  updating: storeState.used.updating,
  updateSuccess: storeState.used.updateSuccess
});

const mapDispatchToProps = {
  getSuggestions,
  getDresses,
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
)(UsedUpdate);
