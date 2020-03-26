import { mxgraph } from 'mxgraph';
import { isNullOrUndefined } from 'util';
import { myMxCell } from '../myMxCell';
import { MatchingConfiguratorComponent } from './matching-configurator.component';

declare var require: any;

const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

export class MatchingConfiguratorPopupMenu {
  graph: mxgraph.mxGraph;
  editorInstance: MatchingConfiguratorComponent;

  constructor(graph: mxgraph.mxGraph, editorInstance: MatchingConfiguratorComponent) {
    this.graph = graph;
    this.editorInstance = editorInstance;
  }

  createPopupMenuHandler(graph: mxgraph.mxGraph) {

    const outer = this;
    return new mx.mxPopupMenuHandler(graph, function (menu, cell, evt) {
      return outer.createPopupMenu(this.graph, menu, cell, evt);
    });
  }

  private createPopupMenu(graph: mxgraph.mxGraph, menu: mxgraph.mxPopupMenu, cell: myMxCell, evt) {
    const outer = this;

    if (cell != null) {
      // Options present in every cell (vertexes as well as edges)
      const printCellItem = menu.addItem('Print cell to console', null, function () {
        console.log(cell);
      }, null, null, true, true);

      if (cell.cellType === 'matchingOperator' || cell.cellType === 'matchingConnector') {
        const deleteItem = menu.addItem('Delete', null, function () {

          outer.editorInstance.deleteOperators([cell]);


        }, null, null, true, true);
      }
    } else {
      // console.log('clicked empty canvas space');
      // console.log(event);
      // console.log(graph);

    }
  }






}
