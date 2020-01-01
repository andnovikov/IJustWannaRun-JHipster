import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserRegistration } from 'app/shared/model/user-registration.model';

@Component({
  selector: 'jhi-user-registration-detail',
  templateUrl: './user-registration-detail.component.html'
})
export class UserRegistrationDetailComponent implements OnInit {
  userRegistration: IUserRegistration | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userRegistration }) => {
      this.userRegistration = userRegistration;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
