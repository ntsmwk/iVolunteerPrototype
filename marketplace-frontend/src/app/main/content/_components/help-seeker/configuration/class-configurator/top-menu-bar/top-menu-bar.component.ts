import { Component, ElementRef, ViewChild, Output, EventEmitter, Input, AfterViewInit, OnChanges } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { isNullOrUndefined } from 'util';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { ClassConfiguration } from 'app/main/content/_model/meta/configurations';
import { Relationship } from 'app/main/content/_model/meta/relationship';
import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { DialogFactoryDirective } from 'app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.component';
import { NewClassConfigurationDialogData } from '../_dialogs/new-dialog/new-dialog.component';
import { OpenClassConfigurationDialogData } from '../_dialogs/open-dialog/open-dialog.component';
import { DeleteClassConfigurationDialogData } from '../_dialogs/delete-dialog/delete-dialog.component';

export interface RootMenuItem {
  id: number;
  label: string;
  icon: string;
}

export interface SubMenuItem {
  rootId: number;
  id: number;
  label: string;
  clickAction: string;
  icon: string;
}

export class TopMenuResponse {
  action: string;
  classConfiguration: ClassConfiguration;
  classDefintions: ClassDefinition[];
  relationships: Relationship[];

  deletedClassDefinitions?: string[];
  deletedRelationships?: string[];

  constructor() {
    this.classDefintions = [];
    this.relationships = [];
  }
}

const rootMenuItems: RootMenuItem[] = [
  { id: 1, label: 'Menü', icon: 'menu' },
  // { id: 2, label: 'Bearbeiten' },
  // { id: 3, label: 'Ansicht' },
  // { id: 4, label: 'Extras' },
  // { id: 5, label: 'Hilfe' },
];

const subMenuItems: SubMenuItem[] = [
  { rootId: 1, id: 1, label: 'Neue Konfiguration', clickAction: 'newClicked', icon: undefined },
  { rootId: 1, id: 2, label: 'Konfiguration öffnen', clickAction: 'openClicked', icon: undefined },
  { rootId: 1, id: 3, label: 'Konfiguration speichern', clickAction: 'saveClicked', icon: undefined },
  // { rootId: 1, id: 3, label: 'Konfiguration speichern unter', clickAction: 'saveAsClicked', icon: undefined },
  { rootId: 1, id: 3, label: 'Konfiguration löschen', clickAction: 'deleteClicked', icon: undefined },
  { rootId: 1, id: 4, label: 'Instanz erstellen', clickAction: 'createEditorClicked', icon: undefined },

  // { rootId: 2, id: 1, label: 'Test Entry 21', clickAction: 'test', icon: undefined },
  // { rootId: 2, id: 2, label: 'Test Entry 22', clickAction: 'test', icon: undefined },
  // { rootId: 2, id: 3, label: 'Test Entry 23', clickAction: 'test', icon: undefined },
  // { rootId: 2, id: 4, label: 'Test Entry 24', clickAction: 'test', icon: undefined },

  // { rootId: 3, id: 1, label: 'Test Entry 31', clickAction: 'test', icon: undefined },
  // { rootId: 3, id: 2, label: 'Test Entry 32', clickAction: 'test', icon: undefined },
  // { rootId: 3, id: 3, label: 'Test Entry 33', clickAction: 'test', icon: undefined },

  // { rootId: 4, id: 1, label: 'Test Entry 41', clickAction: 'test', icon: undefined },
  // { rootId: 4, id: 2, label: 'Test Entry 42', clickAction: 'test', icon: undefined },
  // { rootId: 4, id: 3, label: 'Test Entry 43', clickAction: 'test', icon: undefined },
  // { rootId: 4, id: 4, label: 'Test Entry 44', clickAction: 'test', icon: undefined },
  // { rootId: 4, id: 5, label: 'Test Entry 45', clickAction: 'test', icon: undefined },

  // { rootId: 5, id: 1, label: 'Test Entry 51', clickAction: 'test', icon: undefined },
];

@Component({
  selector: 'editor-top-menu-bar',
  templateUrl: './top-menu-bar.component.html',
  styleUrls: ['./top-menu-bar.component.scss']
})
export class EditorTopMenuBarComponent implements AfterViewInit, OnChanges {

  isLoaded: boolean;
  menuOpen: boolean;

  rootMenuItems = rootMenuItems;
  subMenuItems = subMenuItems;
  currentRootId = 1;

  @ViewChild('menubarContainer', { static: true }) menubarContainer: ElementRef;
  @ViewChild('submenuContainer', { static: true }) submenuContainer: ElementRef;

  @Input() marketplace: Marketplace;
  @Input() eventResponse: TopMenuResponse;
  @Output() menuOptionClickedEvent: EventEmitter<any> = new EventEmitter();

  configurationName: string;

  constructor(private router: Router,
    private route: ActivatedRoute,
    private dialogFactory: DialogFactoryDirective
  ) {

  }

  ngOnInit() {
  }

  ngAfterViewInit() {

    this.submenuContainer.nativeElement.style.position = 'absolute';
    this.submenuContainer.nativeElement.style.overflow = 'hidden';
    this.submenuContainer.nativeElement.style.padding = '0px';
    this.submenuContainer.nativeElement.style.top = '29px';
    this.submenuContainer.nativeElement.style.left = '10px';
    this.submenuContainer.nativeElement.style.height = 'auto';
    this.submenuContainer.nativeElement.style.width = '200px';
    this.submenuContainer.nativeElement.style.background = 'white';
    this.submenuContainer.nativeElement.style.font = 'Arial, Helvetica, sans-serif';
    this.submenuContainer.nativeElement.style.display = 'none';

    this.menubarContainer.nativeElement.style.position = 'absolute';
    this.menubarContainer.nativeElement.style.overflow = 'hidden';
    // this.menubarContainer.nativeElement.style.padding = '2px';
    this.menubarContainer.nativeElement.style.right = '0px';
    this.menubarContainer.nativeElement.style.top = '0px';
    this.menubarContainer.nativeElement.style.left = '0px';
    this.menubarContainer.nativeElement.style.height = '35px';
    this.menubarContainer.nativeElement.style.background = 'white';
    this.menubarContainer.nativeElement.style.font = 'Arial, Helvetica, sans-serif';

    const outer = this;

    document.addEventListener('click', function (event) {
      outer.handleHTMLClickEvent(event);
    });
  }

  handleHTMLClickEvent(event: any) {
    if (event.srcElement.className !== 'menuitem' && event.srcElement.className !== 'menuitem-icon mat-icon notranslate material-icons mat-icon-no-color') {
      this.submenuContainer.nativeElement.style.display = 'none';
    }
  }

  openSubmenu(event: any, rootItemId: number) {
    this.currentRootId = rootItemId;
    this.submenuContainer.nativeElement.style.display = 'block';
    const leftPosition = this.calculateLeftSpace(event.srcElement.offsetParent, rootItemId);
    this.submenuContainer.nativeElement.style.left = leftPosition + 'px';
  }

  private calculateLeftSpace(offsetParent: any, rootItemId: number) {
    let space = 10;
    if (!isNullOrUndefined(offsetParent.children)) {
      for (const child of offsetParent.children) {
        if (rootItemId - 1 <= 0) {
          return space;
        }
        space += child.clientWidth;
        rootItemId--;
      }
    }
  }

  newClicked(event: any, item: SubMenuItem) {
    this.dialogFactory.openNewClassConfigurationDialog(this.marketplace).then((ret: NewClassConfigurationDialogData) => {
      if (!isNullOrUndefined(ret)) {
        this.configurationName = ret.classConfiguration.name;
        this.menuOptionClickedEvent.emit({ id: 'editor_new', payload: ret });
      } else {
        this.menuOptionClickedEvent.emit({ id: 'cancelled' });
      }
    });
  }

  openClicked(event: any, item: SubMenuItem) {
    this.dialogFactory.openConfiguratorDialog(this.marketplace).then((ret: OpenClassConfigurationDialogData) => {
      if (!isNullOrUndefined(ret)) {
        this.configurationName = ret.classConfiguration.name;
        this.menuOptionClickedEvent.emit({ id: 'editor_open', payload: ret });
      } else {
        this.menuOptionClickedEvent.emit({ id: 'cancelled' });
      }

    });
  }

  saveClicked(event: any, item: SubMenuItem) {
    this.menuOptionClickedEvent.emit({ id: 'editor_save' });
  }

  private performSave(classConfiguration: ClassConfiguration, classDefintions: ClassDefinition[],
    relationships: Relationship[], deletedClassDefinitions: string[], deletedRelationships: string[]) {

    this.dialogFactory
      .openSaveConfirmationDialog(this.marketplace, classConfiguration, classDefintions, relationships, deletedClassDefinitions, deletedRelationships)
      .then((ret) => {
        if (!isNullOrUndefined(ret)) {
          //return

          this.menuOptionClickedEvent.emit({ id: 'editor_save_return', payload: ret });
        } else {
          this.menuOptionClickedEvent.emit({ id: 'cancelled' });
        }
      });
  }

  deleteClicked(event: any, item: SubMenuItem) {
    this.dialogFactory.openDeleteClassConfiguratorDialog(this.marketplace).then((ret: DeleteClassConfigurationDialogData) => {
      if (!isNullOrUndefined(ret)) {
        this.menuOptionClickedEvent.emit({ id: 'editor_delete', payload: ret });
      } else {
        this.menuOptionClickedEvent.emit({ id: 'cancelled' });
      }
    });


  }

  // private performSaveAs(classConfiguration: ClassConfiguration, classDefintions: ClassDefinition[],
  //   relationships: Relationship[], deletedClassDefinitions: string[], deletedRelationships: string[]) {

  //   console.log("perform save as");
  //   this.dialogFactory
  //     .openSaveClassConfigurationAsDialog(this.marketplace, classConfiguration, classDefintions, relationships, deletedClassDefinitions, deletedRelationships)
  //     .then((ret) => {
  //       if (!isNullOrUndefined(ret)) {
  //         //return
  //         console.log(ret);
  //       } else {
  //         this.menuOptionClickedEvent.emit({ id: 'cancelled' });
  //       }
  //     });
  // }

  // saveAsClicked(event: any, item: SubMenuItem) {
  //   this.menuOptionClickedEvent.emit({ id: 'editor_save_as' });

  //   // wrapped in setTimeout - hack to avoid ExpressionChangedAfterItHasBeenCheckedError because of ngOnChanges lifecycle hook
  //   // setTimeout(() => {
  //   //   this.dialogFactory
  //   //   .openSaveClassConfigurationAsDialog(this.marketplace, classConfiguration, classDefintions, relationships, deletedClassDefinitions, deletedRelationships)
  //   //   .then((ret: any) => {
  //   //     if (!isNullOrUndefined(ret)) {
  //   //       this.menuOptionClickedEvent.emit({ id: 'editor_save_as', configurator: ret });
  //   //     } else {
  //   //       this.menuOptionClickedEvent.emit({ id: 'cancelled' });
  //   //     }
  //   //   });
  //   // });
  // }

  createEditorClicked(event: any, item: SubMenuItem) {
    this.menuOptionClickedEvent.emit({ id: 'editor_create_instance' });

  }

  ngOnChanges() {
    const eventResponseAction = this.eventResponse.action;
    const eventClassConfiguration = this.eventResponse.classConfiguration;
    const eventClassDefinitions = this.eventResponse.classDefintions;
    const eventRelationships = this.eventResponse.relationships;
    const eventDeletedClassDefinitions = this.eventResponse.deletedClassDefinitions;
    const eventDeletedRelationships = this.eventResponse.deletedRelationships;

    this.eventResponse = new TopMenuResponse();
    if (eventResponseAction === 'saveAsClicked') {
      // this[eventResponseAction](eventResponseAction, undefined);
    } else if (eventResponseAction === 'save') {
      if (isNullOrUndefined(eventClassConfiguration)) {

      } else {
        this.performSave(eventClassConfiguration, eventClassDefinitions, eventRelationships, eventDeletedClassDefinitions, eventDeletedRelationships);
      }
    } else if (eventResponseAction === 'saveAs') {
      // this.performSaveAs(eventClassConfiguration, eventClassDefinitions, eventRelationships, eventDeletedClassDefinitions, eventDeletedRelationships);
    }
  }

}
