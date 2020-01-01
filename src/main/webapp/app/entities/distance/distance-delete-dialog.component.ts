import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDistance } from 'app/shared/model/distance.model';
import { DistanceService } from './distance.service';

@Component({
  templateUrl: './distance-delete-dialog.component.html'
})
export class DistanceDeleteDialogComponent {
  distance?: IDistance;

  constructor(protected distanceService: DistanceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.distanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('distanceListModification');
      this.activeModal.close();
    });
  }
}
