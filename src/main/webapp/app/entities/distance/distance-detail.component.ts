import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDistance } from 'app/shared/model/distance.model';

@Component({
  selector: 'jhi-distance-detail',
  templateUrl: './distance-detail.component.html'
})
export class DistanceDetailComponent implements OnInit {
  distance: IDistance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ distance }) => {
      this.distance = distance;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
