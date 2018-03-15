import {Component} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {TaskTypeService} from '../task-type.service';
import {TaskType} from '../task-type';
import {Router} from '@angular/router';
import {OnInit} from '@angular/core/src/metadata/lifecycle_hooks';
import {CompetenceService} from '../../competence/competence.service';
import {Competence} from '../../competence/competence';

@Component({
  templateUrl: './task-type-create.component.html',
  styleUrls: ['./task-type-create.component.css']
})
export class TaskTypeCreateComponent implements OnInit {
  competences: Competence[];

  taskTypeForm: FormGroup;

  constructor(formBuilder: FormBuilder,
              private router: Router,
              private taskTypeService: TaskTypeService,
              private competenceService: CompetenceService) {
    this.taskTypeForm = formBuilder.group({
      'name': new FormControl('', Validators.required),
      'description': new FormControl('', Validators.required),
      'requiredCompetences': new FormControl(''),
      'acquirableCompetences': new FormControl('')
    });
  }

  ngOnInit() {
    this.competenceService.findAll().toPromise().then((competences: Competence[]) => this.competences = competences);
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
