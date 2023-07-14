import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgxPaginationModule } from 'ngx-pagination';


import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserListComponent } from './component/user-list/user-list.component';
import { HomeComponent } from './component/home/home.component';
import { UserCreateComponent } from './component/user-create/user-create.component';
import { UserEditComponent } from './component/user-edit/user-edit.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    UserListComponent,
    HomeComponent,
    UserCreateComponent,
    UserEditComponent
  ],
  imports: [
    HttpClientModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
