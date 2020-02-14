import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICategory } from 'app/shared/model/category.model';
import { getEntities as getCategories } from 'app/entities/category/category.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './dress.reducer';
import { IDress } from 'app/shared/model/dress.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDressUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IDressUpdateState {
  isNew: boolean;
  categoryId: string;
}

export class DressUpdate extends React.Component<IDressUpdateProps, IDressUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      categoryId: '0',
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

    this.props.getCategories();
  }

  onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => this.props.setBlob(name, data, contentType), isAnImage);
  };

  clearBlob = name => () => {
    this.props.setBlob(name, undefined, undefined);
  };

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { dressEntity } = this.props;
      const entity = {
        ...dressEntity,
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
    this.props.history.push('/entity/dress');
  };

  render() {
    const { dressEntity, categories, loading, updating } = this.props;
    const { isNew } = this.state;

    const { image, imageContentType } = dressEntity;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="limsyApp.dress.home.createOrEditLabel">Create or edit a Dress</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : dressEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="dress-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="colorLabel" for="color">
                    Color
                  </Label>
                  <AvField id="dress-color" type="text" name="color" />
                </AvGroup>
                <AvGroup>
                  <Label id="purchaseDateLabel" for="purchaseDate">
                    Purchase Date
                  </Label>
                  <AvField id="dress-purchaseDate" type="date" className="form-control" name="purchaseDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="price">
                    Price
                  </Label>
                  <AvField id="dress-price" type="string" className="form-control" name="price" />
                </AvGroup>
                <AvGroup>
                  <Label id="inUseLabel" check>
                    <AvInput id="dress-inUse" type="checkbox" className="form-control" name="inUse" />
                    In Use
                  </Label>
                </AvGroup>
                <AvGroup>
                  <AvGroup>
                    <Label id="imageLabel" for="image">
                      Image
                    </Label>
                    <br />
                    {image ? (
                      <div>
                        <a onClick={openFile(imageContentType, image)}>
                          <img src={`data:${imageContentType};base64,${image}`} style={{ maxHeight: '100px' }} />
                        </a>
                        <br />
                        <Row>
                          <Col md="11">
                            <span>
                              {imageContentType}, {byteSize(image)}
                            </span>
                          </Col>
                          <Col md="1">
                            <Button color="danger" onClick={this.clearBlob('image')}>
                              <FontAwesomeIcon icon="times-circle" />
                            </Button>
                          </Col>
                        </Row>
                      </div>
                    ) : null}
                    <input id="file_image" type="file" onChange={this.onBlobChange(true, 'image')} accept="image/*" />
                    <AvInput type="hidden" name="image" value={image} />
                  </AvGroup>
                </AvGroup>
                <AvGroup>
                  <Label for="category.name">Category</Label>
                  <AvInput id="dress-category" type="select" className="form-control" name="category.id">
                    <option value="" key="0" />
                    {categories
                      ? categories.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/dress" replace color="info">
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
  categories: storeState.category.entities,
  dressEntity: storeState.dress.entity,
  loading: storeState.dress.loading,
  updating: storeState.dress.updating,
  updateSuccess: storeState.dress.updateSuccess
});

const mapDispatchToProps = {
  getCategories,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DressUpdate);
