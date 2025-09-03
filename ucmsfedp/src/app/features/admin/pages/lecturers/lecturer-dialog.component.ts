
import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialogRef } from '@angular/material/dialog';
import { AdminUsersService } from '../../users/admin-users.service';



@Component({
  selector: 'app-lecturer-dialog',
  templateUrl: './lecturer-dialog.component.html',
  styleUrls: ['./lecturer-dialog.component.scss'],
  standalone:false
})
export class LecturerDialogComponent {
  loading = false;
  form !: FormGroup;


  constructor(
    private fb: FormBuilder,
    private api: AdminUsersService,
    private snack: MatSnackBar,
    public dialogRef: MatDialogRef<LecturerDialogComponent>  // <-- injected
  ) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      fullName: ['', Validators.required],
      email: [''],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  save() {
    if (this.form.invalid) return;
    this.loading = true;

    this.api.createLecturer(this.form.value as any).subscribe({
      next: _ => {
        this.loading = false;
        this.snack.open('Lecturer created', 'Close', { duration: 2000 });
        this.dialogRef.close(true); // <-- close dialog and return "true"
      },
      error: e => {
        this.loading = false;
        this.snack.open(e?.error?.message || 'Failed', 'Close', { duration: 3000 });
      }
    });
  }


}
