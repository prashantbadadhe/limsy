import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISelectedSuggestion, defaultValue } from 'app/shared/model/selected-suggestion.model';

export const ACTION_TYPES = {
  FETCH_SELECTEDSUGGESTION_LIST: 'selectedSuggestion/FETCH_SELECTEDSUGGESTION_LIST',
  FETCH_SELECTEDSUGGESTION: 'selectedSuggestion/FETCH_SELECTEDSUGGESTION',
  CREATE_SELECTEDSUGGESTION: 'selectedSuggestion/CREATE_SELECTEDSUGGESTION',
  UPDATE_SELECTEDSUGGESTION: 'selectedSuggestion/UPDATE_SELECTEDSUGGESTION',
  DELETE_SELECTEDSUGGESTION: 'selectedSuggestion/DELETE_SELECTEDSUGGESTION',
  RESET: 'selectedSuggestion/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISelectedSuggestion>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type SelectedSuggestionState = Readonly<typeof initialState>;

// Reducer

export default (state: SelectedSuggestionState = initialState, action): SelectedSuggestionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SELECTEDSUGGESTION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SELECTEDSUGGESTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SELECTEDSUGGESTION):
    case REQUEST(ACTION_TYPES.UPDATE_SELECTEDSUGGESTION):
    case REQUEST(ACTION_TYPES.DELETE_SELECTEDSUGGESTION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SELECTEDSUGGESTION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SELECTEDSUGGESTION):
    case FAILURE(ACTION_TYPES.CREATE_SELECTEDSUGGESTION):
    case FAILURE(ACTION_TYPES.UPDATE_SELECTEDSUGGESTION):
    case FAILURE(ACTION_TYPES.DELETE_SELECTEDSUGGESTION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SELECTEDSUGGESTION_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SELECTEDSUGGESTION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SELECTEDSUGGESTION):
    case SUCCESS(ACTION_TYPES.UPDATE_SELECTEDSUGGESTION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SELECTEDSUGGESTION):
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

const apiUrl = 'api/selected-suggestions';

// Actions

export const getEntities: ICrudGetAllAction<ISelectedSuggestion> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SELECTEDSUGGESTION_LIST,
    payload: axios.get<ISelectedSuggestion>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ISelectedSuggestion> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SELECTEDSUGGESTION,
    payload: axios.get<ISelectedSuggestion>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISelectedSuggestion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SELECTEDSUGGESTION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISelectedSuggestion> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SELECTEDSUGGESTION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISelectedSuggestion> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SELECTEDSUGGESTION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
