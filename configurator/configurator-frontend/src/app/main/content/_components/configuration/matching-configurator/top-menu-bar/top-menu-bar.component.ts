import { Component, ElementRef, ViewChild, Output, EventEmitter, Input, AfterViewInit } from '@angular/core';
import { isNullOrUndefined } from 'util';
import { DialogFactoryDirective } from 'app/main/content/_components/_shared/dialogs/_dialog-factory/dialog-factory.component';

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

const rootMenuItems: RootMenuItem[] = [
  { id: 1, label: 'Menü', icon: 'menu' }
  // { id: 2, label: 'Bearbeiten' },
  // { id: 3, label: 'Ansicht' },
  // { id: 4, label: 'Extras' },
  // { id: 5, label: 'Hilfe' },
];

const subMenuItems: SubMenuItem[] = [
  {
    rootId: 1,
    id: 1,
    label: 'Neues Matching',
    clickAction: 'newClicked',
    icon: undefined
  },
  {
    rootId: 1,
    id: 2,
    label: 'Matching öffnen',
    clickAction: 'openClicked',
    icon: undefined
  },
  {
    rootId: 1,
    id: 3,
    label: 'Matching speichern',
    clickAction: 'saveClicked',
    icon: undefined
  },
  {
    rootId: 1,
    id: 4,
    label: 'Matching löschen',
    clickAction: 'deleteClicked',
    icon: undefined
  }

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
  selector: "matching-editor-top-menu-bar",
  templateUrl: './top-menu-bar.component.html',
  styleUrls: ['./top-menu-bar.component.scss']
})
export class MatchingTopMenuBarComponent implements AfterViewInit {
  isLoaded = false;
  menuOpen: false;

  rootMenuItems = rootMenuItems;
  subMenuItems = subMenuItems;
  currentRootId = 1;

  @ViewChild('menubarContainer', { static: true }) menubarContainer: ElementRef;
  @ViewChild('submenuContainer', { static: true }) submenuContainer: ElementRef;
  @ViewChild('titlebarTextContainer', { static: true })
  titleBarTextContainer: ElementRef;

  @Input() eventResponseAction: string;
  @Input() tenantId: string;
  @Input() redirectUrl: string;
  @Output() menuOptionClickedEvent: EventEmitter<any> = new EventEmitter();

  constructor(
    private dialogFactory: DialogFactoryDirective
  ) { }

  ngOnInit() { }

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
    // this.menubarContainer.nativeElement.style.padding = '2px';
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

  newClicked(event: any, item: SubMenuItem) {
    this.dialogFactory.openNewMatchingDialog(this.tenantId).then((ret: any) => {
      if (!isNullOrUndefined(ret)) {
        this.menuOptionClickedEvent.emit({ id: 'editor_new', payload: ret });
      }
    });
  }

  editClicked() { }

  openClicked(event: any, item: SubMenuItem) {
    this.dialogFactory.openOpenMatchingDialog(this.tenantId).then((ret: any) => {
      if (!isNullOrUndefined(ret)) {
        this.menuOptionClickedEvent.emit({ id: 'editor_open', payload: ret });
      } else {
        this.menuOptionClickedEvent.emit({ id: 'cancelled' });
      }
    });
  }

  saveClicked(event: any, item: SubMenuItem) {
    this.dialogFactory
      .confirmationDialog(
        'Save',
        'Are you sure you want to save? The existing model will be overidden...'
      )
      .then((result: boolean) => {
        if (result) {
          this.menuOptionClickedEvent.emit({ id: 'editor_save' });
        } else {
          this.menuOptionClickedEvent.emit({ id: 'cancelled' });
        }
      });
  }

  deleteClicked(event: any, item: SubMenuItem) {
    this.dialogFactory.openDeleteMatchingDialog(this.tenantId, this.redirectUrl).then((ret: any) => {
      if (!isNullOrUndefined(ret)) {
        this.menuOptionClickedEvent.emit({ id: 'editor_delete', payload: ret });
      } else {
        this.menuOptionClickedEvent.emit({ id: 'cancelled' });
      }
    });
  }
}
