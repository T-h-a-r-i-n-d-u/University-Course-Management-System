
import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { AdminUsersService, Lecturer } from '../../../admin/users/admin-users.service';

@Component({
  template: `
  <h2 mat-dialog-title>Assign Lecturer</h2>
  <mat-dialog-content>
    <mat-form-field appearance="outline" class="wfull">
      <mat-label>Lecturer</mat-label>
      <mat-select [(value)]="selectedId">
        <mat-option *ngFor="let l of lecturers" [value]="l.id">{{ l.fullName }} ({{ l.username }})</mat-option>
      </mat-select>
    </mat-form-field>
  </mat-dialog-content>
  <mat-dialog-actions align="end">
    <button mat-stroked-button (click)="close()">Cancel</button>
    <button mat-raised-button color="primary" [disabled]="!selectedId" (click)="ok()">Assign</button>
  </mat-dialog-actions>`,
  styles:[`.wfull{width:420px;max-width:86vw}`],
  standalone:false
})
export class AssignLecturerDialog implements OnInit {
  lecturers: Lecturer[]=[]; selectedId?:number;
  constructor(private admin:AdminUsersService, private ref:MatDialogRef<AssignLecturerDialog>){}
  ngOnInit(){ this.admin.lecturers().subscribe(r=>this.lecturers=r); }
  close(){ this.ref.close(); }
  ok(){ this.ref.close(this.selectedId); }
}
