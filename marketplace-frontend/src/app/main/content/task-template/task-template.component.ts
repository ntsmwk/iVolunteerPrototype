import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MessageService} from '../_service/message.service';
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

@Component({
  selector: 'fuse-task-template',
  templateUrl: './task-template.component.html',
  styleUrls: ['./task-template.component.scss'],
  providers: [TaskTemplateService]
})
export class FuseTaskTemplateComponent implements OnInit {

  taskTemplate: TaskTemplate;
  competences: Competence[];
  taskTemplateForm: FormGroup;
  workflowTypes: Array<WorkflowType>;

  constructor(private route: ActivatedRoute,
              private taskTemplateService: TaskTemplateService,
              private workflowService: WorkflowService,
              private competenceService: CompetenceService,
              private loginService: LoginService,
              private coreEmployeeService: CoreEmployeeService,
              private messageService: MessageService,
              private router: Router) {
  }

  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((employee: Participant) => {
      this.coreEmployeeService.findRegisteredMarketplaces(employee.id).toPromise().then((marketplace: Marketplace) => {
        Promise.all([
          this.workflowService.findAllTypes(marketplace).toPromise().then((workflowTypes: Array<WorkflowType>) => this.workflowTypes = workflowTypes),
          this.competenceService.findAll(marketplace).toPromise().then((competences: Competence[]) => this.competences = competences)

        ]).then(() => this.route.params.subscribe(params => this.findTaskTemplate(marketplace, params['id'])));
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
