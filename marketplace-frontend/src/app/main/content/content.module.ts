import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { FuseSharedModule } from '@fuse/shared.module';
import { FuseContentComponent } from 'app/main/content/content.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './_interceptor/token.interceptor';
import { VolunteerGuard } from './_guard/volunteer.guard';
import { LoginGuard } from './_guard/login.guard';
import { FlexProdOrTenantAdminGuard } from './_guard/flexprod-helpseeker.guard';
import { RecruiterGuard } from './_guard/recruiter.guard';
import { ShareMenuComponent } from './_components/volunteer/task-management/achievements/share-menu/share-menu.component';
import { ShareMenuModule } from './_components/volunteer/task-management/achievements/share-menu/share-menu.module';
import { AnonymGuard } from './_guard/anonym.guard';
import { AdminGuard } from './_guard/admin.guard';
import { TenantAdminGuard } from './_guard/tenant-admin.guard';
import { HelpSeekerOrTenantAdminGuard } from './_guard/helpseeker-tenantAdmin.guard';
import { TokenGuard } from './_guard/token.guard';
import { AdminOrTenantAdminGuard } from './_guard/admin-tenantAdmin.guard';

const routes: Route[] = [
  {
    path: 'login',
    loadChildren: () =>
      import('./_components/common/user_management/login/login.module').then(
        (m) => m.FuseLoginModule
      ),
    // canActivate: [AnonymGuard],
  },
  {
    path: 'register/volunteer',
    loadChildren: () =>
      import(
        './_components/common/user_management/registration/volunteer/registration.module'
      ).then((m) => m.VolunteerRegistrationModule),
    canActivate: [AnonymGuard],
  },
  {
    path: 'register/organization',
    loadChildren: () =>
      import(
        './_components/common/user_management/registration/organization/registration.module'
      ).then((m) => m.OrganizationRegistrationModule),
    canActivate: [AnonymGuard],
  },
  {
    path: 'register/activate',
    loadChildren: () =>
      import(
        './_components/common/user_management/activation/activation.module'
      ).then((m) => m.ActivationModule),
    canActivate: [AnonymGuard],
  },
  {
    path: 'role',
    loadChildren: () =>
      import(
        './_components/common/user_management/role-switch/role-switch.module'
      ).then((m) => m.FuseRoleSwitchModule),
    canActivate: [TokenGuard],
  },
  {
    path: 'main/helpseeker/asset-inbox',
    loadChildren: () =>
      import(
        './_components/help-seeker/asset-inbox-helpseeker/asset-inbox-helpseeker.module'
      ).then((m) => m.AssetInboxHelpseekerModule),
    canActivate: [TokenGuard, HelpSeekerOrTenantAdminGuard],
  },
  {
    path: 'main/helpseeker/asset-inbox/confirm',
    loadChildren: () =>
      import(
        './_components/help-seeker/asset-inbox-helpseeker/confirmation-screen/confirmation-screen.module'
      ).then((m) => m.HelpseekerConfirmationScreenModule),
    canActivate: [TokenGuard, HelpSeekerOrTenantAdminGuard],
  },
  {
    path: 'main/dashboard',
    loadChildren: () =>
      import('./_components/common/dashboard/dashboard.module').then(
        (m) => m.FuseDashboardModule
      ),
    canActivate: [TokenGuard, LoginGuard],
  },
  {
    path: 'main/create-tenant',
    loadChildren: () =>
      import(
        './_components/common/user_management/create-tenant/create-tenant.module'
      ).then((m) => m.CreateTenantModule),
    canActivate: [TokenGuard, LoginGuard],
  },
  {
    path: 'main/achievements/overview',
    loadChildren: () =>
      import(
        './_components/volunteer/task-management/achievements/management-summary/management-summary.module'
      ).then((m) => m.ManagementSummary),
    canActivate: [TokenGuard, VolunteerGuard],
  },
  {
    path: 'main/achievements/details',
    loadChildren: () =>
      import(
        './_components/volunteer/task-management/achievements/achievements.module'
      ).then((m) => m.AchievementsModule),
    canActivate: [TokenGuard, VolunteerGuard],
  },

  {
    path: 'main/get-connected',
    loadChildren: () =>
      import(
        './_components/volunteer/social-management/get-connected/get-connected.module'
      ).then((m) => m.FuseGetConnectedModule),
    canActivate: [TokenGuard, VolunteerGuard],
  },
  {
    path: 'main/task-select',
    loadChildren: () =>
      import(
        './_components/help-seeker/task-management/task-select/task-select.module'
      ).then((m) => m.FuseTaskSelectModule),
    canActivate: [TokenGuard, HelpSeekerOrTenantAdminGuard],
  },
  {
    path: 'main/tasks/all',
    loadChildren: () =>
      import(
        './_components/help-seeker/task-management/task-list/task-list.module'
      ).then((m) => m.FuseTaskListModule),
    canActivate: [TokenGuard, HelpSeekerOrTenantAdminGuard],
  },
  {
    path: 'main/properties/all',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/property-configurator/list/property-list.module'
      ).then((m) => m.PropertyListModule),
    canActivate: [TokenGuard, FlexProdOrTenantAdminGuard],
  },
  {
    path: 'main/class-configurator',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/class-configurator/configurator.module'
      ).then((m) => m.ConfiguratorModule),
    canActivate: [TokenGuard, FlexProdOrTenantAdminGuard],
  },
  {
    path: 'main/recruitment',
    loadChildren: () =>
      import('./_components/recruiter/recruit-view/recruit-view.module').then(
        (m) => m.RecruitViewModule
      ),
    canActivate: [TokenGuard, RecruiterGuard],
  },
  {
    path: 'main/marketplace/all',
    loadChildren: () =>
      import(
        './_components/admin/marketplace-list/marketplace-list.module'
      ).then((m) => m.FuseMarketplaceListModule),
    canActivate: [TokenGuard, AdminGuard],
  },
  {
    path: 'main/marketplace-form',
    loadChildren: () =>
      import(
        './_components/admin/marketplace-form/marketplace-form.module'
      ).then((m) => m.FuseMarketplaceFormModule),
    canActivate: [TokenGuard, AdminGuard],
  },
  {
    path: 'main/edit-tenant',
    loadChildren: () =>
      import('./_components/admin/tenant-form/tenant-form.module').then(
        (m) => m.FuseTenantFormModule
      ),
    canActivate: [TokenGuard, AdminOrTenantAdminGuard],
  },
  {
    path: 'main/import',
    loadChildren: () =>
      import('./_components/help-seeker/import/import.module').then(
        (m) => m.ImportModule
      ),
    canActivate: [TokenGuard, HelpSeekerOrTenantAdminGuard],
  },
  {
    path: 'main/profile',
    loadChildren: () =>
      import('./_components/common/profile/profile.module').then(
        (m) => m.ProfileModule
      ),
    canActivate: [TokenGuard, LoginGuard],
  },
  {
    path: 'main/matching-configurator',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/matching-configurator/matching-configurator.module'
      ).then((m) => m.MatchingConfiguratorModule),
    canActivate: [TokenGuard, TenantAdminGuard],
  },
  {
    path: 'main/details',
    loadChildren: () =>
      import(
        './_components/common/class-instance-details/class-instance-details.module'
      ).then((m) => m.ClassInstanceDetailsModule),
    canActivate: [TokenGuard, LoginGuard],
  },
];

@NgModule({
  declarations: [FuseContentComponent],
  imports: [RouterModule.forChild(routes), FuseSharedModule, ShareMenuModule],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
  ],
  exports: [FuseContentComponent, ShareMenuComponent],
})
export class FuseContentModule { }
