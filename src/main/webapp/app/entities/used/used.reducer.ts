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

import { IUsed, defaultValue } from 'app/shared/model/used.model';

export const ACTION_TYPES = {
  FETCH_USED_LIST: 'used/FETCH_USED_LIST',
  FETCH_USED: 'used/FETCH_USED',
  CREATE_USED: 'used/CREATE_USED',
  UPDATE_USED: 'used/UPDATE_USED',
  DELETE_USED: 'used/DELETE_USED',
  RESET: 'used/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUsed>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type UsedState = Readonly<typeof initialState>;

// Reducer

export default (state: UsedState = initialState, action): UsedState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USED_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_USED):
    case REQUEST(ACTION_TYPES.UPDATE_USED):
    case REQUEST(ACTION_TYPES.DELETE_USED):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_USED_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USED):
    case FAILURE(ACTION_TYPES.CREATE_USED):
    case FAILURE(ACTION_TYPES.UPDATE_USED):
    case FAILURE(ACTION_TYPES.DELETE_USED):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_USED_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_USED):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_USED):
    case SUCCESS(ACTION_TYPES.UPDATE_USED):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_USED):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/useds';

// Actions

export const getEntities: ICrudGetAllAction<IUsed> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_USED_LIST,
    payload: axios.get<IUsed>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IUsed> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USED,
    payload: axios.get<IUsed>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUsed> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USED,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IUsed> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USED,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUsed> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USED,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
