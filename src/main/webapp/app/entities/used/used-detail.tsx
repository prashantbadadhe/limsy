import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './used.reducer';
import { IUsed } from 'app/shared/model/used.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUsedDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UsedDetail extends React.Component<IUsedDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { usedEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Used [<b>{usedEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="selected">Selected</span>
            </dt>
            <dd>{usedEntity.selected ? 'true' : 'false'}</dd>
            <dt>
              <span id="manualSelect">Manual Select</span>
            </dt>
            <dd>{usedEntity.manualSelect ? 'true' : 'false'}</dd>
            <dt>Suggestion</dt>
            <dd>{usedEntity.suggestion ? usedEntity.suggestion.date : ''}</dd>
            <dt>Dress</dt>
            <dd>{usedEntity.dress ? usedEntity.dress.image : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/used" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/used/${usedEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ used }: IRootState) => ({
  usedEntity: used.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UsedDetail);
