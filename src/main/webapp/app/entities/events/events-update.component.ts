import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IEvents, Events } from 'app/shared/model/events.model';
import { EventsService } from './events.service';
import { IDistance } from 'app/shared/model/distance.model';
import { DistanceService } from 'app/entities/distance/distance.service';

@Component({
  selector: 'jhi-events-update',
  templateUrl: './events-update.component.html'
})
export class EventsUpdateComponent implements OnInit {
  isSaving = false;

  distances: IDistance[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    date: [],
    regStart: [],
    regEnd: [],
    status: [],
    kind: [],
    url: [],
    distances: []
  });

  constructor(
    protected eventsService: EventsService,
    protected distanceService: DistanceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ events }) => {
      this.updateForm(events);

      this.distanceService
        .query()
        .pipe(
          map((res: HttpResponse<IDistance[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IDistance[]) => (this.distances = resBody));
    });
  }

  updateForm(events: IEvents): void {
    this.editForm.patchValue({
      id: events.id,
      name: events.name,
      date: events.date != null ? events.date.format(DATE_TIME_FORMAT) : null,
      regStart: events.regStart != null ? events.regStart.format(DATE_TIME_FORMAT) : null,
      regEnd: events.regEnd != null ? events.regEnd.format(DATE_TIME_FORMAT) : null,
      status: events.status,
      kind: events.kind,
      url: events.url,
      distances: events.distances
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const events = this.createFromForm();
    if (events.id !== undefined) {
      this.subscribeToSaveResponse(this.eventsService.update(events));
    } else {
      this.subscribeToSaveResponse(this.eventsService.create(events));
    }
  }

  private createFromForm(): IEvents {
    return {
      ...new Events(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      date: this.editForm.get(['date'])!.value != null ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      regStart:
        this.editForm.get(['regStart'])!.value != null ? moment(this.editForm.get(['regStart'])!.value, DATE_TIME_FORMAT) : undefined,
      regEnd: this.editForm.get(['regEnd'])!.value != null ? moment(this.editForm.get(['regEnd'])!.value, DATE_TIME_FORMAT) : undefined,
      status: this.editForm.get(['status'])!.value,
      kind: this.editForm.get(['kind'])!.value,
      url: this.editForm.get(['url'])!.value,
      distances: this.editForm.get(['distances'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEvents>>): void {
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

  trackById(index: number, item: IDistance): any {
    return item.id;
  }
}
