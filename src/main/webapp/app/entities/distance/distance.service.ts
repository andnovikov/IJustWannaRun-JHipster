import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDistance } from 'app/shared/model/distance.model';

type EntityResponseType = HttpResponse<IDistance>;
type EntityArrayResponseType = HttpResponse<IDistance[]>;

@Injectable({ providedIn: 'root' })
export class DistanceService {
  public resourceUrl = SERVER_API_URL + 'api/distances';

  constructor(protected http: HttpClient) {}

  create(distance: IDistance): Observable<EntityResponseType> {
    return this.http.post<IDistance>(this.resourceUrl, distance, { observe: 'response' });
  }

  update(distance: IDistance): Observable<EntityResponseType> {
    return this.http.put<IDistance>(this.resourceUrl, distance, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IDistance>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDistance[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
