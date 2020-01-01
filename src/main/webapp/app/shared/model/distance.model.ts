import { IEvents } from 'app/shared/model/events.model';
import { IUserRegistration } from 'app/shared/model/user-registration.model';

export interface IDistance {
  id?: string;
  length?: number;
  limit?: number;
  events?: IEvents[];
  userRegistration?: IUserRegistration;
}

export class Distance implements IDistance {
  constructor(
    public id?: string,
    public length?: number,
    public limit?: number,
    public events?: IEvents[],
    public userRegistration?: IUserRegistration
  ) {}
}
