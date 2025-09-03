import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import {AuthService} from '../../../../core/services/auth.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone:false
})
export class LoginComponent {
  loading = false;
  form !:FormGroup;

  constructor(private fb: FormBuilder, private api: AuthService, private router: Router, private snack: MatSnackBar) {
    this.form = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  submit() {
    if (this.form.invalid) return;
    this.loading = true;
    const { username, password } = this.form.value;

    this.api.login(username!, password!).subscribe({
      next: () => {
        this.loading = false;
        // If your backend sets approved=false until admin approves:
        const approved = this.api.snapshot.approved;
        if (approved === false) {
          this.snack.open('Registered but not yet approved. Please wait for admin approval.', 'Close', { duration: 4000 });
          return; // remain on login page intentionally
        }
        this.router.navigateByUrl('/dashboard');
      },
      error: (e) => {
        this.loading = false;
        this.snack.open(e?.error?.message || e?.message || 'Login failed', 'Close', { duration: 3000 });
      }
    });
  }

}
