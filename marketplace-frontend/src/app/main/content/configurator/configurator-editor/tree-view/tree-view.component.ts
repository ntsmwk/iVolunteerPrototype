import { Component, OnInit, Input } from '@angular/core';
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { mxgraph } from 'mxgraph';
import { ConfiguratorEditorComponent, myMxCell } from '../configurator-editor.component';

export interface GraphNode {
  id: string;
  name: string;
  type: string;

  children?: GraphNode[];
}


@Component({
  selector: 'editor-tree-view',
  templateUrl: './tree-view.component.html',
  styleUrls: ['./tree-view.component.scss']
})
export class EditorTreeViewComponent implements OnInit {

  @Input() editorInstance: ConfiguratorEditorComponent;

  constructor(

  ) {
  }

  ngOnInit() {
    this.constructJsonFromGraph();
    





  }

  //TODO
  constructJsonFromGraph() {
    let vertices = this.editorInstance.graph.getChildVertices(this.editorInstance.graph.getDefaultParent());
    let roots = vertices.filter((cell: myMxCell) => {
      return cell.root;
    });
    console.log(vertices);
    console.log(roots);

    let graphNodes = [];
    for (let root of roots) {
      graphNodes.push({id: root.id, name: root.value, type:'root'});
    }

    for (let node of graphNodes) {
      
    }

    


  }

  addChildrenToGraphNode() {

  }






  itemSelected(event: any, c: Configurator) {

  }







}


