import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './dress.reducer';
import { IDress } from 'app/shared/model/dress.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDressDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DressDetail extends React.Component<IDressDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { dressEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Dress [<b>{dressEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="color">Color</span>
            </dt>
            <dd>{dressEntity.color}</dd>
            <dt>
              <span id="purchaseDate">Purchase Date</span>
            </dt>
            <dd>
              <TextFormat value={dressEntity.purchaseDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="price">Price</span>
            </dt>
            <dd>{dressEntity.price}</dd>
            <dt>
              <span id="inUse">In Use</span>
            </dt>
            <dd>{dressEntity.inUse ? 'true' : 'false'}</dd>
            <dt>
              <span id="image">Image</span>
            </dt>
            <dd>
              {dressEntity.image ? (
                <div>
                  <a onClick={openFile(dressEntity.imageContentType, dressEntity.image)}>
                    <img src={`data:${dressEntity.imageContentType};base64,${dressEntity.image}`} style={{ maxHeight: '30px' }} />
                  </a>
                  <span>
                    {dressEntity.imageContentType}, {byteSize(dressEntity.image)}
                  </span>
                </div>
              ) : null}
            </dd>
            <dt>Category</dt>
            <dd>{dressEntity.category ? dressEntity.category.name : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/dress" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/dress/${dressEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ dress }: IRootState) => ({
  dressEntity: dress.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DressDetail);
