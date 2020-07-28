import { NgModule } from "@angular/core";
import { Route, RouterModule } from "@angular/router";
import { FuseSharedModule } from "@fuse/shared.module";
import { FuseContentComponent } from "app/main/content/content.component";
import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { TokenInterceptor } from "./_interceptor/token.interceptor";
import { Http401Interceptor } from "./_interceptor/http-401.interceptor";
import { TokenGuard } from "./_guard/token.guard";
import { HelpSeekerGuard } from "./_guard/help-seeker.guard";
import { VolunteerGuard } from "./_guard/volunteer.guard";
import { LoginGuard } from "./_guard/login.guard";
import { FlexProdOrTenantAdminGuard } from "./_guard/flexprod-helpseeker.guard";
import { RecruiterGuard } from "./_guard/recruiter.guard";
import { ShareMenuComponent } from "./_components/volunteer/task-management/achievements/share-menu/share-menu.component";
import { ShareMenuModule } from "./_components/volunteer/task-management/achievements/share-menu/share-menu.module";
import { AnonymGuard } from "./_guard/anonym.guard";
import { AdminGuard } from "./_guard/admin.guard";
import { TenantAdminGuard } from "./_guard/tenant-admin.guard";
import { HelpSeekerOrTenantAdminGuard } from "./_guard/helpseeker-tenantAdmin.guard";

const routes: Route[] = [
  {
    path: "login",
    loadChildren: () =>
      import("./_components/common/user_management/login/login.module").then(
        (m) => m.FuseLoginModule
      ),
    canActivate: [AnonymGuard],
  },
  {
    path: "register",
    loadChildren: () =>
      import(
        "./_components/common/user_management/registration/registration.module"
      ).then((m) => m.FuseRegistrationModule),
    // canActivate: [AnonymGuard]
  },
  {
    path: "role",
    loadChildren: () =>
      import(
        "./_components/common/user_management/role-switch/role-switch.module"
      ).then((m) => m.FuseRoleSwitchModule),
    // canActivate: [TokenGuard, VolunteerGuard, HelpSeekerGuard],
  },
  {
    path: "main/volunteer/asset-inbox",
    loadChildren: () =>
      import(
        "./_components/volunteer/common/asset-inbox-volunteer/asset-inbox-volunteer.module"
      ).then((m) => m.AssetInboxVolunteerModule),
    canActivate: [TokenGuard, VolunteerGuard],
  },
  {
    path: "main/helpseeker/asset-inbox",
    loadChildren: () =>
      import(
        "./_components/help-seeker/asset-inbox-helpseeker/asset-inbox-helpseeker.module"
      ).then((m) => m.AssetInboxHelpseekerModule),
    canActivate: [TokenGuard, HelpSeekerOrTenantAdminGuard],
  },
  {
    path: "main/volunteer/asset-inbox/confirm",
    loadChildren: () =>
      import(
        "./_components/volunteer/common/asset-inbox-volunteer/confirmation-screen/confirmation-screen.module"
      ).then((m) => m.VolunteerConfirmationScreenModule),
    canActivate: [TokenGuard, VolunteerGuard],
  },
  {
    path: "main/helpseeker/asset-inbox/confirm",
    loadChildren: () =>
      import(
        "./_components/help-seeker/asset-inbox-helpseeker/confirmation-screen/confirmation-screen.module"
      ).then((m) => m.HelpseekerConfirmationScreenModule),
    canActivate: [TokenGuard, HelpSeekerOrTenantAdminGuard],
  },
  {
    path: "main/dashboard",
    loadChildren: () =>
      import("./_components/common/dashboard/dashboard.module").then(
        (m) => m.FuseDashboardModule
      ),
    canActivate: [TokenGuard, LoginGuard],
  },
  {
    path: "main/achievements/overview",
    loadChildren: () =>
      import(
        "./_components/volunteer/task-management/achievements/management-summary/management-summary.module"
      ).then((m) => m.ManagementSummary),
    canActivate: [TokenGuard, VolunteerGuard],
  },
  {
    path: "main/achievements/details",
    loadChildren: () =>
      import(
        "./_components/volunteer/task-management/achievements/achievements.module"
      ).then((m) => m.AchievementsModule),
    canActivate: [TokenGuard, VolunteerGuard],
  },

  {
    path: "main/get-connected",
    loadChildren: () =>
      import(
        "./_components/volunteer/social-management/get-connected/get-connected.module"
      ).then((m) => m.FuseGetConnectedModule),
    canActivate: [TokenGuard, VolunteerGuard],
  },
  {
    path: "main/task-select",
    // TODO tenantAdmin ???
    loadChildren: () =>
      import(
        "./_components/help-seeker/task-management/task-select/task-select.module"
      ).then((m) => m.FuseTaskSelectModule),
    canActivate: [TokenGuard, TenantAdminGuard],
  },
  {
    path: "main/tasks/all",
    loadChildren: () =>
      import(
        "./_components/help-seeker/task-management/task-list/task-list.module"
      ).then((m) => m.FuseTaskListModule),
    canActivate: [TokenGuard, HelpSeekerOrTenantAdminGuard],
  },
  {
    path: "main/properties/all",
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/property-enum-configurator/list/property-enum-list.module"
      ).then((m) => m.PropertyEnumListModule),
    canActivate: [TokenGuard, FlexProdOrTenantAdminGuard],
  },

  {
    path: "main/property/detail/view",
    // TODO helpseeker and tenantAdmin ???
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/property-enum-configurator/detail/property-detail.module"
      ).then((m) => m.PropertyDetailModule),
    canActivate: [TokenGuard, HelpSeekerOrTenantAdminGuard],
  },
  {
    path: "main/property-builder",
    // TODO tenantAdmin ???
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/property-enum-configurator/builder/property-build-form.module"
      ).then((m) => m.PropertyBuildFormModule),
    canActivate: [TokenGuard, TenantAdminGuard],
  },
  {
    path: "main/configurator",
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/class-configurator/configurator.module"
      ).then((m) => m.ConfiguratorModule),
    canActivate: [TokenGuard, FlexProdOrTenantAdminGuard],
  },

  {
    path: "main/configurator/instance-editor",
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/class-instances/form-editor/class-instance-form-editor.module"
      ).then((m) => m.ClassInstanceFormEditorModule),
    canActivate: [TokenGuard, TenantAdminGuard],
  },
  {
    path: "main/rules/all",
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/rule-view/rule-overview.module"
      ).then((m) => m.FuseRuleOverviewModule),
    canActivate: [TokenGuard, TenantAdminGuard],
  },
  {
    path: "main/rule",
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/rule-configurator/rule-configurator.module"
      ).then((m) => m.FuseRuleConfiguratorModule),
    canActivate: [TokenGuard, TenantAdminGuard],
  },
  {
    path: "main/recruitment",
    loadChildren: () =>
      import("./_components/recruiter/recruit-view/recruit-view.module").then(
        (m) => m.RecruitViewModule
      ),
    canActivate: [TokenGuard, RecruiterGuard],
  },
  {
    path: "main/marketplace/all",
    loadChildren: () =>
      import(
        "./_components/admin/marketplace-list/marketplace-list.module"
      ).then((m) => m.FuseMarketplaceListModule),
    canActivate: [TokenGuard, AdminGuard],
  },
  {
    path: "main/marketplace-form",
    loadChildren: () =>
      import(
        "./_components/admin/marketplace-form/marketplace-form.module"
      ).then((m) => m.FuseMarketplaceFormModule),
    canActivate: [TokenGuard, AdminGuard],
  },
  {
    path: "main/tenant-form",
    loadChildren: () =>
      import("./_components/admin/tenant-form/tenant-form.module").then(
        (m) => m.FuseTenantFormModule
      ),
    canActivate: [TokenGuard, AdminGuard],
  },
  {
    path: "main/import",
    loadChildren: () =>
      import("./_components/help-seeker/import/import.module").then(
        (m) => m.ImportModule
      ),
    canActivate: [TokenGuard, HelpSeekerOrTenantAdminGuard],
  },
  {
    path: "main/profile",
    loadChildren: () =>
      import("./_components/common/profile/profile.module").then(
        (m) => m.ProfileModule
      ),
    canActivate: [TokenGuard, LoginGuard],
  },
  {
    path: "main/matching-configurator",
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/matching-configurator/matching-configurator.module"
      ).then((m) => m.MatchingConfiguratorModule),
    canActivate: [TokenGuard, TenantAdminGuard],
  },
  {
    path: "main/details",
    loadChildren: () =>
      import(
        "./_components/common/class-instance-details/class-instance-details.module"
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
export class FuseContentModule {}
