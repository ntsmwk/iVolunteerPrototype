import { Component, OnInit, Input, DoCheck } from '@angular/core';
import { isNullOrUndefined } from 'util';
import { NestedTreeControl } from '@angular/cdk/tree';
import { MatTreeNestedDataSource } from '@angular/material';
import { MyMxCell } from '../../myMxCell';
import { ClassConfiguratorComponent } from '../class-configurator.component';

export interface GraphNode {
  id: string;
  name: string;
  type: string;
  cell: MyMxCell;
  visible: boolean;
  children?: GraphNode[];
}

@Component({
  selector: 'editor-tree-view',
  templateUrl: './tree-view.component.html',
  styleUrls: ['./tree-view.component.scss']
})
export class EditorTreeViewComponent implements OnInit, DoCheck {

  @Input() editorInstance: ClassConfiguratorComponent;
  oldEditorInstance: ClassConfiguratorComponent;

  treeControl = new NestedTreeControl<GraphNode>(node => node.children);
  dataSource = new MatTreeNestedDataSource<GraphNode>();

  constructor() {

  }

  ngOnInit() {
    this.oldEditorInstance = Object.assign({}, this.editorInstance);
    this.constructJsonFromGraph();
  }

  ngDoCheck() {
    if (this.editorInstance.modelUpdated) {
      this.constructJsonFromGraph();
      this.editorInstance.modelUpdated = false;
    }

    this.oldEditorInstance = Object.assign({}, this.editorInstance);
  }

  // TODO
  constructJsonFromGraph() {
    const vertices: MyMxCell[] = this.editorInstance.graph.getChildVertices(this.editorInstance.graph.getDefaultParent()) as MyMxCell[];
    const roots: MyMxCell[] = vertices.filter((cell: MyMxCell) => {
      return cell.root;
    }) as MyMxCell[];

    const graphNodes: GraphNode[] = [];
    for (const root of roots) {
      const length = graphNodes.push({ id: root.id, name: root.value, type: 'root', cell: root, visible: root.isVisible() });
      graphNodes[length - 1].children = this.addChildrenToGraphNode(graphNodes[length - 1], vertices);
    }

    this.dataSource.data = graphNodes;
    this.treeControl.dataNodes = graphNodes;
    this.treeControl.expandAll();
  }

  private addChildrenToGraphNode(node: GraphNode, vertices: MyMxCell[]): GraphNode[] {
    const edges: MyMxCell[] = this.editorInstance.graph.getEdges(node.cell, undefined, false, true) as MyMxCell[];

    if (!isNullOrUndefined(edges)) {
      node.children = [];
      for (const edge of edges) {
        const child = vertices.find((cell: MyMxCell) => {
          return cell.id === edge.target.id;
        });
        if (!isNullOrUndefined(child)) {
          const length = node.children.push({ id: child.id, name: child.value, type: 'node', cell: child, visible: child.isVisible() });
          node.children[length - 1].children = this.addChildrenToGraphNode(node.children[length - 1], vertices);
        }
      }
    }
    return node.children;
  }


  ngOnChanges() {
  }

  hasChild = (_: number, node: GraphNode) => !!node.children && node.children.length > 0;








}


