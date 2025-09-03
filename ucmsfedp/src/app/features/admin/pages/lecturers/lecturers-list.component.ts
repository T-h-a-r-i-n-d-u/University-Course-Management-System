
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AdminUsersService, Lecturer } from '../../users/admin-users.service';
import {LecturerDialogComponent} from './lecturer-dialog.component';

@Component({
  selector: 'app-lecturers-list',
  templateUrl: './lecturers-list.component.html',
  styleUrls: ['./lecturers-list.component.scss'],
  standalone:false
})
export class LecturersListComponent implements OnInit {
  loading=false; data:Lecturer[]=[];
  constructor(private api:AdminUsersService, private dialog:MatDialog){}
  ngOnInit(){ this.fetch(); }
  fetch(){ this.loading=true; this.api.lecturers().subscribe({ next:r=>{this.data=r; this.loading=false;}, error:_=>this.loading=false }); }
  openCreate(){
    const dialogRef =  this.dialog.open(LecturerDialogComponent, {
      height:'500px'
    });
    dialogRef.afterClosed().subscribe(x=>{ if(x) this.fetch(); }); }
}

// inline import to avoid circular dep in this snippet
import { Component as C2 } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';


// @C2({
//   selector: 'app-lecturer-dialog',
//   templateUrl: './lecturer-dialog.component.html',
//   styleUrls: ['./lecturer-dialog.component.scss'],
//   standalone:false
// })
// export class LecturerDialogComponent {
//   loading=false;
//   form !:FormGroup;
//
//   constructor(private fb:FormBuilder, private api:AdminUsersService, private snack:MatSnackBar,private dialog: MatDialog) {
//     this.form = this.fb.group({
//       username: ['', Validators.required],
//       fullName: ['', Validators.required],
//       email: [''],
//       password: ['', [Validators.required, Validators.minLength(6)]]
//     });
//   }
//
//
//
//   save(dialogRef:any){
//     if(this.form.invalid) return;
//     this.loading=true;
//     this.api.createLecturer(this.form.value as any).subscribe({
//       next: _ => { this.loading=false; this.snack.open('Lecturer created','Close',{duration:2000}); dialogRef.close(true); },
//       error: e => { this.loading=false; this.snack.open(e?.error?.message || 'Failed','Close',{duration:3000}); }
//     });
//   }
// }
