import { Component, OnInit, ElementRef, ViewChild, Output, EventEmitter, Input, AfterViewInit, OnChanges } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { isNullOrUndefined } from 'util';
import { DialogFactoryComponent } from 'app/main/content/_components/dialogs/_dialog-factory/dialog-factory.component';
import { Marketplace } from 'app/main/content/_model/marketplace';



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
  { id: 1, label: 'File' },
  { id: 2, label: 'Edit' },
  { id: 3, label: 'View' },
  { id: 4, label: 'Extras' },
  { id: 5, label: 'Help' },
];

const subMenuItems: SubMenuItem[] = [
  { rootId: 1, id: 1, label: 'New Configurator', clickAction: 'newClicked', icon: undefined },
  { rootId: 1, id: 2, label: 'Open Configurator', clickAction: 'openClicked', icon: undefined },
  { rootId: 1, id: 3, label: 'Save Configurator', clickAction: 'saveClicked', icon: undefined },
  { rootId: 1, id: 3, label: 'Save Configurator As', clickAction: 'saveAsClicked', icon: undefined },

  { rootId: 1, id: 4, label: 'Create Editor', clickAction: 'createEditorClicked', icon: undefined },

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

  isLoaded: boolean = false;
  menuOpen: false;

  rootMenuItems = rootMenuItems;
  subMenuItems = subMenuItems;
  currentRootId = 1;

  @ViewChild('menubarContainer') menubarContainer: ElementRef;
  @ViewChild('submenuContainer') submenuContainer: ElementRef;

  @Input() marketplace: Marketplace;
  @Input() eventResponseAction: string;
  @Output() menuOptionClickedEvent: EventEmitter<any> = new EventEmitter();



  constructor(private router: Router,
    private route: ActivatedRoute,
    private dialogFactory: DialogFactoryComponent
  ) {

  }

  ngOnInit() {
  }

  ngAfterViewInit() {

    this.submenuContainer.nativeElement.style.position = 'absolute';
    this.submenuContainer.nativeElement.style.overflow = 'hidden';
    this.submenuContainer.nativeElement.style.padding = '2px';
    this.submenuContainer.nativeElement.style.top = '25px';
    this.submenuContainer.nativeElement.style.left = '10px';
    this.submenuContainer.nativeElement.style.height = 'auto';
    this.submenuContainer.nativeElement.style.width = '200px'
    this.submenuContainer.nativeElement.style.background = 'white';
    this.submenuContainer.nativeElement.style.font = 'Arial, Helvetica, sans-serif';
    this.submenuContainer.nativeElement.style.display = 'none';

    this.menubarContainer.nativeElement.style.position = 'absolute';
    this.menubarContainer.nativeElement.style.overflow = 'hidden';
    this.menubarContainer.nativeElement.style.padding = '2px';
    this.menubarContainer.nativeElement.style.right = '0px';
    this.menubarContainer.nativeElement.style.top = '0px';
    this.menubarContainer.nativeElement.style.left = '0px';
    this.menubarContainer.nativeElement.style.height = '25px';
    this.menubarContainer.nativeElement.style.background = 'white';
    this.menubarContainer.nativeElement.style.font = 'Arial, Helvetica, sans-serif';

    let outer = this;

    document.addEventListener("click", function (event) {
      outer.handleHTMLClickEvent(event);
    });
  }

  handleHTMLClickEvent(event: any) {
    if (event.srcElement.className != "menuitem") {
      this.submenuContainer.nativeElement.style.display = 'none';
    }
  }

  openSubmenu(event: any, rootItemId: number) {
    this.currentRootId = rootItemId;
    this.submenuContainer.nativeElement.style.display = 'block';
    let leftPosition = this.calculateLeftSpace(event.srcElement.offsetParent, rootItemId);
    this.submenuContainer.nativeElement.style.left = leftPosition + 'px';
  }

  private calculateLeftSpace(offsetParent: any, rootItemId: number) {
    let space = 10;
    if (!isNullOrUndefined(offsetParent.children)) {
      for (let child of offsetParent.children) {
        if (rootItemId - 1 <= 0) {
          return space;
        }
        space += child.clientWidth;
        rootItemId--;
      }
    }
  }

  newClicked(event: any, item: SubMenuItem) {
    this.dialogFactory.confirmationDialog("New", "Create New Editor? Unsaved changes will be lost...").then((cont: boolean) => {
      if (cont) {
        this.menuOptionClickedEvent.emit({ id: "editor_new" });
      }
    });

  }

  openClicked(event: any, item: SubMenuItem) {

    this.dialogFactory.openConfiguratorDialog(this.marketplace).then((ret: any) => {
      if (!isNullOrUndefined(ret)) {
        console.log("open returned");
        console.log(ret);
        this.menuOptionClickedEvent.emit({ id: "editor_open", configurator: ret });
      } else {
        this.menuOptionClickedEvent.emit({ id: "cancelled"});
      }

    });
  }

  saveClicked(event: any, item: SubMenuItem) {
    this.dialogFactory.confirmationDialog("Save", "Are you sure you want to save? The existing model will be overidden...").then((result: boolean) => {
    if (result) {
        this.menuOptionClickedEvent.emit({ id: "editor_save" });
      } else {
        this.menuOptionClickedEvent.emit({id: "cancelled"});
      }
    })
  }

  saveAsClicked(event: any, item: SubMenuItem) {   
    //wrapped in setTimeout - hack to avoid ExpressionChangedAfterItHasBeenCheckedError because of ngOnChanges lifecycle hook
    setTimeout(() => {
      this.dialogFactory.openSaveConfiguratorDialog(this.marketplace).then((ret: any) => {
        if (!isNullOrUndefined(ret)) {
          this.menuOptionClickedEvent.emit({ id: "editor_save", configurator: ret });
        } else {
          this.menuOptionClickedEvent.emit({ id: "cancelled"});
        }
      });
    });
  }

  createEditorClicked(event: any, item: SubMenuItem) {

  }

  ngOnChanges() {
    if (this.eventResponseAction == "saveAsClicked") {
      this[this.eventResponseAction](this.eventResponseAction, undefined);
    }
  }


  test(event: any, item: SubMenuItem) {
    console.log("test");
    console.log(event);
    console.log(item);
  }





  navigateBack() {
    window.history.back();
  }

}
