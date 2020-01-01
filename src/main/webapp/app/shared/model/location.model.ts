import { ICountry } from 'app/shared/model/country.model';
import { IEvents } from 'app/shared/model/events.model';

export interface ILocation {
  id?: string;
  streetAddress?: string;
  postalCode?: string;
  city?: string;
  stateProvince?: string;
  country?: ICountry;
  events?: IEvents;
}

export class Location implements ILocation {
  constructor(
    public id?: string,
    public streetAddress?: string,
    public postalCode?: string,
    public city?: string,
    public stateProvince?: string,
    public country?: ICountry,
    public events?: IEvents
  ) {}
}
