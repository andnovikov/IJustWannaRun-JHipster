import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDistance } from 'app/shared/model/distance.model';
import { DistanceService } from './distance.service';
import { DistanceDeleteDialogComponent } from './distance-delete-dialog.component';

@Component({
  selector: 'jhi-distance',
  templateUrl: './distance.component.html'
})
export class DistanceComponent implements OnInit, OnDestroy {
  distances?: IDistance[];
  eventSubscriber?: Subscription;

  constructor(protected distanceService: DistanceService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.distanceService.query().subscribe((res: HttpResponse<IDistance[]>) => {
      this.distances = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDistances();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDistance): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDistances(): void {
    this.eventSubscriber = this.eventManager.subscribe('distanceListModification', () => this.loadAll());
  }

  delete(distance: IDistance): void {
    const modalRef = this.modalService.open(DistanceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.distance = distance;
  }
}
