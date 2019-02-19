import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import { FormGroup,FormBuilder,FormControl } from '@angular/forms';
import { isNullOrUndefined } from 'util';
import { GroupService } from '../_service/group.service';
import { Group } from '../_model/group';

@Component({
  templateUrl: './group-form.component.html',
  styleUrls: ['./group-form.component.scss']
})
export class FuseGroupFormComponent implements OnInit {

  taskForm: FormGroup;
  

  constructor(private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private groupService: GroupService) {
      this.taskForm = formBuilder.group({
        'name': new FormControl("New Group"),
        'description': new FormControl(undefined),
        'autoJoin': new FormControl(undefined)
      })
  }

  ngOnInit() {
  }

  isEditMode() {
    return !isNullOrUndefined(this.taskForm.value.id);
  }

  save(): void {
    //console.log(this.taskForm.value);
    const group = this.taskForm.value;
    console.log(group);
    console.log(this.groupService.save(group).toPromise().then()  );

  }
}
