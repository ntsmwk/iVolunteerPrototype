import {NgModule} from '@angular/core';
import {Route, RouterModule} from '@angular/router';
import {MatButtonModule, MatDatepickerModule, MatFormFieldModule, MatInputModule, MatNativeDateModule} from '@angular/material';
import {FuseSharedModule} from '@fuse/shared.module';
import {FuseMarketplaceFormComponent} from './marketplace-form.component';

const routes: Route[] = [
  {path: '', component: FuseMarketplaceFormComponent},
  {path: ':marketplaceId', component: FuseMarketplaceFormComponent}
];

@NgModule({
  declarations: [
    FuseMarketplaceFormComponent
  ],
  imports: [
    RouterModule.forChild(routes),

    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatFormFieldModule,
    MatInputModule,

    FuseSharedModule
  ]
})
export class FuseMarketplaceFormModule {
}
