import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { ClassConfiguration } from 'app/main/content/_model/configurator/configurations';
import { FormControl, FormGroup } from '@angular/forms';
import { Relationship } from 'app/main/content/_model/configurator/relationship';
import { ClassDefinition } from 'app/main/content/_model/configurator/class';
import { RelationshipService } from 'app/main/content/_service/meta/core/relationship/relationship.service';
import { ClassDefinitionService } from 'app/main/content/_service/meta/core/class/class-definition.service';


export interface SaveClassConfigurationAsDialogData {
  classConfiguration: ClassConfiguration;
  classDefinitions: ClassDefinition[];
  relationships: Relationship[];

  deletedClassDefinitions: string[];
  deletedRelationships: string[];
}

@Component({
  selector: 'save-class-configuration-as-dialog',
  templateUrl: './save-as-dialog.component.html',
  styleUrls: ['./save-as-dialog.component.scss']
})
export class SaveClassConfigurationAsDialogComponent implements OnInit {

  constructor(
    public dialogRef: MatDialogRef<SaveClassConfigurationAsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: SaveClassConfigurationAsDialogData,
    private classConfigurationService: ClassConfigurationService,
    private relationshipsService: RelationshipService,
    private classDefintionService: ClassDefinitionService,
  ) {
  }

  dialogForm = new FormGroup({
    label: new FormControl(''),
    description: new FormControl('')
  });

  allClassConfigurations: ClassConfiguration[];
  loaded: boolean;
  browseMode: boolean;


  ngOnInit() {
    this.loaded = true;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onOKClick() {

    // if (this.dialogForm.invalid) {
    //   this.dialogForm.get('label').markAsTouched();
    //   this.dialogForm.get('description').markAsTouched();
    // } else {

    //   const classConfiguration = new ClassConfiguration();

    //   classConfiguration.name = this.dialogForm.get('label').value;
    //   classConfiguration.description = this.dialogForm.get('description').value;

    //   classConfiguration.relationshipIds = this.data.relationships.map(r => r.id);
    //   classConfiguration.classDefinitionIds = this.data.classDefinitions.map(c => c.id);

    //   Promise.all([
    //     this.relationshipsService.addAndUpdateRelationships(this.data.marketplace, this.data.relationships).toPromise().then((ret: Relationship) => {
    //       console.log(ret);
    //     }),
    //     this.classDefintionService.addOrUpdateClassDefintions(this.data.marketplace, this.data.classDefinitions).toPromise().then((ret: ClassDefinition) => {
    //       console.log(ret);
    //     })
    //   ]).then(() => {
    //     this.classConfigurationService.createNewClassConfiguration(this.data.marketplace, classConfiguration).toPromise().then((ret: ClassConfiguration) => {
    //       console.log(ret);
    //       this.data.classConfiguration = ret;
    //     }).then(() => {
    //       console.log('finished');

    //       this.dialogRef.close(this.data);
    //     });
    //   });
    // }
  }

  handleBrowseClick() {
    // this.classConfigurationService.getAllClassConfigurations().toPromise().then((classConfigurations: ClassConfiguration[]) => {
    //   this.allClassConfigurations = classConfigurations;

    //   // ----DEBUG
    //   // this.recentMatchingConfigurations.push(...this.recentMatchingConfigurations);
    //   // this.recentMatchingConfigurations.push(...this.recentMatchingConfigurations);
    //   // ----

    //   this.loaded = true;
    // });
  }




}


