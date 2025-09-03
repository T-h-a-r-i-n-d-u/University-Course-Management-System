import { Component, OnInit } from '@angular/core';
import { ResultsService, Result } from '../../results.service';

@Component({
  selector: 'app-results-my',
  templateUrl: './results-my.component.html',
  styleUrls: ['./results-my.component.scss'],
  standalone:false
})
export class ResultsMyComponent implements OnInit {
  loading=false; data: Result[]=[];
  constructor(private api: ResultsService) {}
  ngOnInit(){ this.loading=true; this.api.mine().subscribe({ next:r=>{this.data=r; this.loading=false;}, error:_=>this.loading=false }); }
}
