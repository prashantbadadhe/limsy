import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDress, defaultValue } from 'app/shared/model/dress.model';

export const ACTION_TYPES = {
  FETCH_DRESS_LIST: 'dress/FETCH_DRESS_LIST',
  FETCH_DRESS: 'dress/FETCH_DRESS',
  CREATE_DRESS: 'dress/CREATE_DRESS',
  UPDATE_DRESS: 'dress/UPDATE_DRESS',
  DELETE_DRESS: 'dress/DELETE_DRESS',
  SET_BLOB: 'dress/SET_BLOB',
  RESET: 'dress/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDress>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type DressState = Readonly<typeof initialState>;

// Reducer

export default (state: DressState = initialState, action): DressState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DRESS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DRESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DRESS):
    case REQUEST(ACTION_TYPES.UPDATE_DRESS):
    case REQUEST(ACTION_TYPES.DELETE_DRESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_DRESS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DRESS):
    case FAILURE(ACTION_TYPES.CREATE_DRESS):
    case FAILURE(ACTION_TYPES.UPDATE_DRESS):
    case FAILURE(ACTION_TYPES.DELETE_DRESS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_DRESS_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_DRESS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DRESS):
    case SUCCESS(ACTION_TYPES.UPDATE_DRESS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DRESS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/dresses';

// Actions

export const getEntities: ICrudGetAllAction<IDress> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DRESS_LIST,
    payload: axios.get<IDress>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IDress> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DRESS,
    payload: axios.get<IDress>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDress> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DRESS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IDress> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DRESS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDress> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DRESS,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
