import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './selected-suggestion.reducer';
import { ISelectedSuggestion } from 'app/shared/model/selected-suggestion.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISelectedSuggestionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class SelectedSuggestionDetail extends React.Component<ISelectedSuggestionDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { selectedSuggestionEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            SelectedSuggestion [<b>{selectedSuggestionEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="selected">Selected</span>
            </dt>
            <dd>{selectedSuggestionEntity.selected ? 'true' : 'false'}</dd>
            <dt>
              <span id="manualSelect">Manual Select</span>
            </dt>
            <dd>{selectedSuggestionEntity.manualSelect ? 'true' : 'false'}</dd>
            <dt>Suggestion</dt>
            <dd>{selectedSuggestionEntity.suggestion ? selectedSuggestionEntity.suggestion.id : ''}</dd>
            <dt>Dress</dt>
            <dd>{selectedSuggestionEntity.dress ? selectedSuggestionEntity.dress.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/selected-suggestion" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/selected-suggestion/${selectedSuggestionEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ selectedSuggestion }: IRootState) => ({
  selectedSuggestionEntity: selectedSuggestion.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SelectedSuggestionDetail);
