import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { TaskTemplate } from '../_model/task-template';
import { TaskTemplateService } from '../_service/task-template.service';
import { CompetenceService } from '../_service/competence.service';
import { WorkflowService } from '../_service/workflow.service';
import { WorkflowType } from '../_model/workflow-type';
import { LoginService } from '../_service/login.service';
import { Participant } from '../_model/participant';
import { CoreHelpSeekerService } from '../_service/core-helpseeker.service';
import { Marketplace } from '../_model/marketplace';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { CompetenceValidator } from '../_validator/competence.validator';
import { CompetenceClassDefinition } from '../_model/meta/class';

@Component({
  templateUrl: './task-template-form.component.html',
  styleUrls: ['./task-template-form.component.scss']
})
export class FuseTaskTemplateFormComponent implements OnInit {

  competences: CompetenceClassDefinition[];
  taskTemplateForm: FormGroup;
  workflowTypes: Array<WorkflowType>;

  constructor(formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private taskTemplateService: TaskTemplateService,
    private workflowService: WorkflowService,
    private competenceService: CompetenceService,
    private loginService: LoginService,
    private coreHelpSeekerService: CoreHelpSeekerService,
    private router: Router) {
    this.taskTemplateForm = formBuilder.group({
      'id': new FormControl(undefined),
      'name': new FormControl(undefined),
      'description': new FormControl(undefined),
      'requiredCompetences': new FormControl([]),
      'acquirableCompetences': new FormControl([]),
      'workflowType': new FormControl(undefined),
    }, { validator: CompetenceValidator });
  }


  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.coreHelpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          Promise.all([
            this.competenceService.findAll(marketplace).toPromise().then((competences: CompetenceClassDefinition[]) => this.competences = competences),

            this.workflowService.findAllTypes(marketplace).toPromise().then((workflowTypes: Array<WorkflowType>) => this.workflowTypes = workflowTypes)
          ]).then(() => this.route.params.subscribe(params => this.findTaskTemplate(marketplace, params['taskTemplateId'])));
        }
      });
    });
  }

  private findTaskTemplate(marketplace: Marketplace, taskTemplateId: string) {
    if (isNullOrUndefined(taskTemplateId) || taskTemplateId.length === 0) {
      return;
    }
    this.taskTemplateService.findById(marketplace, taskTemplateId).toPromise().then((taskTemplate: TaskTemplate) => {
      this.taskTemplateForm.setValue({
        id: taskTemplate.id,
        name: taskTemplate.name,
        description: taskTemplate.description,
        requiredCompetences: this.competences.filter((competence: CompetenceClassDefinition) => {
          return taskTemplate.requiredCompetences.find((requiredCompetence: CompetenceClassDefinition) => requiredCompetence.name === competence.name);
        }),
        acquirableCompetences: this.competences.filter((competence: CompetenceClassDefinition) => {
          return taskTemplate.acquirableCompetences.find((acquirableCompetence: CompetenceClassDefinition) => acquirableCompetence.name === competence.name);
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
    this.loginService.getLoggedIn().toPromise().then((helpSeeker: Participant) => {
      this.coreHelpSeekerService.findRegisteredMarketplaces(helpSeeker.id).toPromise().then((marketplace: Marketplace) => {
        if (!isNullOrUndefined(marketplace)) {
          this.taskTemplateService.save(marketplace, <TaskTemplate>taskTemplate)
            .toPromise()
            .then(() => this.router.navigate(['/main/task-templates/all']));
        }
      });
    });
  }


}
