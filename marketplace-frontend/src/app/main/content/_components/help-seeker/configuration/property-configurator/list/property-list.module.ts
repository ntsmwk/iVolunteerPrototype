import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCommonModule, MatOptionModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTableModule } from '@angular/material/table';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FuseSharedModule } from '@fuse/shared.module';
import { PropertyListComponent } from './property-list.component';
import { FuseTruncatePipeModule } from 'app/main/content/_pipe/truncate-pipe.module';
import { MatInputModule, MatFormFieldModule, MatSelectModule } from '@angular/material';
import { DialogFactoryModule } from 'app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.module';


const routes: Route[] = [
  { path: '', component: PropertyListComponent }
];

@NgModule({
  declarations: [
    PropertyListComponent
  ],

  imports: [
    RouterModule.forChild(routes),
    DialogFactoryModule,
    MatCommonModule,
    MatCardModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatTooltipModule,
    MatSlideToggleModule,



    MatFormFieldModule,
    MatInputModule,

    MatSelectModule,
    MatOptionModule,

    MatProgressSpinnerModule,

    FuseSharedModule,
    FuseTruncatePipeModule
  ]

})

export class PropertyListModule { }
