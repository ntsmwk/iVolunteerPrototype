import { mxgraph } from 'mxgraph';
import { MyMxCell, MyMxCellType } from '../myMxCell';
import { ClassConfiguratorComponent } from './class-configurator.component';

declare var require: any;

const mx: typeof mxgraph = require('mxgraph')({
  // mxDefaultLanguage: 'de',
  // mxBasePath: './mxgraph_resources',
});

export class EditorPopupMenu {
  editorInstance: ClassConfiguratorComponent;

  constructor(editorInstance: ClassConfiguratorComponent) {
    this.editorInstance = editorInstance;
  }

  createPopupMenuHandler(graph: mxgraph.mxGraph) {
    return new mx.mxPopupMenuHandler(graph, (menu, cell, evt) => {
      return this.createPopupMenu(menu, cell, evt);
    });
  }

  private createPopupMenu(menu: mxgraph.mxPopupMenu, cell: MyMxCell, evt: any) {
    if (cell != null) {
      if (cell.isVertex() && cell.cellType === MyMxCellType.CLASS) {
        menu.addItem('Eintrag erfassen', null, () => {
          this.editorInstance.showInstanceForm();
        }, null, null, true, true);

        menu.addItem('JSON Exportieren', null, () => {
          this.editorInstance.showExportDialog();
        }, null, null, true, true);
      }
      // Options present in every cell (vertexes as well as edges)
      menu.addItem('DEBUG: Print cell to console', null, () => {
        console.log(cell);
      }, null, null, true, true);

    }
  }
}
