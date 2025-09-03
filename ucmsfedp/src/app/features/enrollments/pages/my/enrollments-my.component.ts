import { Component, OnInit } from '@angular/core';
import { EnrollmentsService, Enrollment } from '../../enrollments.service';

@Component({
  selector: 'app-enrollments-my',
  templateUrl: './enrollments-my.component.html',
  styleUrls: ['./enrollments-my.component.scss'],
  standalone:false
})
export class EnrollmentsMyComponent implements OnInit {
  loading = false; data: Enrollment[] = [];
  constructor(private api: EnrollmentsService) {}
  ngOnInit(): void { this.loading = true; this.api.my().subscribe({ next: r => { this.data = r; this.loading=false; }, error: _ => this.loading=false }); }
}
