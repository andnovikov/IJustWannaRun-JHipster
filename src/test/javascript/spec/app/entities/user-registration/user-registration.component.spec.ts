import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IJustWannaRunTestModule } from '../../../test.module';
import { UserRegistrationComponent } from 'app/entities/user-registration/user-registration.component';
import { UserRegistrationService } from 'app/entities/user-registration/user-registration.service';
import { UserRegistration } from 'app/shared/model/user-registration.model';

describe('Component Tests', () => {
  describe('UserRegistration Management Component', () => {
    let comp: UserRegistrationComponent;
    let fixture: ComponentFixture<UserRegistrationComponent>;
    let service: UserRegistrationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IJustWannaRunTestModule],
        declarations: [UserRegistrationComponent],
        providers: []
      })
        .overrideTemplate(UserRegistrationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(UserRegistrationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(UserRegistrationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new UserRegistration('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.userRegistrations && comp.userRegistrations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
