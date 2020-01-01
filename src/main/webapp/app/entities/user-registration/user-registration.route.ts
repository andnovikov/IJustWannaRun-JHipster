import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IUserRegistration, UserRegistration } from 'app/shared/model/user-registration.model';
import { UserRegistrationService } from './user-registration.service';
import { UserRegistrationComponent } from './user-registration.component';
import { UserRegistrationDetailComponent } from './user-registration-detail.component';
import { UserRegistrationUpdateComponent } from './user-registration-update.component';

@Injectable({ providedIn: 'root' })
export class UserRegistrationResolve implements Resolve<IUserRegistration> {
  constructor(private service: UserRegistrationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IUserRegistration> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((userRegistration: HttpResponse<UserRegistration>) => {
          if (userRegistration.body) {
            return of(userRegistration.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new UserRegistration());
  }
}

export const userRegistrationRoute: Routes = [
  {
    path: '',
    component: UserRegistrationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'iJustWannaRunApp.userRegistration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserRegistrationDetailComponent,
    resolve: {
      userRegistration: UserRegistrationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'iJustWannaRunApp.userRegistration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserRegistrationUpdateComponent,
    resolve: {
      userRegistration: UserRegistrationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'iJustWannaRunApp.userRegistration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserRegistrationUpdateComponent,
    resolve: {
      userRegistration: UserRegistrationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'iJustWannaRunApp.userRegistration.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
