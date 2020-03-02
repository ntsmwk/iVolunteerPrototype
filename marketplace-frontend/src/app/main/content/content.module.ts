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
import { FlexProdOrHelpseekerGuard } from "./_guard/flexprod-helpseeker.guard";
import { RecruiterGuard } from "./_guard/recruiter.guard";
import { DataTransportService } from "./_service/data-transport/data-transport.service";
import { ShareMenuComponent } from "./achievements/share-menu/share-menu.component";
import { ShareMenuModule } from "./achievements/share-menu/share-menu.module";
import { AnonymGuard } from "./_guard/anonym.guard";

const routes: Route[] = [
  {
    path: "login",
    loadChildren: () =>
      import("./login/login.module").then(m => m.FuseLoginModule),
    canActivate: [AnonymGuard]
  },
  {
    path: "register",
    loadChildren: () =>
      import("./registration/registration.module").then(
        m => m.FuseRegistrationModule
      )
    // canActivate: [AnonymGuard]
  },
  {
    path: "main/volunteer/asset-inbox",
    loadChildren: () =>
      import("./asset-inbox-volunteer/asset-inbox-volunteer.module").then(
        m => m.AssetInboxVolunteerModule
      ),
    canActivate: [TokenGuard, VolunteerGuard]
  },
  {
    path: "main/helpseeker/asset-inbox",
    loadChildren: () =>
      import("./asset-inbox-helpseeker/asset-inbox-helpseeker.module").then(
        m => m.AssetInboxHelpseekerModule
      ),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: "main/volunteer/asset-inbox/confirm",
    loadChildren: () =>
      import(
        "./asset-inbox-volunteer/confirmation-screen/confirmation-screen.module"
      ).then(m => m.VolunteerConfirmationScreenModule),
    canActivate: [TokenGuard, VolunteerGuard]
  },
  {
    path: "main/helpseeker/asset-inbox/confirm",
    loadChildren: () =>
      import(
        "./asset-inbox-helpseeker/confirmation-screen/confirmation-screen.module"
      ).then(m => m.HelpseekerConfirmationScreenModule),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: "main/dashboard",
    loadChildren: () =>
      import("./dashboard/dashboard.module").then(m => m.FuseDashboardModule),
    canActivate: [TokenGuard, LoginGuard]
  },
  {
    path: "main/engagements",
    loadChildren: () =>
      import("./engagements/engagements.module").then(
        m => m.FuseEngagementsModule
      ),
    canActivate: [TokenGuard, VolunteerGuard]
  },

  {
    path: "main/achievements/summary",
    loadChildren: () =>
      import(
        "./achievements/achievements-management-summary/achievements-management-summary.module"
      ).then(m => m.AchievementsManagementSummary),
    canActivate: [TokenGuard, VolunteerGuard]
  },
  {
    path: "main/achievements/fireBrigade",
    loadChildren: () =>
      import(
        "./achievements/achievements-fire-brigade/achievements-fire-brigade.module"
      ).then(m => m.AchievementsFireBrigadeModule),
    canActivate: [TokenGuard, VolunteerGuard]
  },
  {
    path: "main/achievements/music",
    loadChildren: () =>
      import(
        "./achievements/achievements-music/achievements-music.module"
      ).then(m => m.AchievementsMusicModule),
    canActivate: [TokenGuard, VolunteerGuard]
  },

  {
    path: "main/get-connected",
    loadChildren: () =>
      import("./get-connected/get-connected.module").then(
        m => m.FuseGetConnectedModule
      ),
    canActivate: [TokenGuard, VolunteerGuard]
  },
  {
    path: "main/get-engaged",
    loadChildren: () =>
      import("./get-engaged/get-engaged.module").then(
        m => m.FuseGetEngagedModule
      ),
    canActivate: [TokenGuard, VolunteerGuard]
  },
  {
    path: "main/project-form",
    loadChildren: () =>
      import("./project-form/project-form.module").then(
        m => m.FuseProjectFormModule
      ),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: "main/projects/all",
    loadChildren: () =>
      import("./project-list/project-list.module").then(
        m => m.FuseProjectListModule
      ),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: "main/task",
    loadChildren: () =>
      import("./task-detail/task-detail.module").then(
        m => m.FuseTaskDetailModule
      ),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: "main/task-form",
    loadChildren: () =>
      import("./task-form/task-form.module").then(m => m.FuseTaskFormModule),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: "main/task-select",
    loadChildren: () =>
      import("./task-select/task-select.module").then(
        m => m.FuseTaskSelectModule
      ),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: "main/tasks/all",
    loadChildren: () =>
      import("./task-list/task-list.module").then(m => m.FuseTaskListModule),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: "main/properties/all",
    loadChildren: () =>
      import("./property-list/property-list.module").then(
        m => m.PropertyListModule
      ),
    canActivate: [TokenGuard, FlexProdOrHelpseekerGuard]
  },

  {
    path: "main/property/detail/view",
    loadChildren: () =>
      import("./property-detail/property-detail.module").then(
        m => m.PropertyDetailModule
      ),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: "main/property/detail/edit",
    loadChildren: () =>
      import("./property-build-form/property-build-form.module").then(
        m => m.PropertyBuildFormModule
      ),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: "main/task-templates/user/all",
    loadChildren: () =>
      import(
        "./user-defined-task-template-list/user-defined-task-template-list.module"
      ).then(m => m.UserDefinedTaskTemplateListModule),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },

  {
    path: "main/task-templates/user/detail/single",
    loadChildren: () =>
      import(
        "./user-defined-task-template-detail-single/user-defined-task-template-detail-single.module"
      ).then(m => m.SingleUserDefinedTaskTemplateDetailModule),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },

  {
    path: "main/task-templates/user/edit",
    loadChildren: () =>
      import(
        "./user-defined-task-template-detail-form-single/user-defined-task-template-detail-form-single.module"
      ).then(m => m.SingleUserDefinedTaskTemplateDetailFormModule),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },

  {
    path: "main/task-templates/user/detail/nested",
    loadChildren: () =>
      import(
        "./user-defined-task-template-detail-nested/user-defined-task-template-detail-nested.module"
      ).then(m => m.NestedUserDefinedTaskTemplateDetailModule),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },

  {
    path: "main/test-map-property",
    loadChildren: () =>
      import(
        "./_components/dynamic-forms/dynamic-form-question/map-property-test/map-property-test.module"
      ).then(m => m.MapPropertyTestModule),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },

  {
    path: "main/configurator",
    loadChildren: () =>
      import("./configurator/configurator.module").then(
        m => m.ConfiguratorModule
      ),
    canActivate: [TokenGuard, FlexProdOrHelpseekerGuard]
  },

  {
    path: "main/configurator/instance-editor",
    loadChildren: () =>
      import(
        "./configurator/class-instances/form-editor/class-instance-form-editor.module"
      ).then(m => m.ClassInstanceFormEditorModule),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: "main/rules/all",
    loadChildren: () =>
      import("./rule-view/rule-overview.module").then(
        m => m.FuseRuleOverviewModule
      ),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: "main/rule",
    loadChildren: () =>
      import("./rule-configurator/rule-configurator.module").then(
        m => m.FuseRuleConfiguratorModule
      ),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },

  {
    path: "main/task-template-form",
    loadChildren: () =>
      import("./task-template-form/task-template-form.module").then(
        m => m.FuseTaskTemplateFormModule
      ),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: "main/task-templates/all",
    loadChildren: () =>
      import("./task-template-list/task-template-list.module").then(
        m => m.FuseTaskTemplateListModule
      ),
    canActivate: [TokenGuard, HelpSeekerGuard]
  },
  {
    path: "main/recruitment",
    loadChildren: () =>
      import("./recruit-view/recruit-view.module").then(
        m => m.RecruitViewModule
      ),
    canActivate: [TokenGuard, RecruiterGuard]
  }
];

@NgModule({
  declarations: [FuseContentComponent],
  imports: [RouterModule.forChild(routes), FuseSharedModule, ShareMenuModule],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: Http401Interceptor, multi: true },
    DataTransportService
  ],
  exports: [FuseContentComponent, ShareMenuComponent]
})
export class FuseContentModule {}
