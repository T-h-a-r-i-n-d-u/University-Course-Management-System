import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MaterialModule } from '../shared/material/material.module';
import { ShellComponent } from './shell/shell.component';

@NgModule({
  imports: [
    CommonModule,        // *ngIf, async pipe
    RouterModule,        // routerLink, router-outlet
    MaterialModule       // mat-sidenav, mat-toolbar, mat-icon, etc.
  ],
  declarations: [ShellComponent],
  exports: [ShellComponent]
})
export class LayoutModule {}
