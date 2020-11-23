import { Component, Inject, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ClassDefinition } from 'app/main/content/_model/configurator/class';
import { isNullOrUndefined } from 'util';
import { MatchingEntityMappingConfiguration, ClassConfigurationDTO } from 'app/main/content/_model/configurator/configurations';
import { MatchingEntity } from 'app/main/content/_model/matching';
import { mxgraph } from 'mxgraph';
import { MyMxCell, MyMxCellType } from '../../../myMxCell';
import { ClassConfigurationService } from 'app/main/content/_service/configuration/class-configuration.service';
import { CConstants } from '../../../class-configurator/utils-and-constants';
import { Relationship, RelationshipType } from 'app/main/content/_model/configurator/relationship';

declare var require: any;
const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

const CLASSDEFINITION_CELL_WIDTH = 110;
const CLASSDEFINITION_CELL_HEIGHT = 40;


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
    private classConfigurationServce: ClassConfigurationService,
  ) { }


  graph: mxgraph.mxGraph;
  @ViewChild('graphContainer', { static: true }) graphContainer: ElementRef;
  graphData: ClassConfigurationDTO;
  layout: any;

  loaded: boolean;

  async ngOnInit() {

    this.graphData = <ClassConfigurationDTO>(
      await this.classConfigurationServce.getAllForClassConfigurationInOne(this.dialogData.matchingEntityConfiguration.classConfigurationId).toPromise()
    );
    this.loaded = true;
    this.initGraph();
  }

  private initGraph() {
    this.graph = new mx.mxGraph(this.graphContainer.nativeElement);

    this.graph.isCellSelectable = function (cell) {
      const state = this.view.getState(cell);
      const style = state != null ? state.style : this.getCellStyle(cell);
      return (this.isCellsSelectable() && !this.isCellLocked(cell) && style['selectable'] !== 0);
    };

    this.graph.getCursorForCell = (cell: MyMxCell) => {
      if (isNullOrUndefined(cell.cellType) || cell.getStyle() === CConstants.mxStyles.classDisabled) {
        return 'default';
      }
      if (cell.cellType === MyMxCellType.CLASS) {
        return mx.mxConstants.CURSOR_TERMINAL_HANDLE;
      }
    };

    if (!mx.mxClient.isBrowserSupported()) {
      mx.mxUtils.error('Browser is not supported!', 200, false);
    } else {
      // Disables the built-in context menu
      mx.mxEvent.disableContextMenu(this.graphContainer.nativeElement);
      this.graph.setPanning(true);

      this.addMouseListeners(this.graph);

      const rootCell = this.createGraph();

      this.setLayout();
      this.executeLayout(rootCell);

      this.graph.setEnabled(false);
    }
  }

  private addMouseListeners(graph: mxgraph.mxGraph) {
    const outer = this;
    graph.addMouseListener({
      highlightedCell: undefined,
      deselectedCell: undefined,

      mouseDown: function (evt, state) { },

      mouseMove: function (evt, state) {
        if (this.pointerOnClass(state)) {
          const cell = state.state.cell as MyMxCell;
          const { addedEntities } = outer.dialogData;

          if (addedEntities.findIndex(e => e.classDefinition.id === cell.id) !== -1) { return; }

          this.highlightedCell = cell;
          outer.graph.setCellStyle(CConstants.mxStyles.classHighlighted, [cell]);

        } else {
          const { addedEntities } = outer.dialogData;

          if (
            (!isNullOrUndefined(this.highlightedCell)
              && addedEntities.findIndex(e => e.classDefinition.id === this.highlightedCell.id) === -1)
            || !isNullOrUndefined(this.deselectedCell)
          ) {
            outer.graph.setCellStyle(CConstants.mxStyles.classNormal, [this.highlightedCell]);
            this.deselectedCell = undefined;
          }
          this.highlightedCell = undefined;
        }
      },

      mouseUp: function (evt, state) {
        if (!this.pointerOnClass(state)) { return; }

        const { entities } = outer.dialogData.matchingEntityConfiguration.mappings;
        const cell = state.state.cell as MyMxCell;
        if (cell.getStyle() === CConstants.mxStyles.classDisabled) { return; }

        const entity = entities.find(e => e.classDefinition.id === cell.id);

        if (outer.dialogData.addedEntities.findIndex(e => e.classDefinition.id === cell.id) === -1) {
          outer.dialogData.addedEntities.push(entity);
          outer.graph.setCellStyle(CConstants.mxStyles.classHighlighted, [cell]);
          outer.addCheckedOverlay(cell);

        } else {
          outer.dialogData.addedEntities = outer.dialogData.addedEntities.filter(e => e.classDefinition.id !== cell.id);
          outer.graph.setCellStyle(CConstants.mxStyles.classNormal, [cell]);
          this.deselectedCell = cell;
          outer.graph.removeCellOverlay(cell, undefined);
        }
      },

      pointerOnClass: function (state) {
        return !isNullOrUndefined(state) && !isNullOrUndefined(state.state) && !isNullOrUndefined(state.state.cell) && state.state.cell.cellType === MyMxCellType.CLASS;
      },
    });
  }

  private createGraph(): MyMxCell {
    this.graph.getModel().beginUpdate();
    const rootCell = this.addClassDefinitions(this.graphData.classDefinitions);
    this.addRelationships(this.graphData.relationships);
    this.graph.getModel().endUpdate();
    return rootCell;
  }

  private addClassDefinitions(classDefinitions: ClassDefinition[]) {
    let rootCell: MyMxCell;
    for (const classDefinition of classDefinitions) {
      const cell = this.addClassDefinitionCell(classDefinition);
      if (cell.root) { rootCell = cell; }
    }
    return rootCell;
  }

  private addClassDefinitionCell(classDefinition: ClassDefinition): MyMxCell {
    const mxStyle = this.dialogData.existingEntityPaths.findIndex(p => p.endsWith(classDefinition.id)) !== -1
      ? CConstants.mxStyles.classDisabled
      : this.dialogData.addedEntities.findIndex(e => e.classDefinition.id === classDefinition.id) !== -1
        ? CConstants.mxStyles.classHighlighted
        : CConstants.mxStyles.classNormal;

    const cell = this.graph.insertVertex(
      this.graph.getDefaultParent(), classDefinition.id, classDefinition.name,
      0, 0, CLASSDEFINITION_CELL_WIDTH, CLASSDEFINITION_CELL_HEIGHT,
      mxStyle
    ) as MyMxCell;

    cell.root = false;
    cell.cellType = MyMxCellType.CLASS;

    if (cell.getStyle() === CConstants.mxStyles.classDisabled || cell.getStyle() === CConstants.mxStyles.classHighlighted) {
      this.addCheckedOverlay(cell);
    }

    return cell;
  }

  private addCheckedOverlay(cell: MyMxCell) {
    const overlay = new mx.mxCellOverlay(
      new mx.mxImage('/assets/icons/class_editor/check-solid_white.png', 14, 14),
      'Overlay', mx.mxConstants.ALIGN_LEFT, mx.mxConstants.ALIGN_TOP, new mx.mxPoint(9, 10)
    );
    this.graph.addCellOverlay(cell, overlay);
  }

  private addRelationships(relationships: Relationship[]) {
    for (const relationship of relationships) {
      this.createRelationshipCellById(relationship);
    }
  }

  private createRelationshipCellById(relationship: Relationship): MyMxCell {
    const source: MyMxCell = this.graph.getModel().getCell(relationship.source) as MyMxCell;
    const target: MyMxCell = this.graph.getModel().getCell(relationship.target) as MyMxCell;

    const style = relationship.relationshipType === RelationshipType.INHERITANCE ?
      CConstants.mxStyles.inheritance : relationship.relationshipType === RelationshipType.ASSOCIATION ?
        CConstants.mxStyles.association : CConstants.mxStyles.association;

    const cell = this.graph.insertEdge(
      this.graph.getDefaultParent(), relationship.id, '', source, target, style
    ) as MyMxCell;

    cell.cellType = MyMxCellType.TREE_CONNECTOR;
    return cell;
  }

  private setLayout() {
    this.layout = new mx.mxCompactTreeLayout(this.graph, false, false);
    this.layout.levelDistance = 50;
    this.layout.alignRanks = true;
    this.layout.minEdgeJetty = 50;
    this.layout.prefHozEdgeSep = 5;
    this.layout.resetEdges = false;
    this.layout.edgeRouting = true;
  }

  private executeLayout(rootCell: MyMxCell) {
    if (!isNullOrUndefined(this.layout)) {
      this.layout.execute(this.graph.getDefaultParent(), rootCell);
    }
  }
}
