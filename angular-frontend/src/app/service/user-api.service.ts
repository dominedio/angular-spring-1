import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { user } from '../model/user';
import { Observable, throwError  } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserApiService {

  constructor(private httpclient: HttpClient) { }
  baseUrl: string = 'http://localhost:8080/spring-rest-api/users';

  getusers(page: number,size :number):Observable<user>{
    return this.httpclient.get<user>(this.baseUrl+'?page'+(page-1)+'&size'+size)
    //.pipe( catchError((err)=> throwError(err))
    //);
  }

  public getuserbyid(id: string): Observable<user> {
    return this.httpclient.get<user>(`${this.baseUrl}/id/${id}`);
  }

  public postuser(User: user): Observable<user> {
    const headers = { 'content-type': 'application/json' };
    const body = JSON.stringify(User);
    return this.httpclient
      .post<user>(`${this.baseUrl}/create`, body, { headers: headers });
  }

  public putuser(id: string, User: user): Observable<user> {
    const headers = { 'content-type': 'application/json' };
    const body = JSON.stringify(User);
    return this.httpclient
      .put<user>(`${this.baseUrl}/put/${id}`, body, { headers: headers });
  }

  public deleteuser(id: string): Observable<user> {
    const headers = { 'content-type': 'application/json' };
    return this.httpclient
      .delete<user>(`${this.baseUrl}/delete/${id}`, { headers: headers });
  }
}
