import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {ReactiveFormsModule} from '@angular/forms';
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";
import { MatListModule } from '@angular/material/list'

import {FuseSharedModule} from '@fuse/shared.module';


import { MatButtonModule, MatChipsModule, MatFormFieldModule, MatIconModule, 
  MatInputModule, MatSidenavModule, MatDividerModule, MatTableModule, MatCheckboxModule } 
  from '@angular/material';

  import { FusePropertyDetailComponent } from './property-detail.component';
import { MultiPropertyDetailComponent } from './multi-property-detail/multi-property-detail.component';

const routes = [
  {path: ':marketplaceId/:propertyId', component: FusePropertyDetailComponent}
];

@NgModule({
  declarations: [FusePropertyDetailComponent, MultiPropertyDetailComponent],
  
  imports: [
    RouterModule.forChild(routes),
    ReactiveFormsModule,
    MatButtonModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSidenavModule,
    MatDividerModule,
    MatTableModule,
    MatCheckboxModule,
    MatProgressSpinnerModule,
    MatListModule,

    FuseSharedModule

  ]
  
})
export class FusePropertyDetailModule { }
