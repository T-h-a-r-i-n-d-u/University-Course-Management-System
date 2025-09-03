import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';


import { AppRoutingModule } from './app-routing.module';
import { SharedModule } from './shared/shared.module';
import { CoreModule } from './core/core.module';
import { AppComponent } from './app.component';
import { ShellComponent } from './layout/shell/shell.component';

@NgModule({
  declarations: [AppComponent, ShellComponent],
  imports: [
    BrowserModule,
    // BrowserAnimationsModule,
    SharedModule,
    CoreModule,
    AppRoutingModule],
  bootstrap: [AppComponent]
})
export class AppModule {}
