import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Task} from '../../_model/task';
import {TaskService} from '../../_service/task.service';
import {TaskTemplate} from '../../_model/task-template';
import {TaskTemplateService} from '../../_service/task-template.service';
import {ActivatedRoute, Router} from '@angular/router';
import {isArray, isNullOrUndefined} from 'util';
import {Competence} from '../../_model/competence';
import {CompetenceService} from '../../_service/competence.service';
import {CompetenceValidator} from '../../_validator/competence.validator';
import {WorkflowService} from '../../_service/workflow.service';
import {WorkflowType} from '../../_model/workflow-type';
import {LoginService} from '../../_service/login.service';
import {Participant} from '../../_model/participant';

@Component({
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.css']
})
export class TaskCreateComponent implements OnInit {
  taskForm: FormGroup;
  competences: Array<Competence>;
  taskTemplates: Array<TaskTemplate>;
  workflowTypes: Array<WorkflowType>;

  constructor(formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private loginService: LoginService,
              private taskService: TaskService,
              private competenceService: CompetenceService,
              private taskTemplateService: TaskTemplateService,
              private workflowService: WorkflowService) {
    this.taskForm = formBuilder.group({
      'id': new FormControl(undefined),
      'name': new FormControl(undefined),
      'description': new FormControl(undefined),
      'workflowType': new FormControl(undefined, Validators.required),
      'startDate': new FormControl(undefined, Validators.required),
      'endDate': new FormControl(undefined),
      'requiredCompetences': new FormControl([]),
      'acquirableCompetences': new FormControl([])
    }, {validator: CompetenceValidator});
  }


  ngOnInit() {
    Promise.all([
      this.taskTemplateService.findAll().toPromise().then((taskTemplates: Array<TaskTemplate>) => this.taskTemplates = taskTemplates),
      this.workflowService.findAllTypes().toPromise().then((workflowTypes: Array<WorkflowType>) => this.workflowTypes = workflowTypes),
      this.competenceService.findAll().toPromise().then((competences: Array<Competence>) => this.competences = competences)
    ]).then(() => this.route.params.subscribe(params => this.findTask(params['id'])));
  }

  private findTask(id: string) {
    if (isNullOrUndefined(id) || id.length === 0) {
      return;
    }
    this.taskService.findById(id).toPromise().then((task: Task) => {
      this.taskForm.setValue({
        id: task.id,
        name: task.name,
        description: task.description,
        workflowType: this.workflowTypes.find((value: WorkflowType) => task.workflowKey === value.key),
        startDate: new Date(task.startDate),
        endDate: new Date(task.endDate),
        requiredCompetences: this.competences.filter((competence: Competence) => {
          return task.requiredCompetences.find((requiredCompetence: Competence) => requiredCompetence.name === competence.name);
        }),
        acquirableCompetences: this.competences.filter((competence: Competence) => {
          return task.acquirableCompetences.find((acquirableCompetence: Competence) => acquirableCompetence.name === competence.name);
        })
      });
    });
  }

  save() {
    if (!this.taskForm.valid) {
      return;
    }

    const task = this.taskForm.value;
    task.workflowKey = task.workflowType.key;
    delete task.workflowType;

    Promise.all([
      this.loginService.getLoggedIn().toPromise(),
      this.taskService.save(<Task>task).toPromise()
    ]).then((values: any[]) => {
      const createdTask = <Task> values[1];
      const participantId = (<Participant> values[0]).id;
      this.workflowService.startWorkflow(createdTask.workflowKey, createdTask.id, participantId).toPromise().then(() => this.router.navigate(['/tasks']));
    });
  }

  isEditMode() {
    return !isNullOrUndefined(this.taskForm.value.id);
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
      currentValues.requiredCompetences = this.competences.filter((competence: Competence) => {
        return taskTemplate.requiredCompetences.find((value: Competence) => value.name === competence.name);
      });
    } else {
      currentValues.requiredCompetences = [];
    }

    if (isArray(taskTemplate.acquirableCompetences)) {
      currentValues.acquirableCompetences = this.competences.filter((competence: Competence) => {
        return taskTemplate.acquirableCompetences.find((value: Competence) => value.name === competence.name);
      });
    } else {
      currentValues.acquirableCompetences = [];
    }

    this.taskForm.setValue(currentValues);
  }
}
