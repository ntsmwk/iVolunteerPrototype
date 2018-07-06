import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {TaskService} from '../_service/task.service';
import {MessageService} from '../_service/message.service';
import { TaskTemplate } from '../_model/task-template';
import { TaskTemplateService } from '../_service/task-template.service';
import { CompetenceService } from '../_service/competence.service';
import { WorkflowService } from '../_service/workflow.service';
import { WorkflowType } from '../_model/workflow-type';
import { Competence } from '../_model/competence';
import { LoginService } from '../_service/login.service';
import { Participant } from '../_model/participant';

@Component({
  selector: 'fuse-task-template',
  templateUrl: './task-template.component.html',
  styleUrls: ['./task-template.component.scss'],
  providers: [TaskTemplateService]
})
export class FuseTaskTemplateComponent implements OnInit {

  taskTemplate: TaskTemplate;

  constructor(private route: ActivatedRoute,
              private taskTemplateService: TaskTemplateService,
              private workflowService: WorkflowService,
              private competenceService: CompetenceService,
              private loginService: LoginService,
              private coreEmployeeService: CoreEmployeeService,
              private messageService: MessageService) {
  }

  ngOnInit() {
    Promise.all([
      //TODO
      this.loginService.getLoggedIn().toPromise().then((employee: Participant) => {
      
      this.workflowService.findAllTypes().toPromise().then((workflowTypes: Array<WorkflowType>) => this.workflowTypes = workflowTypes),
      this.competenceService.findAll().toPromise().then((competences: Competence[]) => this.competences = competences)
    ]).then(() => this.route.params.subscribe(params => this.findTaskTemplate(params['id'])));
  }

  private findTaskTemplate(id: string) {
    if (isNullOrUndefined(id) || id.length === 0) {
      return;
    }
    this.taskTemplateService.findById(id).toPromise().then((taskTemplate: TaskTemplate) => {
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
    this.taskTemplateService.save(<TaskTemplate> taskTemplate)
      .toPromise()
      .then(() => this.router.navigate(['/taskTemplates']));
  }
}
