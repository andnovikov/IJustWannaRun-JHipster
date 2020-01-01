import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDistance, Distance } from 'app/shared/model/distance.model';
import { DistanceService } from './distance.service';
import { DistanceComponent } from './distance.component';
import { DistanceDetailComponent } from './distance-detail.component';
import { DistanceUpdateComponent } from './distance-update.component';

@Injectable({ providedIn: 'root' })
export class DistanceResolve implements Resolve<IDistance> {
  constructor(private service: DistanceService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDistance> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((distance: HttpResponse<Distance>) => {
          if (distance.body) {
            return of(distance.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Distance());
  }
}

export const distanceRoute: Routes = [
  {
    path: '',
    component: DistanceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'iJustWannaRunApp.distance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DistanceDetailComponent,
    resolve: {
      distance: DistanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'iJustWannaRunApp.distance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DistanceUpdateComponent,
    resolve: {
      distance: DistanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'iJustWannaRunApp.distance.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DistanceUpdateComponent,
    resolve: {
      distance: DistanceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'iJustWannaRunApp.distance.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
