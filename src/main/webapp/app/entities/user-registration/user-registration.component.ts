import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUserRegistration } from 'app/shared/model/user-registration.model';
import { UserRegistrationService } from './user-registration.service';
import { UserRegistrationDeleteDialogComponent } from './user-registration-delete-dialog.component';

@Component({
  selector: 'jhi-user-registration',
  templateUrl: './user-registration.component.html'
})
export class UserRegistrationComponent implements OnInit, OnDestroy {
  userRegistrations?: IUserRegistration[];
  eventSubscriber?: Subscription;

  constructor(
    protected userRegistrationService: UserRegistrationService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.userRegistrationService.query().subscribe((res: HttpResponse<IUserRegistration[]>) => {
      this.userRegistrations = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUserRegistrations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUserRegistration): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUserRegistrations(): void {
    this.eventSubscriber = this.eventManager.subscribe('userRegistrationListModification', () => this.loadAll());
  }

  delete(userRegistration: IUserRegistration): void {
    const modalRef = this.modalService.open(UserRegistrationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.userRegistration = userRegistration;
  }
}
