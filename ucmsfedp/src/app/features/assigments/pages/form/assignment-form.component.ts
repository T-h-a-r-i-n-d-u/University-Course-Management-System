import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AssignmentsService } from '../../assignments.service';

@Component({
  selector: 'app-assignment-form',
  templateUrl: './assignment-form.component.html',
  styleUrls: ['./assignment-form.component.scss'],
  standalone: false
})
export class AssignmentFormComponent implements OnInit {
  isEdit = false;
  id?: number;
  courseId!: number;
  loading = false;
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private api: AssignmentsService,
    private snack: MatSnackBar
  ) {
    this.form = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      deadlineLocal: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    const courseIdParam = this.route.parent?.snapshot.paramMap.get('id');
    this.courseId = Number(courseIdParam);

    const aidParam = this.route.snapshot.paramMap.get('aid');
    if (aidParam) {
      this.isEdit = true;
      this.id = Number(aidParam);

      this.loading = true;
      this.api.getOne(this.id!).subscribe({
        next: a => {
          this.courseId = a.courseId; // keep in sync
          this.form.patchValue({
            title: a.title,
            description: a.description || '',
            deadlineLocal: this.toLocalInput(a.deadline)
          });
          this.loading = false;
        },
        error: () => {
          this.loading = false;
          this.snack.open('Assignment not found', 'Close', { duration: 2500 });
          this.router.navigate(['/courses', this.courseId, 'assignments']);
        }
      });
    }
  }

  private toLocalInput(iso: string) {
    const d = new Date(iso);
    const pad = (n: number) => n.toString().padStart(2, '0');
    return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}T${pad(d.getHours())}:${pad(d.getMinutes())}`;
  }

  private toIso(local: string) {
    return new Date(local).toISOString();
  }

  save() {
    if (this.form.invalid) return;

    const v = this.form.value;
    const dto = {
      courseId: this.courseId,
      title: v.title as string,
      description: (v.description as string) || '',
      deadline: this.toIso(v.deadlineLocal as string)
    };

    if (!this.isEdit) {
      this.api.create(dto).subscribe({
        next: () => {
          this.snack.open('Assignment created', 'Close', { duration: 2500 });
          this.router.navigate(['/courses', this.courseId, 'assignments']); // go back to list
        },
        error: e => this.snack.open(e?.error?.message || 'Create failed', 'Close', { duration: 3000 })
      });
    } else {
      this.api.update(this.id!, dto).subscribe({
        next: () => {
          this.snack.open('Assignment updated', 'Close', { duration: 2500 });
          this.router.navigate(['/courses', this.courseId, 'assignments']);
        },
        error: e => this.snack.open(e?.error?.message || 'Update failed', 'Close', { duration: 3000 })
      });
    }
  }

  cancel() {
    this.router.navigate(['/courses', this.courseId, 'assignments']);
  }
}
