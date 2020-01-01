import { Moment } from 'moment';
import { IDistance } from 'app/shared/model/distance.model';

export interface IUserRegistration {
  id?: string;
  regDate?: Moment;
  regNumber?: number;
  events?: IDistance[];
}

export class UserRegistration implements IUserRegistration {
  constructor(public id?: string, public regDate?: Moment, public regNumber?: number, public events?: IDistance[]) {}
}
