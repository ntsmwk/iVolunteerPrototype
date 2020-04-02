import { Component, OnInit, ElementRef, ViewChild, Output, EventEmitter, Input, AfterViewInit, OnChanges } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { isNullOrUndefined } from 'util';
import { DialogFactoryDirective } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.component';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { NewClassConfigurationDialogData } from '../new-dialog/new-dialog.component';
import { ClassConfiguration } from 'app/main/content/_model/configurations';



export interface RootMenuItem {
  id: number;
  label: string;
}

export interface SubMenuItem {
  rootId: number;
  id: number;
  label: string;
  clickAction: string;
  icon: string;
}

const rootMenuItems: RootMenuItem[] = [
  { id: 1, label: 'Datei' },
  { id: 2, label: 'Bearbeiten' },
  { id: 3, label: 'Ansicht' },
  { id: 4, label: 'Extras' },
  { id: 5, label: 'Hilfe' },
];

const subMenuItems: SubMenuItem[] = [
  { rootId: 1, id: 1, label: 'Neue Konfiguration', clickAction: 'newClicked', icon: undefined },
  { rootId: 1, id: 2, label: 'Konfiguration öffnen', clickAction: 'openClicked', icon: undefined },
  { rootId: 1, id: 3, label: 'Konfiguration speichern', clickAction: 'saveClicked', icon: undefined },
  { rootId: 1, id: 3, label: 'Konfiguration speichern unter', clickAction: 'saveAsClicked', icon: undefined },

  { rootId: 1, id: 4, label: 'Instanz erstellen', clickAction: 'createEditorClicked', icon: undefined },

  { rootId: 2, id: 1, label: 'Test Entry 21', clickAction: 'test', icon: undefined },
  { rootId: 2, id: 2, label: 'Test Entry 22', clickAction: 'test', icon: undefined },
  { rootId: 2, id: 3, label: 'Test Entry 23', clickAction: 'test', icon: undefined },
  { rootId: 2, id: 4, label: 'Test Entry 24', clickAction: 'test', icon: undefined },

  { rootId: 3, id: 1, label: 'Test Entry 31', clickAction: 'test', icon: undefined },
  { rootId: 3, id: 2, label: 'Test Entry 32', clickAction: 'test', icon: undefined },
  { rootId: 3, id: 3, label: 'Test Entry 33', clickAction: 'test', icon: undefined },

  { rootId: 4, id: 1, label: 'Test Entry 41', clickAction: 'test', icon: undefined },
  { rootId: 4, id: 2, label: 'Test Entry 42', clickAction: 'test', icon: undefined },
  { rootId: 4, id: 3, label: 'Test Entry 43', clickAction: 'test', icon: undefined },
  { rootId: 4, id: 4, label: 'Test Entry 44', clickAction: 'test', icon: undefined },
  { rootId: 4, id: 5, label: 'Test Entry 45', clickAction: 'test', icon: undefined },

  { rootId: 5, id: 1, label: 'Test Entry 51', clickAction: 'test', icon: undefined },
];

@Component({
  selector: 'editor-top-menu-bar',
  templateUrl: './top-menu-bar.component.html',
  styleUrls: ['./top-menu-bar.component.scss']
})
export class EditorTopMenuBarComponent implements AfterViewInit, OnChanges {

  isLoaded = false;
  menuOpen: false;

  rootMenuItems = rootMenuItems;
  subMenuItems = subMenuItems;
  currentRootId = 1;

  @ViewChild('menubarContainer', { static: true }) menubarContainer: ElementRef;
  @ViewChild('submenuContainer', { static: true }) submenuContainer: ElementRef;

  @Input() marketplace: Marketplace;
  @Input() eventResponseAction: string;
  @Output() menuOptionClickedEvent: EventEmitter<any> = new EventEmitter();



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
    this.submenuContainer.nativeElement.style.padding = '2px';
    this.submenuContainer.nativeElement.style.top = '30px';
    this.submenuContainer.nativeElement.style.left = '10px';
    this.submenuContainer.nativeElement.style.height = 'auto';
    this.submenuContainer.nativeElement.style.width = '200px';
    this.submenuContainer.nativeElement.style.background = 'white';
    this.submenuContainer.nativeElement.style.font = 'Arial, Helvetica, sans-serif';
    this.submenuContainer.nativeElement.style.display = 'none';

    this.menubarContainer.nativeElement.style.position = 'absolute';
    this.menubarContainer.nativeElement.style.overflow = 'hidden';
    this.menubarContainer.nativeElement.style.padding = '2px';
    this.menubarContainer.nativeElement.style.right = '0px';
    this.menubarContainer.nativeElement.style.top = '0px';
    this.menubarContainer.nativeElement.style.left = '0px';
    this.menubarContainer.nativeElement.style.height = '30px';
    this.menubarContainer.nativeElement.style.background = 'white';
    this.menubarContainer.nativeElement.style.font = 'Arial, Helvetica, sans-serif';

    const outer = this;

    document.addEventListener('click', function (event) {
      outer.handleHTMLClickEvent(event);
    });
  }

  handleHTMLClickEvent(event: any) {
    if (event.srcElement.className != 'menuitem') {
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
    // this.dialogFactory.confirmationDialog("New", "Create New Editor? Unsaved changes will be lost...").then((cont: boolean) => {
    //   if (cont) {
    //     this.menuOptionClickedEvent.emit({ id: "editor_new" });
    //   }
    // });

    this.dialogFactory.openNewClassConfigurationDialog(this.marketplace).then((ret: NewClassConfigurationDialogData) => {
      if (!isNullOrUndefined(ret)) {
        this.menuOptionClickedEvent.emit({ id: 'editor_new', payload: ret });
      } else {
        this.menuOptionClickedEvent.emit({ id: 'cancelled' });
      }
    });

  }

  openClicked(event: any, item: SubMenuItem) {

    this.dialogFactory.openConfiguratorDialog(this.marketplace).then((ret: any) => {
      if (!isNullOrUndefined(ret)) {
        this.menuOptionClickedEvent.emit({ id: 'editor_open', payload: ret });
      } else {
        this.menuOptionClickedEvent.emit({ id: 'cancelled' });
      }

    });
  }

  saveClicked(event: any, item: SubMenuItem) {
    this.dialogFactory.confirmationDialog('Save', 'Are you sure you want to save? The existing model will be overidden...').then((result: boolean) => {
      if (result) {
        this.menuOptionClickedEvent.emit({ id: 'editor_save' });
      } else {
        this.menuOptionClickedEvent.emit({ id: 'cancelled' });
      }
    });
  }

  saveAsClicked(event: any, item: SubMenuItem) {
    // wrapped in setTimeout - hack to avoid ExpressionChangedAfterItHasBeenCheckedError because of ngOnChanges lifecycle hook
    setTimeout(() => {
      this.dialogFactory.openSaveConfiguratorDialog(this.marketplace).then((ret: any) => {
        if (!isNullOrUndefined(ret)) {
          this.menuOptionClickedEvent.emit({ id: 'editor_save_as', configurator: ret });
        } else {
          this.menuOptionClickedEvent.emit({ id: 'cancelled' });
        }
      });
    });
  }

  createEditorClicked(event: any, item: SubMenuItem) {

  }

  ngOnChanges() {
    const eventResponse = this.eventResponseAction;
    this.eventResponseAction = undefined;
    if (eventResponse == 'saveAsClicked') {
      this[eventResponse](eventResponse, undefined);
    }
  }


  test(event: any, item: SubMenuItem) {
    console.log('test');
    console.log(event);
    console.log(item);
  }





  navigateBack() {
    window.history.back();
  }

}
