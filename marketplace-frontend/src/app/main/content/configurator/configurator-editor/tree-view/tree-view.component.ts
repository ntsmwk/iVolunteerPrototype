import { Component, OnInit, Input, RootRenderer, DoCheck } from '@angular/core';
import { Configurator } from 'app/main/content/_model/meta/Configurator';
import { ConfiguratorEditorComponent, myMxCell } from '../configurator-editor.component';
import { isNullOrUndefined } from 'util';
import { NestedTreeControl } from '@angular/cdk/tree';
import { MatTreeNestedDataSource } from '@angular/material';
import { ChildActivationEnd } from '@angular/router';

export interface GraphNode {
  id: string;
  name: string;
  type: string;
  cell: myMxCell;
  visible: boolean;

  children?: GraphNode[];
}


@Component({
  selector: 'editor-tree-view',
  templateUrl: './tree-view.component.html',
  styleUrls: ['./tree-view.component.scss']
})
export class EditorTreeViewComponent implements OnInit, DoCheck {

  @Input() editorInstance: ConfiguratorEditorComponent;
  oldEditorInstance: ConfiguratorEditorComponent;

  treeControl = new NestedTreeControl<GraphNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<GraphNode>();

  constructor() {

  }

  ngOnInit() {

    this.oldEditorInstance = Object.assign({}, this.editorInstance);
    this.constructJsonFromGraph();

  }




  ngDoCheck() {

    // if (this.oldEditorInstance.graph.getAllEdges(this.oldEditorInstance.graph.getDefaultParent()) !== this.editorInstance.graph.getAllEdges(this.editorInstance.graph.getDefaultParent())) {
    if (this.editorInstance.modelUpdated) {
      this.constructJsonFromGraph();
      this.editorInstance.modelUpdated = false;
    } 

    this.oldEditorInstance = Object.assign({}, this.editorInstance);

  }

  //TODO
  constructJsonFromGraph() {
    let vertices: myMxCell[] = this.editorInstance.graph.getChildVertices(this.editorInstance.graph.getDefaultParent()) as myMxCell[];
    let roots: myMxCell[] = vertices.filter((cell: myMxCell) => {
      return cell.root;
    }) as myMxCell[];


    let graphNodes: GraphNode[] = [];
    for (let root of roots) {
      let length = graphNodes.push({ id: root.id, name: root.value, type: 'root', cell: root, visible: root.isVisible() });
      graphNodes[length - 1].children = this.addChildrenToGraphNode(graphNodes[length - 1], vertices);
    }

    this.dataSource.data = graphNodes;
    this.treeControl.dataNodes = graphNodes;
    this.treeControl.expandAll();
    // console.log(this.treeControl.dataNodes);
    // this.treeControl.expandAll();

  }



  private addChildrenToGraphNode(node: GraphNode, vertices: myMxCell[]): GraphNode[] {
    let edges: myMxCell[] = this.editorInstance.graph.getEdges(node.cell, undefined, false, true) as myMxCell[];

    if (!isNullOrUndefined(edges)) {
      node.children = [];
      for (let edge of edges) {
        let child = vertices.find((cell: myMxCell) => {
          return cell.id == edge.target.id;
        });
        if (!isNullOrUndefined(child)) {
          let length = node.children.push({ id: child.id, name: child.value, type: 'node', cell: child, visible: child.isVisible()});
          node.children[length - 1].children = this.addChildrenToGraphNode(node.children[length - 1], vertices);
        }
      }
    }
    return node.children;
  }


  ngOnChanges() {
  }

  hideSidebar() {
    this.editorInstance.rightSidebarContainer.nativeElement.style.background = 'rgba(214, 239, 249, 0.0)';
    this.editorInstance.rightSidebarVisible = false;
    this.editorInstance.rightSidebarContainer.nativeElement.style.borderLeft = "none";
    this.editorInstance.rightSidebarContainer.nativeElement.style.height = "50px";
    
  }





  itemSelected(event: any, c: Configurator) {

  }

  hasChild = (_: number, node: GraphNode) => !!node.children && node.children.length > 0;








}


