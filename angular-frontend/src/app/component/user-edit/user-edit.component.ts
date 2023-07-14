import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Location } from '@angular/common';

import { user } from 'src/app/model/user';
import { UserApiService } from 'src/app/service/user-api.service';

@Component({
  selector: 'app-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: ['./user-edit.component.css']
})
export class UserEditComponent implements OnInit {
  editUserForm: FormGroup = this.formBuilder.group({});
  putUserSubscription: Subscription;
  timeoutId: ReturnType<typeof setTimeout>;
  feedback: { type: string; msg: string };
  User: user;

  constructor(
    private formBuilder: FormBuilder,
    private apiUser: UserApiService,
    private activatedRoute: ActivatedRoute,
    private location: Location
  ) {}

  ngOnInit() {
    this.User={
      name:"",
      email:"",
    }
    this.activatedRoute.params.subscribe((params) => {
      this.User.id = params['id'];
    });
    this.editUserForm = this.formBuilder.group({
      name: [""],
      email: [""],
    })
    this.feedback = { type: "", msg: "" }
  }

  onPut() {
    if (this.editUserForm.invalid) return;
    this.putUserSubscription = this.apiUser
      .putuser(this.User.id, {
        name: this.name.value,
        email: this.email.value,
      })
      .subscribe({
        complete: () =>
        {
          (this.feedback = { type: 'success', msg: 'Successfully updated!' })
        },
      error: (err) => {
        this.feedback.msg = err?.error.message;
        this.feedback.type = err?.error.status;
      }
      });
    this.timeoutId = setTimeout(() => {
      this.feedback = { type: '', msg: '' };
    }, 3000);
    if (window.confirm('Do you want to return to user list?')) {
      this.onBack();
    }
  }

  getProduct(id: string) {
    this.apiUser.getuserbyid(id).subscribe({
      next: (res) => (this.User = res),
      complete: () => {
        this.editUserForm.setValue({
          name: this.User.name,
          email: this.User.email,
        });
      },
      error: (err) => {
        this.feedback.msg = err?.error.message;
        this.feedback.type = err?.error.status;
      }
    });
  }

  onBack() {
    this.location.back();
  }

  get name(): FormControl {
    return <FormControl>this.editUserForm.controls['name'];
  }
  get email(): FormControl {
    return <FormControl>this.editUserForm.controls['email'];
  }

}
