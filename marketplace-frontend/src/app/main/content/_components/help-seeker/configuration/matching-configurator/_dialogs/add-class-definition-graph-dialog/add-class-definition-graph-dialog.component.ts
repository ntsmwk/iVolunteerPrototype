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
import { MatchingEntityMappingConfiguration, ClassConfigurationDTO } from 'app/main/content/_model/meta/configurations';
import { MatchingEntity } from 'app/main/content/_model/matching';
import { mxgraph } from 'mxgraph';
import { MyMxCell, MyMxCellType } from '../../../myMxCell';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { CConstants } from '../../../class-configurator/utils-and-constants';
import { Relationship } from 'app/main/content/_model/meta/relationship';

declare var require: any;
const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

const CLASSDEFINITION_CELL_WIDTH = 110;
const CLASSDEFINITION_CELL_HEIGHT = 70;


export interface AddClassDefinitionGraphDialogData {
  matchingEntityConfiguration: MatchingEntityMappingConfiguration;
  existingEntityPaths: string[];
  addedEntities: MatchingEntity[];
}

@Component({
  selector: 'add-class-definition-graph-dialog',
  templateUrl: './add-class-definition-graph-dialog.component.html',
  styleUrls: ['./add-class-definition-graph-dialog.component.scss'],
})
export class AddClassDefinitionGraphDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<AddClassDefinitionGraphDialogData>,
    @Inject(MAT_DIALOG_DATA) public dialogData: AddClassDefinitionGraphDialogData,
    private loginService: LoginService,
    private classConfigurationServce: ClassConfigurationService,
  ) { }


  globalInfo: GlobalInfo;
  tenant: Tenant;

  graph: mxgraph.mxGraph;
  @ViewChild('graphContainer', { static: true }) graphContainer: ElementRef;
  graphData: ClassConfigurationDTO;

  loaded: boolean;

  async ngOnInit() {
    this.globalInfo = <GlobalInfo>(
      await this.loginService.getGlobalInfo().toPromise()
    );
    this.tenant = this.globalInfo.tenants[0];

    this.graphData = <ClassConfigurationDTO>(
      await this.classConfigurationServce.getAllForClassConfigurationInOne(
        this.globalInfo.marketplace, this.dialogData.matchingEntityConfiguration.classConfigurationId
      ).toPromise()
    );


    this.loaded = true;
    this.initGraph();
  }

  private initGraph() {
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

      this.createGraph();
    }
  }

  private createGraph() {
    this.graph.getModel().beginUpdate();
    this.addClassDefinitions(this.graphData.classDefinitions);
    this.addRelationships(this.graphData.relationships);
    this.graph.getModel().endUpdate();
  }

  private addClassDefinitions(classDefinitions: ClassDefinition[]) {
    for (const classDefinition of classDefinitions) {
      const cell = this.addClassDefinitionCell(classDefinition);
    }
  }

  private addClassDefinitionCell(classDefinition: ClassDefinition): MyMxCell {
    const cell = this.graph.insertVertex(
      this.graph.getDefaultParent(),
      classDefinition.id,
      classDefinition.name,
      0,
      0,
      CLASSDEFINITION_CELL_WIDTH,
      CLASSDEFINITION_CELL_HEIGHT,
      CConstants.mxStyles.classTree
    ) as MyMxCell;
    cell.root = false;
    cell.cellType = MyMxCellType.TREE_ENTRY;

    return cell;
  }


  private addRelationships(relationships: Relationship[]) {
    for (const relationship of relationships) {
      const cell = this.createRelationshipCellById(relationship);
    }
  }

  private createRelationshipCellById(relationship: Relationship): MyMxCell {
    const source: MyMxCell = this.graph
      .getModel().getCell(relationship.source) as MyMxCell;
    const target: MyMxCell = this.graph
      .getModel().getCell(relationship.target) as MyMxCell;

    return this.createRelationshipCell(relationship.id, source, target);
  }

  private createRelationshipCell(id: string, source: MyMxCell, target: MyMxCell): MyMxCell {
    const cell = this.graph.insertEdge(
      this.graph.getDefaultParent(),
      id,
      '',
      source,
      target,
      CConstants.mxStyles.genericConnection
    ) as MyMxCell;
    cell.cellType = MyMxCellType.TREE_CONNECTOR;
    return cell;
  }

  private handleClickEvent(evt) {
    console.log('click');
  }

  onSubmit() {
    this.dialogRef.close(this.dialogData);
  }

}
