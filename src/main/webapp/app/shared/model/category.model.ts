import { ICategory } from 'app/shared/model/category.model';

export interface ICategory {
  id?: number;
  name?: string;
  parent?: ICategory;
}

export const defaultValue: Readonly<ICategory> = {};
