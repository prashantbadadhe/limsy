import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-additional.reducer';
import { IUserAdditional } from 'app/shared/model/user-additional.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserAdditionalDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UserAdditionalDetail extends React.Component<IUserAdditionalDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { userAdditionalEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            UserAdditional [<b>{userAdditionalEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="gender">Gender</span>
            </dt>
            <dd>{userAdditionalEntity.gender}</dd>
            <dt>
              <span id="birthDate">Birth Date</span>
            </dt>
            <dd>
              <TextFormat value={userAdditionalEntity.birthDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="phone">Phone</span>
            </dt>
            <dd>{userAdditionalEntity.phone}</dd>
            <dt>
              <span id="email">Email</span>
            </dt>
            <dd>{userAdditionalEntity.email}</dd>
            <dt>Address</dt>
            <dd>{userAdditionalEntity.address ? userAdditionalEntity.address.area : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/user-additional" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/user-additional/${userAdditionalEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ userAdditional }: IRootState) => ({
  userAdditionalEntity: userAdditional.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserAdditionalDetail);
