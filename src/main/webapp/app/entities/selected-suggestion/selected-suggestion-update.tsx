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
import { getEntity, updateEntity, createEntity, reset } from './selected-suggestion.reducer';
import { ISelectedSuggestion } from 'app/shared/model/selected-suggestion.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISelectedSuggestionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ISelectedSuggestionUpdateState {
  isNew: boolean;
  suggestionId: string;
  dressId: string;
}

export class SelectedSuggestionUpdate extends React.Component<ISelectedSuggestionUpdateProps, ISelectedSuggestionUpdateState> {
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
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getSuggestions();
    this.props.getDresses();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { selectedSuggestionEntity } = this.props;
      const entity = {
        ...selectedSuggestionEntity,
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
    this.props.history.push('/entity/selected-suggestion');
  };

  render() {
    const { selectedSuggestionEntity, suggestions, dresses, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="limsyApp.selectedSuggestion.home.createOrEditLabel">Create or edit a SelectedSuggestion</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : selectedSuggestionEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="selected-suggestion-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="selectedLabel" check>
                    <AvInput id="selected-suggestion-selected" type="checkbox" className="form-control" name="selected" />
                    Selected
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="manualSelectLabel" check>
                    <AvInput id="selected-suggestion-manualSelect" type="checkbox" className="form-control" name="manualSelect" />
                    Manual Select
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="suggestion.id">Suggestion</Label>
                  <AvInput id="selected-suggestion-suggestion" type="select" className="form-control" name="suggestion.id">
                    <option value="" key="0" />
                    {suggestions
                      ? suggestions.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="dress.id">Dress</Label>
                  <AvInput id="selected-suggestion-dress" type="select" className="form-control" name="dress.id">
                    <option value="" key="0" />
                    {dresses
                      ? dresses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/selected-suggestion" replace color="info">
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
  selectedSuggestionEntity: storeState.selectedSuggestion.entity,
  loading: storeState.selectedSuggestion.loading,
  updating: storeState.selectedSuggestion.updating,
  updateSuccess: storeState.selectedSuggestion.updateSuccess
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
)(SelectedSuggestionUpdate);
