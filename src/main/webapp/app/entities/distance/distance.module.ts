import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { IJustWannaRunSharedModule } from 'app/shared/shared.module';
import { DistanceComponent } from './distance.component';
import { DistanceDetailComponent } from './distance-detail.component';
import { DistanceUpdateComponent } from './distance-update.component';
import { DistanceDeleteDialogComponent } from './distance-delete-dialog.component';
import { distanceRoute } from './distance.route';

@NgModule({
  imports: [IJustWannaRunSharedModule, RouterModule.forChild(distanceRoute)],
  declarations: [DistanceComponent, DistanceDetailComponent, DistanceUpdateComponent, DistanceDeleteDialogComponent],
  entryComponents: [DistanceDeleteDialogComponent]
})
export class IJustWannaRunDistanceModule {}
