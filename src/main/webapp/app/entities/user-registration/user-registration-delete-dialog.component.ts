import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserRegistration } from 'app/shared/model/user-registration.model';
import { UserRegistrationService } from './user-registration.service';

@Component({
  templateUrl: './user-registration-delete-dialog.component.html'
})
export class UserRegistrationDeleteDialogComponent {
  userRegistration?: IUserRegistration;

  constructor(
    protected userRegistrationService: UserRegistrationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.userRegistrationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userRegistrationListModification');
      this.activeModal.close();
    });
  }
}
