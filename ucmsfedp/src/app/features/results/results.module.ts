import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared.module';
import { RouterModule, Routes } from '@angular/router';
import {ResultsMyComponent} from './page/my/results-my.component';


const routes: Routes = [{ path: '', component: ResultsMyComponent }];

@NgModule({
  imports: [SharedModule, RouterModule.forChild(routes)],
  declarations: [ResultsMyComponent]
})
export class ResultsModule {}
