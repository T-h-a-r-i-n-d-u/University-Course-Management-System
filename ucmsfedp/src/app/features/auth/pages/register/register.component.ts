import { Component } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  standalone:false,
})
export class RegisterComponent {
  loading = false;
  form: FormGroup;

  constructor(
    private fb: FormBuilder,
    private auth: AuthService,
    private router: Router,
    private snack: MatSnackBar
  ) {
    this.form = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  submit() {
    if (this.form.invalid) return;
    this.loading = true;
    this.auth.register(this.form.value as any).subscribe({
      next: (msg) => {
        this.loading = false;
        this.snack.open((msg || 'Registered! Await admin approval.'), 'Close', { duration: 3000 });
        this.router.navigateByUrl('/auth/login');
      },
      error: (e) => {
        this.loading = false;
        const message =
          e?.error?.message || e?.error || 'Registration failed';
        if(typeof message === "string")
        {
          this.snack.open(message, 'Close', { duration: 3500 });
        }else {
          this.snack.open(message.text, 'Close', { duration: 3500 });
        }
        console.log(message);
      }
    });
  }
  onBackclick(){
    window.history.back();
  }

  protected readonly onclick = onclick;
}
