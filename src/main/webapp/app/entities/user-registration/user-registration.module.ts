import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IJustWannaRunSharedModule } from 'app/shared/shared.module';
import { UserRegistrationComponent } from './user-registration.component';
import { UserRegistrationDetailComponent } from './user-registration-detail.component';
import { UserRegistrationUpdateComponent } from './user-registration-update.component';
import { UserRegistrationDeleteDialogComponent } from './user-registration-delete-dialog.component';
import { userRegistrationRoute } from './user-registration.route';

@NgModule({
  imports: [IJustWannaRunSharedModule, RouterModule.forChild(userRegistrationRoute)],
  declarations: [
    UserRegistrationComponent,
    UserRegistrationDetailComponent,
    UserRegistrationUpdateComponent,
    UserRegistrationDeleteDialogComponent
  ],
  entryComponents: [UserRegistrationDeleteDialogComponent]
})
export class IJustWannaRunUserRegistrationModule {}
