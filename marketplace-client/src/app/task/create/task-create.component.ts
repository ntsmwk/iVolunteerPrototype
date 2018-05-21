import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Task} from '../../_model/task';
import {TaskService} from '../../_service/task.service';
import {TaskTemplate} from '../../_model/task-template';
import {TaskTemplateService} from '../../_service/task-template.service';
import {ActivatedRoute, Router} from '@angular/router';
import {isNullOrUndefined} from 'util';
import {Competence} from '../../_model/competence';
import {CompetenceService} from '../../_service/competence.service';
import {TaskTemplateValidator} from '../../task-template/task-template.validator';

@Component({
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.css']
})
export class TaskCreateComponent implements OnInit {
  taskForm: FormGroup;
  competences: Competence[];
  taskTemplates: TaskTemplate[];

  constructor(formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private taskService: TaskService,
              private competenceService: CompetenceService,
              private taskTemplateService: TaskTemplateService) {
    this.taskForm = formBuilder.group({
      'id': new FormControl(undefined),
      'name': new FormControl(undefined),
      'description': new FormControl(undefined),
      'startDate': new FormControl(undefined, Validators.required),
      'endDate': new FormControl(undefined),
      'requiredCompetences': new FormControl([]),
      'acquirableCompetences': new FormControl([])
    }, {validator: TaskTemplateValidator});
  }

  ngOnInit() {
    this.taskTemplateService.findAll()
      .toPromise()
      .then((taskTemplates: TaskTemplate[]) => {
        this.taskTemplates = taskTemplates;
        this.route.params.subscribe(params => this.findTask(params['id']));
      });

    this.competenceService.findAll().toPromise().then((competences: Competence[]) => {
      this.competences = competences;
    });
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
        startDate: new Date(task.startDate),
        endDate: new Date(task.endDate),
        acquirableCompetences: task.acquirableCompetences,
        requiredCompetences: task.requiredCompetences
      });
    });
  }

  save() {
    if (!this.taskForm.valid) {
      return;
    }

    const task = <Task> this.taskForm.value;
    this.taskService.save(task).toPromise().then(() => this.router.navigate(['/tasks']));
  }


}
