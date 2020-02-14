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

import { ISuggestion, defaultValue } from 'app/shared/model/suggestion.model';

export const ACTION_TYPES = {
  FETCH_SUGGESTION_LIST: 'suggestion/FETCH_SUGGESTION_LIST',
  FETCH_SUGGESTION: 'suggestion/FETCH_SUGGESTION',
  CREATE_SUGGESTION: 'suggestion/CREATE_SUGGESTION',
  UPDATE_SUGGESTION: 'suggestion/UPDATE_SUGGESTION',
  DELETE_SUGGESTION: 'suggestion/DELETE_SUGGESTION',
  RESET: 'suggestion/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISuggestion>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type SuggestionState = Readonly<typeof initialState>;

// Reducer

export default (state: SuggestionState = initialState, action): SuggestionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SUGGESTION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SUGGESTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SUGGESTION):
    case REQUEST(ACTION_TYPES.UPDATE_SUGGESTION):
    case REQUEST(ACTION_TYPES.DELETE_SUGGESTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SUGGESTION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SUGGESTION):
    case FAILURE(ACTION_TYPES.CREATE_SUGGESTION):
    case FAILURE(ACTION_TYPES.UPDATE_SUGGESTION):
    case FAILURE(ACTION_TYPES.DELETE_SUGGESTION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUGGESTION_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUGGESTION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SUGGESTION):
    case SUCCESS(ACTION_TYPES.UPDATE_SUGGESTION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SUGGESTION):
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

const apiUrl = 'api/suggestions';

// Actions

export const getEntities: ICrudGetAllAction<ISuggestion> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SUGGESTION_LIST,
    payload: axios.get<ISuggestion>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ISuggestion> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SUGGESTION,
    payload: axios.get<ISuggestion>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISuggestion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SUGGESTION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<ISuggestion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SUGGESTION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISuggestion> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SUGGESTION,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
