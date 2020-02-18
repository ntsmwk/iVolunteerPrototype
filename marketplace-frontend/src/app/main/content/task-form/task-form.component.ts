import { Component, OnInit } from '@angular/core';
import { isArray, isNullOrUndefined } from 'util';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Task } from '../_model/task';
import { TaskTemplate } from '../_model/task-template';
import { WorkflowType } from '../_model/workflow-type';
import { ActivatedRoute, Router } from '@angular/router';
import { LoginService } from '../_service/login.service';
import { TaskService } from '../_service/task.service';
import { CompetenceService } from '../_service/competence.service';
import { TaskTemplateService } from '../_service/task-template.service';
import { WorkflowService } from '../_service/workflow.service';
import { CompetenceValidator } from '../_validator/competence.validator';
import { Participant } from '../_model/participant';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { Marketplace } from '../_model/marketplace';
import { Project } from '../_model/project';
import { ProjectService } from '../_service/project.service';
import { CompetenceClassDefinition } from '../_model/meta/Class';
import { CoreTenantService } from '../_service/core-tenant.service';
// import * as $ from 'jquery'
// import 'periodpicker'
// declare var jquery:any;
// declare var $ :any;
declare var $: JQuery;

declare global {
  interface JQuery {
    (selector: string): any;
    periodpicker(t): any;
  }
}

@Component({
  templateUrl: './task-form.component.html',
  styleUrls: ['./task-form.component.scss'],
})
export class FuseTaskFormComponent implements OnInit {
  taskForm: FormGroup;

  projects: Array<Project>;
  competences: Array<CompetenceClassDefinition>;
  taskTemplates: Array<TaskTemplate>;
  workflowTypes: Array<WorkflowType>;

  private tenantName: string = 'FF_Eidenberg';
  private tenantId: string;

  constructor(formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private loginService: LoginService,
    private coreHelpSeekerService: CoreHelpSeekerService,
    private taskService: TaskService,
    private competenceService: CompetenceService,
    private taskTemplateService: TaskTemplateService,
    private workflowService: WorkflowService,
    private projectService: ProjectService,
    private coreTenantService: CoreTenantService) {
    this.taskForm = formBuilder.group({
      'id': new FormControl(undefined),
      'name': new FormControl(undefined),
      'description': new FormControl(undefined),
      'project': new FormControl(undefined),
      'workflowType': new FormControl(undefined, Validators.required),
      'startDate': new FormControl(undefined, Validators.required),
      'endDate': new FormControl(undefined),
      'requiredCompetences': new FormControl([]),
      'acquirableCompetences': new FormControl([])
    }, { validator: CompetenceValidator });
  }


  isEditMode() {
    return !isNullOrUndefined(this.taskForm.value.id);
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.coreHelpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {

          this.coreTenantService.findByName(this.tenantName).toPromise().then((tenantId: string) => {
            this.tenantId = tenantId;

            Promise.all([
              this.projectService.findAll(marketplace).toPromise().then((projects: Array<Project>) => this.projects = projects),
              this.competenceService.findAll(marketplace, this.tenantId).toPromise().then((competences: Array<CompetenceClassDefinition>) => this.competences = competences),
              this.taskTemplateService.findAll(marketplace).toPromise().then((taskTemplates: Array<TaskTemplate>) => this.taskTemplates = taskTemplates),
              this.workflowService.findAllTypes(marketplace).toPromise().then((workflowTypes: Array<WorkflowType>) => this.workflowTypes = workflowTypes)
            ]).then(() => this.route.params.subscribe(params => this.findTask(marketplace, params['taskId'])));
          });
        }
      });
    });


    $("#startDate").periodpicker({
      norange: true, // use only one value
      cells: [1, 1], // show only one month

      resizeButton: false, // deny resize picker
      fullsizeButton: false,
      fullsizeOnDblClick: false,

      timepicker: true, // use timepicker
      timepickerOptions: {
        hours: true,
        minutes: true,
        seconds: false,
        ampm: true
      }
    });

    $("#endDate").periodpicker({
      norange: true, // use only one value
      cells: [1, 1], // show only one month

      resizeButton: false, // deny resize picker
      fullsizeButton: false,
      fullsizeOnDblClick: false,

      timepicker: true, // use timepicker
      timepickerOptions: {
        hours: true,
        minutes: true,
        seconds: false,
        ampm: true
      }
    });
  }

  private findTask(marketplace: Marketplace, taskId: string) {
    if (isNullOrUndefined(taskId) || taskId.length === 0) {
      return;
    }

    this.taskService.findById(marketplace, taskId).toPromise().then((task: Task) => {
      this.taskForm.setValue({
        id: task.id,
        name: task.name,
        description: task.description,
        project: this.projects.find((value: Project) => task.project.id === value.id),
        workflowType: this.workflowTypes.find((value: WorkflowType) => task.workflowKey === value.key),
        startDate: new Date(task.startDate),
        endDate: new Date(task.endDate),
        requiredCompetences: this.competences.filter((competence: CompetenceClassDefinition) => {
          task.requiredCompetences.find((requiredCompetence: CompetenceClassDefinition) => requiredCompetence.id === competence.id);
        }),
        acquirableCompetences: this.competences.filter((competence: CompetenceClassDefinition) => {
          return task.acquirableCompetences.find((acquirableCompetence: CompetenceClassDefinition) => acquirableCompetence.id === competence.id);
        })
      });
    });
  }

  save() {
    // console.error(this.taskForm.value);
    // console.error($("#startDate")[0].value);
    // console.error($("#endDate")[0].value);


    const task = this.taskForm.value;
    // task.startDate = $("#startDate")[0].value;
    task.workflowKey = task.workflowType.key;
    delete task.workflowType;

    this.loginService.getLoggedIn().toPromise().then((participant: Participant) => {
      this.coreHelpSeekerService.findRegisteredMarketplaces(participant.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          if (this.isEditMode()) {
            this.taskService.save(marketplace, <Task>task).toPromise().then(() => this.router.navigate(['/main/tasks/all']));
          } else {
            Promise.all([
              this.loginService.getLoggedIn().toPromise(),
              this.taskService.save(marketplace, <Task>task).toPromise()
            ]).then((values: any[]) => {
              const createdTask = <Task>values[1];
              const participantId = (<Participant>values[0]).id;

              this.workflowService.startWorkflow(marketplace, createdTask.workflowKey, createdTask.id, participantId)
                .toPromise()
                .then(() => this.router.navigate(['/main/tasks/all']));
            });
          }
        }
      });
    });
  }

  prefillForm(event, taskTemplate) {
    if (!event.source.selected) {
      return;
    }
    const currentValues = this.taskForm.value;
    currentValues.name = taskTemplate.name;
    currentValues.description = taskTemplate.description;
    currentValues.workflowType = this.workflowTypes.find((value: WorkflowType) => value.key === taskTemplate.workflowKey);

    if (isArray(taskTemplate.requiredCompetences)) {
      currentValues.requiredCompetences = this.competences.filter((competence: CompetenceClassDefinition) => {
        return taskTemplate.requiredCompetences.find((value: CompetenceClassDefinition) => value.name === competence.name);
      });
    } else {
      currentValues.requiredCompetences = [];
    }

    if (isArray(taskTemplate.acquirableCompetences)) {
      currentValues.acquirableCompetences = this.competences.filter((competence: CompetenceClassDefinition) => {
        return taskTemplate.acquirableCompetences.find((value: CompetenceClassDefinition) => value.name === competence.name);
      });
    } else {
      currentValues.acquirableCompetences = [];
    }

    this.taskForm.setValue(currentValues);
  }
}
