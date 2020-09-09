import { NgModule } from "@angular/core";
import { Route, RouterModule } from "@angular/router";
import { FuseSharedModule } from "@fuse/shared.module";
import { FuseContentComponent } from "app/main/content/content.component";
import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { TokenInterceptor } from "./_interceptor/token.interceptor";
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
    path: "register/volunteer",
    loadChildren: () =>
      import(
        "./_components/common/user_management/registration/registration.module"
      ).then((m) => m.FuseRegistrationModule),
    canActivate: [AnonymGuard],
  },

  {
    path: "register/activate",
    loadChildren: () =>
      import(
        "./_components/common/user_management/activation/activation.module"
      ).then((m) => m.ActivationModule),
    canActivate: [AnonymGuard],
  },
  {
    path: "role",
    loadChildren: () =>
      import(
        "./_components/common/user_management/role-switch/role-switch.module"
      ).then((m) => m.FuseRoleSwitchModule),
    // canActivate: [],
  },
  {
    path: "main/volunteer/asset-inbox",
    loadChildren: () =>
      import(
        "./_components/volunteer/common/asset-inbox-volunteer/asset-inbox-volunteer.module"
      ).then((m) => m.AssetInboxVolunteerModule),
    canActivate: [VolunteerGuard],
  },
  {
    path: "main/helpseeker/asset-inbox",
    loadChildren: () =>
      import(
        "./_components/help-seeker/asset-inbox-helpseeker/asset-inbox-helpseeker.module"
      ).then((m) => m.AssetInboxHelpseekerModule),
    canActivate: [HelpSeekerOrTenantAdminGuard],
  },
  {
    path: "main/volunteer/asset-inbox/confirm",
    loadChildren: () =>
      import(
        "./_components/volunteer/common/asset-inbox-volunteer/confirmation-screen/confirmation-screen.module"
      ).then((m) => m.VolunteerConfirmationScreenModule),
    canActivate: [VolunteerGuard],
  },
  {
    path: "main/helpseeker/asset-inbox/confirm",
    loadChildren: () =>
      import(
        "./_components/help-seeker/asset-inbox-helpseeker/confirmation-screen/confirmation-screen.module"
      ).then((m) => m.HelpseekerConfirmationScreenModule),
    canActivate: [HelpSeekerOrTenantAdminGuard],
  },
  {
    path: "main/dashboard",
    loadChildren: () =>
      import("./_components/common/dashboard/dashboard.module").then(
        (m) => m.FuseDashboardModule
      ),
    canActivate: [LoginGuard],
  },
  {
    path: "main/achievements/overview",
    loadChildren: () =>
      import(
        "./_components/volunteer/task-management/achievements/management-summary/management-summary.module"
      ).then((m) => m.ManagementSummary),
    canActivate: [VolunteerGuard],
  },
  {
    path: "main/achievements/details",
    loadChildren: () =>
      import(
        "./_components/volunteer/task-management/achievements/achievements.module"
      ).then((m) => m.AchievementsModule),
    canActivate: [VolunteerGuard],
  },

  {
    path: "main/get-connected",
    loadChildren: () =>
      import(
        "./_components/volunteer/social-management/get-connected/get-connected.module"
      ).then((m) => m.FuseGetConnectedModule),
    canActivate: [VolunteerGuard],
  },
  {
    path: "main/task-select",
    loadChildren: () =>
      import(
        "./_components/help-seeker/task-management/task-select/task-select.module"
      ).then((m) => m.FuseTaskSelectModule),
    canActivate: [TenantAdminGuard],
  },
  {
    path: "main/tasks/all",
    loadChildren: () =>
      import(
        "./_components/help-seeker/task-management/task-list/task-list.module"
      ).then((m) => m.FuseTaskListModule),
    canActivate: [HelpSeekerOrTenantAdminGuard],
  },
  {
    path: "main/properties/all",
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/property-configurator/list/property-list.module"
      ).then((m) => m.PropertyListModule),
    canActivate: [FlexProdOrTenantAdminGuard],
  },

  {
    path: "main/property/detail/view",
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/property-configurator/detail/property-detail.module"
      ).then((m) => m.PropertyDetailModule),
    canActivate: [HelpSeekerOrTenantAdminGuard],
  },
  {
    path: "main/property-builder",
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/property-configurator/builder/property-build-form.module"
      ).then((m) => m.PropertyBuildFormModule),
    canActivate: [TenantAdminGuard],
  },
  {
    path: "main/class-configurator",
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/class-configurator/configurator.module"
      ).then((m) => m.ConfiguratorModule),
    canActivate: [FlexProdOrTenantAdminGuard],
  },

  {
    path: "main/instance-editor",
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/class-instance-configurator/form-editor/class-instance-form-editor.module"
      ).then((m) => m.ClassInstanceFormEditorModule),
    canActivate: [TenantAdminGuard],
  },
  {
    path: "main/rules/all",
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/rule-view/rule-overview.module"
      ).then((m) => m.FuseRuleOverviewModule),
    canActivate: [TenantAdminGuard],
  },
  {
    path: "main/rule",
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/rule-configurator/rule-configurator.module"
      ).then((m) => m.FuseRuleConfiguratorModule),
    canActivate: [TenantAdminGuard],
  },
  {
    path: "main/recruitment",
    loadChildren: () =>
      import("./_components/recruiter/recruit-view/recruit-view.module").then(
        (m) => m.RecruitViewModule
      ),
    canActivate: [RecruiterGuard],
  },
  {
    path: "main/marketplace/all",
    loadChildren: () =>
      import(
        "./_components/admin/marketplace-list/marketplace-list.module"
      ).then((m) => m.FuseMarketplaceListModule),
    canActivate: [AdminGuard],
  },
  {
    path: "main/marketplace-form",
    loadChildren: () =>
      import(
        "./_components/admin/marketplace-form/marketplace-form.module"
      ).then((m) => m.FuseMarketplaceFormModule),
    canActivate: [AdminGuard],
  },
  {
    path: "main/tenant-form",
    loadChildren: () =>
      import("./_components/admin/tenant-form/tenant-form.module").then(
        (m) => m.FuseTenantFormModule
      ),
    canActivate: [AdminGuard],
  },
  {
    path: "main/import",
    loadChildren: () =>
      import("./_components/help-seeker/import/import.module").then(
        (m) => m.ImportModule
      ),
    canActivate: [HelpSeekerOrTenantAdminGuard],
  },
  {
    path: "main/profile",
    loadChildren: () =>
      import("./_components/common/profile/profile.module").then(
        (m) => m.ProfileModule
      ),
    canActivate: [LoginGuard],
  },
  {
    path: "main/matching-configurator",
    loadChildren: () =>
      import(
        "./_components/help-seeker/configuration/matching-configurator/matching-configurator.module"
      ).then((m) => m.MatchingConfiguratorModule),
    canActivate: [TenantAdminGuard],
  },
  {
    path: "main/details",
    loadChildren: () =>
      import(
        "./_components/common/class-instance-details/class-instance-details.module"
      ).then((m) => m.ClassInstanceDetailsModule),
    canActivate: [LoginGuard],
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
export class FuseContentModule {}
