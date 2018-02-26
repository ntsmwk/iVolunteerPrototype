import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Volunteer} from '../participant/volunteer';
import {Task} from '../task/task';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {TaskTransactionService} from '../task-transaction/task-transaction.service';
import {TaskService} from '../task/task.service';
import {VolunteerService} from '../participant/volunteer.service';
import {TransactionType} from '../task-transaction/transaction-type';
import {TaskTransaction} from '../task-transaction/task-transaction';

@Component({
  selector: 'app-reserve-task',
  templateUrl: './reserve-task.component.html',
  styleUrls: ['./reserve-task.component.css']
})
export class ReserveTaskComponent implements OnInit {
  @Output()
  onSaved = new EventEmitter<TaskTransaction>();

  reserveTaskForm: FormGroup;

  tasks: Task[];
  volunteers: Volunteer[];
  transactionTypes: TransactionType[] = [TransactionType.RESERVATION, TransactionType.ASSIGNMENT];


  constructor(formBuilder: FormBuilder,
              private taskTransactionService: TaskTransactionService,
              private taskService: TaskService,
              private volunteerService: VolunteerService) {

    this.reserveTaskForm = formBuilder.group({
      'task': new FormControl('', Validators.required),
      'volunteer': new FormControl('', Validators.required),
      'transactionType': new FormControl('', Validators.required),
      'timestamp': new FormControl('')
    });
  }

  ngOnInit() {
    this.taskService.findAll().subscribe((tasks: Task[]) => this.tasks = tasks);
    this.volunteerService.findAll().subscribe((volunteers: Volunteer[]) => this.volunteers = volunteers);

  }

  valueOf(index: number): string {
    return Object.keys(TransactionType)[index];
  }

  getTimestamp(): Date {
    return new Date();
  }

  save() {

    if (!this.reserveTaskForm.valid) {
      return;
    }

    this.taskTransactionService.save(<TaskTransaction> this.reserveTaskForm.value)
      .toPromise()
      .then((taskTransaction: TaskTransaction) => this.onSaved.emit(taskTransaction));

  }


}
