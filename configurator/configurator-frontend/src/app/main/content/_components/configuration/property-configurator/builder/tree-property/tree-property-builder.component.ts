import { Component, OnInit, Input, EventEmitter, Output } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { TreePropertyDefinition } from 'app/main/content/_model/configurator/property/tree-property';
import { TreePropertyDefinitionService } from 'app/main/content/_service/meta/core/property/tree-property-definition.service';
import { isNullOrUndefined } from 'util';
import { MatDialog } from '@angular/material';
import { ResponseService } from 'app/main/content/_service/response.service';
import { propertyNameUniqueValidator } from 'app/main/content/_validator/property-name-unique.validator';
import { stringsUnique } from 'app/main/content/_validator/strings-unique.validator';

@Component({
  selector: "app-tree-property-builder",
  templateUrl: './tree-property-builder.component.html',
  styleUrls: ['./tree-property-builder.component.scss'],
  // providers: [DialogFactoryDirective]
})
export class TreePropertyBuilderComponent implements OnInit {
  constructor(
    public dialog: MatDialog,
    private formBuilder: FormBuilder,
    private treePropertyDefinitionService: TreePropertyDefinitionService,
    private responseService: ResponseService
  ) { }

  @Input() entryId: string;
  @Input() sourceString: string;
  @Input() tenantId: string;
  @Input() redirectUrl: string;
  @Output() result: EventEmitter<{ builderType: string, value: TreePropertyDefinition }> = new EventEmitter();
  @Output() management: EventEmitter<String> = new EventEmitter();

  form: FormGroup;
  treePropertyDefinition: TreePropertyDefinition;
  showEditor: boolean;
  multipleToggled: boolean;

  loaded: boolean;

  async ngOnInit() {
    this.form = this.formBuilder.group({
      name: this.formBuilder.control('', Validators.required),
      description: this.formBuilder.control(''),
      multiple: this.formBuilder.control(''),
      required: this.formBuilder.control(''),
      requiredMessage: this.formBuilder.control(''),
    });

    if (!isNullOrUndefined(this.entryId)) {
      this.treePropertyDefinitionService
        .getPropertyDefinitionById(this.entryId)
        .toPromise().then((ret: TreePropertyDefinition) => {
          this.treePropertyDefinition = ret;
          this.form.get('name').setValue(this.treePropertyDefinition.name);
          this.form
            .get('description')
            .setValue(this.treePropertyDefinition.description);

          this.form.get('required').setValue(this.treePropertyDefinition.required);
          this.form.get('requiredMessage').setValue(this.treePropertyDefinition.requiredMessage);
          this.multipleToggled = this.treePropertyDefinition.multiple;
          this.showEditor = true;
          this.loaded = true;
        });
    } else {
      this.loaded = true;
    }
  }

  navigateBack() {
    window.history.back();
  }

  populateEditor() { }

  handleCreateClick() {
    this.form.controls['name'].markAsTouched();
    if (this.form.invalid) {
      this.form.markAllAsTouched();
    } else {

      this.form.disable();
      const newTreePropertyDefinition = new TreePropertyDefinition(
        this.form.controls['name'].value,
        this.form.controls['description'].value,
        this.form.controls['multiple'].value,
        this.form.controls['required'].value,
        this.form.controls['requiredMessage'].value
      );

      if (!newTreePropertyDefinition.required) {
        newTreePropertyDefinition.requiredMessage = null;
      }

      newTreePropertyDefinition.custom = true;

      newTreePropertyDefinition.tenantId = this.tenantId;

      this.treePropertyDefinitionService.newPropertyDefinition(newTreePropertyDefinition).toPromise().then((treePropertyDefinition: TreePropertyDefinition) => {
        if (!isNullOrUndefined(treePropertyDefinition)) {
          this.treePropertyDefinition = treePropertyDefinition;
          this.responseService.sendPropertyConfiguratorResponse(this.redirectUrl, undefined, [treePropertyDefinition.id], 'save').toPromise().then(() => {

            this.showEditor = true;
          });
        }
      }).catch(error => {
        this.form.enable();
        const str = '' + this.form.value.name;
        this.form.controls['name'].setValidators([Validators.required, stringsUnique(str, this.form.value.name)]);
        this.form.controls['name'].updateValueAndValidity();
      });
    }
  }

  handleNameKeyUp() {
    if (this.form.controls['name'].hasError('stringsUnique')) {
      this.form.controls['name'].setValidators([Validators.required]);
      this.form.controls['name'].updateValueAndValidity();
    }
  }

  handleCancelClick() {
    this.result.emit(undefined);
  }

  handleResult(event: { type: string; payload: TreePropertyDefinition }) {

    event.payload.tenantId = this.tenantId;

    if (event.type === 'save') {
      event.payload.description = this.form.controls['description'].value;
      event.payload.multiple = this.multipleToggled;
      this.treePropertyDefinitionService
        .savePropertyDefinition(event.payload)
        .toPromise().then((ret: TreePropertyDefinition) => {
          this.responseService.sendPropertyConfiguratorResponse(this.redirectUrl, undefined, [ret.id], 'save').toPromise().then(() => {
            return; // don't emit result
          });
        });

    } else if (event.type === 'back') {
      this.result.emit({ builderType: 'tree', value: event.payload });

    } else if (event.type === 'saveAndBack') {
      event.payload.description = this.form.controls['description'].value;
      event.payload.multiple = this.multipleToggled;

      this.treePropertyDefinitionService
        .savePropertyDefinition(event.payload)
        .toPromise().then((ret: TreePropertyDefinition) => {
          this.responseService.sendPropertyConfiguratorResponse(this.redirectUrl, undefined, [ret.id], 'save').toPromise().then(() => {
            this.result.emit({ builderType: 'tree', value: ret });
          });
        });
    }
  }

  handleManagementEvent(event: string) {
    this.management.emit(event);
  }
}
