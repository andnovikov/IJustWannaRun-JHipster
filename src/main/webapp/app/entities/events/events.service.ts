import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEvents } from 'app/shared/model/events.model';

type EntityResponseType = HttpResponse<IEvents>;
type EntityArrayResponseType = HttpResponse<IEvents[]>;

@Injectable({ providedIn: 'root' })
export class EventsService {
  public resourceUrl = SERVER_API_URL + 'api/events';

  constructor(protected http: HttpClient) {}

  create(events: IEvents): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(events);
    return this.http
      .post<IEvents>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(events: IEvents): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(events);
    return this.http
      .put<IEvents>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IEvents>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEvents[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(events: IEvents): IEvents {
    const copy: IEvents = Object.assign({}, events, {
      date: events.date && events.date.isValid() ? events.date.toJSON() : undefined,
      regStart: events.regStart && events.regStart.isValid() ? events.regStart.toJSON() : undefined,
      regEnd: events.regEnd && events.regEnd.isValid() ? events.regEnd.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
      res.body.regStart = res.body.regStart ? moment(res.body.regStart) : undefined;
      res.body.regEnd = res.body.regEnd ? moment(res.body.regEnd) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((events: IEvents) => {
        events.date = events.date ? moment(events.date) : undefined;
        events.regStart = events.regStart ? moment(events.regStart) : undefined;
        events.regEnd = events.regEnd ? moment(events.regEnd) : undefined;
      });
    }
    return res;
  }
}
