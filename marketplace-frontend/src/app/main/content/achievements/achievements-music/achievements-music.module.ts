import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AchievementsMusicComponent } from './achievements-music.component';
import { RouterModule } from '@angular/router';

const routes = [
  { path: '', component: AchievementsMusicComponent }
];

@NgModule({
  declarations: [
    AchievementsMusicComponent,
  ],
  imports: [
    RouterModule.forChild(routes),
    CommonModule
  ]
})
export class AchievementsMusicModule { }
