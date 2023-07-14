import { Component, OnInit } from '@angular/core';
import { UserApiService } from 'src/app/service/user-api.service';
import { user } from 'src/app/model/user';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {
  users: user[] = [];
  users_number: number;
  pagination: number =0;
  size: number = 2;

  msg: any;
  status: any;

  constructor(private userapi: UserApiService) { }

  ngOnInit() {
    this.fetchusers();
  }

  fetchusers() {

    this.userapi.getusers(this.pagination, this.size).subscribe({
      next: (res: any) => {
        this.users = res.users;
        this.users_number=res.count;
        console.log(res);
      },
      error: (err) => {
        this.msg = err?.error.message;
        this.status = err?.error.status;
      }
    })
  }

  renderPage(event: number) {
    this.pagination = event;
    this.fetchusers();
  }
}
