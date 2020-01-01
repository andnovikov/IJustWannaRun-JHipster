import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'events',
        loadChildren: () => import('./events/events.module').then(m => m.IJustWannaRunEventsModule)
      },
      {
        path: 'distance',
        loadChildren: () => import('./distance/distance.module').then(m => m.IJustWannaRunDistanceModule)
      },
      {
        path: 'user-registration',
        loadChildren: () => import('./user-registration/user-registration.module').then(m => m.IJustWannaRunUserRegistrationModule)
      },
      {
        path: 'region',
        loadChildren: () => import('./region/region.module').then(m => m.IJustWannaRunRegionModule)
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.IJustWannaRunCountryModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.IJustWannaRunLocationModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class IJustWannaRunEntityModule {}
