import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DropOffComponent } from './drop-off/drop-off.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'drop-off' },
  { path: 'drop-off', component: DropOffComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
