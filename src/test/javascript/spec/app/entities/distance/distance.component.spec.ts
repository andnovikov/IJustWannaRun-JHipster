import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { IJustWannaRunTestModule } from '../../../test.module';
import { DistanceComponent } from 'app/entities/distance/distance.component';
import { DistanceService } from 'app/entities/distance/distance.service';
import { Distance } from 'app/shared/model/distance.model';

describe('Component Tests', () => {
  describe('Distance Management Component', () => {
    let comp: DistanceComponent;
    let fixture: ComponentFixture<DistanceComponent>;
    let service: DistanceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [IJustWannaRunTestModule],
        declarations: [DistanceComponent],
        providers: []
      })
        .overrideTemplate(DistanceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DistanceComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DistanceService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Distance('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.distances && comp.distances[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
