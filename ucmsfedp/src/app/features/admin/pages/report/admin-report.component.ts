import { Component, OnInit } from '@angular/core';
import { AdminReportService, AdminReport } from '../../admin-report.service';

@Component({
  selector: 'app-admin-report',
  templateUrl: './admin-report.component.html',
  styleUrls: ['./admin-report.component.scss'],
  standalone:false
})
export class AdminReportComponent implements OnInit {
  loading=false; data?: AdminReport;
  constructor(private api: AdminReportService) {}
  ngOnInit(){ this.loading=true; this.api.get().subscribe({ next:r=>{this.data=r; this.loading=false;}, error:_=>this.loading=false }); }
}
