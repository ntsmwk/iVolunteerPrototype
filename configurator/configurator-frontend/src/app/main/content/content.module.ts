import { FuseSharedModule } from '@fuse/shared.module';
import { FuseContentComponent } from 'app/main/content/content.component';
import { AnonymGuard } from './_guard/anonym.guard';
import { Route, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';

const routes: Route[] = [
  {
    path: 'main/dashboard',
    loadChildren: () =>
      import('./_components/dashboard/dashboard.module').then(
        (m) => m.FuseDashboardModule
      ),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/properties/all',
    loadChildren: () =>
      import(
        './_components/configuration/property-configurator/list/property-list.module'
      ).then((m) => m.PropertyListModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/property-builder',
    loadChildren: () =>
      import(
        './_components/configuration/property-configurator/builder/property-build-form.module'
      ).then((m) => m.PropertyBuildFormModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/class-configurator',
    loadChildren: () =>
      import(
        './_components/configuration/class-configurator/configurator.module'
      ).then((m) => m.ConfiguratorModule),
    canActivate: [AnonymGuard]
  },

  {
    path: 'main/instance-editor',
    loadChildren: () =>
      import(
        './_components/configuration/class-instance-configurator/form-editor/class-instance-form-editor.module'
      ).then((m) => m.ClassInstanceFormEditorModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/matching-configurator',
    loadChildren: () =>
      import(
        './_components/configuration/matching-configurator/matching-configurator.module'
      ).then((m) => m.MatchingConfiguratorModule),
    canActivate: [AnonymGuard]
  },
  {
    path: 'main/task-select',
    loadChildren: () =>
      import(
        './_components/task-select/task-select.module'
      ).then((m) => m.FuseTaskSelectModule),
    canActivate: [AnonymGuard],
  },
  {
    path: 'main/invalid-parameters',
    loadChildren: () =>
      import(
        './_components/_shared/invalid-parameters/invalid-parameters.module'
      ).then((m) => m.InvalidParametersModule),
    canActivate: [AnonymGuard],
  },
];

@NgModule({
  declarations: [FuseContentComponent],
  imports: [RouterModule.forChild(routes), FuseSharedModule],
  // providers: [
  //   { provide: HTTP_INTERCEPTORS, multi: true },
  // ],
  exports: [FuseContentComponent],
})
export class FuseContentModule { }
