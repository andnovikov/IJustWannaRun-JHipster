import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EventsService } from 'app/entities/events/events.service';
import { IEvents, Events } from 'app/shared/model/events.model';
import { EventStatus } from 'app/shared/model/enumerations/event-status.model';
import { EventKind } from 'app/shared/model/enumerations/event-kind.model';

describe('Service Tests', () => {
  describe('Events Service', () => {
    let injector: TestBed;
    let service: EventsService;
    let httpMock: HttpTestingController;
    let elemDefault: IEvents;
    let expectedResult: IEvents | IEvents[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EventsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Events('ID', 'AAAAAAA', currentDate, currentDate, currentDate, EventStatus.PLAN, EventKind.RUNNING, 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            date: currentDate.format(DATE_TIME_FORMAT),
            regStart: currentDate.format(DATE_TIME_FORMAT),
            regEnd: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find('123')
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Events', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            date: currentDate.format(DATE_TIME_FORMAT),
            regStart: currentDate.format(DATE_TIME_FORMAT),
            regEnd: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate,
            regStart: currentDate,
            regEnd: currentDate
          },
          returnedFromService
        );
        service
          .create(new Events())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Events', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            date: currentDate.format(DATE_TIME_FORMAT),
            regStart: currentDate.format(DATE_TIME_FORMAT),
            regEnd: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            kind: 'BBBBBB',
            url: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            date: currentDate,
            regStart: currentDate,
            regEnd: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Events', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            date: currentDate.format(DATE_TIME_FORMAT),
            regStart: currentDate.format(DATE_TIME_FORMAT),
            regEnd: currentDate.format(DATE_TIME_FORMAT),
            status: 'BBBBBB',
            kind: 'BBBBBB',
            url: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            date: currentDate,
            regStart: currentDate,
            regEnd: currentDate
          },
          returnedFromService
        );
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Events', () => {
        service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
