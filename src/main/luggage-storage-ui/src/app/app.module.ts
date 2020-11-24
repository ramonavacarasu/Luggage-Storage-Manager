import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DropOffComponent } from './drop-off/drop-off.component';
import { StowService } from './shared/stow.service';
import { HttpClientModule } from '@angular/common/http';
import { AlertComponent } from './alert/alert.component';
import { AlertService } from './shared/alert.service';
import { CommonModule } from '@angular/common';
import { FileSaverModule } from 'ngx-filesaver';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    DropOffComponent,
    AlertComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    CommonModule,
    FileSaverModule,
    ReactiveFormsModule
  ],
  providers: [
    StowService,
    AlertService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
