import { Moment } from 'moment';
import { ILocation } from 'app/shared/model/location.model';
import { IDistance } from 'app/shared/model/distance.model';
import { EventStatus } from 'app/shared/model/enumerations/event-status.model';
import { EventKind } from 'app/shared/model/enumerations/event-kind.model';

export interface IEvents {
  id?: string;
  name?: string;
  date?: Moment;
  regStart?: Moment;
  regEnd?: Moment;
  status?: EventStatus;
  kind?: EventKind;
  url?: string;
  locations?: ILocation[];
  distances?: IDistance;
}

export class Events implements IEvents {
  constructor(
    public id?: string,
    public name?: string,
    public date?: Moment,
    public regStart?: Moment,
    public regEnd?: Moment,
    public status?: EventStatus,
    public kind?: EventKind,
    public url?: string,
    public locations?: ILocation[],
    public distances?: IDistance
  ) {}
}
