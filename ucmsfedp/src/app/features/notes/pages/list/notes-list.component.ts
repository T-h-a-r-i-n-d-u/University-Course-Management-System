
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NotesService, LectureNote } from '../../notes.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  templateUrl: './notes-list.component.html',
  styleUrls: ['./notes-list.component.scss'],
  standalone:false
})
export class NotesListComponent implements OnInit {
  courseId!: number; loading=false; data:LectureNote[]=[];
  title=''; file?:File; uploading=false;

  constructor(private route:ActivatedRoute, private api:NotesService, private snack:MatSnackBar, public auth:AuthService){}

  ngOnInit(): void {
    this.courseId = Number(this.route.parent?.snapshot.paramMap.get('id') ?? this.route.snapshot.paramMap.get('id'));
    this.fetch();
  }
  fetch(){ this.loading=true; this.api.listByCourse(this.courseId).subscribe({ next:r=>{this.data=r; this.loading=false;}, error:_=>{this.loading=false;} }); }

  onFile(e:any){ this.file = e.target.files[0]; }
  upload(){
    if(!this.title || !this.file){ this.snack.open('Title & file required','Close',{duration:1500}); return; }
    this.uploading=true;
    this.api.upload(this.courseId, this.title, this.file).subscribe({
      next: _ => { this.uploading=false; this.title=''; this.file=undefined; this.fetch(); this.snack.open('Uploaded','Close',{duration:1500}); },
      error: e => { this.uploading=false; this.snack.open(e?.error?.message || 'Upload failed','Close',{duration:2500}); }
    });
  }

  dl(n:LectureNote){ this.api.download(n.id); }
  rm(n:LectureNote){
    if(!confirm('Delete this note?')) return;
    this.api.delete(n.id).subscribe({ next: _ => { this.fetch(); }, error: _ => this.snack.open('Delete failed','Close',{duration:2000}) });
  }
}
