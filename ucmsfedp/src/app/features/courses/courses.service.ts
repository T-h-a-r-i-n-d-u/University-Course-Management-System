import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import {environment} from '../../environments/environment';

export interface Course {
  id: number;
  code: string;
  title: string;
  description?: string;
  credits?: number;
  semester?: string;
}

export interface CreateCourseDto {
  code: string; title: string; description?: string; credits?: number; semester?: string;
}
export interface UpdateCourseDto extends Partial<CreateCourseDto> {}

@Injectable({ providedIn: 'root' })
export class CoursesService {
  private base = environment.apiBaseUrl || '';
  constructor(private http: HttpClient) {}

  list(): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.base}/api/courses`);
  }
  get(id: number): Observable<Course> {
    return this.http.get<Course>(`${this.base}/api/courses/${id}`);
  }
  create(dto: CreateCourseDto): Observable<Course> {
    return this.http.post<Course>(`${this.base}/api/courses`, dto);
  }
  update(id: number, dto: UpdateCourseDto): Observable<Course> {
    return this.http.put<Course>(`${this.base}/api/courses/${id}`, dto);
  }
  remove(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/api/courses/${id}`);
  }
  assignLecturer(courseId:number, userId:number){
    return this.http.post(`${this.base}/api/courses/${courseId}/lecturers/${userId}`, {});
  }

}
