import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IJustWannaRunTestModule } from '../../../test.module';
import { UserRegistrationDetailComponent } from 'app/entities/user-registration/user-registration-detail.component';
import { UserRegistration } from 'app/shared/model/user-registration.model';

describe('Component Tests', () => {
  describe('UserRegistration Management Detail Component', () => {
    let comp: UserRegistrationDetailComponent;
    let fixture: ComponentFixture<UserRegistrationDetailComponent>;
    const route = ({ data: of({ userRegistration: new UserRegistration('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IJustWannaRunTestModule],
        declarations: [UserRegistrationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(UserRegistrationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserRegistrationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userRegistration on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userRegistration).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
