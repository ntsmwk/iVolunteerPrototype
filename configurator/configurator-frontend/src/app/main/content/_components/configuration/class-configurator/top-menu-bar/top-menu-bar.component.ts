import { Component, ElementRef, ViewChild, Output, EventEmitter, Input, AfterViewInit, OnChanges } from '@angular/core';
import { isNullOrUndefined } from 'util';
import { ClassConfiguration, ClassConfigurationDTO } from 'app/main/content/_model/configurator/configurations';
import { Relationship } from 'app/main/content/_model/configurator/relationship';
import { ClassDefinition } from 'app/main/content/_model/configurator/class';
import { DialogFactoryDirective } from 'app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.component';
import { NewClassConfigurationDialogData } from '../_dialogs/new-dialog/new-dialog.component';
import { OpenClassConfigurationDialogData } from '../_dialogs/open-dialog/open-dialog.component';
import { DeleteClassConfigurationDialogData } from '../_dialogs/delete-dialog/delete-dialog.component';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';

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

  followingAction?: string;
  classConfiguration?: ClassConfiguration;
  classConfigurationId?: string;
  classDefintions?: ClassDefinition[];
  relationships?: Relationship[];

  deletedClassDefinitions?: string[];
  deletedRelationships?: string[];

  tenantId?: string;
  redirectUrl: string;

  constructor() {
    this.classDefintions = [];
    this.relationships = [];
  }
}

const rootMenuItems: RootMenuItem[] = [{ id: 1, label: 'Menü', icon: 'menu' }];

const subMenuItems: SubMenuItem[] = [
  {
    rootId: 1,
    id: 1,
    label: 'Neue Konfiguration',
    clickAction: 'newClicked',
    icon: undefined
  },
  {
    rootId: 1,
    id: 2,
    label: 'Konfiguration öffnen',
    clickAction: 'openClicked',
    icon: undefined
  },
  {
    rootId: 1,
    id: 3,
    label: 'Konfiguration speichern',
    clickAction: 'saveClicked',
    icon: undefined
  },
  {
    rootId: 1,
    id: 3,
    label: 'Konfiguration löschen',
    clickAction: 'deleteClicked',
    icon: undefined
  },
  {
    rootId: 1,
    id: 4,
    label: 'Eintrag erfassen',
    clickAction: 'createEditorClicked',
    icon: undefined
  }
];

@Component({
  selector: "editor-top-menu-bar",
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
  @ViewChild('titlebarTextContainer', { static: true })
  titleBarTextContainer: ElementRef;

  @Input() eventResponse: TopMenuResponse;
  @Input() tenantId: string;
  @Input() redirectUrl: string;
  @Output() menuOptionClickedEvent: EventEmitter<any> = new EventEmitter();

  currentClassConfiguration: ClassConfiguration;



  constructor(
    private route: ActivatedRoute,
    private dialogFactory: DialogFactoryDirective,
    private classConfigurationService: ClassConfigurationService
  ) { }

  ngOnInit() {
    // this.route.queryParams.subscribe(params => {
    //   this.tenantId = params['tenantId'];
    //   this.redirectUrl = params['redirect'];
    // });
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
    this.submenuContainer.nativeElement.style.font =
      'Arial, Helvetica, sans-serif';
    this.submenuContainer.nativeElement.style.display = 'none';

    this.menubarContainer.nativeElement.style.position = 'absolute';
    this.menubarContainer.nativeElement.style.overflow = 'hidden';
    this.menubarContainer.nativeElement.style.right = '0px';
    this.menubarContainer.nativeElement.style.top = '0px';
    this.menubarContainer.nativeElement.style.left = '0px';
    this.menubarContainer.nativeElement.style.height = '35px';
    this.menubarContainer.nativeElement.style.background = 'white';
    this.menubarContainer.nativeElement.style.font =
      'Arial, Helvetica, sans-serif';

    this.titleBarTextContainer.nativeElement.style.maxWidth =
      // clientwidth of titlebar - margin left/right - icon left
      this.menubarContainer.nativeElement.clientWidth - 50 - 15 + 'px';

    const outer = this;

    document.addEventListener('click', function (event) {
      outer.handleHTMLClickEvent(event);
    });
  }

  handleHTMLClickEvent(event: any) {
    if (
      event.srcElement.className !== 'menuitem' &&
      event.srcElement.className !==
      'menuitem-icon mat-icon notranslate material-icons mat-icon-no-color'
    ) {
      this.submenuContainer.nativeElement.style.display = 'none';
    }
  }

  openSubmenu(event: any, rootItemId: number) {
    this.currentRootId = rootItemId;
    this.submenuContainer.nativeElement.style.display = 'block';
    const leftPosition = this.calculateLeftSpace(
      event.srcElement.offsetParent,
      rootItemId
    );
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

  // menu functions

  newClicked() {
    if (!isNullOrUndefined(this.currentClassConfiguration)) {
      this.saveClicked('performNew');
    } else {
      this.performNew();
    }
  }

  private performNew() {
    this.dialogFactory
      .openNewClassConfigurationDialog(this.tenantId, this.redirectUrl)
      .then((ret: NewClassConfigurationDialogData) => {
        if (!isNullOrUndefined(ret)) {
          this.currentClassConfiguration = ret.classConfiguration;
          this.menuOptionClickedEvent.emit({ id: 'editor_new', payload: ret });
        } else {
          this.menuOptionClickedEvent.emit({ id: 'cancelled' });
        }
      });
  }

  editClicked() {
    this.performEdit();
  }

  private performEdit() {
    this.dialogFactory
      .openNewClassConfigurationDialog(this.tenantId, this.redirectUrl, this.currentClassConfiguration)
      .then((ret: NewClassConfigurationDialogData) => {
        if (!isNullOrUndefined(ret)) {
          this.currentClassConfiguration = ret.classConfiguration;
          this.menuOptionClickedEvent.emit({
            id: 'editor_meta_edit',
            payload: {
              name: ret.classConfiguration.name,
              description: ret.classConfiguration.description
            }
          });
        } else {
          this.menuOptionClickedEvent.emit({ id: 'cancelled' });
        }
      });
  }

  openClicked() {
    if (!isNullOrUndefined(this.currentClassConfiguration)) {
      this.saveClicked('performOpen');
    } else {
      this.performOpen();
    }
  }

  private performOpen() {
    this.dialogFactory
      .openOpenClassConfigurationDialog(this.tenantId)
      .then((ret: OpenClassConfigurationDialogData) => {
        if (!isNullOrUndefined(ret)) {
          this.currentClassConfiguration = ret.classConfiguration;
          this.menuOptionClickedEvent.emit({ id: 'editor_open', payload: ret });
        } else {
          this.menuOptionClickedEvent.emit({ id: 'cancelled' });
        }
      });
  }

  private performOpenByid(classConfigurationId: string) {
    this.classConfigurationService.getAllForClassConfigurationInOne(classConfigurationId).toPromise()
      .then((dto: ClassConfigurationDTO) => {
        if (!isNullOrUndefined(dto)) {
          this.currentClassConfiguration = dto.classConfiguration;
          this.menuOptionClickedEvent.emit({ id: 'editor_open', payload: dto });
        }
      })
      .catch((error: HttpErrorResponse) => {
        if (error.status === 500 && error.statusText === 'OK') {
          console.error(
            'no classConfiguration with this id: ' + classConfigurationId
          );
          this.performOpen();
        }
      });
  }

  saveClicked(followingAction: string) {
    this.menuOptionClickedEvent.emit({
      id: 'editor_save',
      followingAction: followingAction
    });
  }

  private performSave(
    classConfiguration: ClassConfiguration,
    classDefintions: ClassDefinition[],
    relationships: Relationship[],
    deletedClassDefinitions: string[],
    deletedRelationships: string[],
    actionAfter: string,
    tenantId: string,
    redirectUrl: string
  ) {
    this.dialogFactory
      .openSaveClassConfigurationConfirmationDialog(
        classConfiguration,
        classDefintions,
        relationships,
        deletedClassDefinitions,
        deletedRelationships,
        tenantId,
        redirectUrl
      )
      .then(ret => {
        if (isNullOrUndefined(ret)) {
          this.menuOptionClickedEvent.emit({ id: 'cancelled' });
        } else {
          this.menuOptionClickedEvent.emit({
            id: 'editor_save_return',
            payload: ret
          });
        }

        if (!isNullOrUndefined(actionAfter)) {
          this[actionAfter]();
        }
      });
  }

  deleteClicked() {
    this.dialogFactory
      .openDeleteClassConfigurationDialog(this.tenantId, this.redirectUrl)
      .then((ret: DeleteClassConfigurationDialogData) => {
        if (!isNullOrUndefined(ret)) {
          this.menuOptionClickedEvent.emit({
            id: 'editor_delete',
            payload: ret
          });
        } else {
          this.menuOptionClickedEvent.emit({ id: 'cancelled' });
        }
      });
  }

  createEditorClicked() {
    if (!isNullOrUndefined(this.currentClassConfiguration)) {
      this.saveClicked('performCreate');
    } else {
      this.performCreate();
    }
  }

  performCreate() {
    this.menuOptionClickedEvent.emit({ id: 'editor_create_instance' });
  }

  ngOnChanges() {
    const eventResponseAction = this.eventResponse.action;
    const eventFollowingAction = this.eventResponse.followingAction;
    const eventClassConfiguration = this.eventResponse.classConfiguration;
    const eventClassConfigurationId = this.eventResponse.classConfigurationId;
    const eventClassDefinitions = this.eventResponse.classDefintions;
    const eventRelationships = this.eventResponse.relationships;
    const eventDeletedClassDefinitions = this.eventResponse
      .deletedClassDefinitions;
    const eventDeletedRelationships = this.eventResponse.deletedRelationships;
    const eventTenantId = this.eventResponse.tenantId;
    const eventRedirectUrl = this.eventResponse.redirectUrl;

    this.eventResponse = new TopMenuResponse();
    if (eventResponseAction === 'save') {
      if (!isNullOrUndefined(eventClassConfiguration)) {
        this.performSave(
          eventClassConfiguration,
          eventClassDefinitions,
          eventRelationships,
          eventDeletedClassDefinitions,
          eventDeletedRelationships,
          eventFollowingAction,
          eventTenantId,
          eventRedirectUrl
        );
      }
    } else if (eventResponseAction === 'open') {
      if (!isNullOrUndefined(eventClassConfigurationId)) {
        this.performOpenByid(eventClassConfigurationId);
      }
    }
  }
}
