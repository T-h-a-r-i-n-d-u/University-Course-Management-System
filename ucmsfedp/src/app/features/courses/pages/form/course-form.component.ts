import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { CoursesService, CreateCourseDto, UpdateCourseDto, Course } from '../../courses.service';

@Component({
  selector: 'app-course-form',
  templateUrl: './course-form.component.html',
  styleUrls: ['./course-form.component.scss'],
  standalone:false
})
export class CourseFormComponent implements OnInit {
  id?: number;
  loading = false;
  isEdit = false;
  form !:FormGroup;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private api: CoursesService,
    private snack: MatSnackBar
  ) {
    this.form = this.fb.group({
      code: ['', Validators.required],
      title: ['', Validators.required],
      description: [''],
      credits: [null],
      semester: ['']
    });

  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.id = Number(idParam);
      this.isEdit = true;
      this.loading = true;
      this.api.get(this.id).subscribe({
        next: (c: Course) => { this.form.patchValue(c as any); this.loading = false; },
        error: () => { this.loading = false; this.snack.open('Course not found', 'Close', { duration: 2500 }); this.router.navigateByUrl('/courses'); }
      });
    }
  }

  save() {
    if (this.form.invalid) return;
    const dto = this.form.value as CreateCourseDto;

    if (!this.isEdit) {
      this.api.create(dto).subscribe({
        next: (c) => { this.snack.open('Course created', 'Close', { duration: 2500 }); this.router.navigate(['/courses', c.id]); },
        error: (e) => this.snack.open(e?.error?.message || 'Create failed', 'Close', { duration: 3000 })
      });
    } else {
      this.api.update(this.id!, dto as UpdateCourseDto).subscribe({
        next: () => { this.snack.open('Course updated', 'Close', { duration: 2500 }); this.router.navigate(['/courses', this.id]); },
        error: (e) => this.snack.open(e?.error?.message || 'Update failed', 'Close', { duration: 3000 })
      });
    }
  }

  cancel() {
    if (this.isEdit) this.router.navigate(['/courses', this.id]);
    else this.router.navigateByUrl('/courses');
  }
}
