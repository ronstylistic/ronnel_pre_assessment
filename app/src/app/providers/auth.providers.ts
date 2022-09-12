import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestResponse } from '../models/api.io';
import { ApiHeaders } from '../models/headers';
import { User } from '../models/user';

@Injectable()
export class AuthProvider {
  constructor(
    private http: HttpClient
  ) { }

  authenticate(email: string, password: string, code?: string): Observable<RestResponse> {
    return this.http
      .post<RestResponse>(`/api/auth`, {
        email,
        password,
        code: code ? code: null
      }, ApiHeaders.getOptions());
  }


  register(user: User, password: string): Observable<RestResponse> {
    return this.http
      .post<RestResponse>(`/api/auth/register?password=${password}`, user, ApiHeaders.getOptions());
  }
}
