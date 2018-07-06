import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MessageService} from '../_service/message.service';
<<<<<<< HEAD
import {TaskTemplate} from '../_model/task-template';
import {TaskTemplateService} from '../_service/task-template.service';
import {CompetenceService} from '../_service/competence.service';
import {WorkflowService} from '../_service/workflow.service';
import {WorkflowType} from '../_model/workflow-type';
import {Competence} from '../_model/competence';
import {LoginService} from '../_service/login.service';
import {Participant} from '../_model/participant';
import {CoreEmployeeService} from '../_service/core-employee.service';
import {Marketplace} from '../_model/marketplace';
import {FormGroup} from '@angular/forms';
import {isNullOrUndefined} from 'util';
=======
import { TaskTemplate } from '../_model/task-template';
import { TaskTemplateService } from '../_service/task-template.service';
import { CompetenceService } from '../_service/competence.service';
import { WorkflowService } from '../_service/workflow.service';
import { WorkflowType } from '../_model/workflow-type';
import { Competence } from '../_model/competence';
import { LoginService } from '../_service/login.service';
import { Participant } from '../_model/participant';
import { CoreEmployeeService } from '../_service/core-employee.service';
import { Marketplace } from '../_model/marketplace';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { CompetenceValidator } from '../_validator/competence.validator';
>>>>>>> f3c9cfdf7e5b06afcbe310137f2204578a7948f9

@Component({
  selector: 'fuse-task-template',
  templateUrl: './task-template.component.html',
  styleUrls: ['./task-template.component.scss'],
  providers: [TaskTemplateService, WorkflowService, CompetenceService, LoginService, CoreEmployeeService]
})
export class FuseTaskTemplateComponent implements OnInit {

  competences: Competence[];
  taskTemplateForm: FormGroup;
  workflowTypes: Array<WorkflowType>;

  constructor(formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private taskTemplateService: TaskTemplateService,
              private workflowService: WorkflowService,
              private competenceService: CompetenceService,
              private loginService: LoginService,
              private coreEmployeeService: CoreEmployeeService,
<<<<<<< HEAD
              private messageService: MessageService,
              private router: Router) {
  }
=======
              private router: Router
            ) 
  {
    this.taskTemplateForm = formBuilder.group({
      'id': new FormControl(undefined),
      'name': new FormControl(undefined),
      'description': new FormControl(undefined),
      'requiredCompetences': new FormControl([]),
      'acquirableCompetences': new FormControl([]),
      'workflowType': new FormControl(undefined),
    }, {validator: CompetenceValidator});
}
>>>>>>> f3c9cfdf7e5b06afcbe310137f2204578a7948f9

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((employee: Participant) => {
      this.coreEmployeeService.findRegisteredMarketplaces(employee.id).toPromise().then((marketplace: Marketplace) => {
        Promise.all([
<<<<<<< HEAD
          this.workflowService.findAllTypes(marketplace).toPromise().then((workflowTypes: Array<WorkflowType>) => this.workflowTypes = workflowTypes),
          this.competenceService.findAll(marketplace).toPromise().then((competences: Competence[]) => this.competences = competences)

        ]).then(() => this.route.params.subscribe(params => this.findTaskTemplate(marketplace, params['id'])));
=======
          this.workflowService.findAllTypes(marketplace.url).toPromise().then((workflowTypes: Array<WorkflowType>) => this.workflowTypes = workflowTypes),
          this.competenceService.findAll(marketplace.url).toPromise().then((competences: Competence[]) => this.competences = competences)
        ]).then(() => this.route.params.subscribe(params => {
          console.error("c: " + this.competences);
          console.error("w: " + this.workflowTypes);
          this.findTaskTemplate(params['id'], marketplace.url);
        })); 
>>>>>>> f3c9cfdf7e5b06afcbe310137f2204578a7948f9
      });
    });
  }

  private findTaskTemplate(marketplace: Marketplace, id: string) {
    if (isNullOrUndefined(id) || id.length === 0) {
      return;
    }
    this.taskTemplateService.findById(marketplace, id).toPromise().then((taskTemplate: TaskTemplate) => {
      this.taskTemplateForm.setValue({
        id: taskTemplate.id,
        name: taskTemplate.name,
        description: taskTemplate.description,
        requiredCompetences: this.competences.filter((competence: Competence) => {
          return taskTemplate.requiredCompetences.find((requiredCompetence: Competence) => requiredCompetence.name === competence.name);
        }),
        acquirableCompetences: this.competences.filter((competence: Competence) => {
          return taskTemplate.acquirableCompetences.find((acquirableCompetence: Competence) => acquirableCompetence.name === competence.name);
        }),
        workflowType: this.workflowTypes.find((value: WorkflowType) => taskTemplate.workflowKey === value.key)
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
    this.loginService.getLoggedIn().toPromise().then((employee: Participant) => {
      this.coreEmployeeService.findRegisteredMarketplaces(employee.id).toPromise().then((marketplace: Marketplace) => {
        this.taskTemplateService.save(marketplace, <TaskTemplate> taskTemplate)
          .toPromise()
          .then(() => this.router.navigate(['/taskTemplates']));
      });
    });
  }
}
