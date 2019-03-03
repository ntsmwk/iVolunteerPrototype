import {Component, OnInit} from '@angular/core';
import {isNullOrUndefined} from 'util';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {BCEntry} from '../_model/bcEntry';
import {CoreBCEntryService} from '../_service/core-bcEntry.service';

@Component({
  templateUrl: './bcEntry-form.component.html',
  styleUrls: ['./bcEntry-form.component.scss']
})
export class FuseBCEntryFormComponent implements OnInit {
  bcEntryForm: FormGroup;

  constructor(formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private bcEntryService: CoreBCEntryService) {
    this.bcEntryForm = formBuilder.group({
      'id': new FormControl(undefined),
      'name': new FormControl(undefined, Validators.required),
      'checked': new FormControl(undefined)
    });
  }

  isEditMode() {
    return !isNullOrUndefined(this.bcEntryForm.value.id);
  }

  ngOnInit() {
    this.route.params.subscribe(params => this.findBCEntry(params['id']));
  }

  private findBCEntry(id: string) {
    if (isNullOrUndefined(id) || id.length === 0) {
      return;
    }

    this.bcEntryService.findById(id).toPromise().then((bcEntry: BCEntry) => {
      this.bcEntryForm.setValue({
        id: bcEntry.id,
        name: bcEntry.name,
        checked : bcEntry.checked
      });
    });
  }

  save() {
    if (!this.bcEntryForm.valid) {
      return;
    }

    this.bcEntryService.save(<BCEntry>this.bcEntryForm.value)
      .toPromise()
      .then(() => this.router.navigate(['/main/bcEntries/all']));
  }
}
