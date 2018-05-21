import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Task} from '../../_model/task';
import {TaskService} from '../../_service/task.service';
import {TaskType} from '../../_model/task-type';
import {TaskTypeService} from '../../_service/task-type.service';
import {ActivatedRoute, Router} from '@angular/router';
import {isNullOrUndefined} from 'util';
import {WorkflowService} from '../../_service/workflow.service';
import {WorkflowType} from '../../_model/workflow-type';

@Component({
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.css']
})
export class TaskCreateComponent implements OnInit {
  taskForm: FormGroup;
  taskTypes: Array<TaskType>;
  workflowTypes: Array<WorkflowType>;

  constructor(formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private taskService: TaskService,
              private taskTypeService: TaskTypeService,
              private workflowService: WorkflowService) {
    this.taskForm = formBuilder.group({
      'id': new FormControl(undefined),
      'taksType': new FormControl(undefined, Validators.required),
      'workflowKey': new FormControl(undefined, Validators.required),
      'startDate': new FormControl(undefined, Validators.required),
      'endDate': new FormControl(undefined)
    });
  }

  ngOnInit() {
    Promise.all([
      this.taskTypeService.findAll().toPromise().then((taskTypes: Array<TaskType>) => this.taskTypes = taskTypes),
      this.workflowService.findAllTypes().toPromise().then((workflowTypes: Array<WorkflowType>) => this.workflowTypes = workflowTypes)
    ]).then(() => this.route.params.subscribe(params => this.findTask(params['id'])));
  }

  private findTask(id: string) {
    if (isNullOrUndefined(id) || id.length === 0) {
      return;
    }
    this.taskService.findById(id).toPromise().then((task: Task) => {
      this.taskForm.setValue({
        id: task.id,
        taskType: this.taskTypes.find((value: TaskType) => task.taskType.id === value.id),
        workflowKey: this.workflowTypes.find((value: WorkflowType) => task.workflowKey == value.key),
        startDate: new Date(task.startDate),
        endDate: new Date(task.endDate)
      });
    });
  }

  save() {
    if (!this.taskForm.valid) {
      return;
    }

    const task = this.taskForm.value;
    task.workflowKey = task.workflowKey.key;
    this.taskService.save(<Task>task).toPromise().then(() => this.router.navigate(['/tasks']));
  }

  isEditMode() {
    return !isNullOrUndefined(this.taskForm.value.id);
  }
}
