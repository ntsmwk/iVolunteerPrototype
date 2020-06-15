import { NgModule } from '@angular/core';
import { Route, RouterModule } from '@angular/router';
import { FuseSharedModule } from '@fuse/shared.module';
import { FuseContentComponent } from 'app/main/content/content.component';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './_interceptor/token.interceptor';
import { Http401Interceptor } from './_interceptor/http-401.interceptor';
import { TokenGuard } from './_guard/token.guard';
import { HelpSeekerGuard } from './_guard/help-seeker.guard';
import { VolunteerGuard } from './_guard/volunteer.guard';
import { LoginGuard } from './_guard/login.guard';
import { FlexProdOrHelpseekerGuard } from './_guard/flexprod-helpseeker.guard';
import { RecruiterGuard } from './_guard/recruiter.guard';
import { ShareMenuComponent } from './_components/volunteer/task-management/achievements/share-menu/share-menu.component';
import { ShareMenuModule } from './_components/volunteer/task-management/achievements/share-menu/share-menu.module';
import { AnonymGuard } from './_guard/anonym.guard';
import { AdminGuard } from './_guard/admin.guard';

const routes: Route[] = [
  {
    path: 'login',
    loadChildren: () =>
      import('./_components/common/user_management/login/login.module').then(
        (m) => m.FuseLoginModule
      ),
    canActivate: [AnonymGuard],
  },
  {
    path: 'register',
    loadChildren: () =>
      import(
        './_components/common/user_management/registration/registration.module'
      ).then((m) => m.FuseRegistrationModule),
    // canActivate: [AnonymGuard]
  },
  {
    path: 'main/volunteer/asset-inbox',
    loadChildren: () =>
      import(
        './_components/volunteer/common/asset-inbox-volunteer/asset-inbox-volunteer.module'
      ).then((m) => m.AssetInboxVolunteerModule),
    canActivate: [TokenGuard, VolunteerGuard],
  },
  {
    path: 'main/helpseeker/asset-inbox',
    loadChildren: () =>
      import(
        './_components/help-seeker/asset-inbox-helpseeker/asset-inbox-helpseeker.module'
      ).then((m) => m.AssetInboxHelpseekerModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },
  {
    path: 'main/volunteer/asset-inbox/confirm',
    loadChildren: () =>
      import(
        './_components/volunteer/common/asset-inbox-volunteer/confirmation-screen/confirmation-screen.module'
      ).then((m) => m.VolunteerConfirmationScreenModule),
    canActivate: [TokenGuard, VolunteerGuard],
  },
  {
    path: 'main/helpseeker/asset-inbox/confirm',
    loadChildren: () =>
      import(
        './_components/help-seeker/asset-inbox-helpseeker/confirmation-screen/confirmation-screen.module'
      ).then((m) => m.HelpseekerConfirmationScreenModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
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
    path: 'main/engagements',
    loadChildren: () =>
      import(
        './_components/volunteer/task-management/engagements/engagements.module'
      ).then((m) => m.FuseEngagementsModule),
    canActivate: [TokenGuard, VolunteerGuard],
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
    path: 'main/get-engaged',
    loadChildren: () =>
      import(
        './_components/volunteer/task-management/get-engaged/get-engaged.module'
      ).then((m) => m.FuseGetEngagedModule),
    canActivate: [TokenGuard, VolunteerGuard],
  },
  {
    path: 'main/project-form',
    loadChildren: () =>
      import(
        './_components/help-seeker/task-management/project-form/project-form.module'
      ).then((m) => m.FuseProjectFormModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },
  {
    path: 'main/projects/all',
    loadChildren: () =>
      import(
        './_components/help-seeker/task-management/project-list/project-list.module'
      ).then((m) => m.FuseProjectListModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },
  {
    path: 'main/task',
    loadChildren: () =>
      import(
        './_components/help-seeker/task-management/task-detail/task-detail.module'
      ).then((m) => m.FuseTaskDetailModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },
  {
    path: 'main/task-form',
    loadChildren: () =>
      import(
        './_components/help-seeker/task-management/task-form/task-form.module'
      ).then((m) => m.FuseTaskFormModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },
  {
    path: 'main/task-select',
    loadChildren: () =>
      import(
        './_components/help-seeker/task-management/task-select/task-select.module'
      ).then((m) => m.FuseTaskSelectModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },
  {
    path: 'main/tasks/all',
    loadChildren: () =>
      import(
        './_components/help-seeker/task-management/task-list/task-list.module'
      ).then((m) => m.FuseTaskListModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },
  {
    path: 'main/properties/all',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/property-enum-configurator/property-enum-list/property-enum-list.module'
      ).then((m) => m.PropertyEnumListModule),
    canActivate: [TokenGuard, FlexProdOrHelpseekerGuard],
  },

  {
    path: 'main/property/detail/view',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/property-enum-configurator/property-detail/property-detail.module'
      ).then((m) => m.PropertyDetailModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },
  {
    path: 'main/property-builder',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/property-enum-configurator/builder/property-build-form.module'
      ).then((m) => m.PropertyBuildFormModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },
  {
    path: 'main/task-templates/user/all',
    loadChildren: () =>
      import(
        './_components/help-seeker/task-management/user-defined-task-template-list/user-defined-task-template-list.module'
      ).then((m) => m.UserDefinedTaskTemplateListModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },

  {
    path: 'main/task-templates/user/detail/single',
    loadChildren: () =>
      import(
        './_components/help-seeker/task-management/user-defined-task-template-detail-single/user-defined-task-template-detail-single.module'
      ).then((m) => m.SingleUserDefinedTaskTemplateDetailModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },

  {
    path: 'main/task-templates/user/edit',
    loadChildren: () =>
      import(
        './_components/help-seeker/task-management/user-defined-task-template-detail-form-single/user-defined-task-template-detail-form-single.module'
      ).then((m) => m.SingleUserDefinedTaskTemplateDetailFormModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },

  {
    path: 'main/task-templates/user/detail/nested',
    loadChildren: () =>
      import(
        './_components/help-seeker/task-management/user-defined-task-template-detail-nested/user-defined-task-template-detail-nested.module'
      ).then((m) => m.NestedUserDefinedTaskTemplateDetailModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },

  {
    path: 'main/configurator',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/class-configurator/configurator.module'
      ).then((m) => m.ConfiguratorModule),
    canActivate: [TokenGuard, FlexProdOrHelpseekerGuard],
  },

  {
    path: 'main/configurator/instance-editor',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/class-instances/form-editor/class-instance-form-editor.module'
      ).then((m) => m.ClassInstanceFormEditorModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },
  {
    path: 'main/rules/all',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/rule-view/rule-overview.module'
      ).then((m) => m.FuseRuleOverviewModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },
  {
    path: 'main/rule',
    loadChildren: () =>
      import(
        './_components/help-seeker/configuration/rule-configurator/rule-configurator.module'
      ).then((m) => m.FuseRuleConfiguratorModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },

  {
    path: 'main/task-template-form',
    loadChildren: () =>
      import(
        './_components/help-seeker/task-management/task-template-form/task-template-form.module'
      ).then((m) => m.FuseTaskTemplateFormModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
  },
  {
    path: 'main/task-templates/all',
    loadChildren: () =>
      import(
        './_components/help-seeker/task-management/task-template-list/task-template-list.module'
      ).then((m) => m.FuseTaskTemplateListModule),
    canActivate: [TokenGuard, HelpSeekerGuard],
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
    path: 'main/tenant-form',
    loadChildren: () =>
      import('./_components/admin/tenant-form/tenant-form.module').then(
        (m) => m.FuseTenantFormModule
      ),
    canActivate: [TokenGuard, AdminGuard],
  },
  {
    path: 'main/import',
    loadChildren: () =>
      import('./_components/help-seeker/import/import.module').then(
        (m) => m.ImportModule
      ),
    canActivate: [TokenGuard, HelpSeekerGuard],
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
    canActivate: [TokenGuard, HelpSeekerGuard],
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
    { provide: HTTP_INTERCEPTORS, useClass: Http401Interceptor, multi: true },
  ],
  exports: [FuseContentComponent, ShareMenuComponent],
})
export class FuseContentModule { }
