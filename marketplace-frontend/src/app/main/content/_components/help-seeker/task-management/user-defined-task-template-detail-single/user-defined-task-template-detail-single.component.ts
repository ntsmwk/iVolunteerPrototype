import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { QuestionService } from 'app/main/content/_service/question.service';
import { DialogFactoryDirective } from 'app/main/content/_shared_components/dialogs/_dialog-factory/dialog-factory.component';
import { ParticipantRole } from 'app/main/content/_model/participant';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { UserDefinedTaskTemplate } from 'app/main/content/_model/user-defined-task-template';
import { QuestionBase } from 'app/main/content/_model/dynamic-forms/questions';
import { PropertyItem, PropertyDefinition } from 'app/main/content/_model/meta/property';
import { LoginService } from 'app/main/content/_service/login.service';
import { CoreMarketplaceService } from 'app/main/content/_service/core-marketplace.service';
import { UserDefinedTaskTemplateService } from 'app/main/content/_service/user-defined-task-template.service';
import { PropertyDefinitionService } from 'app/main/content/_service/meta/core/property/property-definition.service';
import { SortDialogData } from 'app/main/content/_shared_components/dialogs/sort-dialog/sort-dialog.component';
import { isNullOrUndefined } from 'util';

@Component({
  selector: 'user-defined-task-template-detail-single',
  templateUrl: './user-defined-task-template-detail-single.html',
  styleUrls: ['./user-defined-task-template-detail-single.scss'],
  providers: [QuestionService, DialogFactoryDirective]
})
export class SingleUserDefinedTaskTemplateDetailComponent implements OnInit {

  role: ParticipantRole;
  helpseeker: Helpseeker;
  marketplace: Marketplace;
  template: UserDefinedTaskTemplate;
  isLoaded: boolean;
  dialogIds: string[];
  questions: QuestionBase<any>[];
  allPropertiesList: PropertyItem[];

  constructor(private router: Router,
    private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private userDefinedTaskTemplateService: UserDefinedTaskTemplateService,
    private questionService: QuestionService,
    private propertyDefinitionService: PropertyDefinitionService,
    private dialogFactory: DialogFactoryDirective
  ) {
    this.isLoaded = false;
  }

  ngOnInit() {

    Promise.all([
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => this.role = role),
      this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => this.helpseeker = helpseeker),
    ]).then(() => {
      this.route.params.subscribe(params => this.loadPropertiesFromTemplate(params['marketplaceId'], params['templateId']));
    });
  }

  loadPropertiesFromTemplate(marketplaceId: string, templateId: string): void {
    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.marketplace = marketplace;
      this.userDefinedTaskTemplateService.getTemplate(marketplace, templateId).toPromise().then((template: UserDefinedTaskTemplate) => {
        this.template = template;

      })
        .then(() => {
          this.propertyDefinitionService.getAllPropertyDefinitons(this.marketplace, this.helpseeker.tenantId).toPromise().then((propertyDefinitions: PropertyDefinition<any>[]) => {
            this.allPropertiesList = propertyDefinitions;
          }).then(() => {
            if (!isNullOrUndefined(this.template)) {
              this.questions = this.questionService.getQuestionsFromProperties(this.template.templateProperties);
            }
            this.isLoaded = true;
          });
        });
    });
  }


  loadAllPropertyDefinitions() {
    this.propertyDefinitionService.getAllPropertyDefinitons(this.marketplace, this.helpseeker.tenantId).toPromise().then((propertyDefinitions: PropertyDefinition<any>[]) => {
      this.allPropertiesList = propertyDefinitions;
    });
  }


  navigateBack() {
    window.history.back();
  }

  navigateEditForm() {
    this.router.navigate([`/main/task-templates/user/edit/${this.marketplace.id}/${this.template.id}`], { queryParams: { ref: 'single' } });
  }

  deleteTemplate() {
    this.dialogFactory.confirmationDialog(
      'Are you sure?', 'Are you sure you want to delete this Template? This action cannot be reverted')
      .then((cont: boolean) => {
        if (cont) {
          this.userDefinedTaskTemplateService.deleteRootTaskTemplate(this.marketplace, this.template.id).toPromise().then((success: boolean) => {
            this.navigateBack();
          });
        }
      });
  }



  addPropertyDialog() {

    // this.dialogFactory.addPropertyDialog(this.template, this.allPropertiesList).then((propIds: string[]) => {
    //   if (!isNullOrUndefined(propIds)) {
    //     this.userDefinedTaskTemplateService.addPropertiesToSingleTemplate(this.marketplace, this.template.id, propIds).toPromise().then(() => {
    //       this.refresh();
    //     });
    //   }
    // });
  }

  removePropertyDialog() {
    // this.dialogFactory.removePropertyDialog(this.template).then((propIds: string[]) => {
    //   if (!isNullOrUndefined(propIds)) {
    //     this.userDefinedTaskTemplateService.removePropertiesFromSingleTemplate(this.marketplace, this.template.id, propIds).toPromise().then(() => {
    //       this.refresh();
    //     });
    //   }
    // });

  }

  // TODO
  changePropertyOrderDialog() {

    this.dialogFactory.changePropertyOrderDialog(this.template.templateProperties).then((data: SortDialogData) => {
      if (!isNullOrUndefined(data)) {
        for (let i = 0; i < data.list.length; i++) {
          data.list[i].order = i;
        }

        this.userDefinedTaskTemplateService.updatePropertyOrderSingle(this.marketplace, this.template.id, data.list).toPromise().then((ret: UserDefinedTaskTemplate) => {
          this.refresh();
        });

      }

    });
  }

  editDescriptionDialog() {

    this.dialogFactory.editTemplateDescriptionDialog(this.template).then((description: string) => {
      if (!isNullOrUndefined(description)) {
        this.userDefinedTaskTemplateService
          .updateRootTaskTemplate(this.marketplace, this.template.id, null, description)
          .toPromise()
          .then((updatedTemplate: UserDefinedTaskTemplate) => {
            this.template.description = updatedTemplate.description;
          });
      }
    });
  }

  editNameDialog() {

    this.dialogFactory.editTemplateNameDialog(this.template).then((name: string) => {
      if (!isNullOrUndefined(name)) {
        this.userDefinedTaskTemplateService.updateRootTaskTemplate(this.marketplace, this.template.id, name, null).toPromise().then((updatedTemplate: UserDefinedTaskTemplate) => {
          this.template.name = updatedTemplate.name;
        });
      }
    });
  }

  private refresh() {
    this.isLoaded = false;
    this.ngOnInit();
  }
}




