import { Moment } from 'moment';
import { ICategory } from 'app/shared/model/category.model';

export interface IDress {
  id?: number;
  color?: string;
  purchaseDate?: Moment;
  price?: number;
  inUse?: boolean;
  imageContentType?: string;
  image?: any;
  category?: ICategory;
}

export const defaultValue: Readonly<IDress> = {
  inUse: false
};
