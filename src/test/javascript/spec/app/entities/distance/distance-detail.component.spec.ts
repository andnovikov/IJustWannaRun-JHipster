import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { IJustWannaRunTestModule } from '../../../test.module';
import { DistanceDetailComponent } from 'app/entities/distance/distance-detail.component';
import { Distance } from 'app/shared/model/distance.model';

describe('Component Tests', () => {
  describe('Distance Management Detail Component', () => {
    let comp: DistanceDetailComponent;
    let fixture: ComponentFixture<DistanceDetailComponent>;
    const route = ({ data: of({ distance: new Distance('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IJustWannaRunTestModule],
        declarations: [DistanceDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DistanceDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DistanceDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load distance on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.distance).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
