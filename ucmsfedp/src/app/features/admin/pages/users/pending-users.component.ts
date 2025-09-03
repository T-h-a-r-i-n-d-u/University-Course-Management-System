
import { Component, OnInit } from '@angular/core';
import { AdminUsersService, UserDto } from '../../users/admin-users.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-pending-users',
  templateUrl: './pending-users.component.html',
  styleUrls: ['./pending-users.component.scss'],
  standalone:false
})
export class PendingUsersComponent implements OnInit {
  loading=false; data:UserDto[]=[];

  constructor(private api:AdminUsersService, private snack:MatSnackBar){}

  ngOnInit(){ this.fetch(); }
  fetch(){ this.loading=true; this.api.pending().subscribe({ next:r=>{this.data=r; this.loading=false;}, error:_=>this.loading=false }); }

  approve(u:UserDto){ this.api.approve(u.id).subscribe({ next:()=>{ this.snack.open('Approved','Close',{duration:2000}); this.fetch(); } }); }
  reject(u:UserDto){ if(!confirm('Reject/delete this user?')) return;
    this.api.reject(u.id).subscribe({ next:()=>{ this.snack.open('Rejected','Close',{duration:2000}); this.fetch(); } }); }
}
