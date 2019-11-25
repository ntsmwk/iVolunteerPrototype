import { mxgraph } from "mxgraph";
import { myMxCell, ConfiguratorEditorComponent } from "./configurator-editor.component";
import { isNullOrUndefined } from "util";
import { ClassDefinition } from "app/main/content/_model/meta/Class";
import { PropertyItem } from "app/main/content/_model/meta/Property";
import { Relationship, AssociationCardinality, Association } from "app/main/content/_model/meta/Relationship";

declare var require: any;

const mx: typeof mxgraph = require('mxgraph')({
    // mxDefaultLanguage: 'de',
    // mxBasePath: './mxgraph_resources',
  });

export class EditorPopupMenu {
    graph: mxgraph.mxGraph;
    editorInstance: ConfiguratorEditorComponent;

    constructor(graph: mxgraph.mxGraph, editorInstance: ConfiguratorEditorComponent) {
        this.graph = graph;
        this.editorInstance = editorInstance;
    }

    createPopupMenuHandler(graph: mxgraph.mxGraph) {

        let outer = this;
       return  new mx.mxPopupMenuHandler(graph, function (menu, cell, evt) {
            return outer.createPopupMenu(this.graph, menu, cell, evt);
          });
    }

    private createPopupMenu(graph: mxgraph.mxGraph, menu: mxgraph.mxPopupMenu, cell: myMxCell, evt) {
        let outer = this;
    
        if (cell != null) {
          if (cell.isEdge()) {
    
            if (cell.cellType == 'association') {
              var item1 = menu.addItem('Set Cardinality', null, null, null, null, true, false);
    
              menu.addSeparator(null, true);
              //on error: define "	var td;" in mxgraph/build.js line 15755
              var nestedMenu1 = menu.addItem(`"start" Node (${cell.children[0].value})`, null, null, null, null, null, null);
              var nestedMenu2 = menu.addItem(`"end" Node (${cell.children[1].value})`, null, null, null, null, null, null);
    
              this.addCardinalitiesToSubmenu(graph, menu, nestedMenu1, cell, 0);
              this.addCardinalitiesToSubmenu(graph, menu, nestedMenu2, cell, 1);
    
              menu.addSeparator(null, false);
    
            }
          } else if (cell.isVertex()) {
    
            if (cell.cellType == 'associationLabel') {
              var item1 = menu.addItem('Set Cardinality', null, function() {
                console.log(cell);

              }, null, null, true, false);

              this.addCardinalitiesToSubmenu(graph, menu, undefined, cell, undefined);
              menu.addSeparator(null, false);
    
            } else if (cell.root) {

              var rootItem = menu.addItem("unset as Root", null, function() {
                cell.root = false;
              }, null, null, null, null);

            } else if (!cell.root){
              var rootItem = menu.addItem("set as Root", null, function() {
                cell.root = true;
              }, null, null, null, null);
              
            } else {
              // var showPropertiesItem = menu.addItem("Show Properties", null, function () {
    
              //   outer.toggleRightSidebar(true);
              //   outer.rightSidebarContext = 'properties';
    
              //   outer.currentClass = outer.configurableClasses.find((c: ClassDefinition) => {
              //     return c.id == cell.id;
              //   });
              // }, null, null, true, true);
    
              // var createClassInstanceItem = menu.addItem('Create new Instance', null, function () {
              //   outer.createClassInstanceClicked(cell);
              // }, null, null, true, true);
    
            }
          }
    
          //Options present in every cell (vertexes as well as edges)
          var testItem = menu.addItem("Print cell to console", null, function () {
            if (cell.isVertex()) {
              console.log(cell);
            } else {
              console.log(cell);
            }
          }, null, null, true, true);
    
          var deleteItem = menu.addItem("Delete", null, function () {
    
            graph.getModel().beginUpdate();
    
            try {
    
              //remove property from Class in Array
              if (cell.cellType == 'property') {
                let classIndex = outer.editorInstance.configurableClasses.findIndex((c: ClassDefinition) => {
                  return c.id == cell.getParent().getId();
                });
                let remIndex = outer.editorInstance.configurableClasses[classIndex].properties.findIndex((p: PropertyItem) => {
                  return p.id == cell.propertyId;
                });
    
                if (remIndex >= 0) {
                  outer.editorInstance.configurableClasses[classIndex].properties.splice(remIndex, 1);
                }
                outer.editorInstance.redrawContent();
    
    
                //remove Class from Array
              } else if (cell.cellType == "class") {
    
                let classIndex = outer.editorInstance.configurableClasses.findIndex((c: ClassDefinition) => {
                  return c.id == cell.getId();
                });
    
                if (classIndex >= 0) {
                  let deleted = outer.editorInstance.configurableClasses.splice(classIndex, 1);
                  //add to delete Queue
                  outer.editorInstance.deletedClassIds.push(deleted.pop().id);
                }
                graph.removeCells([cell], false);
    
    
                //remove Relationship when clicking on a Label
              } else if (cell.cellType == 'associationLabel') {
                let parent = cell.getParent();
    
                outer.graph.getModel().remove(parent);
    
                let relationshipIndex = outer.editorInstance.relationships.findIndex((c: Relationship) => {
                  return c.id == parent.id;
                });
    
                let deleted = outer.editorInstance.relationships.splice(relationshipIndex, 1);
                outer.editorInstance.deletedRelationshipIds.push(deleted.pop().id);

                //remove relationship
              } else if (cell.cellType == 'association' || cell.cellType == 'inheritance') {
                graph.getModel().remove(cell);
    
                let relationshipIndex = outer.editorInstance.relationships.findIndex((c: Relationship) => {
                  return c.id == cell.id;
                });
    
                let deleted = outer.editorInstance.relationships.splice(relationshipIndex, 1);
                outer.editorInstance.deletedRelationshipIds.push(deleted.pop().id);
    
              } else {
                console.error("cell is not Vertex nor Edge - undefined behaviour");
              }
    
    
            } finally {
              graph.getModel().endUpdate();
            }
    
    
          }, null, null, true, true);
    
          if (cell.cellType != 'property') {
            var copyItem = menu.addItem("Duplicate", null, function () {
              if (cell.isVertex()) {
                if (cell.cellType == 'associationLabel') {
                  duplicateEdge(cell.getParent());
                } else {
                  duplicateVertex(cell);
                }
              } else if (cell.isEdge()) {
                duplicateEdge(cell);
    
    
              }
    
              function duplicateEdge(cell: mxgraph.mxCell) {
                var dupe: myMxCell = graph.getModel().cloneCell(cell);
                if (!isNullOrUndefined(cell.geometry.points)) {
                  dupe.getGeometry().points[0].x = dupe.getGeometry().sourcePoint.x = cell.getGeometry().points[0].x + 20;
                  dupe.getGeometry().points[0].y = dupe.getGeometry().sourcePoint.y = cell.getGeometry().points[0].y + 20;
                  dupe.getGeometry().points[1].x = dupe.getGeometry().targetPoint.x = cell.getGeometry().points[1].x + 20;
                  dupe.getGeometry().points[1].y = dupe.getGeometry().targetPoint.y = cell.getGeometry().points[1].y + 20;
                } else {
                  dupe.getGeometry().sourcePoint.x += 20;
                  dupe.getGeometry().sourcePoint.y += 20;
                  dupe.getGeometry().targetPoint.x += 20;
                  dupe.getGeometry().targetPoint.y += 20;
                }
                dupe.newlyAdded = true;
                let added = graph.addCell(dupe);
                added.setId("new"+added.getId());
              }
    
              function duplicateVertex(cell: mxgraph.mxCell) {
                var dupe: myMxCell = graph.getModel().cloneCell(cell);
                dupe.getGeometry().x += 20;
                dupe.getGeometry().y += 20;
                dupe.newlyAdded = true;
                let added = graph.addCell(dupe);
                added.setId("new"+added.getId());

              }
            }, null, null, true, true);
          }
    
        } else {
          console.log("clicked empty canvas space");
          console.log(event);
          console.log(graph);
    
        }
      }

      addCardinalitiesToSubmenu(graph: mxgraph.mxGraph, menu: mxgraph.mxPopupMenu, parent: HTMLTableRowElement, cell: myMxCell, childId: 0 | 1) {
        menu.addItem(AssociationCardinality.ZEROSTAR, null, function () {
          setCellValue(cell, childId, 'ZEROSTAR');
        }, parent, null, null, null);
    
        menu.addItem(AssociationCardinality.ZEROONE, null, function () {
          setCellValue(cell, childId, 'ZEROONE');
        }, parent, null, null, null);
    
        menu.addItem(AssociationCardinality.ONE, null, function () {
          setCellValue(cell, childId, 'ONE');
        }, parent, null, null, null);
    
        menu.addItem(AssociationCardinality.ONESTAR, null, function () {
          setCellValue(cell, childId, 'ONESTAR');
        }, parent, null, null, null);
    
    
        let outer = this;
        function setCellValue(cell: myMxCell, childId: 0 | 1, parameter: string) {
          // updateCardinality(cell, childId, parameter);

          try {
            graph.getModel().beginUpdate();
            if (!isNullOrUndefined(cell.children)) {
              graph.getModel().getChildren(cell)[childId].setValue(AssociationCardinality[parameter]);
              //workaround to get graph to update
              graph.getModel().remove(cell);
              graph.addCell(cell);
            } else {
              cell.setValue(AssociationCardinality[parameter]);
              graph.getModel().remove(cell.getParent());
              graph.addCell(cell.getParent());
            }
          } finally {
            graph.getModel().endUpdate();
          }
        }

        function updateCardinality(associationCell: myMxCell, childId: 0 | 1, parameter: string) {
          (<Association> outer.editorInstance.relationships.find((r: Association) => {
            return r.id == associationCell.id;
          }))['param'+(childId+1)] = parameter;
          console.log(outer.editorInstance.relationships);
  
        }
      }




    
}