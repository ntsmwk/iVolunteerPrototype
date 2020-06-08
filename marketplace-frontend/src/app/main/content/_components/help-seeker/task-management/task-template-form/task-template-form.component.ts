import { Component, OnInit } from "@angular/core";
import { CompetenceClassDefinition } from "app/main/content/_model/meta/class";
import { FormGroup, FormBuilder, FormControl } from "@angular/forms";
import { WorkflowType } from "app/main/content/_model/workflow-type";
import { ActivatedRoute, Router } from "@angular/router";
import { TaskTemplateService } from "app/main/content/_service/task-template.service";
import { WorkflowService } from "app/main/content/_service/workflow.service";
import { LoginService } from "app/main/content/_service/login.service";
import { CoreHelpSeekerService } from "app/main/content/_service/core-helpseeker.service";
import { CompetenceValidator } from "app/main/content/_validator/competence.validator";
import { Participant } from "app/main/content/_model/participant";
import { Marketplace } from "app/main/content/_model/marketplace";
import { TaskTemplate } from "app/main/content/_model/task-template";
import { isNullOrUndefined } from "util";

@Component({
  templateUrl: "./task-template-form.component.html",
  styleUrls: ["./task-template-form.component.scss"]
})
export class FuseTaskTemplateFormComponent implements OnInit {
  competences: CompetenceClassDefinition[];
  taskTemplateForm: FormGroup;
  workflowTypes: Array<WorkflowType>;

  private tenantName = "FF Eidenberg";
  private tenantId: string;

  constructor(
    formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private taskTemplateService: TaskTemplateService,
    private workflowService: WorkflowService,
    private loginService: LoginService,
    private coreHelpSeekerService: CoreHelpSeekerService,
    private router: Router
  ) {
    this.taskTemplateForm = formBuilder.group(
      {
        id: new FormControl(undefined),
        name: new FormControl(undefined),
        description: new FormControl(undefined),
        requiredCompetences: new FormControl([]),
        acquirableCompetences: new FormControl([]),
        workflowType: new FormControl(undefined)
      },
      { validator: CompetenceValidator }
    );
  }

  ngOnInit() {
    this.loginService
      .getLoggedIn()
      .toPromise()
      .then((helpSeeker: Participant) => {
        this.coreHelpSeekerService
          .findRegisteredMarketplaces(helpSeeker.id)
          .toPromise()
          .then((marketplace: Marketplace) => {
            if (!isNullOrUndefined(marketplace)) {
              Promise.all([
                this.workflowService
                  .findAllTypes(marketplace)
                  .toPromise()
                  .then(
                    (workflowTypes: Array<WorkflowType>) =>
                      (this.workflowTypes = workflowTypes)
                  )
              ]).then(() =>
                this.route.params.subscribe(params =>
                  this.findTaskTemplate(marketplace, params["taskTemplateId"])
                )
              );
            }
          });
      });
  }

  private findTaskTemplate(marketplace: Marketplace, taskTemplateId: string) {
    if (isNullOrUndefined(taskTemplateId) || taskTemplateId.length === 0) {
      return;
    }
    this.taskTemplateService
      .findById(marketplace, taskTemplateId)
      .toPromise()
      .then((taskTemplate: TaskTemplate) => {
        this.taskTemplateForm.setValue({
          id: taskTemplate.id,
          name: taskTemplate.name,
          description: taskTemplate.description,
          requiredCompetences: this.competences.filter(
            (competence: CompetenceClassDefinition) => {
              return taskTemplate.requiredCompetences.find(
                (requiredCompetence: CompetenceClassDefinition) =>
                  requiredCompetence.name === competence.name
              );
            }
          ),
          acquirableCompetences: this.competences.filter(
            (competence: CompetenceClassDefinition) => {
              return taskTemplate.acquirableCompetences.find(
                (acquirableCompetence: CompetenceClassDefinition) =>
                  acquirableCompetence.name === competence.name
              );
            }
          ),
          workflowType: this.workflowTypes.find(
            (value: WorkflowType) => taskTemplate.workflowKey === value.key
          )
        });
      });
  }

  save() {
    if (!this.taskTemplateForm.valid) {
      return;
    }

    const taskTemplate = this.taskTemplateForm.value;
    taskTemplate.workflowKey = taskTemplate.workflowType.key;
    delete taskTemplate.workflowType;
    this.loginService
      .getLoggedIn()
      .toPromise()
      .then((helpSeeker: Participant) => {
        this.coreHelpSeekerService
          .findRegisteredMarketplaces(helpSeeker.id)
          .toPromise()
          .then((marketplace: Marketplace) => {
            if (!isNullOrUndefined(marketplace)) {
              this.taskTemplateService
                .save(marketplace, <TaskTemplate>taskTemplate)
                .toPromise()
                .then(() => this.router.navigate(["/main/task-templates/all"]));
            }
          });
      });
  }
}
