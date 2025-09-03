
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import {environment} from '../../environments/environment';

export interface LectureNote {
  id: number;
  courseId: number;
  title: string;
  originalFilename: string;
  contentType: string;
  size: number;
  uploaderName: string;
  uploadedAt: string;
}

@Injectable({ providedIn: 'root' })
export class NotesService {
  private base = environment.apiBaseUrl || '';
  constructor(private http: HttpClient) {}

  listByCourse(courseId:number): Observable<LectureNote[]> {
    return this.http.get<LectureNote[]>(`${this.base}/api/notes/course/${courseId}`);
  }

  upload(courseId:number, title:string, file:File): Observable<LectureNote> {
    const fd = new FormData(); fd.append('title', title); fd.append('file', file);
    return this.http.post<LectureNote>(`${this.base}/api/notes/course/${courseId}`, fd);
  }

  download(noteId:number) {
    window.location.href = `${this.base}/api/notes/${noteId}/file`;
  }

  delete(noteId:number){ return this.http.delete<void>(`${this.base}/api/notes/${noteId}`); }
}
