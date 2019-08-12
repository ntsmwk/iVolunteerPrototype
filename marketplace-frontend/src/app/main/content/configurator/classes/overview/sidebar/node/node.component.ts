import { Component, OnInit, Input, ViewChild, ElementRef, HostListener, AfterViewInit, Output, EventEmitter, OnDestroy, OnChanges } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { Marketplace } from '../../../../../_model/marketplace';
import { ConfiguratorService } from '../../../../../_service/configurator.service';
import { ConfigurableClass } from '../../../../../_model/configurables/Configurable';

import { fuseAnimations } from '@fuse/animations';

import { Relationship, RelationshipType, Inheritance, Association } from 'app/main/content/_model/configurables/Relationship';
import { Network } from 'vis';
import { DialogFactoryComponent } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.component';
import { isNullOrUndefined, isNull } from 'util';
import { Property } from 'app/main/content/_model/configurables/Property';
import { PropertyService } from 'app/main/content/_service/property.service';


@Component({
  selector: 'app-sidebar-node',
  templateUrl: './node.component.html',
  styleUrls: ['./node.component.scss'],
  animations: fuseAnimations,
  providers: [DialogFactoryComponent]


})
export class SidebarNodeComponent implements OnInit, OnDestroy, OnChanges {

  @Input() marketplace: Marketplace;
  @Input() configurableClass: ConfigurableClass;
  @Input() relationships: Relationship[];
  @Input() network: Network;

  @Output() toggleEditMode: EventEmitter<any> = new EventEmitter();
  @Output() rebuildNetwork: EventEmitter<any> = new EventEmitter();


  relationshipEditMode: boolean;
  newRelationships: Relationship[];

  allProperties: Property<any>[];
  selectedProperties: Property<any>[];
  addedProperties: Property<any>[];
  

  constructor(private router: Router,
    private route: ActivatedRoute,
    private configuratorService: ConfiguratorService,
    private propertyService: PropertyService,
    private dialogFactory: DialogFactoryComponent) {

  }


  ngOnInit() {
    this.relationshipEditMode = false;
    this.newRelationships = [];

    this.propertyService.getProperties(this.marketplace).toPromise().then((properties: Property<any>[]) => {
      this.allProperties = properties;
    });

    
    // this.selectedProperties = ConfigurableClass.getProperties(this.configurableClass);
    this.selectedProperties = this.configurableClass.properties;
    this.addedProperties = [];

    this.network.on('click', params => {
      this.handleClickEvent(params);
    });
  }

  ngOnChanges() {
    // this.selectedProperties = ConfigurableClass.getProperties(this.configurableClass);
    this.selectedProperties = this.configurableClass.properties;
  }

  clickedAddProperty() {
    this.dialogFactory.addPropertyDialogGeneric(this.allProperties, this.selectedProperties).then((result: Property<any>[]) => {
      this.addedProperties.push(...result);
      this.selectedProperties.push(...result);
      //update View
    });
  }

  clickedAddRelationship() {
    this.relationshipEditMode = true;
    this.toggleEditMode.emit(true);
  }

  clickedChangeName() {
    this.dialogFactory.genericDialog1Textfield("Change Name", undefined, "Class Name", this.configurableClass.name).then((result: string) => {
      
      if (!isNullOrUndefined(result)) {
        this.configurableClass.name = result;
      }
    });
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


      this.relationshipEditMode = false;
      this.toggleEditMode.emit(false);
    }

    


    
  }


  onSubmit() {

    Promise.all([
      this.configuratorService.addRelationships(this.marketplace, this.configurableClass, this.newRelationships).then((ret: Relationship[]) => {

        if (!isNullOrUndefined(ret)) {
          this.relationships.push(...ret);
        }
      }),

      this.configuratorService.addPropertiesToConfigClassById(this.marketplace, this.configurableClass.id, this.addedProperties.map(p => p.id)).toPromise().then((ret: ConfigurableClass) => {
        this.configurableClass = ret;
      }),
      this.configuratorService.changeConfigClassName(this.marketplace, this.configurableClass.id, this.configurableClass.name).toPromise().then((ret: ConfigurableClass) => {
        this.configurableClass = ret;
      })
    ]).then(() => {
      this.doCleanup();
    });
    
  }

  doCleanup() {
    this.newRelationships = [];
    this.relationshipEditMode = false;


    let event = {relationships: undefined, configurableClasses: undefined};
    Promise.all([
      this.configuratorService.findAllRelationships(this.marketplace).toPromise().then((ret: Relationship[]) => {
        event.relationships = ret;
  
      }),
      this.configuratorService.getAllConfigClasses(this.marketplace).toPromise().then((ret: ConfigurableClass[]) => {
        event.configurableClasses = ret;
      })
    ]).then(() => {
      this.rebuildNetwork.emit(event);

    });



  }

  ngOnDestroy() {
    this.relationshipEditMode = false;
    this.toggleEditMode.emit(false);
  }

}
