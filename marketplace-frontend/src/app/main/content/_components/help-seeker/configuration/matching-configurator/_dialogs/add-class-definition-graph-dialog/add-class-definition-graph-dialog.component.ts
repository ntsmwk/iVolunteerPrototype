import { Component, Inject, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import {
  MatDialogRef, MAT_DIALOG_DATA,
} from '@angular/material/dialog';

import { ClassDefinition } from 'app/main/content/_model/meta/class';
import { MatTableDataSource, MatSort } from '@angular/material';
import { SelectionModel } from '@angular/cdk/collections';
import { isNullOrUndefined } from 'util';
import { GlobalInfo } from 'app/main/content/_model/global-info';
import { LoginService } from 'app/main/content/_service/login.service';
import { Tenant } from 'app/main/content/_model/tenant';
import { MatchingEntityMappingConfiguration } from 'app/main/content/_model/meta/configurations';
import { MatchingEntity } from 'app/main/content/_model/matching';
import { mxgraph } from 'mxgraph';
import { MyMxCell } from '../../../myMxCell';

declare var require: any;
const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});


export interface AddClassDefinitionGraphDialogData {

}

@Component({
  selector: 'add-class-definition-graph-dialog',
  templateUrl: './add-class-definition-graph-dialog.component.html',
  styleUrls: ['./add-class-definition-graph-dialog.component.scss'],
})
export class AddClassDefinitionGraphDialogComponent implements OnInit, AfterViewInit {
  constructor(
    public dialogRef: MatDialogRef<AddClassDefinitionGraphDialogData>,
    @Inject(MAT_DIALOG_DATA) public data: AddClassDefinitionGraphDialogData,
    private loginService: LoginService,
  ) { }

  loaded: boolean;
  globalInfo: GlobalInfo;
  tenant: Tenant;
  graph: mxgraph.mxGraph;

  @ViewChild('graphContainer', { static: true }) graphContainer: ElementRef;


  async ngOnInit() {
    this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.tenant = this.globalInfo.tenants[0];
    this.loaded = true;
  }

  ngAfterViewInit() {
    this.graph = new mx.mxGraph(this.graphContainer.nativeElement);;

    this.graph.isCellSelectable = function (cell) {
      const state = this.view.getState(cell);
      const style = state != null ? state.style : this.getCellStyle(cell);

      return (this.isCellsSelectable() && !this.isCellLocked(cell) && style['selectable'] !== 0);
    };

    this.graph.getCursorForCell = (cell: MyMxCell) => {

      if (isNullOrUndefined(cell.cellType)) {
        return 'default';
      }

      if (cell.cellType.startsWith('ADD_CLASS_BUTTON')) {
        return mx.mxConstants.CURSOR_TERMINAL_HANDLE;
      }
    };

    // const modelGetStyle = this.graph.model.getStyle;
    // this.graph.model.getStyle = function (cell) {
    //   if (cell != null) {
    //     let style = modelGetStyle.apply(this, arguments);

    //     if (this.isCollapsed(cell)) {
    //       style = style + ';shape=rectangle';
    //     }
    //     return style;
    //   }
    //   return null;
    // };

    if (!mx.mxClient.isBrowserSupported()) {
      mx.mxUtils.error('Browser is not supported!', 200, false);
    } else {
      // Disables the built-in context menu
      mx.mxEvent.disableContextMenu(this.graphContainer.nativeElement);

      this.graph.setPanning(true);

      this.graph.addListener(mx.mxEvent.CLICK, (sender: any, evt: mxgraph.mxEventObject) => {
        this.handleClickEvent(evt);
      });
    }
  }

  private handleClickEvent(evt) {
    console.log('click');
  }

  onSubmit() {
    this.dialogRef.close(this.data);
  }

}
