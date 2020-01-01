import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserRegistration } from 'app/shared/model/user-registration.model';

type EntityResponseType = HttpResponse<IUserRegistration>;
type EntityArrayResponseType = HttpResponse<IUserRegistration[]>;

@Injectable({ providedIn: 'root' })
export class UserRegistrationService {
  public resourceUrl = SERVER_API_URL + 'api/user-registrations';

  constructor(protected http: HttpClient) {}

  create(userRegistration: IUserRegistration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userRegistration);
    return this.http
      .post<IUserRegistration>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userRegistration: IUserRegistration): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userRegistration);
    return this.http
      .put<IUserRegistration>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IUserRegistration>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserRegistration[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userRegistration: IUserRegistration): IUserRegistration {
    const copy: IUserRegistration = Object.assign({}, userRegistration, {
      regDate: userRegistration.regDate && userRegistration.regDate.isValid() ? userRegistration.regDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.regDate = res.body.regDate ? moment(res.body.regDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userRegistration: IUserRegistration) => {
        userRegistration.regDate = userRegistration.regDate ? moment(userRegistration.regDate) : undefined;
      });
    }
    return res;
  }
}
