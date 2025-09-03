import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import {environment} from '../../environments/environment';

export interface Result { id:number; courseId:number; studentId:number; totalScore:number; grade:string; published:boolean; updatedAt:string; }

@Injectable({ providedIn: 'root' })
export class ResultsService {
  private base = environment.apiBaseUrl || '';
  constructor(private http: HttpClient) {}
  mine(): Observable<Result[]> { return this.http.get<Result[]>(`${this.base}/api/results/student/me`); }
}
