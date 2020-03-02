import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserDefinedTaskTemplateService } from '../../../_service/user-defined-task-template.service';
import { QuestionService } from '../../../_service/question.service';
import { ParticipantRole, Participant } from '../../../_model/participant';
import { Marketplace } from '../../../_model/marketplace';
import { UserDefinedTaskTemplate } from '../../../_model/user-defined-task-template';
import { LoginService } from '../../../_service/login.service';
import { CoreMarketplaceService } from '../../../_service/core-marketplace.service';
import { PropertyInstance, PropertyItem, PropertyDefinition, TemplateProperty } from '../../../_model/meta/Property';
import { isNullOrUndefined } from 'util';
import { MatTableDataSource } from '@angular/material/table';
import { DialogFactoryComponent } from '../../../_shared_components/dialogs/_dialog-factory/dialog-factory.component';
import { SortDialogData } from '../../../_shared_components/dialogs/sort-dialog/sort-dialog.component';
import { PropertyInstanceService } from '../../../_service/meta/core/property/property-instance.service';
import { PropertyDefinitionService } from '../../../_service/meta/core/property/property-definition.service';
import { Helpseeker } from '../../../_model/helpseeker';


@Component({
  selector: 'app-user-defined-task-template-detail-nested',
  templateUrl: './user-defined-task-template-detail-nested.component.html',
  styleUrls: ['./user-defined-task-template-detail-nested.component.scss'],
  providers: [QuestionService, DialogFactoryComponent],
})
export class NestedUserDefinedTaskTemplateDetailComponent implements OnInit {

  role: ParticipantRole;
  helpseeker: Helpseeker;
  marketplace: Marketplace;

  allPropertiesList: PropertyItem[];

  template: UserDefinedTaskTemplate;
  isLoaded: boolean;

  dataSources: MatTableDataSource<TemplateProperty<any>>[] = [];
  displayedColumns = ['name', 'value', 'kind', 'actions'];

  expandedPanelMap: boolean[] = [];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private loginService: LoginService,
    private marketplaceService: CoreMarketplaceService,
    private userDefinedTaskTemplateService: UserDefinedTaskTemplateService,
    private propertyInstanceService: PropertyInstanceService,
    private propertyDefinitionService: PropertyDefinitionService,
    private dialogFactory: DialogFactoryComponent,
  ) {
    this.isLoaded = false;
  }


  ngOnInit() {
    console.log("call on init");
    Promise.all([
      this.loginService.getLoggedInParticipantRole().toPromise().then((role: ParticipantRole) => this.role = role),
      this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => this.helpseeker = helpseeker)
    ]).then(() => {
      this.route.params.subscribe(params => this.loadProperty(params['marketplaceId'], params['templateId']));
    });
  }

  loadProperty(marketplaceId: string, templateId: string): void {
    this.marketplaceService.findById(marketplaceId).toPromise().then((marketplace: Marketplace) => {
      this.marketplace = marketplace;

      Promise.all([
        this.userDefinedTaskTemplateService.getTemplate(marketplace, templateId).toPromise().then((template: UserDefinedTaskTemplate) => {
          this.template = template;
        }).then(() => {
          console.log(this.template);
          this.setUpDataSourcesAndExpandedStates();
        }),

        this.propertyDefinitionService.getAllPropertyDefinitons(this.marketplace, this.helpseeker.tenantId).toPromise().then((propertyDefinitions: PropertyDefinition<any>[]) => {
          this.allPropertiesList = propertyDefinitions;
        })
      ]).then(() => {
        //finally, after all of the crap has been loaded from the server, display it
        this.isLoaded = true;
      });
    });
  }

  private setUpDataSourcesAndExpandedStates() {

    for (let t of this.template.templates) {
      let dataSource: MatTableDataSource<TemplateProperty<any>> = new MatTableDataSource<TemplateProperty<any>>();
      dataSource.data = t.templateProperties;
      this.dataSources.push(dataSource);
      this.expandedPanelMap.push(false);
    }
  }


  /**
   *  OnClick Actions
   */

  //actions for the root template
  editDescription() {
    this.dialogFactory.editTemplateDescriptionDialog(this.template).then((description: string) => {
      if (!isNullOrUndefined(description)) {
        this.userDefinedTaskTemplateService.updateRootTaskTemplate(this.marketplace, this.template.id, null, description).toPromise().then((updatedTemplate: UserDefinedTaskTemplate) => {
          this.template.description = updatedTemplate.description;
        });
      }
    });
  }

  editName() {
    this.dialogFactory.editTemplateNameDialog(this.template).then((name: string) => {
      if (!isNullOrUndefined(name)) {
        this.userDefinedTaskTemplateService.updateRootTaskTemplate(this.marketplace, this.template.id, name, null).toPromise().then((updatedTemplate: UserDefinedTaskTemplate) => {
          this.template.name = updatedTemplate.name;
        });
      }
    });
  }

  removeTemplate() {
    this.dialogFactory.confirmationDialog(
      "Are You Sure?",
      "Do you really want to delete this template '" + this.template.name + "'? \n Sub-Templates will be deleted as well. This action cannot be reverted!"
    ).then((cont: boolean) => {
      if (cont) {
        this.userDefinedTaskTemplateService.deleteRootTaskTemplate(this.marketplace, this.template.id).toPromise().then((success: boolean) => {
          if (success) {
            this.navigateBack();
          }
        });
      }
    });
  }





  // actions on each subtemplate (displayed once)
  newSubTemplate() {
    this.dialogFactory.newTaskTemplateDialog().then((result: string[]) => {
      this.userDefinedTaskTemplateService.newNestedSingleTaskTemplate(this.marketplace, this.template.id, result[0], result[1]).toPromise().then((createdTemplate: UserDefinedTaskTemplate) => {

        this.template.templates.push(createdTemplate.templates[createdTemplate.templates.length - 1]);
        let newDataSource = new MatTableDataSource<TemplateProperty<any>>();

        newDataSource.data = createdTemplate.templates[createdTemplate.templates.length - 1].templateProperties;
        this.dataSources.push(newDataSource);
        this.expandedPanelMap.push(true);
        console.log("New: Array lengths: " + this.template.templates.length + " " + this.dataSources.length + " " + this.expandedPanelMap.length)
      });
    });
  }

  removeSubTemplate(subtemplate: UserDefinedTaskTemplate, subTemplateIndex: number) {
    this.dialogFactory.confirmationDialog("Remove Sub-Template " + subtemplate.name + "...",
      "Are you sure you want to delete the Sub-Template " + subtemplate.name + "? \nThis action cannot be reverted")
      .then((cont: boolean) => {
        if (cont) {
          this.userDefinedTaskTemplateService.deleteNestedTaskTemplate(this.marketplace, this.template.id, subtemplate.id).toPromise().then((success: boolean) => {
            if (success) {
              this.template.templates.splice(subTemplateIndex, 1);
              this.dataSources.splice(subTemplateIndex, 1);
              this.expandedPanelMap.splice(subTemplateIndex, 1);
            }
            console.log("Delete: Array lengths: " + this.template.templates.length + " " + this.dataSources.length + " " + this.expandedPanelMap.length)
          });
        }
      });
  }

  editSubTemplateName(subtemplate: UserDefinedTaskTemplate, subTemplateIndex: number) {
    console.log("edit subtemplate name " + subtemplate.name);
    this.dialogFactory.editTemplateNameDialog(subtemplate).then((name: string) => {
      if (!isNullOrUndefined(name)) {
        this.userDefinedTaskTemplateService.updateNestedTaskTemplate(this.marketplace, this.template.id, subtemplate.id, name, null).toPromise().then((updatedTemplate: UserDefinedTaskTemplate) => {
          if (!isNullOrUndefined(updatedTemplate)) {
            this.template.templates[subTemplateIndex].name = updatedTemplate.templates[subTemplateIndex].name;
          }
        });
      }
    });
  }

  editSubTemplateDescription(subtemplate: UserDefinedTaskTemplate, subTemplateIndex: number) {
    console.log("edit subtemplate description " + subtemplate.description);
    this.dialogFactory.editTemplateDescriptionDialog(subtemplate).then((description: string) => {
      if (!isNullOrUndefined(description)) {
        this.userDefinedTaskTemplateService.updateNestedTaskTemplate(this.marketplace, this.template.id, subtemplate.id, null, description).toPromise().then((updatedTemplate: UserDefinedTaskTemplate) => {
          if (!isNullOrUndefined(updatedTemplate)) {
            this.template.templates[subTemplateIndex].description = updatedTemplate.templates[subTemplateIndex].description;
          }
        });
      }
    });
  }

  changeTemplateOrderDialog() {
    this.dialogFactory.changeSubtemplatesOrderDialog(this.template).then((data: SortDialogData) => {

      if (!isNullOrUndefined(data)) {
        console.log(data.list);

        for (let i = 0; i < data.list.length; i++) {
          data.list[i].order = i;
        }

        this.userDefinedTaskTemplateService.updateTemplateOrderNested(this.marketplace, this.template.id, data.list).toPromise().then((ret: UserDefinedTaskTemplate) => {
          console.log(ret);
          this.refresh();
        });
      }

    });
  }


  addProperties(subtemplate: UserDefinedTaskTemplate, subTemplateIndex: number) {
    this.dialogFactory.addPropertyDialog(subtemplate, this.allPropertiesList).then((propIds: string[]) => {
      if (!isNullOrUndefined(propIds)) {
        this.userDefinedTaskTemplateService.addPropertiesToNestedTemplate(this.marketplace, this.template.id, subtemplate.id, propIds).toPromise().then((updatedTemplate: UserDefinedTaskTemplate) => {
          this.template = updatedTemplate;
          this.dataSources[subTemplateIndex].data = updatedTemplate.templates[subTemplateIndex].templateProperties;
          // this.refresh();
        });
      }
    });
  }

  editProperties(subtemplate: UserDefinedTaskTemplate, subTemplateIndex: number) {
    console.log("edit Properties")
    console.log(subtemplate);
    console.log(subTemplateIndex);
    this.router.navigate([`/main/task-templates/user/edit/${this.marketplace.id}/${this.template.id}/${subtemplate.id}`], { queryParams: { ref: 'nested' } });

  }

  changePropertyOrder(subtemplate: UserDefinedTaskTemplate, subTemplateIndex: number) {
    console.log("change Property Order");
    console.log(subtemplate);
    console.log(subTemplateIndex);

    this.dialogFactory.changePropertyOrderDialog(subtemplate.templateProperties).then((data: SortDialogData) => {
      if (!isNullOrUndefined(data)) {
        for (let i = 0; i < data.list.length; i++) {
          data.list[i].order = i;
        }

        this.userDefinedTaskTemplateService.updatePropertyOrderNested(this.marketplace, this.template.id, subtemplate.id, data.list).toPromise().then((updatedTemplate: UserDefinedTaskTemplate) => {
          console.log(updatedTemplate);
          this.template = updatedTemplate;
          this.dataSources[subTemplateIndex].data = updatedTemplate.templates[subTemplateIndex].templateProperties;
          // this.refresh();        
        });
      }
    });
  }

  removeProperties(subtemplate: UserDefinedTaskTemplate, subTemplateIndex: number) {
    this.dialogFactory.removePropertyDialog(subtemplate).then((propIds: string[]) => {
      if (!isNullOrUndefined(propIds)) {
        this.userDefinedTaskTemplateService.removePropertiesFromNestedTemplate(this.marketplace, this.template.id, subtemplate.id, propIds).toPromise().then((updatedTemplate: UserDefinedTaskTemplate) => {
          this.template = updatedTemplate;
          this.dataSources[subTemplateIndex].data = updatedTemplate.templates[subTemplateIndex].templateProperties;
        });
      }
    });
  }

  // actions on each row of each subtemplate
  viewPropertyDetails(subtemplate: UserDefinedTaskTemplate, templateProperty: TemplateProperty<any>, subTemplateIndex: number) {
    console.log("View Property " + templateProperty.name + " from subtemplate " + subtemplate.name + " at index " + subTemplateIndex + "...");
    console.log(templateProperty);

    console.log("route: " + `main/task-templates/user/detail/viewproperty/${this.marketplace.id}/${this.template.id}/${subtemplate.id}/${templateProperty.id}`);

    this.router.navigate([`main/property/detail/view/${this.marketplace.id}/${this.template.id}/${subtemplate.id}/${templateProperty.id}`], { queryParams: { ref: 'subtemplate' } })
  }

  removeProperty(subtemplate: UserDefinedTaskTemplate, templateProperty: TemplateProperty<any>, subTemplateIndex: number) {
    this.userDefinedTaskTemplateService.removePropertiesFromNestedTemplate(this.marketplace, this.template.id, subtemplate.id, [templateProperty.id]).toPromise().then((updatedTemplate: UserDefinedTaskTemplate) => {
      this.template = updatedTemplate;
      this.dataSources[subTemplateIndex].data = updatedTemplate.templates[subTemplateIndex].templateProperties;
    });
  }
  //end OnClick Actions


  //==================
  //Misc
  //==================

  printValue(property: PropertyInstance<any>) {
    return PropertyInstance.getValue(property);
  }

  // printDefaultValue(property: PropertyInstance<any>) {
  //   return PropertyInstance.getDefaultValue(property);
  // }


  navigateBack() {
    window.history.back();
  }

  private refresh() {
    this.isLoaded = false;
    this.ngOnInit();
  }

}




