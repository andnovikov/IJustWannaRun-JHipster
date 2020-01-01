import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { UserRegistrationService } from 'app/entities/user-registration/user-registration.service';
import { IUserRegistration, UserRegistration } from 'app/shared/model/user-registration.model';

describe('Service Tests', () => {
  describe('UserRegistration Service', () => {
    let injector: TestBed;
    let service: UserRegistrationService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserRegistration;
    let expectedResult: IUserRegistration | IUserRegistration[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserRegistrationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new UserRegistration('ID', currentDate, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            regDate: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a UserRegistration', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            regDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            regDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new UserRegistration())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserRegistration', () => {
        const returnedFromService = Object.assign(
          {
            regDate: currentDate.format(DATE_TIME_FORMAT),
            regNumber: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            regDate: currentDate
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

      it('should return a list of UserRegistration', () => {
        const returnedFromService = Object.assign(
          {
            regDate: currentDate.format(DATE_TIME_FORMAT),
            regNumber: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            regDate: currentDate
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

      it('should delete a UserRegistration', () => {
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
