import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AssignmentsService, Assignment } from '../../assignments.service';
import { AuthService } from '../../../../core/services/auth.service';
import {CoursesService} from '../../../courses/courses.service';
import {take} from 'rxjs';

@Component({
  selector: 'app-assignments-list',
  templateUrl: './assignments-list.component.html',
  styleUrls: ['./assignments-list.component.scss'],
  standalone:false
})
export class AssignmentsListComponent implements OnInit {
  courseId!: number;
  loading = false;
  data: Assignment[] = [];
  courser:any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private api: AssignmentsService,
    public auth: AuthService,
    private snack: MatSnackBar,
    private courserApi:CoursesService
  ) {}

  ngOnInit(): void {
    this.courseId = Number(this.route.parent?.snapshot.paramMap.get('id') ?? this.route.snapshot.paramMap.get('id'));
    this.getCourseById(this.courseId);
    this.fetch();
  }


  fetch(){ this.loading=true; this.api.listByCourse(this.courseId).subscribe({
    next:r=>{this.data=r; this.loading=false;}, error:_=>{this.loading=false; this.snack.open('Load failed','Close',{duration:2500});}
  });}

  getCourseById(id:any){
    this.courserApi.get(id).pipe(take(1)).subscribe(
      data =>{
        if(data){
         this.courser = data
        }
      }
    )
  }

  isLecturer(){ return this.auth.hasRole('LECTURER'); }
  add(){ this.router.navigate(['/courses', this.courseId, 'assignments', 'new']); }
  view(a:Assignment){ this.router.navigate(['/courses', this.courseId, 'assignments', a.id]); }
  edit(a:Assignment){ this.router.navigate(['/courses', this.courseId, 'assignments', a.id, 'edit']); }
}
