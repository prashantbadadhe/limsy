import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './suggestion.reducer';
import { ISuggestion } from 'app/shared/model/suggestion.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISuggestionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class SuggestionDetail extends React.Component<ISuggestionDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { suggestionEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Suggestion [<b>{suggestionEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="date">Date</span>
            </dt>
            <dd>
              <TextFormat value={suggestionEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/suggestion" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/suggestion/${suggestionEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ suggestion }: IRootState) => ({
  suggestionEntity: suggestion.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SuggestionDetail);
