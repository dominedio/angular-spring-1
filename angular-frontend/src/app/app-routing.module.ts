import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './component/home/home.component';
import { UserCreateComponent } from './component/user-create/user-create.component';
import { UserListComponent } from './component/user-list/user-list.component';
import { UserEditComponent } from './component/user-edit/user-edit.component';

const routes: Routes = [
{ path: '', redirectTo: 'home', pathMatch: 'full' },
{ path: 'home', component: HomeComponent },
{ path: 'user-list', component: UserListComponent },
{ path: 'user-create', component: UserCreateComponent },
{ path: 'user-edit/:id', component: UserEditComponent },
{ path: '**', component: HomeComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
