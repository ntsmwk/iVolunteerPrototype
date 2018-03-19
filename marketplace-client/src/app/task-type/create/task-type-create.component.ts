import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {TaskTypeService} from '../task-type.service';
import {TaskType} from '../task-type';
import {ActivatedRoute, Router} from '@angular/router';
import {CompetenceService} from '../../competence/competence.service';
import {Competence} from '../../competence/competence';
import {TaskTypeValidator} from '../task-type.validator';
import {isNullOrUndefined} from 'util';

@Component({
  templateUrl: './task-type-create.component.html',
  styleUrls: ['./task-type-create.component.css']
})
export class TaskTypeCreateComponent implements OnInit {
  competences: Competence[];

  taskTypeForm: FormGroup;

  constructor(formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private taskTypeService: TaskTypeService,
              private competenceService: CompetenceService) {
    this.taskTypeForm = formBuilder.group({
      'id': new FormControl(undefined),
      'name': new FormControl(undefined, Validators.required),
      'description': new FormControl(undefined, Validators.required),
      'requiredCompetences': new FormControl([]),
      'acquirableCompetences': new FormControl([])
    }, {validator: TaskTypeValidator});
  }


  ngOnInit() {
    this.competenceService.findAll().toPromise().then((competences: Competence[]) => {
      this.competences = competences;
      this.route.params.subscribe(params => this.findTaskType(params['id']));
    });
  }

  private findTaskType(id: string) {
    if (isNullOrUndefined(id) || id.length === 0) {
      return;
    }
    this.taskTypeService.findById(id).toPromise().then((taskType: TaskType) => {
      this.taskTypeForm.setValue({
        id: taskType.id,
        name: taskType.name,
        description: taskType.description,
        requiredCompetences: this.competences.filter((competence: Competence) => {
          return taskType.requiredCompetences.find((requiredCompetence: Competence) => requiredCompetence.name === competence.name);
        }),
        acquirableCompetences: this.competences.filter((competence: Competence) => {
          return taskType.acquirableCompetences.find((acquirableCompetence: Competence) => acquirableCompetence.name === competence.name);
        })
      });
    });
  }

  save() {
    if (!this.taskTypeForm.valid) {
      return;
    }
    this.taskTypeService.save(<TaskType> this.taskTypeForm.value)
      .toPromise()
      .then((taskType: TaskType) => this.router.navigate(['/taskTypes']));
  }
}
