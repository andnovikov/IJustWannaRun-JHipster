import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDistance, Distance } from 'app/shared/model/distance.model';
import { DistanceService } from './distance.service';
import { IUserRegistration } from 'app/shared/model/user-registration.model';
import { UserRegistrationService } from 'app/entities/user-registration/user-registration.service';

@Component({
  selector: 'jhi-distance-update',
  templateUrl: './distance-update.component.html'
})
export class DistanceUpdateComponent implements OnInit {
  isSaving = false;

  userregistrations: IUserRegistration[] = [];

  editForm = this.fb.group({
    id: [],
    length: [],
    limit: [],
    userRegistration: []
  });

  constructor(
    protected distanceService: DistanceService,
    protected userRegistrationService: UserRegistrationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ distance }) => {
      this.updateForm(distance);

      this.userRegistrationService
        .query()
        .pipe(
          map((res: HttpResponse<IUserRegistration[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUserRegistration[]) => (this.userregistrations = resBody));
    });
  }

  updateForm(distance: IDistance): void {
    this.editForm.patchValue({
      id: distance.id,
      length: distance.length,
      limit: distance.limit,
      userRegistration: distance.userRegistration
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const distance = this.createFromForm();
    if (distance.id !== undefined) {
      this.subscribeToSaveResponse(this.distanceService.update(distance));
    } else {
      this.subscribeToSaveResponse(this.distanceService.create(distance));
    }
  }

  private createFromForm(): IDistance {
    return {
      ...new Distance(),
      id: this.editForm.get(['id'])!.value,
      length: this.editForm.get(['length'])!.value,
      limit: this.editForm.get(['limit'])!.value,
      userRegistration: this.editForm.get(['userRegistration'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDistance>>): void {
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

  trackById(index: number, item: IUserRegistration): any {
    return item.id;
  }
}
