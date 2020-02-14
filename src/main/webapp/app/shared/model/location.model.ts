export interface ILocation {
  id?: number;
  area?: string;
  zip?: string;
  city?: string;
  state?: string;
  country?: string;
}

export const defaultValue: Readonly<ILocation> = {};
