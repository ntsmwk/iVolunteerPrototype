import { Component, Inject, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { LoginService } from 'app/main/content/_service/login.service';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { ClassConfiguration } from 'app/main/content/_model/configurations';
import { isNullOrUndefined } from 'util';
import { FormControl, FormGroup } from '@angular/forms';
import { CUtils } from '../utils-and-constants';
import { ObjectIdService } from 'app/main/content/_service/objectid.service.';
import { Relationship } from 'app/main/content/_model/meta/Relationship';
import { ClassDefinition } from 'app/main/content/_model/meta/Class';
import { RelationshipService } from 'app/main/content/_service/meta/core/relationship/relationship.service';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';


export interface NewClassConfigurationDialogData {
  marketplace: Marketplace;

  classConfiguration: ClassConfiguration;
  relationships: Relationship[];
  classDefinitions: ClassDefinition[];
}

@Component({
  selector: 'new-class-configuration-dialog',
  templateUrl: './new-dialog.component.html',
  styleUrls: ['./new-dialog.component.scss']
})
export class NewClassConfigurationDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<NewClassConfigurationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: NewClassConfigurationDialogData,
    private classConfigurationService: ClassConfigurationService,
    private relationshipsService: RelationshipService,
    private classDefintionService: ClassDefinitionService,
    private objectIdService: ObjectIdService,
    private loginService: LoginService,
  ) {
  }

  dialogForm = new FormGroup({
    label: new FormControl(''),
    description: new FormControl(''),
    rootLabel: new FormControl('')
  });

  allClassConfigurations: ClassConfiguration[];
  loaded = false;


  ngOnInit() {
    this.loginService.getLoggedIn().toPromise().then((helpseeker: Helpseeker) => {
      this.classConfigurationService.getAllClassConfigurationsSortedDesc(this.data.marketplace).toPromise().then((classConfigurations: ClassConfiguration[]) => {

        this.allClassConfigurations = classConfigurations;


        // ----DEBUG
        // this.recentMatchingConfigurations.push(...this.recentMatchingConfigurations);
        // this.recentMatchingConfigurations.push(...this.recentMatchingConfigurations);
        // ----

        this.loaded = true;
      });
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  //TODO just use a fucking FormControl

  onOKClick() {


    console.log(this.dialogForm.value);
    console.log(this.dialogForm);




    if (this.dialogForm.invalid) {
      this.dialogForm.get('label').markAsTouched();
      this.dialogForm.get('description').markAsTouched();
      this.dialogForm.get('rootLabel').markAsTouched();
    } else {

      const classConfiguration = new ClassConfiguration();

      classConfiguration.name = this.dialogForm.get('label').value;
      classConfiguration.description = this.dialogForm.get('description').value;


      const standardObjects = CUtils.getStandardObjects(this.data.marketplace.id, this.objectIdService, this.dialogForm.get('rootLabel').value);
      this.data.relationships = standardObjects.relationships;
      this.data.classDefinitions = standardObjects.classDefinitions;

      classConfiguration.relationshipIds = this.data.relationships.map(r => r.id);
      classConfiguration.classDefinitionIds = this.data.classDefinitions.map(c => c.id);

      Promise.all([
        this.relationshipsService.addAndUpdateRelationships(this.data.marketplace, this.data.relationships).toPromise().then((ret: Relationship) => {
          console.log(ret);
        }),
        this.classDefintionService.addOrUpdateClassDefintions(this.data.marketplace, this.data.classDefinitions).toPromise().then((ret: ClassDefinition) => {
          console.log(ret);
        }),
        this.classConfigurationService.createNewClassConfiguration(this.data.marketplace, classConfiguration).toPromise().then((ret: ClassConfiguration) => {
          console.log(ret);
          this.data.classConfiguration = ret;
        })
      ]).then(() => {
        console.log('finished');

        this.dialogRef.close(this.data);

      });




    }



  }




}


