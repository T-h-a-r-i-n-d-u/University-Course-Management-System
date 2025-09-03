
import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

export interface UserDto {
  id: number;
  username: string;
  fullName: string;
  email: string;
  approved: boolean;
  roles: string[];
  createdAt?: string;
}

// "Lecture interface" (Lecturer)
export interface Lecturer extends UserDto {} // same shape; role includes 'LECTURER'

@Injectable({ providedIn: 'root' })
export class AdminUsersService {
  private base = environment.apiBaseUrl || '';
  constructor(private http: HttpClient){}

  // pending approvals
  pending(): Observable<UserDto[]> { return this.http.get<UserDto[]>(`${this.base}/api/admin/users/pending`); }
  approve(id:number): Observable<UserDto> { return this.http.post<UserDto>(`${this.base}/api/admin/users/${id}/approval`, {},
    {params:new HttpParams()
    .append('approved', true)
    });
  }
  reject(id:number): Observable<UserDto> { return this.http.post<UserDto>(`${this.base}/api/admin/users/${id}/reject`, {}); }

  // lecturers
  lecturers(): Observable<Lecturer[]> { return this.http.get<Lecturer[]>(`${this.base}/api/admin/users/lecturers`); }
  createLecturer(dto: {username:string; fullName:string; email?:string; password:string;}): Observable<Lecturer> {
    return this.http.post<Lecturer>(`${this.base}/api/admin/users/lecturers`, dto);
  }

  // roles/approval
  all(): Observable<UserDto[]> { return this.http.get<UserDto[]>(`${this.base}/api/admin/users`); }
  setRoles(id:number, roles:string[]): Observable<UserDto> {
    return this.http.post<UserDto>(`${this.base}/api/admin/users/${id}/roles`, { roles });
  }
  toggleApproval(id:number, approved:boolean): Observable<UserDto> {
    return this.http.post<UserDto>(`${this.base}/api/admin/users/${id}/approval?approved=${approved}`, {});
  }
}
