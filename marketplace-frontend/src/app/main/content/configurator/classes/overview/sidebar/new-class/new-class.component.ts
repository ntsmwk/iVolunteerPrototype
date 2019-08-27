import { Component, OnInit, Input, Output, EventEmitter, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../../../_model/marketplace';
import { ConfiguratorService } from '../../../../../_service/configurator.service';
import { ClassDefintion } from '../../../../../_model/meta/Class';

import { fuseAnimations } from '@fuse/animations';

import { Relationship, RelationshipType, Inheritance, Association } from 'app/main/content/_model/meta/Relationship';
import { FormGroup, FormBuilder } from '@angular/forms';
import { DialogFactoryComponent } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.component';
import { PropertyService } from 'app/main/content/_service/property.service';
import { Property } from 'app/main/content/_model/meta/Property';
import { Network } from 'vis';
import { isNullOrUndefined } from 'util';
import { ok } from 'assert';
import { of } from 'rxjs';


@Component({
  selector: 'app-sidebar-new-class',
  templateUrl: './new-class.component.html',
  styleUrls: ['./new-class.component.scss'],
  animations: fuseAnimations,
  providers: [DialogFactoryComponent]

})
export class SidebarNewClassComponent implements OnInit, OnDestroy {

  @Input() marketplace: Marketplace;
  @Input() configurableClasses: ClassDefintion[];
  @Input() relationships: Relationship[];
  @Input() network: Network;

  @Output() sidebarCloseEvent: EventEmitter<any> = new EventEmitter();

  form: FormGroup;
  allProperties: Property<any>[];
  selectedProperties: Property<any>[];

  relationshipEditMode: boolean = false;
  newRelationships: any[];

  constructor(private router: Router,
    private route: ActivatedRoute,
    private configuratorService: ConfiguratorService,
    private fb: FormBuilder,
    private dialogFactory: DialogFactoryComponent,
    private propertyService: PropertyService) {

  }


  ngOnInit() {
    this.form = this.fb.group(
      { name: '' }
    )

    this.selectedProperties = [];
    this.newRelationships = [];

    this.propertyService.getProperties(this.marketplace).toPromise().then((properties: Property<any>[]) => {
      this.allProperties = properties;
    });

    this.network.on('click', (params: any) => {
      this.handleClickEvent(params);
    });
  }

  onSubmit() {

    let newClass: ClassDefintion = new ClassDefintion();
    newClass.id = null;
    newClass.name = this.form.value['name'];
    newClass.properties = this.selectedProperties;

    this.configuratorService.createNewConfigClass(this.marketplace, newClass).toPromise().then((ret: ClassDefintion) => {

      this.configurableClasses.push(ret);

      //Patch assigned ID
      let promises: Promise<any>[] = [];

      for (let r of this.newRelationships) {
        promises.push(this.createPromise(r, ret));
      }

      Promise.all(promises).then(() => {
        this.doCleanup();
      });

    });

  }

  createPromise(r: Relationship, ret: ClassDefintion) {

    return new Promise((resolve) => {

      if (r.classId1 == null) {
        r.classId1 = ret.id;
      } else if (r.classId2 == null) {
        r.classId2 = ret.id;
      }


      if (r.relationshipType == RelationshipType.INHERITANCE) {
        if ((r as Inheritance).superClassId == null) {
          (r as Inheritance).superClassId = ret.id;
        }

        resolve(this.configuratorService.addInheritance(this.marketplace, r as Inheritance).toPromise().then((rel: any) => {
          resolve(this.relationships.push(rel));




        }));

      } else if (r.relationshipType == RelationshipType.ASSOCIATION) {
        resolve(this.configuratorService.addAssociation(this.marketplace, r as Association).toPromise().then((rel: any) => {
          resolve(this.relationships.push(rel));

        }));


      }
    });

  }

  doCleanup() {
    this.newRelationships = [];
    this.relationshipEditMode = false;
    this.sidebarCloseEvent.emit();

  }

  addProperty() {

    this.dialogFactory.addPropertyDialogGeneric(this.allProperties, this.selectedProperties).then((result: Property<any>[]) => {
      this.selectedProperties.push(...result);
      //update View
    });
  }

  addRelationship() {
    this.relationshipEditMode = true;
  }


  handleClickEvent(event: any) {
    if (this.relationshipEditMode && event.nodes.length == 1) {
      // let r: Relationship = {id: null, classId1: null, classId2: event.nodes[0], relationshipType: null};
      const classId = event.nodes[0]
      this.dialogFactory.newRelationshipDialog(classId).then((ret: Relationship) => {

        if (!isNullOrUndefined(ret)) {

          if (ret.relationshipType == RelationshipType.INHERITANCE) {
            this.newRelationships.push(ret as Inheritance);
          } else if (ret.relationshipType == RelationshipType.ASSOCIATION) {

            this.newRelationships.push(ret as Association);

          }

        }
      });


    }

    this.relationshipEditMode = false;
  }


  ngOnDestroy() {
    this.relationshipEditMode = false;
  }


}
