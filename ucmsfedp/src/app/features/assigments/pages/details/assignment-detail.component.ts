
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import {AssignmentsService, Assignment, Submission} from '../../assignments.service';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-assignment-detail',
  templateUrl: './assignment-detail.component.html',
  styleUrls: ['./assignment-detail.component.scss'],
  standalone:false
})
export class AssignmentDetailComponent implements OnInit {
  courseId!: number; id!: number;
  loading=false; a?: Assignment;

  // student
  mySubmission?: Submission; uploading=false; selectedFile?: File;

  // lecturer
  subsLoading=false; subs: Submission[]=[];

  constructor(private route:ActivatedRoute, private api:AssignmentsService, private snack:MatSnackBar, public auth:AuthService){}

  ngOnInit(): void {
    this.courseId = Number(this.route.parent?.snapshot.paramMap.get('id') ?? this.route.snapshot.paramMap.get('id'));
    this.id = Number(this.route.snapshot.paramMap.get('aid'));
    this.load();
  }

  load(){
    this.loading = true;
    this.api.getOne(this.id).subscribe({
      next:r=>{ this.a=r; this.loading=false; this.loadRoleSpecific(); },
      error:_=>{ this.loading=false; this.snack.open('Not found','Close',{duration:2500}); }
    });
  }

  loadRoleSpecific(){
    if (this.auth.hasRole('STUDENT')){
      this.api.mine(this.id).subscribe({ next:r=>this.mySubmission=r, error:_=>this.mySubmission=undefined });
    }
    if (this.auth.hasRole('LECTURER') || this.auth.hasRole('ADMIN')){
      this.subsLoading = true;
      this.api.listSubmissions(this.id).subscribe({ next:r=>{this.subs=r; this.subsLoading=false;}, error:_=>{this.subs=[]; this.subsLoading=false;} });
    }
  }

  onFile(e:any){ this.selectedFile = e.target.files[0]; }
  submit(){
    if (!this.selectedFile){ this.snack.open('Choose a file','Close',{duration:1500}); return; }
    this.uploading = true;
    this.api.submit(this.id, this.selectedFile).subscribe({
      next:r=>{ this.uploading=false; this.snack.open('Submitted','Close',{duration:2000}); this.mySubmission=r; },
      error:e=>{ this.uploading=false; this.snack.open(e?.error?.message || 'Submit failed','Close',{duration:3000}); }
    });
  }

  dl(s:Submission){ this.api.download(s.id); }
  grade(s:Submission, marks:any, feedback:string){
    this.api.grade(s.id, Number(marks), feedback||'').subscribe({
      next:r=>{ this.snack.open('Graded','Close',{duration:1500}); const i=this.subs.findIndex(x=>x.id===r.id); if(i>=0) this.subs[i]=r; },
      error:e=>{ this.snack.open(e?.error?.message || 'Grade failed','Close',{duration:2500}); }
    });
  }
}
