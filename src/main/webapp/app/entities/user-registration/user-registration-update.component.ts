import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IUserRegistration, UserRegistration } from 'app/shared/model/user-registration.model';
import { UserRegistrationService } from './user-registration.service';

@Component({
  selector: 'jhi-user-registration-update',
  templateUrl: './user-registration-update.component.html'
})
export class UserRegistrationUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    regDate: [],
    regNumber: []
  });

  constructor(
    protected userRegistrationService: UserRegistrationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userRegistration }) => {
      this.updateForm(userRegistration);
    });
  }

  updateForm(userRegistration: IUserRegistration): void {
    this.editForm.patchValue({
      id: userRegistration.id,
      regDate: userRegistration.regDate != null ? userRegistration.regDate.format(DATE_TIME_FORMAT) : null,
      regNumber: userRegistration.regNumber
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userRegistration = this.createFromForm();
    if (userRegistration.id !== undefined) {
      this.subscribeToSaveResponse(this.userRegistrationService.update(userRegistration));
    } else {
      this.subscribeToSaveResponse(this.userRegistrationService.create(userRegistration));
    }
  }

  private createFromForm(): IUserRegistration {
    return {
      ...new UserRegistration(),
      id: this.editForm.get(['id'])!.value,
      regDate: this.editForm.get(['regDate'])!.value != null ? moment(this.editForm.get(['regDate'])!.value, DATE_TIME_FORMAT) : undefined,
      regNumber: this.editForm.get(['regNumber'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserRegistration>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
