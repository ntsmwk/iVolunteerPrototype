import { Component, OnInit, Input, ElementRef, ViewChild, Output, EventEmitter, HostListener } from '@angular/core';
import { mxgraph } from 'mxgraph';
import { Router } from '@angular/router';
import { ObjectIdService } from 'app/main/content/_service/objectid.service.';
import { Marketplace } from 'app/main/content/_model/marketplace';
import { Helpseeker } from 'app/main/content/_model/helpseeker';
import { MyMxCell, MyMxCellType } from '../../../../myMxCell';
import { EnumDefinition, EnumEntry, EnumRelationship } from 'app/main/content/_model/meta/enum';
import { CConstants } from '../../../../class-configurator/utils-and-constants';
import { isNullOrUndefined } from 'util';
import { EnumDefinitionService } from 'app/main/content/_service/meta/core/enum/enum-configuration.service';
import { of } from 'rxjs';

declare var require: any;

const ENTRY_CELL_WIDTH = 110;
const ENTRY_CELL_HEIGHT = 70;

const mx: typeof mxgraph = require('mxgraph')({
    // mxDefaultLanguage: 'de',
    // mxBasePath: './mxgraph_resources',
});

@Component({
    selector: 'app-enum-graph-editor',
    templateUrl: './enum-graph-editor.component.html',
    styleUrls: ['./enum-graph-editor.component.scss'],
    // providers: [DialogFactoryDirective]
})
export class EnumGraphEditorComponent implements OnInit {
    constructor(
        private router: Router,
        private objectIdService: ObjectIdService,
        private enumDefinitionService: EnumDefinitionService,
        // private dialogFactory: DialogFactoryDirective,
    ) { }

    @Input() marketplace: Marketplace;
    @Input() helpseeker: Helpseeker;
    @Input() enumDefinition: EnumDefinition;
    @Output() result: EventEmitter<any> = new EventEmitter();

    @ViewChild('enumGraphContainer', { static: true }) graphContainer: ElementRef;

    graph: mxgraph.mxGraph;
    rootCell: MyMxCell;
    layout: any;

    /**
     * ******INITIALIZATION******
     */

    ngOnInit() {
    }

    ngAfterContentInit() {
        this.graphContainer.nativeElement.style.overflow = 'hidden';
        this.graphContainer.nativeElement.style.height = '65vh';
        this.graphContainer.nativeElement.style.width = '100%';
        this.graphContainer.nativeElement.style.background = 'white';

        this.graph = new mx.mxGraph(this.graphContainer.nativeElement);
        this.graph.isCellSelectable = function (cell) {
            const state = this.view.getState(cell);
            const style = (state != null) ? state.style : this.getCellStyle(cell);

            return this.isCellsSelectable() && !this.isCellLocked(cell) && style['selectable'] !== 0;
        };

        const outer = this;
        this.graph.getCursorForCell = function (cell: MyMxCell) {
            // todo cursor
            if (cell.cellType === MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON) {
                return mx.mxConstants.CURSOR_TERMINAL_HANDLE;
            }
        };

        const modelGetStyle = this.graph.model.getStyle;

        this.graph.model.getStyle = function (cell) {
            if (cell != null) {
                let style = modelGetStyle.apply(this, arguments);

                if (this.isCollapsed(cell)) {
                    style = style + ';shape=rectangle';
                }
                return style;
            }
            return null;
        };

        if (!mx.mxClient.isBrowserSupported()) {
            mx.mxUtils.error('Browser is not supported!', 200, false);
        } else {
            // Disables the built-in context menu
            mx.mxEvent.disableContextMenu(this.graphContainer.nativeElement);

            // Enables rubberband selection
            // tslint:disable-next-line: no-unused-expression
            new mx.mxRubberband(this.graph);

            this.graph.setPanning(true);

            // this.graph.popupMenuHandler = this.createPopupMenu(this.graph);
            this.graph.tooltipHandler = new mx.mxTooltipHandler(this.graph, 100);

            /**
             * ******EVENT LISTENERS******
             */

            this.graph.addListener(mx.mxEvent.CLICK, function (sender: mxgraph.mxGraph, evt: mxgraph.mxEventObject) {
                // Todo click event   
                const mouseEvent = evt.getProperty('event');

                if (mouseEvent.button === 0) {
                    outer.handleMXGraphLeftClickEvent(evt);
                } else if (mouseEvent.button === 2) {
                    outer.handleMXGraphRightClickEvent(evt);
                }
            });

            this.graph.addListener(mx.mxEvent.DOUBLE_CLICK, function (sender: mxgraph.mxGraph, evt: mxgraph.mxEventObject) {
                // TODO double click event
            });
            this.createGraph();
            this.setLayout();
            this.executeLayout();
        }
    }

    createGraph() {
        this.graph.getModel().beginUpdate();
        this.createRootCell();
        this.createEntryCells(this.enumDefinition.enumEntries);
        this.createRelationshipCells(this.enumDefinition.enumRelationships);
        this.graph.getModel().endUpdate();
    }

    private createRootCell() {
        const rootCell = this.graph.insertVertex(
            this.graph.getDefaultParent(), this.enumDefinition.id, this.enumDefinition.name, 0, 0, ENTRY_CELL_WIDTH, ENTRY_CELL_HEIGHT, CConstants.mxStyles.classEnum
        ) as MyMxCell;
        rootCell.root = true;
        rootCell.cellType = MyMxCellType.ENUM_HEAD;

        this.rootCell = rootCell;

        const nextIcon: MyMxCell = this.graph.insertVertex(
            rootCell, 'add_class_next_level_icon', 'Eintrag hinzufügen',
            85, 45, 20, 20, CConstants.mxStyles.addClassNewLevelIcon) as MyMxCell;
        nextIcon.setConnectable(false);
        nextIcon.cellType = MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON;
    }

    private createEntryCells(enumEntries: EnumEntry[]) {
        for (const entry of enumEntries) {
            const cell = this.createEntryCell(entry);
        }
    }

    private createEntryCell(enumEntry?: EnumEntry, position?: { x: number, y: number }): MyMxCell {
        if (isNullOrUndefined(enumEntry)) {
            enumEntry = this.createNewEnumEntry();
        }
        if (isNullOrUndefined(position)) {
            position = { x: 0, y: 0 };
        }

        const cell = this.graph.insertVertex(
            this.graph.getDefaultParent(), enumEntry.id, enumEntry.value,
            position.x, position.y, ENTRY_CELL_WIDTH, ENTRY_CELL_HEIGHT, CConstants.mxStyles.classEnum
        ) as MyMxCell;
        cell.root = false;
        cell.cellType = MyMxCellType.ENUM_ENTRY;

        const nextIcon: MyMxCell = this.graph.insertVertex(
            cell, 'add_class_next_level_icon', 'Eintrag hinzufügen',
            85, 45, 20, 20, CConstants.mxStyles.addClassNewLevelIcon) as MyMxCell;
        nextIcon.setConnectable(false);
        nextIcon.cellType = MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON;

        const sameIcon: MyMxCell = this.graph.insertVertex(
            cell, 'add_class_same_level_icon', 'Eintrag hinzufügen',
            65, 45, 20, 20, CConstants.mxStyles.addClassSameLevelIcon) as MyMxCell;
        sameIcon.setConnectable(false);
        sameIcon.cellType = MyMxCellType.ADD_CLASS_SAME_LEVEL_ICON;

        return cell;
    }

    private createNewEnumEntry() {
        const enumEntry = new EnumEntry();
        enumEntry.id = this.objectIdService.getNewObjectId();
        enumEntry.selectable = true;
        enumEntry.value = 'neuer Eintrag';
        this.enumDefinition.enumEntries.push(enumEntry);
        return enumEntry;
    }

    private createRelationship(sourceId: string, targetId: string) {
        const relationship = new EnumRelationship();
        relationship.id = this.objectIdService.getNewObjectId();
        relationship.sourceEnumEntryId = sourceId;
        relationship.targetEnumEntryId = targetId;
        this.enumDefinition.enumRelationships.push(relationship);
        return relationship;
    }

    private createRelationshipCells(enumRelationships: EnumRelationship[]) {
        for (const relationship of enumRelationships) {
            const cell = this.createRelationshipCellById(relationship);
        }
    }

    private createRelationshipCellById(enumRelationship: EnumRelationship): MyMxCell {
        const source: MyMxCell = this.graph.getModel().getCell(enumRelationship.sourceEnumEntryId) as MyMxCell;
        const target: MyMxCell = this.graph.getModel().getCell(enumRelationship.targetEnumEntryId) as MyMxCell;

        return this.createRelationshipCell(enumRelationship.id, source, target);
    }

    private createRelationshipCell(id: string, source: MyMxCell, target: MyMxCell): MyMxCell {
        const cell = this.graph.insertEdge(this.graph.getDefaultParent(), id, '', source, target, CConstants.mxStyles.genericConnection) as MyMxCell;
        cell.cellType = MyMxCellType.ENUM_CONNECTOR;
        return cell;
    }

    private handleMXGraphLeftClickEvent(event: mxgraph.mxEventObject) {
        const eventCell = event.getProperty('cell') as MyMxCell;
        if (isNullOrUndefined(eventCell)) {
            return;
        }

        if (eventCell.cellType === MyMxCellType.ADD_CLASS_NEXT_LEVEL_ICON) {
            const newCell = this.createEntryCell();
            const relationship = this.createRelationship(eventCell.getParent().id, newCell.id);
            this.createRelationshipCell(relationship.id, eventCell.getParent() as MyMxCell, newCell);
            this.executeLayout();

        } else if (eventCell.cellType === MyMxCellType.ADD_CLASS_SAME_LEVEL_ICON) {
            const newCell = this.createEntryCell();
            // find parent cell
            const parentCell = this.graph.getIncomingEdges(eventCell.getParent())[0].source as MyMxCell;

            const relationship = this.createRelationship(parentCell.id, newCell.id);
            this.createRelationshipCell(relationship.id, parentCell, newCell);

            this.executeLayout();

        }

    }

    private handleMXGraphRightClickEvent(event: mxgraph.mxEventObject) {

    }

    onSaveClick() {
        this.updateModel();
        return this.enumDefinitionService.saveEnumDefinition(this.marketplace, this.enumDefinition).toPromise().then((ret: EnumDefinition) => {
            return ret;
        });
    }

    updateModel() {
        const vertices = this.graph.getModel().getChildVertices(this.graph.getDefaultParent());
        const edges = this.graph.getModel().getChildEdges(this.graph.getDefaultParent());

        // update head
        this.enumDefinition.name = this.rootCell.value;

        // update entries
        const newEnumEntries: EnumEntry[] = [];
        for (const vertice of vertices) {
            if (vertice.id === this.rootCell.id) {
                continue;
            }
            const enumEntry = this.enumDefinition.enumEntries.find(e => e.id === vertice.id);
            enumEntry.value = vertice.value;
            newEnumEntries.push(enumEntry);
        }

        const newEnumRelationships: EnumRelationship[] = [];
        for (const edge of edges) {
            const relationship = this.enumDefinition.enumRelationships.find(r => r.id === edge.id);
            newEnumRelationships.push(relationship);
        }
    }

    onBackClick() {
        this.result.emit(undefined);
    }

    async onSaveAndBackClick() {
        const ret = await this.onSaveClick();
        this.result.emit(ret);
    }

    private setLayout() {
        if (!isNullOrUndefined(this.rootCell.edges) && this.rootCell.edges.length > 0) {
            this.layout = new mx.mxCompactTreeLayout(this.graph, false, false);
            // const layout: any = new mx.mxFastOrganicLayout(this.graph);
            this.layout.levelDistance = 50;
            this.layout.alignRanks = true;
            this.layout.minEdgeJetty = 50;
            this.layout.prefHozEdgeSep = 5;
            this.layout.resetEdges = false;
            this.layout.edgeRouting = true;
        }
    }

    private executeLayout() {
        if (isNullOrUndefined(this.layout)) {
            this.setLayout();
        }
        if (!isNullOrUndefined(this.layout)) {
            this.layout.execute(this.graph.getDefaultParent(), this.rootCell);
        }
    }

    /**
     * ******KEY LISTENER/HANDLER******
     */
    @HostListener('document:keypress', ['$event'])
    handleKeyboardEvent(event: KeyboardEvent) {
        if (event.key === 'Delete') {
            const cells = this.graph.getSelectionCells() as MyMxCell[];
            this.performDelete(cells);
        }
    }

    private performDelete(cells: MyMxCell[]) {
        cells = cells.filter((c: MyMxCell) => !c.writeProtected);
        const removedCells = this.graph.removeCells(cells, true) as MyMxCell[];

        if (isNullOrUndefined(removedCells)) {
            return;
        }
        this.deleteFromModel(removedCells);
    }

    private deleteFromModel(removedCells: MyMxCell[]) {
        for (const cell of removedCells) {
            if (cell.cellType === MyMxCellType.ENUM_ENTRY) {
                this.enumDefinition.enumEntries = this.enumDefinition.enumEntries.filter(c => c.id !== cell.id);
            } else if (cell.cellType === MyMxCellType.ENUM_CONNECTOR) {
                this.enumDefinition.enumRelationships = this.enumDefinition.enumRelationships.filter(r => r.id !== cell.id);
            }
        }
    }

}
