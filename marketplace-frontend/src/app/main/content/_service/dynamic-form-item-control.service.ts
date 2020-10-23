import { Injectable } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';

import { DynamicFormItemBase } from '../_model/dynamic-forms/item';

@Injectable()
export class DynamicFormItemControlService {

  constructor() { }

  toFormGroup(formItems: DynamicFormItemBase<any>[]) {
    /*const fb: FormBuilder = new FormBuilder();
    const parent = fb.group({});
    const ret = this.addChildToGroup(fb, formItems, parent);

    // console.log(ret.controls);
    return ret; <--- alte version */

    const fb: FormBuilder = new FormBuilder();
    const outerGroup = fb.group({});
    const array = fb.array([]);
    const innerGroup = fb.group({});
    const ret = this.addChildToGroup(fb, formItems, innerGroup);
    array.push(innerGroup);

    outerGroup.setControl("entries", array);

    return outerGroup;
  }

  private displayFormGroup(fg: FormGroup): void {

    console.log("DISPLAYING FORMGROUP: ");
    console.log("RAW: ");
    console.log(fg);

    console.log("CONTROLS: ");
    console.log(fg.controls);

    console.log("VALUES");
    console.log(fg.value);

  }

  private addChildToGroup(fb: FormBuilder, formItems: DynamicFormItemBase<any>[], parent: FormGroup): FormGroup {

    formItems.forEach((formItem: DynamicFormItemBase<any>) => {
      if (formItem.controlType === 'location') {
        parent.addControl(formItem.key, new FormGroup({
          label: new FormControl(''),
          longLatEnabled: new FormControl(''),
          longitude: new FormControl(''),
          latitude: new FormControl(''),
        }));
      } else {
        parent.addControl(formItem.key, fb.control(formItem.value, formItem.validators));
      }
    });
    return parent;
  }

  getControlForSubEntry(formitem: DynamicFormItemBase<any>[]) {
    const fb: FormBuilder = new FormBuilder();
    return this.addChildToGroup(fb, formitem, fb.group({}));
  }



}